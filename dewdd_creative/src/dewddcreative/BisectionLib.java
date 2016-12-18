package dewddcreative;

public class BisectionLib {

	private double start = 0;
	private double end = 10;
	private double mid = 0;

	private double valueOfStart = 0;
	private double valueOfEnd = 0;


	public static double getVa(double input) {
		//return 1 / ( input+1);
		//return input+1;
		//return 1 / input;
		return (input)*2;
	}

	public static void main(String[] abc) {
		double start = -40;
		double end = 20;


		double l = 0-getVa(start);
		double r = 0-getVa(end);
		
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


		this.mid = (start + end) / 2;

	}
	
	

	public double getMid() {
		this.mid = (start + end) / 2;
		
		return mid;
	}

	public double findNextValue(double valueOfMid) {

		if (valueOfMid == 0 || Math.abs(0 - valueOfMid) < 0.001) {
			return mid;
		}

		// if not have to adjust

		// target 7
		// 2 5 30
		// 1 2 3 4 5 6 7 8 9 10

		getMid();

		// double l = Math.abs(valueOfStart - valueOfMid );
		// double r = Math.abs(valueOfEnd - mid );
		
	//	System.out.println("index =  " +  start + " , " + mid +  " , " + end );

	//	System.out.println("value =  " +  valueOfStart + " , " + valueOfMid +  " , " + valueOfEnd );

		if (Math.abs(valueOfStart) > Math.abs(valueOfEnd)) { // adjust another
			
	//		System.out.println("adjust left ");

		//		System.out.println("cuz left is - ");
				
				valueOfStart = valueOfMid;
				start = getMid();

				return getMid();

		} else {
		//	System.out.println("adjust right");

			//	System.out.println("cuz right is + ");
				
				valueOfEnd = valueOfMid;
				end = getMid();

				return getMid();
			
		}

	}

}
