package sdr;

import java.util.Iterator;

public class Sinc implements Iterable<Double> {
	
	private int f;

	public Sinc(int f) {
		this.f = f;
	}

	@Override
	public Iterator<Double> iterator() {
		return new Iterator<Double>() {
			private int current = -5 * f;
			
			@Override
			public boolean hasNext() {
				return current <= 5 * f;
			}

			@Override
			public Double next() {
				double x = Math.PI * current / f;
				current++;
				return x == 0 ? 1 : Math.sin(x) / x;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}					
		};
	}

}
