package dewddcreative;

public class BisectionLib {

	private double start = 0;
	private double end = 10;
	private double mid = 0;

	private double valueOfStart = 0;
	private double valueOfEnd = 0;


	public static double getVa(double input) {
		//return 1 / ( input+1);
		
		// bisection is not compatible with multi root
		
		return  Math.sqrt(input);
		
		//return -input;
		//return input+1;
		//return (input)*2;
	}

	public static void main(String[] abc) {
		double start = 0;
		double end =20;


		double l = getVa(start);
		double r = getVa(end);
		
		System.out.println("value of left , right " + l  + " , " + r );

		BisectionLib bl = new BisectionLib(start, end, l, r);

		for (int i = 0; i < 20; i++) {
			double mid = bl.getMid();
			double midVa = getVa(mid);
			

			System.out.println(i + " >> " + mid + " = value " + midVa + " , next " + bl.findNextValue(midVa));
			System.out.println("------------------------");
			
			if (midVa == 0 || Math.abs(0-midVa ) < 0.001) {
				break;
			}

		}

	}

	public BisectionLib(double start, double end, double valueOfStart, double valueOfEnd) {
		this.start = start;
		this.end = end;

	
		
		this.valueOfStart = valueOfStart;
		this.valueOfEnd = valueOfEnd;


		this.mid = getMid();

	}
	
	

	public double getMid() {
		this.mid = (start + end) / 2;
		
		return mid;
	}

	public double findNextValue(double valueOfMid) {
		if (valueOfStart * valueOfEnd > 0) {
			System.out.println("Bisection error seem sign !!");
			return 0;
		}
		

		if (valueOfMid == 0 || Math.abs(0 - valueOfMid) < 0.0001) {
			return mid;
		}

		// if not have to adjust

		// target 7
		// 2 5 30
		// 1 2 3 4 5 6 7 8 9 10

		getMid();

		// double l = Math.abs(valueOfStart - valueOfMid );
		// double r = Math.abs(valueOfEnd - mid );
		
		System.out.println("index =  " +  start + " , " + mid +  " , " + end );

		System.out.println("value =  " +  valueOfStart + " , " + valueOfMid +  " , " + valueOfEnd );

		
		
		System.out.println("><><>  " + valueOfStart + " * " + valueOfStart + " = " + (valueOfStart  * valueOfMid));
		
		if ((0-valueOfStart)  * (0-valueOfMid )< 0) { // sign is diffeent
	
			
			// - , + , +
			// +  , - , -
				
				System.out.println("adjust right");

				
				valueOfEnd = valueOfMid;
				end = getMid();

				return getMid();
				
		} else {
			System.out.println("adjust left");

				
			valueOfStart = valueOfMid;
				start = getMid();

				return getMid();
		}

	}

}
