from collections import deque

from _pynetworktables import NetworkTablesInstance
from imutils.video import WebcamVideoStream
from imutils.video import FPS
from imutils.video import VideoStream
from networktables import NetworkTables
import threading
import logging
import constants
import numpy as np
import argparse
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
    logging.basicConfig(level=logging.DEBUG)
    NetworkTables.initialize()
    NetworkTables.addConnectionListener(connection_listener, immediateNotify=True)
    with cond:
        print("Waiting")
        if not notified[0]:
            cond.wait()
    print("Connected!")

# screen reso
resolution = (320, 240)

def resize_image(img):
    resized = cv2.resize(img, resolution, interpolation=cv2.INTER_AREA)
    return resized


# argument parser
ap = argparse.ArgumentParser()
ap.add_argument("-n", "--num-frames", type=int, default=100, help="# of frames to loop over for FPS test")
args = vars(ap.parse_args())


# trackbar
def empty(x):
    pass

cv2.namedWindow('controls')
cv2.createTrackbar('dp', 'controls', int(constants.Circles.circles_dp * 10), 30, empty)
cv2.createTrackbar('minDist', 'controls', constants.Circles.circles_minDist, 200, empty)
cv2.createTrackbar('param1', 'controls', constants.Circles.circles_param1, 200, empty)
cv2.createTrackbar('param2', 'controls', constants.Circles.circles_param2, 200, empty)
cv2.createTrackbar('minRadius', 'controls', constants.Circles.circles_minRadius, 500, empty)
cv2.createTrackbar('maxRadius', 'controls', constants.Circles.circles_maxRadius, 1000, empty)

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
# dashboard = NetworkTables.getTable('SmartDashboard')
# team_color = dashboard.getString('team_color')
# dashboard.putNumber('ball_radius', 0)
# dashboard.putNumber('distance', 0)

vs = WebcamVideoStream(src=0).start()
# vs = cv2.VideoCapture(0)
fps = FPS().start()
while True:
    frame = vs.read()

    if frame is None:
        break
    frame = resize_image(frame)
    blurred = cv2.GaussianBlur(frame, (11, 11), 0)
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
                               int(cv2.getTrackbarPos('dp', 'controls')) / 10,
                               int(cv2.getTrackbarPos('minDist', 'controls')),
                               param1=int(cv2.getTrackbarPos('param1', 'controls')),
                               param2=int(cv2.getTrackbarPos('param2', 'controls')),
                               minRadius=int(cv2.getTrackbarPos('minRadius', 'controls')),
                               maxRadius=int(cv2.getTrackbarPos('maxRadius', 'controls')))

    print(circles)
    target_circle = [0, 0, 0]
    if circles is not None:
        circles = np.round(circles[0, :]).astype("int")

        for (x, y, r) in circles:
            if target_circle[2] < r:
                target_circle = [x, y, r]
            cv2.circle(frame, (x, y), r, constants.Circles.circle_color, 4)
            cv2.rectangle(frame, (x - 5, y - 5), (x + 5, y + 5), constants.Circles.rectangle_color, -1)

        radius = target_circle[2]
        # dashboard.putNumber("ball_radius", radius)

        if 150 < target_circle[0] < 170 and 110 < target_circle[1] < 130:
            x_aligned = True
            cv2.putText(frame, 'x_aligned: ALIGNED', (10, 10), font, .5, (0, 0, 0), 1)
        else:
            x_aligned = False
            cv2.putText(frame, f'x_aligned: {160 - target_circle[0]}', (10, 10), font, .5, (0, 0, 0), 1)
            cv2.line(frame, (target_circle[0], target_circle[1]), (int(resolution[0]/2), int(resolution[1]/2)), (0, 0, 0), 2)
            # dashboard.putNumber("distance", 320 - target_circle[0])

    cv2.imshow("frame", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        fps.stop()
        print(fps.fps())
        break
    fps.update()

vs.stop()
cv2.destroyAllWindows()

