package sdr;

import java.util.Iterator;

public class Modulazione implements Iterable<Double>, Iterator<Double> {
	
	private Iterator<Double> real;
	private Iterator<Double> imag;
	private Complex j;

	public Modulazione(Iterable<Double> real, Iterable<Double> imag) {
		this.real = real.iterator();
		this.imag = imag.iterator();
		
		// l'iteratore deve avere almeno un elemento altrimenti c'è una exception.
		j = new Complex(this.real.next(), this.imag.next());
	}

	@Override
	public boolean hasNext() {
		return real.hasNext() && imag.hasNext();
	}

	@Override
	public Double next() {
		Complex i = new Complex(real.next(), imag.next());
		Complex x = i.mul(j.conjugate());
		j = i;
		return Math.atan2(x.imag, x.real);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Double> iterator() {
		return this;
	}

}
