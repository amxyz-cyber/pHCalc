package blue.macroLab.mycmd.phcalc.plot;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.plot.FunctionPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Smooth;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.PostscriptTerminal;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;

/** Creates a bivariate plot
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class BivariatePlot {
	private Data model;
	private JavaPlot p;
	
	public BivariatePlot( Data m) {
		this.model = m;
		this.p = new JavaPlot();
	}
	
	public JavaPlot paintAll() {
		resetPlot();
		PostscriptTerminal epsf = new PostscriptTerminal(model.getChartSettings().getFileName());
		epsf.setColor(true);
		epsf.setEPS(true);
		p.setTerminal(epsf);
		p.setTitle(model.getChartSettings().getChartTitle());
		
		p.getAxis("x").setBoundaries(model.getChartSettings().getXMin(),model.getChartSettings().getXMax());
		p.getAxis("y").setBoundaries(model.getChartSettings().getYMax()*2*(-1),model.getChartSettings().getYMax()*2);
		setTicks(this.model.getChartSettings().getXMin(), this.model.getChartSettings().getXtick(),model.getChartSettings().getXMax(),CurveType.USE_XTICKS);
		setTicks(this.model.getChartSettings().getYMin(), this.model.getChartSettings().getYtick(),model.getChartSettings().getYMax(),CurveType.USE_YTICKS);
		p.getAxis("x").setLabel(model.getChartSettings().getXLabel());
		p.getAxis("y").setLabel(model.getChartSettings().getYLabel());
		
		p.setKey(JavaPlot.Key.OUTSIDE );
		iterPlots();
		
		p.plot();
		return p;
	}
	
	public JavaPlot paintCurve() {
		resetPlot();
		PostscriptTerminal epsf = new PostscriptTerminal(model.getChartSettings().getFileName());
		epsf.setColor(true);
		epsf.setEPS(true);
		p.setTerminal(epsf);
		p.setTitle(model.getChartSettings().getChartTitle());
		p.setKey(JavaPlot.Key.OFF);
		
		p.getAxis("x").setLabel(model.getChartSettings().getXLabel());
		p.getAxis("y").setLabel(model.getChartSettings().getYLabel());
		
		p.getAxis("x").setBoundaries(model.getChartSettings().getXMin(),model.getChartSettings().getXMax());
		p.getAxis("y").setBoundaries(model.getChartSettings().getYMin(),model.getChartSettings().getYMax());
		
		setTicks(this.model.getChartSettings().getXMin(), this.model.getChartSettings().getXtick(),model.getChartSettings().getXMax(),CurveType.USE_XTICKS);
		setTicks(this.model.getChartSettings().getYMin(), this.model.getChartSettings().getYtick(),model.getChartSettings().getYMax(),CurveType.USE_YTICKS);
		
		iterPlots();
		p.plot();
		return p;
	}
	
	
	private PlotStyle styleForm(PlotColor color, AbstractPlot a, Style s ) {
		PlotStyle stl = a.getPlotStyle();
        stl.setStyle(s);
        stl.setLineType(color);
        return stl;
	}
	
	private AbstractPlot plotLine(NamedPlotColor c,Smooth smooth,Style lt,int w) {
		AbstractPlot absplot = (AbstractPlot)p.getPlots().get(p.getPlots().size() - 1);
		absplot.setSmooth(smooth);
		PlotStyle stlFunc = styleForm(c, absplot, lt );
		stlFunc.setLineWidth(w);
		return absplot;
	}
	
	private void setTicks(double dMin, double d,double dMax,CurveType t) {
		if ( this.model.getTypes().contains(t)) {
			String s = dMin + Constants.COMMA + d;
			//String s = String.valueOf(d) ;
			p.set(t.getLabel(),s);
		} else {
			p.getParameters().unset(t.getLabel());
		}
		if ( t.equals(CurveType.USE_XTICKS)) {
			String sRot = "rotate by " + this.model.getChartSettings().getXRotate() ;
			p.set(t.getLabel(),sRot);
		}
    }
	
	private void resetPlot() {
		this.p = new JavaPlot();
	}
	
	/** 
	  * Iterate over <em>MyPlots</em> objects and add them as a multigraph
	  * 
	  * Sets the properties for each plot according to the plot type and <em>MyPlot</em> object  
	  */
	private void iterPlots() {
		for (MyPlot plot: this.model.getPlots()) {
			if (plot.getType().equals(PlotType.CONST)) {
				FunctionPlot fplotX = new FunctionPlot(String.valueOf(plot.getY()));
				fplotX.setTitle("");
				p.addPlot(fplotX);
			} else if (plot.getType().equals(PlotType.EP)) {
				FunctionPlot fplotX = new FunctionPlot(String.valueOf(plot.getPoint().getY()));
				fplotX.setTitle(plot.getType().getLabel());
				p.addPlot(fplotX);
				p.set("arrow", "from "+plot.getPoint().getX()+",graph 0 to "+plot.getPoint().getX()+",graph 1 nohead");
			} else {
				DataSetPlot sPoints = new DataSetPlot(plot.getData());
				sPoints.setTitle(plot.getType().getLabel());
				p.addPlot(sPoints);
			}
			plotLine(plot.getColor(),plot.getSmooth(),plot.getType().getStl(),plot.getType().getWidth());
		  }
	}
}
