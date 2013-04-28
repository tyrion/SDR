# f1 = 40000
# f2 = 1000000
# f1/f2
# sequenza
# espansione / interpolazione / decimazione

from fractions import Fraction
import collections
import itertools
import math


def espansione(xs, f):
    for i, sample in enumerate(xs):
        yield sample
        for i in range(f):
            yield 0

def convoluzione(xs, ys, len):

    def somma_prodotti(xs,ys):
        return sum(x*y for x,y in itertools.izip(xs,ys))

    x_iter = iter(xs)
    y_iter = iter(ys)
    x_window = collections.deque(maxlen=len)
    y_window = collections.deque(maxlen=len)
    
    # coda iniziale
    for x, y in itertools.izip(x_iter, y_iter):
        x_window.append(x)
        y_window.appendleft(y)
        yield somma_prodotti(x_window, y_window)

    # quando sono completamente sovrapposti
    for x in x_iter:
        x_window.append(x)
        yield somma_prodotti(x_window, y_window)

    # la coda finale
    for _ in range(len-1):
        x_window.popleft()
        yield somma_prodotti(x_window, y_window)


def decimazione(zs, f):
    return itertools.islice(zs, 0, None, f)

# a = [-1,0,1,0,-1,0]
# b = [0,1,0,-1,0,1]


def leggi_file(nome):
    for riga in open(nome):
        yield float(riga.strip())

if __name__ == '__main__':
    import sys

    f1 = int(sys.argv[1])
    f2 = int(sys.argv[2])
    f = Fraction(f1, f2)

    print "F1/F2 = %d/%d" % (f.numerator, f.denominator)

    dati = leggi_file(sys.argv[3])

    def sinc(n):
        x = math.pi*n/f.numerator
        return 1 if x == 0 else math.sin(x)/x

    s = 5 * f.numerator

    ys = map(sinc, range(-s, s+1))
    print "Uso la sinc: "
    for y in ys:
        print y
    print "================="

    step1 = espansione(dati, f.numerator -1)
    step2 = convoluzione(step1, ys, len(ys))
    step3 = decimazione(step2, f.denominator)

    for c in step2:
        print c
