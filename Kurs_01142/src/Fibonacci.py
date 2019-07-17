F = [1.0, 1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0, 987.0]

k = 11


def leftfak(i):
    return F[k + 1 - i] / F[k + 3 - i]


def rightfak(i):
    return F[k + 2 - i] / F[k + 3 - i]


def f(x):
    return x ** 2 - x


def findmin(a, b):
    global fx, fy, x, y
    for i in range(k + 1):
        x = a + leftfak(i) * (b - a)
        y = a + rightfak(i) * (b - a)
        fx = f(x)
        fy = f(y)

        print i, "&", a, "&", b, "&", x, "&", y, "&", fx, "&", fy, "\\\\"
        if fx >= fy:
            a = x
            x = y
            fx = fy
        else:
            b = y
    if fx >= fy:
        a = x
    else:
        b = y
    return (a + b) / 2


print "The minimum is: " + str(findmin(-3.05, 3.05))
