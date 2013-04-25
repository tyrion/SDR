package sdr;

import java.util.Iterator;

public class Decimazione implements Iterable<Double>, Iterator<Double> {
	
	private Iterator<Double> zs;
	private int f;

	public Decimazione(Iterable<Double> zs, int f) {
		this.zs = zs.iterator();
		this.f = f;
	}

	@Override
	public boolean hasNext() {
		return zs.hasNext();
	}

	@Override
	public Double next() {
		Double campione = zs.next();
		for (int i = 1; i < f && zs.hasNext(); i++)
			zs.next();
		return campione;
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
