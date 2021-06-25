package ca.utoronto.utm.floatingpoint;

public class q1 {
	public static void main(String[] args) {
		q1 p = new q1();
		System.out.println(p.solve711());
	}
	
	/**
	 * ANSWER GOES HERE!!
	 * .01 has an infintie representation 0.000110011... so you are unable to store an accurate
	 * value of a number with infinite binary representation, which is why we'll never get such an
	 * precise/accurate value 7.11 thus causing a floating point rounding error.
	 *  (because it repeats digits in binary)
	 * @return
	 */
	public String solve711() {
		float a, b, c, d;
		for (a = 0.00f; a < 7.11f; a = a + .01f) {
			for (b = 0.00f; b < 7.11f; b = b + .01f) {
				for (c = 0.00f; c < 7.11f; c = c + .01f) {
					for (d = 0.00f; d < 7.11f; d = d + .01f) {
						System.out.println(d);
						if (a * b * c * d == 7.11f && a + b + c + d == 7.11f) {
							return (a + " " + b + " " + c + " " + d);
						}
					}
				}
			}
		}
		return "";
	}
}
