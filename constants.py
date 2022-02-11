# ============ FIRST ALGORITHM ============
class Circles(object):
    test_img = "cargo1.jpeg"
    rescale_size = 0.4
    circles_dp = 2.2
    circles_minDist = 180
    circles_param1 = 75
    circles_param2 = 90
    circles_minRadius = 10
    circles_maxRadius = 500
    circle_color = (150, 55, 0)
    rectangle_color = (150, 55, 0)
    green = (77, 199, 44)
    frame_width = 640
    frame_height = 480


class TrackCircles(object):
    min_blue_HSV = (76, 73, 29)
    max_blue_HSV = (134, 255, 255)

    min_redx_HSV = (0, 70, 50)
    max_redx_HSV = (10, 255, 255)
    min_redy_HSV = (160, 33, 45)
    max_redy_HSV = (180, 255, 255)

    frame_width = 600
