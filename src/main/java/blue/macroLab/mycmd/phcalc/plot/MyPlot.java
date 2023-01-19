package blue.macroLab.mycmd.phcalc.plot;

import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.Smooth;

import blue.macroLab.mycmd.phcalc.calc.MyDataPoint;

/** MyPlot Model Object
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyPlot {
	private double[][] data;
	private PlotType type;
	private NamedPlotColor color;
	private Smooth smooth = null;
	private MyDataPoint point = null;
	private double y = 0;
	
	/**
	  * Constructor.
	  * 
	  * @param d Contains Data rows  
	  * must be in range <code>1..50</code>. 
	  * @param t Plot type 
	  * @param c Color of curve
	  * @param s Smooth algorithm 
	  */
	public MyPlot(double [][] d, PlotType t, NamedPlotColor c, Smooth s) {
		new MyPlot(d, t, c);
		this.smooth = s;
	}
	
	public MyPlot(double [][] d, PlotType t, NamedPlotColor c) {
		this.data = d;
		this.type = t;
		this.color = c;
	}
	
	/**
	  * Constructor.
	  * 
	  * @param p Point to be plotted  
	  * must be in range <code>1..50</code>. 
	  * @param t Plot type 
	  * @param c Color of curve
	  */
	public MyPlot(MyDataPoint p, PlotType t, NamedPlotColor c) {
		this.point = p;
		this.type = t;
		this.color = c;
	}
	
	/**
	  * Constructor.
	  * 
	  * @param y Plots a horizontal line that intersects y axis at <em>y</em>  
	  * must be in range <code>1..50</code>. 
	  * @param t Plot type 
	  * @param c Color of curve
	  */
	public MyPlot(double y, PlotType t, NamedPlotColor c) {
		this.y = y;
		this.type = t;
		this.color = c;
	}

	public double[][] getData() {
		return data;
	}

	public PlotType getType() {
		return type;
	}

	public NamedPlotColor getColor() {
		return color;
	}

	public Smooth getSmooth() {
		return smooth;
	}

	public void setSmooth(Smooth smooth) {
		this.smooth = smooth;
	}

	public MyDataPoint getPoint() {
		return point;
	}

	public double getY() {
		return y;
	}
}
