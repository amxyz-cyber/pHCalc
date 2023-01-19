package blue.macroLab.mycmd.phcalc.ui;

import java.io.File;

import blue.macroLab.mycmd.phcalc.calc.MyCalc;
import blue.macroLab.mycmd.phcalc.calc.MyTitrationData;
import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.model.Verbosity;
import blue.macroLab.mycmd.phcalc.plot.Asymptote;
import blue.macroLab.mycmd.phcalc.plot.BivariatePlot;
import blue.macroLab.mycmd.phcalc.plot.CurveType;
import blue.macroLab.mycmd.phcalc.plot.PlotType;
import blue.macroLab.mycmd.phcalc.utils.Utility;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;

/** pHCalc tool
 * implemented for the command line user interface.
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
@Command( name = Constants.SW_NAME, 
		  subcommands = { HelpCommand.class }, 
		  version = {
				      "@|green pHCalc 1.0|@",
				      "@|blue Build 12345|@",
				      "@|blue,bg(white) (c) 2023|@",
					  "Picocli " + picocli.CommandLine.VERSION,
					  "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
					  "OS: ${os.name} ${os.version} ${os.arch}"},
		  //version = Constants.SW_NAME + Constants.SPACE + Constants.VERSION, 
		  //headerHeading = "%n@|magenta,bg(yellow),bold,underline Usage|@:%n",
          descriptionHeading = "%n@|magenta,bg(white),bold,underline Description|@:%n",
		  parameterListHeading = "%n@|green,bg(white),bold,underline Parameters|@:%n",
		  optionListHeading = "%n@|green,bg(white),bold,underline Options|@:%n",
		  mixinStandardHelpOptions = true,
		  footer = "@|blue,bg(white) Copyright(c) 2023|@",
		  description = "Calculate the equivalence point, its pH value, and plot or print the results.",
		  synopsisSubcommandLabel = "(compute | print | plot)",
		  showEndOfOptionsDelimiterInUsageHelp = true
		) 
public class PHCalcTool implements Runnable{
	private Data model;
	private int steps;
	
	public PHCalcTool(Data model) {
		this.model = model;
	}
	
	@Option(names = {Constants.SHORT_OPTION+Constants.VERSION_OPTION_SHORT , Constants.OPTION+Constants.VERSION_OPTION}, versionHelp = true, description = Constants.VERSION_INFO )
	boolean versionInfoRequested;

	@Option(names = {Constants.SHORT_OPTION+Constants.HELP_OPTION_SHORT, Constants.OPTION+Constants.HELP_OPTION}, usageHelp = true, description = Constants.HELP_INFO)
	boolean usageHelpRequested;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.VERBOSITY_OPTION_SHORT, Constants.OPTION+Constants.VERBOSITY_OPTION}, negatable=true,required=false, description = { "Specify multiple -v options to increase verbosity.", "For example, `-v -v -v -v` or `-vvvv`"})
	boolean[] verbosity;
	
	public void verbose(int steps) {
		int verbLevel = 0;
		if(verbosity != null) {
			verbLevel = verbosity.length;
			Verbosity verb = Verbosity.getVerbosity(verbLevel);
			if (verb != null && verb.equals(Verbosity.PROGRESS)) {
				model.getLogger().setVerbosity(verb);
				this.model.setupProgressBar();
				this.model.setSteps(steps);
				this.model.setupTimer(); 
			} else if(verb != null) {
				model.getLogger().setVerbosity(verb);
			} else {
				String s = this.model.getMessageValue(Constants.KEY_VERBOSITY_ERROR) + Verbosity.ALL.getLevel();
				System.out.println(s);
			}
		}
	}
	
	public void display(File file,String sTab) {
		if(file != null && Utility.fileExists(file)) {
    		getModel().getLogger().addWarn(getModel().getMessageValue(Constants.KEY_FILE_EXISTS));
    	} else if (file != null) {
    		Utility.writeText(file,sTab,getModel());
    		getModel().getLogger().addLog(getModel().getMessageValue(Constants.KEY_FILE_WRITTEN));
    		getModel().getLogger().summarize(getModel().getMessageValue(Constants.KEY_FILE_WRITTEN)+Constants.DPOINT+Constants.SPACE+file.getAbsolutePath());
    	}else {
    		System.out.println(sTab);
    	}
	}
	
	public MyTitrationData setData() {
		getModel().getMydata().setDataset();
		MyTitrationData myTitration = new MyTitrationData(getModel());
		getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_TITRATION));
        myTitration.calculateDerivatives();
		return myTitration;
	}
	
	public MyCalc calcPoints(MyTitrationData myTitration) {
		MyCalc calc = new MyCalc(myTitration,getModel());
        getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_FUNCTION));
    	calc.setLines();
    	getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_EP));
    	calc.calcEP();
		return calc;
	}
	
	public void calcMass(MyCalc calc) {
		getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_MASS));
		calc.setConcentrationAfterDilution();
    	calc.setConcentrationBeforeDilution();
    	calc.convertConcentration();
    	calc.getMass();
	}
	
	public void setPlot(MyTitrationData titration,Asymptote a) {
    	double [][] dat = null;
    	if (this.getModel().getTypes().contains(CurveType.FITTED)) {
    		dat = titration.simpleCurve(a);
    	} else {
    		dat = titration.toPlotData(PlotType.PH);
    	}
    	this.getModel().setDat(dat);
    }
	
	 public void setPlots(MyTitrationData titration,Asymptote a) {
	    	double [][] dx1 = titration.toPlotData(PlotType.Fst_Derivative); 
	    	double [][] dx2 = titration.toPlotData(PlotType.Snd_Derivative);
	    	this.getModel().setFirstDerivatives(dx1);
	    	this.getModel().setSecondDerivatives(dx2);
	    	setPlot(titration,a);
	    }
	
	public void plotCurve() {
		this.model.getLogger().summarize(this.model.getMessageValue(Constants.KEY_SUM_PLOT));
    	BivariatePlot plot = new BivariatePlot(this.getModel());
    	this.getModel().setPlots();
    	plot.paintCurve();
    }
	
	public void plotPoints(MyCalc calc) {
		this.model.getLogger().summarize(this.model.getMessageValue(Constants.KEY_SUM_PLOT));
    	BivariatePlot titrationPlot = new BivariatePlot(this.model);
    	model.addConst(0);
    	model.addPoint(calc.getEquivalencePoint(), calc.getPH());
    	model.setPlots();
    	titrationPlot.paintAll();
    }
	
	public void plotDerivatives() {
		this.model.getLogger().summarize(this.model.getMessageValue(Constants.KEY_SUM_PLOT));
    	BivariatePlot titrationPlot = new BivariatePlot(this.model);
    	model.addConst(0);
    	model.setPlots();
    	titrationPlot.paintAll();
    }
	
	public void cancel() {
		if ( this.getModel().getTimerTask() != null) {
			this.getModel().getTimerTask().cancelTask();
		}
	}
	
	
	public void run() {
		 this.verbose(-1);
	}


	public Data getModel() {
		return model;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

}


