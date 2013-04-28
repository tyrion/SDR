package sdr;

import java.util.Iterator;

public class Sinc implements Iterable<Double> {
	
	private int f;

	public Sinc(int f) {
		this.f = f;
	}
	
	public double getValue(int n) {
		if (n == 0) return 1.0;
		if (n % f == 0) return 0.0;
		double x =  Math.PI * n / f;
		return Math.sin(x) / x;
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
				return getValue(current++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}					
		};
	}

}
