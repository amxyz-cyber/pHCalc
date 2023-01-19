package blue.macroLab.mycmd.phcalc.calc;

/** MyDataPoint Model Object.
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyDataPoint {
		private double x;
		private double y;
		
		public MyDataPoint(double xVal, double yVal) {
			this.x = xVal;
			this.y = yVal;
		}
		
		public MyDataPoint() {
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public void setX(double x) {
			this.x = x;
		}

		public void setY(double y) {
			this.y = y;
		}
		
}
