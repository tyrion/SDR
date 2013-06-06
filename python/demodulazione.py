from itertools import izip
import math

def dati():
    for r, i in izip(open('dati_re.txt'), open('dati_im.txt')):
        yield complex(float(r), float(i))

def proc():
    input = dati()
    j = next(input)
    for i in input:
        x = i * j.conjugate()
        yield math.atan2(x.imag, x.real)
        j = i

if __name__ == '__main__':
    for x in proc():
        print(x)
