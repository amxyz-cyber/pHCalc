package blue.macroLab.mycmd.phcalc.calc;

/** MyTitration Model Object.
 * <P>Various attributes of lines
 * <P>MyTitration stores the y values, i.e. pH value, first and second derivatives belonging to an x value, i.e. measured in mL
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyTitration {
	private MyDataPoint point;
	private Derivative fstDrv;
	private Derivative sndDrv;
	
	public MyTitration(MyDataPoint p, Derivative fst, Derivative snd) {
		this.point = p;
		this.fstDrv = fst;
		this.sndDrv = snd;
	}
	
	public MyTitration(MyDataPoint p, Derivative fst) {
		this.point = p;
		this.fstDrv = fst;
	}
	
	public MyTitration(MyDataPoint p) {
		this.point = p;
	}
	
	public MyTitration() {
	}
	
	/**
	 * Returns the data point for one data row
	 * 
	 * @return <code>MyDataPoint</code> contains x and y (=PH) values
	 */
	public MyDataPoint getPoint() {
		return point;
	}
	
	/**
	 * Returns the first derivative for one data row
	 * 
	 * @return <code>Derivative</code> contains the y value 
	 */
	public Derivative getFstDrv() {
		return fstDrv;
	}
	
	/**
	 * Returns the second derivative for one data row
	 * 
	 * @return <code>Derivative</code> contains the y value 
	 */
	public Derivative getSndDrv() {
		return sndDrv;
	}
	
	

}
