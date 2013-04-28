package sdr;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Convoluzione implements Iterable<Double>, Iterator<Double> {
	
	private Iterator<Double> xs;
	private Iterator<Double> ys;
	private ArrayDeque<Double> x_deque;
	private ArrayDeque<Double> y_deque;
	private int counter;

	public Convoluzione(Iterable<Double> xs, Iterable<Double> ys, int maxlen) {
		this.xs = xs.iterator();
		this.ys = ys.iterator();
		this.x_deque = new ArrayDeque<>(maxlen);
		this.y_deque = new ArrayDeque<>(maxlen);
		this.counter = (maxlen - 1) / 2;
		
		saltaCodaIniziale();
	}
	
	private void saltaCodaIniziale() {
		for (int t = counter; t != 0; t--) {
			if (ys.hasNext() && xs.hasNext()) {
				x_deque.addLast(xs.next());
				y_deque.addFirst(ys.next());
			}
		}
	}

	@Override
	public boolean hasNext() {
		return xs.hasNext() || counter > 0;
	}

	@Override
	public Double next() {
		if (ys.hasNext()) {
			if (xs.hasNext()) {
				x_deque.addLast(xs.next());
				y_deque.addFirst(ys.next());
				return sommaProdotti(x_deque, y_deque);
				
			} else throw new NoSuchElementException("Il segnale è più corto della Sinc");

		} else if (xs.hasNext()) {
			x_deque.pollFirst();
			x_deque.addLast(xs.next());
			return sommaProdotti(x_deque, y_deque);
	
		
		} else if (counter > 0) {
			counter--;
			x_deque.pollFirst();
			return sommaProdotti(x_deque, y_deque);
		}
		throw new NoSuchElementException();
	}

	private static Double sommaProdotti(Iterable<Double> xs, Iterable<Double> ys) {
		// Non esiste "zip" in Java :'(
		Iterator<Double> iter_x = xs.iterator();
		Iterator<Double> iter_y = ys.iterator();
		double somma = 0.;
		while (iter_x.hasNext() && iter_y.hasNext())
			somma += iter_x.next() * iter_y.next();
		return somma;
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
