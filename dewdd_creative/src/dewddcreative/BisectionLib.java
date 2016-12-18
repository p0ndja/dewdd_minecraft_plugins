package dewddcreative;

public class BisectionLib {

	private double start = 0;
	private double end = 10;
	private double mid = 0;
	private double targetValue = 0;

	private double valueOfStart = 0;
	private double valueOfEnd = 0;

	private boolean leftIsLower = false;

	public static double getVa(double input) {
		return 1 / (input* 10);
	}

	public static void main(String[] abc) {
		double start = 0;
		double end = 10;

		double target = 0.5;

		double l = getVa(start);
		double r = getVa(end);

		BisectionLib bl = new BisectionLib(start, end, target, l, r);

		for (int i = 0; i < 20; i++) {
			double mid = bl.getMid();
			double midVa = getVa(mid);

			System.out.println(mid + " = value " + midVa + " , next " + bl.findNextValue(midVa));
			
			if (midVa == target || Math.abs(midVa - target ) < 0.001) {
				break;
			}

		}

	}

	public BisectionLib(double start, double end, double targetValue, double valueOfStart, double valueOfEnd) {
		this.start = start;
		this.end = end;
		this.targetValue = targetValue;

		this.valueOfStart = valueOfStart;
		this.valueOfEnd = valueOfEnd;

		if (valueOfStart <= valueOfEnd) {
			leftIsLower = true;
		} else {
			leftIsLower = false;
		}

		this.mid = (start + end) / 2;

	}

	public double getMid() {
		this.mid = (start + end) / 2;
		return mid;
	}

	public double findNextValue(double valueOfMid) {

		if (valueOfMid == 0 || Math.abs(targetValue - valueOfMid) < 0.001) {
			return mid;
		}

		// if not have to adjust

		// target 7
		// 2 5 30
		// 1 2 3 4 5 6 7 8 9 10

		getMid();

		// double l = Math.abs(valueOfStart - valueOfMid );
		// double r = Math.abs(valueOfEnd - mid );

		if (valueOfMid <= targetValue) { // adjust another

			if (leftIsLower == true) {
				valueOfStart = valueOfMid;
				start = getMid();

				return getMid();

			} else {
				valueOfEnd = valueOfMid;
				end = getMid();

				return getMid();
			}
		}

		if (valueOfMid >= targetValue) { // adjust another
			if (leftIsLower == true) {
				valueOfEnd = valueOfMid;
				end = getMid();

				return getMid();
			}
		} else {
			valueOfStart = valueOfMid;
			start = getMid();

			return getMid();
		}

		
		System.out.println("WTF Bisection");
		return 0;
	}
	
	
}
