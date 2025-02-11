from collections import deque

from _pynetworktables import NetworkTablesInstance
from imutils.video.pivideostream import PiVideoStream
from imutils.video import FPS
from imutils.video import VideoStream
from picamera.array import PiRGBArray
from picamera import PiCamera
from networktables import NetworkTables
import logging
import constants
import numpy as np
import math
import cv2
import imutils
import time

# network tables
# cond = threading.Condition()
notified = [False]

def connection_listener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()

def init_network_tables():
    ip= '10.56.55.2'
    logging.basicConfig(level=logging.DEBUG)
    NetworkTables.initialize(server=ip)
    NetworkTables.addConnectionListener(connection_listener, immediateNotify=True)
    with cond:
        print("Waiting")
        if not notified[0]:
            cond.wait()
    print("Connected!")

def calculate_yaw_angle(adjacent, opposite):
    ratioA = 36/140
    ratioO = 66/50

    radian = math.atan((opposite/ratioO)/(adjacent/ratioA))
    yaw_angle = radian * (180/math.pi)
    return yaw_angle

# defining variables
x_aligned = False
y_aligned = False
font = cv2.FONT_HERSHEY_SIMPLEX
blueLower = constants.TrackCircles.min_blue_HSV
blueUpper = constants.TrackCircles.max_blue_HSV
redLower_x = constants.TrackCircles.min_redx_HSV
redUpper_x = constants.TrackCircles.max_redx_HSV
redLower_y = constants.TrackCircles.min_redy_HSV
redUpper_y = constants.TrackCircles.max_redy_HSV

# network tables
team_color = 'blue'
# init_network_tables()
dashboard = NetworkTables.getTable('SmartDashboard')
# team_color = dashboard.getString('team_color')
# dashboard.putNumber('ball_yaw_angle', 0)
dashboard.putBoolean('target_ball', False)

# camera
camera = PiCamera()
camera.resolution = (320, 240)
camera.framerate = 32
rawCapture = PiRGBArray(camera, size=(320, 240))

time.sleep(2.0)
fps = FPS().start()
for frame in camera.capture_continuous(rawCapture, format="bgr", use_video_port=True):
    image = frame.array

    if image is None:
        break
    blurred = cv2.GaussianBlur(image, (11, 11), 0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    mask = None
    if team_color is 'blue':
        high_mask = cv2.inRange(hsv, blueLower, blueUpper)
        mask = cv2.erode(high_mask, None, iterations=4)
        mask = cv2.dilate(mask, None, iterations=4)
    if team_color is 'red':
        high_mask_x = cv2.inRange(hsv, redLower_x, redUpper_x)
        high_mask_y = cv2.inRange(hsv, redLower_y, redUpper_y)
        high_mask = high_mask_x | high_mask_y
        mask = cv2.erode(high_mask, None, iterations=4)
        mask = cv2.dilate(mask, None, iterations=4)
    blur = cv2.GaussianBlur(mask, (11, 11), 0)
    blur = cv2.Canny(blur, 300, 500, 3)
    circles = cv2.HoughCircles(blur,
                               cv2.HOUGH_GRADIENT,
                               constants.Circles.circles_dp,
                               constants.Circles.circles_minDist,
                               param1=int(constants.Circles.circles_param1),
                               param2=int(constants.Circles.circles_param2),
                               minRadius=int(constants.Circles.circles_minRadius),
                               maxRadius=int(constants.Circles.circles_maxRadius))

    target_circle = [0, 0, 0]
    if circles is not None:
        circles = np.round(circles[0, :]).astype("int")
        for (x, y, r) in circles:
            if target_circle[2] < r:
                target_circle = [x, y, r]
            cv2.circle(frame, (x, y), r, constants.Circles.circle_color, 4)
            cv2.rectangle(frame, (x - 5, y - 5), (x + 5, y + 5), constants.Circles.rectangle_color, -1)
        radius = target_circle[2]
        if 150 < target_circle[0] < 170 and 110 < target_circle[1] < 130:
            x_aligned = True
            cv2.putText(frame, 'x_aligned: ALIGNED', (10, 10), font, .5, (0, 0, 0), 1)
        else:
            x_aligned = False
            cv2.putText(frame, f'x_aligned: {160 - target_circle[0]}', (10, 10), font, .5, (0, 0, 0), 1)
            cv2.putText(frame, f'x_aligned: {160 - target_circle[0]}', (10, 10), font, .5, (0, 0, 0), 1)
            cv2.line(frame, (target_circle[0], target_circle[1]), 160, 120, (0, 0, 0), 2)
            # dashboard.putNumber("ball_yaw_angle", calculate_yaw_angle(radius, 160 - target_circle[0]))
            print(calculate_yaw_angle(radius, 160 - target_circle[0]))

    cv2.imshow("frame", frame)
    key = cv2.waitKey(1) & 0xFF
    if key == ord("q"):
        fps.stop()
        print(fps.fps())
        break
    fps.update()

cv2.destroyAllWindows()
    
