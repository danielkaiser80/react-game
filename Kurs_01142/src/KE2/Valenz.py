"""
Created on 03.03.2009

@author: daniel
"""
d = [10, 9, 8, 7, 6, 5, 4, 3, 3, 3, 2]
dn = [10, 9, 8, 7, 6, 5, 4, 3, 3, 3, 2]
print(d)
while len(d) > 0:
    d.sort()
    last = d.pop()
    if last > len(d):
        print("ist keine Valenzsequenz")
        break
    for i in range(len(d) - 1, len(d) - last - 1, -1):
        if d[i] > 0:
            d[i] = d[i] - 1
        else:
            print("ist keine Valenzsequenz")
            break
    if len(d) == 0:
        print("ist Valenzsequenz")
        print("Schritte:")
        while len(dn) > 0:
            print(dn)
            dn.sort()
            last = dn.pop()
            for i in range(len(dn) - 1, len(dn) - last - 1, -1):
                if dn[i] > 0:
                    dn[i] = dn[i] - 1
