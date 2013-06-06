package sdr;

public class Complex {

		public final double real;
		public final double imag;

		public Complex(double real, double imag) {
			this.real = real;
			this.imag = imag;
		}
		
		public Complex conjugate() {
			return new Complex(real, -imag);
		}
		
		public Complex mul(Complex c) {
			return new Complex(real * c.real - imag * c.imag,
					           imag * c.real + real * c.imag);
		}
}
