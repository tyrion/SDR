package sdr;

import java.io.IOException;
import java.io.PrintStream;

public class Homework3 {

	public static void main(String[] args) {
		try (Console console = new Console();
				Input real = new Input(console.getInput("File di input (parte reale): ", null));
				Input imag = new Input(console.getInput("File di input (parte immaginaria): ", null));
				PrintStream output = console.getOutput("File di output [stdout]: ", System.out)) {

			Homework3 hw = new Homework3(console, real, imag);
			for (double sample : hw.homework3())
				output.println(sample);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SDRException e) {
			System.err.println(e.getMessage());
		}
	}

	private int f1, f2, bandlimit, gcd, e1, d1, d2;
	private boolean limit;
	private Iterable<Double> real;
	private Iterable<Double> imag;
	private Console console;

	public Homework3() {};

	public Homework3(Console console, Iterable<Double> real, Iterable<Double> imag) {
		this.console = console;
		this.real = real;
		this.imag = imag;
	}
	
	public Iterable<Double> homework3() throws IOException {
		
		boolean hw2 = console.getBool("Eseguire anche Homework2? [S/n]: ", true);
		boolean prima = console.getBool("Eseguire la Modulazione prima? [S/n]: ", true);
		
		Iterable<Double> out;
		if (hw2) {
			getParams();
			if (prima)
				out = homework2(new Demodulazione(real, imag));
			else
				out = new Demodulazione(homework2(real), homework2(imag));
		} else
			out = new Demodulazione(real, imag);

		return out;
	}

	public void getParams() {
		f2 = console.getInt("Input sampling rate [1000000]: ", 1000000);
		f1 = console.getInt("Output sampling rate [70000]: ", 70000);

		limit = console.getBool("Limita espansione alla frequenza originale? [S/n]: ", true);
		//boolean limit = ! "n".equals(scanner.next().toLowerCase());

		bandlimit = console.getInt(
				String.format("Larghezza di banda del segnale [%d]: ", f1 / 2), f1 / 2);

		gcd = GCD(f1, f2);

		e1 = f1 / gcd;
		d2 = f2 / gcd;

		System.out.printf("F1/F2 => %d/%d", e1, d2);
		System.out.println();

		
		if (e1 > 1) {
			// Cerchiamo un fattore di decimazione d1 che ci permetta di decimare del massimo
			// possibile rispettando il teorema del campionamento di Nyquist.
			d1 = d2;
			try {
				while (!(d2 % d1 == 0 && f2 / d1 >= bandlimit * 2))	d1--;
			} catch (ArithmeticException e) {
				throw new SDRException("Impossible rispettare il teorema di Nyquist.");
			}
			d2 /= d1;
			if (limit && e1 > d1) {
				throw new SDRException("Impossible trovare d1 che rispetti sia il limite di espansione che il teorema di Nyquist.");
			}
			System.out.println("Decimo con d1 = " + d1);
			System.out.println("Espando con e1 = " + e1);
		}
		System.out.println("Decimo con d2 = " + d2);
	}
	
	public Iterable<Double> homework2(Iterable<Double> input) throws IOException {
		
		Iterable<Double> xs = input;
		if (e1 > 1) {
			Iterable<Double> decimazione = new Decimazione(input, d1);
			xs = new Espansione(decimazione, e1 - 1);
		}

		Iterable<Double> ys = new Sinc(e1);
		int maxlen = 10 * e1 + 1;

		Iterable<Double> convoluzione = new Convoluzione(xs, ys, maxlen);
		Iterable<Double> decimazione = new Decimazione(convoluzione, d2);

		return decimazione;
	}

	public static int GCD(int a, int b) { return b == 0 ? a : GCD(b, a % b); }
}
