package sdr;

import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class Input implements Iterable<Double>, Iterator<Double> {

	private Scanner scanner;

	public Input(Scanner scanner) {
		this.scanner = scanner;
		scanner.useLocale(Locale.US);
	}

	@Override
	public boolean hasNext() {
		return scanner.hasNextDouble();
	}

	@Override
	public Double next() {
		return scanner.nextDouble();
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
