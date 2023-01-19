package blue.macroLab.mycmd.phcalc.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Timer;

import blue.macroLab.mycmd.phcalc.calc.MyData;
import blue.macroLab.mycmd.phcalc.calc.MyDataPoint;
import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.plot.CurveType;
import blue.macroLab.mycmd.phcalc.plot.MyPlot;
import blue.macroLab.mycmd.phcalc.plot.PlotType;
import blue.macroLab.mycmd.phcalc.settings.CalcSettings;
import blue.macroLab.mycmd.phcalc.settings.ChartSettings;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

/** Data Model Object
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class Data {
	private String appTitle;
	private MyMessage logger;
	private Locale deLocale;
	private Locale gbLocale;
	private ResourceBundle messages;
	private ChartSettings csettings;
	private CalcSettings calcSettings;
	private Config config;
	private MyData mydata;
	private List<MyPlot> plots;
	private List<CurveType> types;
	private int steps = 0;
	private int step = 0;
	private double [][] dat;
	private double [][] dx1;
	private double [][] dx2;
	private ProgressBar pb;
	private MyTimerTask timerTask;
	private Timer timer;
	
	public Data(String title) {
	      this.appTitle = title;
	      this.setLogger();
	      this.plots = new ArrayList<>();
	      this.types = new ArrayList<>();
	      setMydata();
		  this.deLocale = new Locale.Builder().setLanguage("de").setRegion("DE").build();
		  this.gbLocale = new Locale.Builder().setLanguage("en").setRegion("GB").build();
		  setChartSettings();
		  this.setCalcSettings();
		  setConfig();
		}

	public Config getConfig() {
		return this.config;
	}

	public void setConfig(){
		this.config = new Config(this);
	}
	
	public String getTitle() {
		return this.appTitle;
	}
	
	public MyMessage getLogger() {
		return logger;
	}

	public void setLogger() {
		this.logger = new MyMessage(this);
	}

	public Locale getLocale() {
		return deLocale;
	}

	public ResourceBundle getMessages() {
		 return this.messages;
	}
	
	public void setMessages(String shortLanguage){
		  try {
			  String s = shortLanguage.strip().toLowerCase();
			  if (s.equals(deLocale.getLanguage() )) {
				  this.messages = ResourceBundle.getBundle(Constants.MESSAGE_PATH, deLocale);
			  } else {
				  this.messages = ResourceBundle.getBundle(Constants.MESSAGE_PATH, gbLocale);
			  }
			  
		  } catch (MissingResourceException e) {
			  this.logger.addWarn("Error while setting up language: "+e.getMessage());
		  }
		}
	
    public String getMessageValue(String key){
		try {
			return this.messages.getString(key);
		} catch (MissingResourceException ex) {
			this.logger.addWarn("The key "+key+" does not exist.");
			return key;
		}
    	
	}

	public Locale getGbLocale() {
		return gbLocale;
	}

	public ChartSettings getChartSettings() {
		return csettings;
	}

	public void setChartSettings() {
		this.csettings = new ChartSettings(this);
	}

	public CalcSettings getCalcSettings() {
		return calcSettings;
	}

	public void setCalcSettings() {
		this.calcSettings = new CalcSettings();
	}

	public MyData getMydata() {
		return mydata;
	}

	public void setMydata() {
		this.mydata = new MyData(this);
	}

	public void setCurve(MyPlot plot) {
		if (types.contains(CurveType.EDGED)) {
			PlotType t = plot.getType();
			plot.setSmooth(t.getEdged());
		} else if (types.contains(CurveType.SMOOTHED)) {
			PlotType t = plot.getType();
			plot.setSmooth(t.getSmooth());
		} else {
			plot.setSmooth(null);
		}
	}

	public List<CurveType> getTypes() {
		return types;
	}

	public List<MyPlot> getPlots() {
		return plots;
	}

	public void setPlots() {
		if (this.dat != null ) {
			if (this.dat.length > 0 ) {
				MyPlot plotDat = new MyPlot(dat, PlotType.PH , this.getChartSettings().getColorLine());
				setCurve(plotDat);
				this.plots.add(plotDat);
			} 
		}
		
		if (this.dx1 != null ) {
			if (this.dx1.length > 0) {
				MyPlot plotDx1 = new MyPlot(dx1, PlotType.Fst_Derivative , this.getChartSettings().getColorFirst());
				setCurve(plotDx1);
				this.plots.add(plotDx1);
			}
		}
		
		if (this.dx2 != null ) {
			if (this.dx2.length > 0) {
				MyPlot plotDx2 = new MyPlot(dx2, PlotType.Snd_Derivative, this.getChartSettings().getColorSecond());
				setCurve(plotDx2);
				this.plots.add(plotDx2);
			}
		}
	}
	
	public void addConst(double d) {
		MyPlot plotConst = new MyPlot(d, PlotType.CONST, this.getChartSettings().getColorConst0());
		setCurve(plotConst);
		this.plots.add(plotConst);
	}
	
	public void addPoint(double x,double y) {
		MyPlot plotConst = new MyPlot(new MyDataPoint(x,y), PlotType.EP, this.getChartSettings().getColorConstEP());
		setCurve(plotConst);
		this.plots.add(plotConst);
	}
	
	public void setDat(double[][] dat) {
		this.dat = dat;
	}

	public void setFirstDerivatives(double[][] dx1) {
		this.dx1 = dx1;
	}

	public void setSecondDerivatives(double[][] dx2) {
		this.dx2 = dx2;
	}
	public void setupTimer() {
		timer = new Timer(true);
		timerTask = new MyTimerTask(this,timer);
        //running timer task as daemon thread
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
	public void setupProgressBar() {
		ProgressBarBuilder pbb = new ProgressBarBuilder()
							  .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BLOCK);
		this.pb = pbb.build();
		this.pb.maxHint(Constants.STEPS);
	}

	public ProgressBar getProgressBar() {
		return pb;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
		this.step = Constants.STEPS/this.steps;
	}
	
	public void decreaseSteps() {
		this.steps--;
	}
	
	public void inc() {
		if (this.steps > 1) {
			this.pb.stepBy(this.step);
			this.decreaseSteps();
		} else if (this.steps == 1) {
			this.pb.stepTo(Constants.STEPS);
			this.decreaseSteps();
		} 
	}

	public MyTimerTask getTimerTask() {
		return timerTask;
	}
	
}
