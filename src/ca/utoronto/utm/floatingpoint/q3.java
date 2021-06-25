package ca.utoronto.utm.floatingpoint;

public class q3 {
	// See https://docs.oracle.com/javase/8/docs/api/java/lang/Float.html
	// as well as the lecture notes.txt for Week11.
	
	/**
	 * Complete the code below, so that when executed, the output should exactly match 
	 * IEEE754SingleOut.txt included in this project. Do not modify main in any way. 
	 * Implement the methods below so that they perform as expected. You can add additional
	 * static constants as well as static helper methods if it helps.
	 */
	public static void main(String[] args) {
		System.out.println("0 to 10");
		for (float i = 0.0f; i <= 10.0f; i++) {
			System.out.println(binRep(i));
		}
		System.out.println("misc");
		System.out.println(binRep(-6.8f));
		System.out.println(binRep(23.1f));
		System.out.println(binRep(14.625f));
		System.out.println(binRep(.1f));
		System.out.println(binRep(5.75f));
		System.out.println(binRep(1.0f / 3.0f));

		System.out.println("Machine Epsilon");
		float me = machineEpsilon();
		System.out.println("Machine Epsilon = " + binRep(me));
		System.out.println("1+machine epsilon = " + binRep(1.0f + me));
		System.out.println("Underflow");
		System.out.println("Underflow = " + binRep(underflow()));

		// System.out.println("rounds by");
		System.out.println("Overflow");
		System.out.println("Overflow = " + binRep(overflow()));
		System.out.println("MAX_VALUE = " + binRep(Float.MAX_VALUE));
	}
	/**
	 * Search for machine epsilon (eps), that is, the smallest
	 * float such that 1+eps>1. 
	 * Print out progress along the way.
	 * 
	 * @return machine epsilon
	 */
	public static float machineEpsilon() {
		// FIX THIS CODE
		float one = 1.0f, me = 1.0f, meNew = 1.0f;
		System.out.println(binRep(one + meNew));
		while (one + meNew > one) {
			meNew = meNew/2;
			System.out.println(binRep(one + meNew));
		} 
		me = meNew*2;
		return (me);
		
	}

	/**
	 * Search for underflow, that is the smallest float
	 * number that is greater than 0. 
	 * Print out progress along the way.
	 * @return underflow
	 */
	public static float underflow() {
		// FIX THIS CODE
		float ufl = 1.0f, uflNew = 1.0f;
		System.out.println(binRep(ufl));
		while (ufl > 0) {
			if (ufl/2 == 0) {
				uflNew = ufl;
			}
			ufl = ufl/2;
			System.out.println(binRep(ufl));
		}
		return uflNew;
	}

	/**
	 * Search for overflow, the largest float, 
	 * by first finding the largest exponent, and
	 * then finding the largest mantissa. 
	 * Print out progress along the way.
	 * @return overflow
	 */
	
	public static float overflow() {
		// FIX THIS CODE
		/*
		 * Algorithm: First find the maximum exponent and then the mantissa.
		 */
		System.out.println("Maximum Exponent");
		float ofl = 1.0f, oflNew = 1.0f;
		System.out.println(binRep(ofl));
		while ((Integer.parseInt(binRep(ofl).substring(2, 10),2)-127) != Float.MAX_EXPONENT) {
			ofl=ofl*2;
			System.out.println(binRep(ofl));
		}
		/*
		 * Add more (lower order) bits to the mantissa. We rely on round to even here to
		 * stop us.
		 */
		System.out.println("Maximum Mantissa");
		oflNew = ofl;
		float add = 0.5f;
		float sum = 1;
		int exponent = (Integer.parseInt(binRep(ofl).substring(2, 10),2)-127);
		System.out.println(binRep(ofl));
		//System.out.println((Integer.parseInt(binRep(ofl).substring(11, 34),2)));
		//System.out.println(binRep(machineEpsilon()));
		while (ofl<Float.POSITIVE_INFINITY) {
			ofl = (float) ((sum+add)*Math.pow(2, exponent));
			if (ofl<Float.POSITIVE_INFINITY) {
				oflNew = ofl;
				System.out.println(binRep(oflNew));
			}
			sum+=add;
			add=add/2;
		}
		return oflNew;
	}

	/**
	 * Take apart a floating point number and return a string representation of it.
	 * @param d the floating point number to investigate
	 * @return By example, this method returns strings like...
	 * 
	 * binRep(0.0f) returns "0[00000000]00000000000000000000000=+0.00000000000000000000000x2^(0)=0.0"
	 * binRep(1.0f) returns "0[01111111]00000000000000000000000=+1.00000000000000000000000x2^(0)=1.0"
	 * binRep(2.0f) returns "0[10000000]00000000000000000000000=+1.00000000000000000000000x2^(1)=2.0"
	 * binRep(14.625f) returns "0[10000010]11010100000000000000000=+1.11010100000000000000000x2^(3)=14.625"
	 * binRep(0.1f) returns "0[01111011]10011001100110011001101=+1.10011001100110011001101x2^(-4)=0.1"
	 */
	// Return information about the representation of floating point number d
	public static String binRep(float d) {
		// FIX THIS CODE
		/*
		 * See Float.floatToRawIntBits
		 */
		int sign = 0;
		int exponent = 1; 
		int mantissa = 0;
		String sSign = "";
		String sExponent = "";
		String sMantissa = "";
		int trueExponent=0;
		String decimalPoint;
		
		if (d<0) {
			sSign = "1";
		}
		else {
			sSign="0";
		}
		int l = Float.floatToRawIntBits(d); // Use this to pull bits of d
		String r = Integer.toBinaryString(l);
		
		while(r.length()<32) {
			r="0"+r;
			
		}
		sExponent =r.substring(1, 9);
		sMantissa = r.substring(9);
		//System.out.println(sExponent.length());
		String s = sSign + "[" + sExponent + "]" + sMantissa;
		
		String t = (sign == 0) ? "+" : "-";
		exponent = Integer.parseInt(sExponent,2);
		mantissa = Integer.parseInt(sMantissa,2);
		if (exponent==0 && mantissa==0 ) {
			trueExponent=0;
			decimalPoint = "0.";
			
		}
		else if (exponent==0) {
			trueExponent=-126;
			decimalPoint="1.";
		}
		else {
			trueExponent = exponent-127;
			decimalPoint = "1.";
		} 
		
		t = t +decimalPoint+ sMantissa + "x2^(" + trueExponent + ")";
		return (s + "=" + t + "="+ + d);
	}
}