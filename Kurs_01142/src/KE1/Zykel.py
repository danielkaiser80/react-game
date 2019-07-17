N = [1,2,3,4,5,6,7] 
sigma = [0,4,2,7,6,3,1,5]
for a in N:
    s = ''
    b = a 
    s = s + str(b)
    while sigma[b] != a: 
        b = sigma[b] 
        s = s + ' ' + str(b) 
        N.remove(b)
    print(s)