package blue.macroLab.mycmd.phcalc.calc;

/** Calculates the first and second derivatives based on two data points
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class Derivative {
	private MyDataPoint point1;
	private MyDataPoint point2;
	private double value;
	
	public Derivative (MyDataPoint prev, MyDataPoint cur) {
		this.point2 = cur;
		this.point1 = prev;
	}
	
	/** Sets the y value of the first derivative
	*/	
	public void calcDerivative() {
		value = (point2.getY()-point1.getY()) / (point2.getX()-point1.getX());
	}
	
	/** Sets the y value of the first derivative
	 * @param fstNext The y value of the first derivative regarding the next data row
	 * @param fstCur The y value of the first derivative regarding the current data row
	 */
	public void calcDerivative(double fstNext,double fstCur) {
		 value = (fstNext - fstCur) / (point2.getX()-point1.getX()); 
	}
	
	/** Gets the y value of a derivative
	 * @return A double representing y value of one derivative
	*/
	public double getValue() {
		return value;
	}
	
	public MyDataPoint toPoint(double x) {
		return new MyDataPoint(x,value);
	}
}
