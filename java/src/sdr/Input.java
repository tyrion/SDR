package sdr;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class Input implements Iterable<Double>, Iterator<Double>, Closeable {

	private Scanner scanner;

	public Input(Scanner scanner) {
		this.scanner = scanner;
		scanner.useLocale(Locale.US);
	}
	
	public Input(InputStream input) {
		this(new Scanner(input));
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

	@Override
	public void close() throws IOException {
		scanner.close();
	}

}
