import math

F = [1.0, 1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0]
k = 11


def left_fak(i):
    return F[k + 1 - i] / F[k + 3 - i]


def right_fak(i):
    return F[k + 2 - i] / F[k + 3 - i]


def f(x):
    return (math.atan(x)) ** 2


def find_min(a, b):
    for i in range(k + 1):
        x = a + left_fak(i) * (b - a)
        y = a + right_fak(i) * (b - a)
        fx = f(x)
        fy = f(y)

        print(i, "&", a, "&", b, "&", x, "&", y, "&", fx, "&", fy, "\\\\")
        if fx >= fy:
            a = x
        else:
            b = y
    return (a + b) / 2.0


print ("The minimum is: " + str(find_min(-72.0, 88.0)))
