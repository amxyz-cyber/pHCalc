package blue.macroLab.mycmd.phcalc.calc;

import java.text.DecimalFormat;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.utils.Utility;


/** MyLine Model Object.
 * <P>Various attributes of lines
 * <P>MyLine has this format: <em>y = m*x + t</em>
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyLine {
	private double slope = 0;
	private double yIntercept = 0;
	
	public MyLine(double m, double t) {
		this.slope = m;
		this.yIntercept = t;
	}
	
	/** 
	 * Calculates x by equating two lines
	 * 
	 * @param line2 Second line that equates the line stored in this instance
	 * @return double X 
	 */
	public double solveX(MyLine line2) {
		double x = 0;
		double t = 0;
		x = slope + (line2.slope*-1);
		t = line2.yIntercept + (yIntercept*-1);
		x = t / x;
		return x; 
	}
	
	/** 
	 * Calculates x by plugging in an y value into the line
	 * 
	 * @param y Y value
	 * @return double X 
	 */
	public double solveX(double y) {
		double x =  (y- this.yIntercept)/this.slope;
		return x; 
	}
	
	/** 
	 * Calculates y by plugging in an x value into the line
	 * 
	 * @param a X value
	 * @return double Y 
	 */
	public double solveY(double a) {
		double y = 0;
		y = slope*a + yIntercept;
		return y; 
	}

	public double getSlope() {
		return slope;
	}

	public double getyIntercept() {
		return yIntercept;
	}
	
	public String toString() {
		String precision = Utility.repeat(Constants.HASH_TAG, 5);
		DecimalFormat df = new DecimalFormat(Constants.DECIMAL_FORMAT_1+precision);
		StringBuilder s = new StringBuilder("y = ");
		s.append(df.format(this.slope) + "·x" );
		if (this.yIntercept < 0) {
			s.append(Constants.SPACE+ "- "+ df.format(this.yIntercept*(-1)));
		} else {
			 s.append(Constants.SPACE+ "+ "+ df.format(this.yIntercept));
        }
        return s.toString();
	}
		
	/** 
	 * Finds out the line that goes through two data points
	 * 
	 * @param P First Data point
	 * @param Q Second Data point
	 * @return MyLine calculated line 
	 */
	public static MyLine lineFromPoints(MyDataPoint P, MyDataPoint Q) {
		double a = Q.getY() - P.getY();
		double b = P.getX() - Q.getX();
		double c = (a*P.getX()) + (b*P.getY());
		double a_y = (-1*a) / b; 
		double c_y = c / b;
		return new MyLine(a_y,c_y);
	}
}
