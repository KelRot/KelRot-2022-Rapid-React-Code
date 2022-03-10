from imutils.video import VideoStream
from imutils.video.pivideostream import PiVideoStream
from imutils.video import FPS
from collections import deque
from threading import Thread
from networktables import NetworkTables
from picamera.array import PiRGBArray
from picamera import PiCamera
import numpy as np
import threading
import logging
import cv2
import imutils
import time
import math

#cond = threading.Condition()
#notified = [False]

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

min_radius = 10
max_radius = 200
team_color = 'blue'
blue_lower = (97, 106, 43)
blue_upper = (137, 232, 212)

red_lower_x = (0, 70, 50)
red_upper_x = (10, 255, 255)
red_lower_y = (160, 33, 45)
red_upper_y = (180, 255, 255)

def mask_frame(color, hsv):
    mask = None
    if color == 'blue':
        mask = cv2.inRange(hsv, blue_lower, blue_upper)
        mask = cv2.erode(mask, None, iterations=5)
        mask = cv2.dilate(mask, None, iterations=6)
    else:
        mask_x = cv2.inRange(hsv, red_lower_x, red_upper_x)
        mask_y = cv2.inRange(hsv, red_lower_y, red_upper_y)
        mask = mask_x | mask_y
        mask = cv2.erode(mask, None, iterations=5)
        mask = cv2.dilate(mask, None, iterations=6)
    return mask

#    network tables
#init_network_tables()
#dashboard = NetworkTables.getTable('SmartDashboard')
#team_color = dashboard.getString('team_color')
#dashboard.putNumber('ball_yaw_angle', 0)
#dashboard.putNumber('target_ball', 0)

vs = PiVideoStream().start()
time.sleep(2)
fps = FPS().start()

while True:
    #read the frames
    frame = vs.read()

    #break if there's no input from the camera
    if frame is None:
        break
    
    #blur the image, then mask it according to team color
    blurred_frame = cv2.GaussianBlur(frame, (11, 11), 0)
    hsv = cv2.cvtColor(blurred_frame, cv2.COLOR_BGR2HSV)
    mask = mask_frame(team_color, hsv)

    #find the contours
    contours = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    contours = imutils.grab_contours(contours)
    center = None

    #check if at least 1 contour is found
    if len(contours) > 0:

        #detect the ball from contours
        c = max(contours, key=cv2.contourArea)
        ((x, y), radius) = cv2.minEnclosingCircle(c)
        M = cv2.moments(c)
        center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))

        #check if the ball radius is in between min and max values
        if radius > min_radius and radius < max_radius:

            #draw the contours
            cv2.drawContours(frame, contours, -1, (230, 150, 60), 3)
            cv2.circle(frame, (int(x), int(y)), int(radius), (255, 255, 255), 4)

            #send the values to the roborio
            #dashboard.putNumber("ball_yaw_angle", calculate_yaw_angle(radius, 160 - x))
            #dashboard.putNumber("target_ball", 1)
        else:
            print("no balls")
            #tell roborio that no ball is found
            #dashboard.putNumber("target_ball", 0)

    #show the results
    cv2.imshow("frame", frame)

    #exit the program when Q is pressed
    key = cv2.waitKey(1) & 0xFF
    if key == ord("q"):
        fps.stop()
        print(fps.fps())
        break

    #update fps at the end of every loop
    fps.update()

#stop the camera and destroy the windows
vs.stop()
cv2.destroyAllWindows() 
