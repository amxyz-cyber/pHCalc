package blue.macroLab.mycmd.phcalc.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hipparchus.analysis.ParametricUnivariateFunction;
import org.hipparchus.analysis.function.Logistic;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.fitting.SimpleCurveFitter;
import org.hipparchus.fitting.WeightedObservedPoints;
import com.panayotis.gnuplot.dataset.FileDataSet;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.model.MyMessage;
import blue.macroLab.mycmd.phcalc.plot.Asymptote;
import blue.macroLab.mycmd.phcalc.plot.PlotType;
import blue.macroLab.mycmd.phcalc.settings.ChartSettings;

/** Converts a data set into a list of <em>MyTitration</em> data points
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyTitrationData {
	private Data model;
	private MyMessage mylog;
	private FileDataSet fData;
	private MyData mydata;
	private ChartSettings settings;
	private List<MyTitration> myDataPoints;
	private String tblHeadline;
	private String [] tblHeaders;
	public static final int XCol = 0;
	public static final int YCol = 1;
	public static final int YDrv1 = 2;
	public static final int YDrv2 = 3;
	public static final int DIMENSION = 4;
	
	
	public MyTitrationData(Data model) {
		mylog = model.getLogger();
		this.model = model;
	 	mydata = model.getMydata();
	 	settings = model.getChartSettings();
	    this.fData = mydata.getDataset();
		this.myDataPoints =  new ArrayList<>();
	}
	
	 public void calculateDerivatives() {
		  if (this.fData != null) {
		  int max_i = this.fData.size() - 1;
		  MyDataPoint point_prev = new MyDataPoint();
    	  MyDataPoint point_next = new MyDataPoint();
		  
	      for(int i=0; i<this.fData.size(); i++) {
	    	  Double x = mydata.getDataPoint(XCol,i );
	    	  Double pH = mydata.getDataPoint(YCol,i );
	    	  MyDataPoint point_i = new MyDataPoint(x,pH);
	    	  
	    	  if (i == 0) {
	    		  MyTitration row = new MyTitration(point_i);
	    		  this.myDataPoints.add(row);
	    	  } else if (i < max_i) {
	    		  Derivative drv1 = new Derivative(point_prev, point_i);
	    		  drv1.calcDerivative();
	    		  
	    		  Double x_j = mydata.getDataPoint(XCol,i+1 );
		    	  Double pH_j = mydata.getDataPoint(YCol,i+1 );
		    	  point_next.setX(x_j);
		    	  point_next.setY(pH_j);
		    	  
		    	  Derivative drv1_next = new Derivative(point_i,point_next);
		    	  drv1_next.calcDerivative();
	    		  
		    	  Derivative drv2 = new Derivative(point_i,point_next);
	    		  drv2.calcDerivative(drv1_next.getValue(),drv1.getValue());
	    		  
	    		  MyTitration row = new MyTitration(point_i,drv1,drv2);
	    		  this.myDataPoints.add(row);
	    	  // last row of data file
	    	  } else {
	    		  Derivative drv1 = new Derivative(point_prev, point_i);
	    		  drv1.calcDerivative();
	    		  MyTitration row = new MyTitration(point_i,drv1);
	    		  this.myDataPoints.add(row);
	    	  }
	    	  point_prev.setX(point_i.getX());
	    	  point_prev.setY(point_i.getY());
	   }
	      MyTitration maxPointX = Collections.max(myDataPoints , new CompareData());
		  double xMax_new = maxPointX.getPoint().getX();
		  this.settings.setXMax(xMax_new);
		  } else {
			  this.model.getLogger().addWarn(this.model.getMessageValue(Constants.KEY_ERROR_FILESET));
		}
	 }
	 
	 /** Fits the data points of one curve using logistic regression
	  * @param logisticFunction ParametricUnivariateFunction
	  * @param fit Double Array
	  * @param data Double Array
	  * @return <em>double[][]</em> fitted data points
	 */
	  public double[][] fitData(ParametricUnivariateFunction logisticFunction,double[] fit, double [][] data) {
	    int x =0;
	    int size = this.myDataPoints.size();
	    //double y0 = this.myDataPoints.get(0).getPoint().getY();
	    double xMax = this.myDataPoints.get(size-1).getPoint().getX();
	    //double y_Last = this.myDataPoints.get(size-1).getPoint().getY(); 
	    double dSize = xMax/settings.getStep();
	    int iSize = 2+(int)Math.round(dSize);
	    data=new double[iSize][2];
	    for(int i=0; i<iSize  ; i++) {
	    	data[i][XCol] = x; 
	    	double y = logisticFunction.value(x, fit);
	        data[i][YCol] = y;
		     /*if (x==0) {
		        data[i][YCol] = y0;
		      } else if (x > xMax) {
		    	 //double y = logisticFunction.value(xMax, fit);
			     data[i][YCol] = y_Last;
		      } else {	  
		    	double y = logisticFunction.value(x, fit);
		        data[i][YCol] = y;
		      }*/
		      x=x+settings.getStep();
		    }
	    double xMax_new = data[data.length-1][XCol];
	    this.settings.setXMax(xMax_new);
	    return data;
	  }
	 
	/** Calculates the parameter <em>N</em> in a simple way 
	  * @param a Double Parameter 
	  * @param m Double parameter
	  * @return <em>n</em> 
	  */ 
	 public double calcSimpleN(double a, double m) {
		 double n = Math.abs(a-m);
		 return n;
	 }
	 
	 /** Calculates the parameter <em>N</em> using an advanced method 
	  * @param size Int Number of data points in a data set 
	  * @param m Double parameter
	  * @return <em>n</em> 
	  */ 
	 public double calcAdvancedN(int size, double m) {
		 double n = Math.abs(m / (size/2) );
		 return n;
	 }
	 
	 /** Calculates the fit function used for logistic regression 
	  * @param params Asymptote Specifies in which way to calculate the other paramters 
	  * @return <em>double[][]</em> fitted data points
	  */ 
	 public double[][] simpleCurve(Asymptote params) {
		 int size = myDataPoints.size();
		 double n = 0;
		 double [][] titrationData = null;
		 double [][] fittedData = null;
		 WeightedObservedPoints obs = new WeightedObservedPoints();
		 for (MyTitration m: myDataPoints) {
		    obs.add( m.getPoint().getX(),m.getPoint().getY());
		  }
		 MyTitration maxY = Collections.max(myDataPoints , new CompareValue());
		 MyTitration minY = Collections.min(myDataPoints , new CompareValue());
		  double a = minY.getPoint().getY();
		  double k = maxY.getPoint().getY();
		  double m = myDataPoints.get(size/2).getPoint().getX() ;
		  double b = settings.getParamB();
		  double q = settings.getParamQ();
		  
		  switch(params) {
			  case ADVANCED_CALC: n = calcAdvancedN(size,m);
			  					  break;
			  case SIMPLE_CALC:   n = calcSimpleN(a, m);
				  				  break;
			  case NONE:          n = settings.getParamN();
			  					  b = settings.getParamB();
				  				  break;
			  default:            n = params.getN();
			  					  b = params.getB();
			  	 	   			  break;
		  }
		  double[] estimates = new double[]{k, m, b, q, a, n};
		     
		  mylog.logValue("a",a);
		  mylog.logValue("b",b);
		  mylog.logValue("k",k);
		  mylog.logValue("m",m);
		  mylog.logValue("n",n);
		  mylog.logValue("q",q);
		  ParametricUnivariateFunction logisticFunction = new Logistic.Parametric();
		  titrationData =  toPlotData(PlotType.PH);
		  try {
			  SimpleCurveFitter curveFitter = SimpleCurveFitter.create(logisticFunction, estimates);
			  double[] fit = curveFitter.fit(obs.toList());
			  fittedData = fitData(logisticFunction,fit, titrationData);
			  mylog.addLog(model.getMessageValue( Constants.KEY_FIT));
			  return fittedData;
		  } catch(MathIllegalArgumentException ex) {
			  mylog.addWarn(model.getMessageValue( Constants.KEY_NOFIT) +Constants.NEW_LINE+ex.getMessage());
			  return titrationData;
		  } 
		}
	 
	 /** Converts a list of <em>MyDataPoint</em> to a double array 
	  * @return <em>double [][]</em> 
	  */ 
	 public double [][] toData() {
			int index =0;
			int iSize = myDataPoints.size();
			double [] [] data =new double[iSize][DIMENSION];
			
		    for (MyTitration titration_i: myDataPoints) {
		    	  data[index][XCol] = titration_i.getPoint().getX(); 
		    	  data[index][YCol] = titration_i.getPoint().getY();
		    	  if (titration_i.getFstDrv() != null ) {
		    		  data[index][YDrv1] = titration_i.getFstDrv().getValue();
		    	  }
		    	  
		    	  if (titration_i.getSndDrv() != null ) {
		    		  data[index][YDrv2] = titration_i.getSndDrv().getValue();
		    	  }
		    	  index++;
		   }
		    return data;
		}
	 
	 /** Converts a list of <em>MyDataPoint</em> to a list containing a pair of (x,y) values 
	  * @param type PlotType Specifies which y value should be copied to the double array 
	  * @return <em>double [][]</em> 
	  */ 
	 public double [][] toPlotData(PlotType type) {
			int index =0;
			int iSize = myDataPoints.size();
			double [] [] data =new double[iSize][2];
			
		    for (MyTitration titration_i: myDataPoints) {
		    	  
		    	  
		    	  if (type.equals(PlotType.PH)) {
		    		  data[index][YCol] = titration_i.getPoint().getY();
		    		  data[index][XCol] = titration_i.getPoint().getX();
		    	  } else if (type.equals(PlotType.Fst_Derivative)) {
		    		  if (titration_i.getFstDrv() != null ) {
			    		  data[index][YCol] = titration_i.getFstDrv().getValue();
			    		  data[index][XCol] = titration_i.getPoint().getX();
			    	  } 
		    	  } else {
		    		  if (titration_i.getSndDrv() != null ) {
			    		  data[index][YCol] = titration_i.getSndDrv().getValue();
			    		  data[index][XCol] = titration_i.getPoint().getX();
			    	  }  
		    	  }
		    	  index++;
		   }
		    return data;
		}
	 
	public void generateTableData() {
		this.tblHeadline = this.model.getMessageValue(Constants.KEY_TITLE_DATA);
		this.tblHeaders = new String [] {this.settings.getXLabel(),settings.getYLabel(),PlotType.Fst_Derivative.getLabel(),PlotType.Snd_Derivative.getLabel()};
	}

	public List<MyTitration> getMyDataPoints() {
		return myDataPoints;
	}

	public String getTblHeadline() {
		return tblHeadline;
	}

	public String[] getTblHeaders() {
		return tblHeaders;
	}
		
}
