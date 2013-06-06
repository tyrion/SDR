package sdr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.NoSuchElementException;


public class Console extends BufferedReader {


	public Console() {
		super(new InputStreamReader(System.in));
	}

	public interface Parser<T> {
		public T parse(String token) throws Exception;
	}

	public <T> T get(String msg, Parser<T> parser, T def) {
		do {
			System.out.print(msg);
			String line;
			try {
				line = readLine();
			} catch (IOException e) {
				e.printStackTrace();
				throw new NoSuchElementException();
			}
			try {
				return parser.parse(line);
			} catch (Exception e) {
				if ("".equals(line) && def != null) return def;
			}
		} while (true);
	}
	
	public int getInt(String msg, Integer def) {
		return get(msg, new Parser<Integer>() {
			public Integer parse(String token) {
				return Integer.parseInt(token);
			}
		}, def);
	}
	
	public int getInt(String msg) {
		return getInt(msg, null);
	}
	
	public boolean getBool(String msg, Boolean def) {
		return get(msg, new Parser<Boolean>() {
			public Boolean parse(String token) {
				switch (token.toLowerCase()) {
					case "n":
						return false;
					case "y":
						return true;
					default:
						throw new IllegalArgumentException();
				}
			}
		}, def);
	}
	
	public InputStream getInput(String msg, InputStream def) {
		return get(msg, new Parser<InputStream>() {
			public InputStream parse(String token) throws FileNotFoundException {
				return new FileInputStream(token);
			}
		}, def);
	}
	
	public PrintStream getOutput(String msg, PrintStream def) {
		return get(msg, new Parser<PrintStream>() {
			public PrintStream parse(String token) throws FileNotFoundException {
				return new PrintStream(token);
			}
		}, def);
	}

}
