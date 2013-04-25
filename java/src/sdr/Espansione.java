package sdr;

import java.util.Iterator;

public class Espansione implements Iterable<Double>, Iterator<Double> {

	private Iterator<Double> xs;
	private int f, counter = 0;

	public Espansione(Iterable<Double> xs, int f) {
		this.xs = xs.iterator();
		this.f = f;
	}
	
	@Override
	public boolean hasNext() {
		return counter != 0 || xs.hasNext();
	}

	@Override
	public Double next() {
		if (counter == 0) {
			counter = f;
			return xs.next();
		} else {
			counter--;
			return 0.;
		}
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
