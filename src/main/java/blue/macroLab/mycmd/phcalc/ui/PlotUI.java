package blue.macroLab.mycmd.phcalc.ui;

import com.panayotis.gnuplot.style.NamedPlotColor;

import blue.macroLab.mycmd.phcalc.calc.MyCalc;
import blue.macroLab.mycmd.phcalc.calc.MyTitrationData;
import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.plot.Asymptote;
import blue.macroLab.mycmd.phcalc.plot.CurveType;
import blue.macroLab.mycmd.phcalc.utils.Utility;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/** Plot Subcommand
 * implemented for the command line user interface.
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
enum PLOT { curve,derivatives,all }
@Command(
			name = Constants.PLOT_SUBCOMMAND, 
			description = "Plot the calculations or the data.",
			helpCommand = true,
			sortOptions = false,
			sortSynopsis = false,
			headerHeading = "%n@|green,bg(white),bold,underline Usage|@:%n%n",
			synopsisHeading = "%n",
			descriptionHeading = "%n@|magenta,bg(white),bold,underline Description|@:%n",
			parameterListHeading = "%n@|green,bg(white),bold,underline Parameters|@:%n",
			optionListHeading = "%n@|green,bg(white),bold,underline Options|@:%n",
			//header = Constants.PLOT_SUBCOMMAND + Constants.SPACE+ Constants.COMMAND+Constants.NEW_LINE,
			requiredOptionMarker = Constants.REQUIRED_MARKER, 
			abbreviateSynopsis = true,
			showEndOfOptionsDelimiterInUsageHelp = true,
			customSynopsis = {
					          Constants.SW_NAME + Constants.SPACE + Constants.LS + Constants.OPTION_PARAM + Constants.RS + Constants.SPACE + Constants.PLOT_SUBCOMMAND+ Constants.SPACE +
					          Constants.LS+ Constants.SHORT_OPTION + Constants.FIT_OPTION_SHORT+ Constants.SMOOTH_OPTION_SHORT +  Constants.YTICK_OPTION_SHORT + Constants.XTICK_OPTION_SHORT + Constants.RS+ Constants.SPACE+
					          Constants.LS+ Constants.SHORT_OPTION   +Constants.TYPE_OPTION_SHORT+ Constants.EQUALS + Constants.LA + Constants.TYPE_PARAM_LABEL + Constants.RA+Constants.RS+ Constants.SPACE + 
					              
					          Constants.LS+Constants.SHORT_OPTION+Constants.ASYMPTOTE_OPTION_SHORT + Constants.EQUALS +Constants.LA + Constants.ASYMPTOTE_PARAM_LABEL + Constants.RA + Constants.RS + Constants.SPACE +
					          Constants.LS+ Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.CURVE_OPTION + Constants.EQUALS + Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA + Constants.RS + Constants.SPACE +
					          Constants.LS + Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.KEY_EP + Constants.EQUALS + Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA + Constants.RS + Constants.SPACE +
					          Constants.LS + Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.FSTDEV_OPTION + Constants.EQUALS + Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA + Constants.RS + Constants.SPACE +
					          Constants.LS + Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.SNDDEV_OPTION + Constants.EQUALS + Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA + Constants.RS ,
					          Constants.NEW_LINE,
					          Constants.SW_NAME + Constants.SPACE + Constants.LS + Constants.OPTION_PARAM + Constants.RS + Constants.SPACE + Constants.PLOT_SUBCOMMAND+ Constants.SPACE + Constants.LS +Constants.OPTION+Constants.LIST_OPTION+Constants.SHORT_OPTION+Constants.COLORS_OPTION + Constants.RS,
						     }
			)
public class PlotUI extends PHCalcTool implements Runnable{
	private static final boolean singlePlot = true;

	public PlotUI(Data model) {
		super(model);
	}
	
	@Option(names = {Constants.SHORT_OPTION+Constants.LIST_OPTION_SHORT+Constants.COLOR_OPTION_SHORT,Constants.OPTION+Constants.LIST_OPTION+Constants.SHORT_OPTION+Constants.COLORS_OPTION}, description = "Lists all possible colors in which the lines can be plotted" )
	boolean listColorsRequested;
	
	@Option(names = { Constants.SHORT_OPTION+Constants.TYPE_OPTION_SHORT, Constants.OPTION+Constants.TYPE_OPTION}, description = "Specify what you would like to plot: ${COMPLETION-CANDIDATES}",required = true, paramLabel = Constants.LA + Constants.TYPE_PARAM_LABEL + Constants.RA )
	PLOT type = null;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.FIT_OPTION_SHORT, Constants.OPTION+Constants.FIT_OPTION}, negatable=true, description = { "Specify whether the curve should be fitted using logistic regression, i.e. sigmoid curve" , Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL})
	boolean useFit;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.SMOOTH_OPTION_SHORT, Constants.OPTION+Constants.SMOOTH_OPTION}, negatable=true, description = { "Specify whether the curve should be smoothed", Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL})
	boolean useSmooth;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.XTICK_OPTION_SHORT, Constants.OPTION+Constants.XTICK_OPTION}, negatable=true, description = { "Specify whether the chart should contain xtics", Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL})
	boolean useXtics;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.YTICK_OPTION_SHORT, Constants.OPTION+Constants.YTICK_OPTION}, negatable=true, description = { "Specify whether the chart should contain ytics",Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL})
	boolean useYtics;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.ASYMPTOTE_OPTION_SHORT, Constants.OPTION+Constants.ASYMPTOTE_OPTION},  description = { "Specify the parameters n and b if you want to fit your titration curve: ${COMPLETION-CANDIDATES}","Please note, that improper values may result in exceptions so that curve fit will not take place.", Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL},paramLabel = Constants.LA + Constants.ASYMPTOTE_PARAM_LABEL + Constants.RA)
	Asymptote asymp;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.COLOR_OPTION_SHORT, Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.CURVE_OPTION}, defaultValue = "ROYALBLUE", fallbackValue = "ROYALBLUE",  description = { "Specify the color of the titration curve (default: ${DEFAULT-VALUE})",Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL},paramLabel = Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA)
	NamedPlotColor lineColor;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.COLOR_OPTION_SHORT+Constants.KEY_EP, Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.KEY_EP},  defaultValue = "BLACK", fallbackValue = "BLACK", description = { "Specify the color of the equivalence point (default: ${DEFAULT-VALUE})",Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL},paramLabel = Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA)
	NamedPlotColor epColor;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.COLOR_OPTION_SHORT+Constants.FSTDEV_OPTION_SHORT, Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.FSTDEV_OPTION}, defaultValue = "FOREST_GREEN", fallbackValue = "FOREST_GREEN", description = { "Specify the color of the first derivative (default: ${DEFAULT-VALUE})",Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL},paramLabel = Constants.LA + Constants.COLOR_PARAM_LABEL + Constants.RA)
	NamedPlotColor fstDevColor;
	
	@Option(names = {Constants.SHORT_OPTION+Constants.COLOR_OPTION_SHORT+Constants.SNDDEV_OPTION_SHORT, Constants.OPTION+Constants.COLOR_OPTION+Constants.SHORT_OPTION+Constants.SNDDEV_OPTION}, defaultValue = "ORANGE_RED", fallbackValue = "ORANGE_RED", description = { "Specify the color of the second derivative (default: ${DEFAULT-VALUE})",Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL},paramLabel = Constants.LA +  Constants.COLOR_PARAM_LABEL + Constants.RA )
	NamedPlotColor sndDevColor;
	
	public void run() {
		if (type != null) {
		  switch(type) {
			case curve:
				this.verbose(Constants.PLOT_CURVE_STEPS);
				drawCurve();
				testFile();
				cancel();
				break;
			case derivatives:
				this.verbose(Constants.PLOT_DERIVATIVES_STEPS);
				drawDerivatives();
				this.testFile();
				cancel();
				break;
			default:
				this.verbose(Constants.PLOT_ALL_STEPS);
				drawPoint();
				this.testFile();
				cancel();
				break;
			}
		} else if(listColorsRequested) {
			printColors();
		} 
		
	}
	
	private void printColors() {
		StringBuilder sb = new StringBuilder();
		String line = "";
		for (NamedPlotColor c: NamedPlotColor.values()) {
				if (line.length() + c.toString().length() <= Constants.DISPLAY_WIDTH) {
					line += c.toString() + Constants.SPACE;
				} else {
					sb.append(line);
					sb.append(Constants.NEW_LINE);
					line = "";
				}
			}
		System.out.print(sb.toString());
		}
	
	private void drawCurve() {
		this.setProperties();
		this.setColors();
		MyTitrationData myTitration = setData();
		auxAsymptote(myTitration,singlePlot);
		plotCurve();
	}
	
	private void drawPoint() {
		this.setProperties();
		this.setColors();
		MyTitrationData myTitration = setData();
		MyCalc calc = calcPoints(myTitration);
		auxAsymptote(myTitration,!singlePlot);
		plotPoints(calc);
	}
	
	private void drawDerivatives() {
		this.setProperties();
		this.setColors();
		MyTitrationData myTitration = setData();
		auxAsymptote(myTitration,!singlePlot);
		this.plotDerivatives();
	}
	
	private void auxAsymptote(MyTitrationData titration,boolean isSinglePlot) {
		this.getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_FIT));
		if (this.asymp != null && isSinglePlot) {
			setPlot(titration,this.asymp);
		} else if (isSinglePlot){
			setPlot(titration,Asymptote.NONE);
		// multiple plots
		} else if (this.asymp != null) {
			setPlots(titration,this.asymp);
		} else {
			setPlots(titration,Asymptote.NONE);
		}
	}
	
	private void setProperties() {
		this.getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_CHART));
		if (this.useFit) {
			this.getModel().getTypes().add(CurveType.FITTED);
		}
		
		if (this.useXtics) {
			this.getModel().getTypes().add(CurveType.USE_XTICKS);
		}
		
		if (this.useYtics) {
			this.getModel().getTypes().add(CurveType.USE_YTICKS);
		}
		
		if (this.useSmooth) {
			this.getModel().getTypes().add(CurveType.SMOOTHED);
		} else {
			this.getModel().getTypes().add(CurveType.EDGED);
		}
	}
	
	private void setColors() {
		if (this.epColor != null) {
			this.getModel().getChartSettings().setColorConstEP(this.epColor);
		}
		
		if (this.lineColor != null) {
			this.getModel().getChartSettings().setColorLine(lineColor);
		}
		
		if (this.fstDevColor != null) {
			this.getModel().getChartSettings().setColorFirst(fstDevColor);
		}
		
		if (this.sndDevColor != null) {
			this.getModel().getChartSettings().setColorSecond(sndDevColor);
		}
	}
	
	private void testFile() {
		String sFile = this.getModel().getChartSettings().getFileName();
		if (Utility.fileExists(sFile)) {
			this.getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_FILE)+Constants.DPOINT+Constants.SPACE+sFile);
		} else {
			this.getModel().getLogger().addWarn(this.getModel().getMessageValue(Constants.KEY_WRITING_ERROR));
		}
	}

}
