package ca.utoronto.utm.floatingpoint;

public class q2 {
	public static void main(String[] args) {
		q2 p = new q2();
		System.out.println(p.solve711());
		System.out.println(p.solve(7.92));
	}
	//We represent 7.11$ in cents, 711 so that we don't have to deal with decimals
	public String solve711() {
		//we cast the integers as double instead of int so that when we divide by 100 in the last step we get a decimal value.
		//Also, doubles are more precise than floats hence it's less likely for rounding errors to occur.
		double a, b, c, d;
	    for(a=1;a<711;a++) {
	        for(b=1;b<(711-a);b++) {
	            for(c=1;c<(711-a-b);c++) {
	                d = 711-b-c-a;
	                if((a*b*c*d==711000000 && a+b+c+d==711)){
							return (a/100 + " " + b/100 + " " + c/100 + " " + d/100);// here we convert the values back to dollars instead of cents.
							}
					}
				}
			}
		return "";
	}
	
	public String solve(double target) {
		//we cast the integers as double instead of int so that when we divide by 100 in the last step we get a decimal value.
		//Also, doubles are more precise than floats hence it's less likely for rounding errors to occur.
		
		//This function allows us to solve it for any target number, not just 7.11
		double a, b, c, d;
		int target1 = (int) (target*100);
	    for(a=1;a<target1;a++) {
	        for(b=1;b<(target1-a);b++) {
	            for(c=1;c<(target1-a-b);c++) {
	                d = target1-b-c-a;
	                if((a*b*c*d==target1*1000000 && a+b+c+d==target1)){
							return (a/100 + " " + b/100 + " " + c/100 + " " + d/100);// here we convert the values back to dollars instead of cents.
							}
					}
				}
			}
		return "";
	}
}
