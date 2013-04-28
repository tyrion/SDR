package sdr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Homework1 {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Numero errato di argomenti!");
			System.out.println("--- java SDR F1 F2 INPUTFILE");
			return;
		}
		
		int f1 = Integer.parseInt(args[0]);
		int f2 = Integer.parseInt(args[1]);
		int gcd = GCD(f1, f2);
		
		f1 /= gcd;
		f2 /= gcd;
		
		System.out.printf("F1/F2 => %d/%d", f1, f2);
		System.out.println();
		
		Iterable<Double> ys = new Sinc(f1);
		int maxlen = 10 * f1 + 1;
		
		System.out.println("Uso la Sinc:");
		for (Double y : ys)
			System.out.println(y);
		System.out.println("======================");
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(args[2]));
			
			Iterable<Double> input = new Input(scanner);
			Iterable<Double> espansione = new Espansione(input, f1 - 1);
			Iterable<Double> convoluzione = new Convoluzione(espansione, ys, maxlen);
			Iterable<Double> decimazione = new Decimazione(convoluzione, f2);
			
			for (Double out : decimazione)
				System.out.println(out);
			
		} catch (FileNotFoundException e) {
			System.out.println("File non esistente: " + args[2]);
			return;
		} finally {
			if (scanner != null) scanner.close();
		}
	}
	
	public static int GCD(int a, int b) { return b == 0 ? a : GCD(b, a % b); }

}
