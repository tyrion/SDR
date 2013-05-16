package sdr;

import java.io.IOException;
import java.io.PrintStream;;

public class Homework2 {

	public static void main(String[] args) {
		try (Console console = new Console();
			 Input input = new Input(console.getInput("File di input [stdin]: ", System.in));
			 PrintStream output = console.getOutput("File di output [stdout]: ", System.out)) {
			
			int f2 = console.getInt("Input sampling rate [1000000]: ", 1000000);
			int f1 = console.getInt("Output sampling rate [70000]: ", 70000);
			
			boolean limit = console.getBool("Limita espansione alla frequenza originale? [S/n]: ", true);
			//boolean limit = ! "n".equals(scanner.next().toLowerCase());
			
			int bandlimit = console.getInt(
				String.format("Larghezza di banda del segnale [%d]: ", f1 / 2), f1 / 2);
			
			int gcd = GCD(f1, f2);
			
			int e1 = f1 / gcd;
			int d2 = f2 / gcd;
			
			System.out.printf("F1/F2 => %d/%d", e1, d2);
			System.out.println();
		
			Iterable<Double> xs = input;
			if (e1 > 1) {
				// Cerchiamo un fattore di decimazione d1 che ci permetta di decimare del massimo
				// possibile rispettando il teorema del campionamento di Nyquist.
				int d1 = d2;
				try {
					while (!(d2 % d1 == 0 && f2 / d1 >= bandlimit * 2))	d1--;
				} catch (ArithmeticException e) {
					System.err.println("Impossible rispettare il teorema di Nyquist.");
					return;
				}
				d2 /= d1;
				Iterable<Double> decimazione = new Decimazione(input, d1);
				xs = new Espansione(decimazione, e1 - 1);
				System.out.println("Decimo con d1 = " + d1);
				System.out.println("Espando con e1 = " + e1);
				
				if (limit && e1 > d1) {
					System.err.println("Impossible trovare d1 che rispetti sia il limite di espansione che il teorema di Nyquist.");
					return;
				}
			}
			
			Iterable<Double> ys = new Sinc(e1);
			int maxlen = 10 * e1 + 1;
			
			Iterable<Double> convoluzione = new Convoluzione(xs, ys, maxlen);
			Iterable<Double> decimazione = new Decimazione(convoluzione, d2);
			
			System.out.println("Decimo con d2 = " + d2);
			
			for (double sample : decimazione)
				output.println(sample);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int GCD(int a, int b) { return b == 0 ? a : GCD(b, a % b); }


}
