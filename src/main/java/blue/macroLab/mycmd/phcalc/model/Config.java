package blue.macroLab.mycmd.phcalc.model;

import java.io.File;
import javax.measure.quantity.Volume;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.plot.Asymptote;
import blue.macroLab.mycmd.phcalc.utils.Utility;

/** Reads in the configuration file
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class Config {
	
	private static final String MYPHOSPHORIC_PROPERTIES = "phcalc.properties";
	private Configurations configs;
	private Configuration config;
	private static final String KEY_LANG = "language";
	private static final String KEY_LOG_SIZE = "log.size";
	private static final String KEY_DATA_FILE = "data.file";
	private static final String KEY_CHART_FILE = "chart.file";
	private static final String KEY_CHART_TITLE = "chart.title";
	private static final String KEY_Y_TITLE = "y.label";
	private static final String KEY_X_TITLE = "x.label";
	private static final String KEY_STEP = "step";
	private static final String KEY_XTIC = "xtic";
	private static final String KEY_YTIC = "ytic";
	private static final String KEY_PARAM_N = "param.n";
	private static final String KEY_PARAM_Q = "param.q";
	private static final String KEY_PARAM_B = "param.b";
	private static final String KEY_ROTATE_X = "xtic.rotate";
	private static final String KEY_ACID = "acid.formula";
	private static final String KEY_ACID_NAME = "acid.name";
	private static final String KEY_MOL_MASS_ACID = "mol.mass.acid";
	private static final String KEY_TITRANT = "titrant.formula";
	private static final String KEY_TITRANT_NAME = "titrant.name";
	private static final String KEY_MOL_MASS_TITRANT = "mol.mass.titrant";
	private static final String KEY_SAMPLE = "sample";
	private static final String KEY_SAMPLE_NAME = "sample.name";
	private static final String KEY_DILUTION = "dilution";
	private static final String KEY_SOLUTION = "solution";
	private static final String KEY_THRESHOLD = "threshold";
	
	
	private String dataFile;
	private String fileName;
	private String xLabel;
	private String yLabel;
	private String chartTitle;
	private String language;
	private String acidFormula;
	private String sAcid;
	private String titrantFormula;
	private String sTitrant;
	private String sSample;
	private int bufferSize;
	private double dilution;
	private double sample;
	private double solution;
	private double molarMassAcid;
	private double molTitrant;
	private double threshold;
	private double paramN;
	private double paramQ;
	private double paramB;
	private double xtick;
	private double xRotate;
	private double ytick;
	Unit<Volume> mL = SI.MILLI(NonSI.LITER);
	
	private int step;
	private Data data;
	 
	 public Config(Data m) {
		 this.data = m;
		 this.configs = new Configurations();
		 this.reload();
		 
	 }
	 
	 public void reload() {
		 loadDefaults();
		 if(Utility.fileExists(MYPHOSPHORIC_PROPERTIES)) {
			 createConfiguration();
			 readConfigFromFile();
			}
		 this.data.setMessages(language);
		 this.data.getLogger().setMinimumBuffer(bufferSize);
		 String cTitle1 = chartTitle + Constants.SPACE + this.data.getMessageValue(Constants.KEY_OF) + Constants.SPACE +sAcid;
		 String cTitle = cTitle1 + Constants.SPACE + "- " + this.data.getMessageValue(Constants.KEY_SAMPLE) + Constants.DPOINT + Constants.SPACE +  "n="+sample+ Constants.SPACE + mL.toString();
		 String xTitle = xLabel + Constants.SPACE + this.titrantFormula + Constants.SPACE + mL.toString(); 
		 this.data.getChartSettings().setChartTitle(cTitle);
		 this.data.getChartSettings().setDataFile(dataFile);
		 this.data.getChartSettings().setFileName(fileName);
		 this.data.getChartSettings().setColorMeasurement("ROYALBLUE");
		 this.data.getChartSettings().setColorFirstDrv("FOREST_GREEN");
		 this.data.getChartSettings().setColorSecondDrv("DARK_ORANGE");
		 this.data.getChartSettings().setYLabel(yLabel);
		 this.data.getChartSettings().setXLabel(xTitle);
		 this.data.getChartSettings().setStep(step);
		 this.data.getChartSettings().setParamB(paramB);
		 this.data.getChartSettings().setParamN(paramN);
		 this.data.getChartSettings().setParamQ(paramQ);
		 this.data.getChartSettings().setXtick(xtick);
		 this.data.getChartSettings().setYtick(ytick);
		 this.data.getChartSettings().setXRotate(xRotate);
		 this.data.getCalcSettings().setDilution(dilution);
		 this.data.getCalcSettings().setMolTitrant(molTitrant);
		 this.data.getCalcSettings().setSample(sample);
		 this.data.getCalcSettings().setSolution(solution);
		 this.data.getCalcSettings().setThreshold(threshold);
		 this.data.getCalcSettings().setMolarMass(molarMassAcid);
		 this.data.getCalcSettings().setAcidFormula(acidFormula);
		 this.data.getCalcSettings().setAcidName(sAcid);
		 this.data.getCalcSettings().setTitrantFormula(titrantFormula);
		 this.data.getCalcSettings().setSampleName(sSample);
		 
		 this.data.getMydata().setFileData(dataFile);
	 }
	 
	 private void createConfiguration() {
		 try
		 {
		     File file = new File(MYPHOSPHORIC_PROPERTIES);
			 this.config = this.configs.properties(file);
		     this.data.getLogger().addLog("Config: "+file.getAbsolutePath());
		 }
		 catch (ConfigurationException cex)
		 {
			 this.data.getLogger().addWarn("Could not read in the configuration file." + Constants.NEW_LINE + cex.getMessage());
		 } 
	 }
	 
	 public void loadDefaults() {
		 dataFile = "data.dat";
		 fileName = "export-chart.eps";
		 xLabel = "Volume";
		 yLabel ="pH";
		 chartTitle = "Titration";
		 language = "en";
		 acidFormula = "H3PO4";
		 sAcid = "Phosphoric Acid";
		 titrantFormula = "NaOH";
		 sTitrant = "Sodium Hydroxide";
		 this.sSample = "#1";
		 dilution = 0;
		 sample = 0;
		 solution = 0;
		 molTitrant = 0.1;
		 threshold = 2.5;
		 this.molarMassAcid = 0;
		 step = 1;
		 xtick = 0.25;
		 ytick = 1;
		 paramN = Asymptote.FLAT_CURVE.getN();
		 paramQ = 1.0;
		 paramB = 0.1;
		 this.bufferSize = Constants.MAX_LOG_SIZE;
		 this.xRotate = 0;
	 }
	 
	 public void readConfigFromFile() {
		 dataFile =  validate(config.getString(KEY_DATA_FILE),dataFile); 
		 fileName = validate(config.getString(KEY_CHART_FILE),fileName);
		 yLabel = validate(config.getString(KEY_Y_TITLE),yLabel);  
		 xLabel = validate(config.getString(KEY_X_TITLE),xLabel);  
		 chartTitle = validate(config.getString(KEY_CHART_TITLE),chartTitle);  
		 language = validate(config.getString(KEY_LANG),language);
		 acidFormula = validate(config.getString(KEY_ACID),acidFormula);
		 sAcid = validate(config.getString(KEY_ACID_NAME),sAcid);
		 titrantFormula = validate(config.getString(KEY_TITRANT),titrantFormula);
		 sTitrant = validate(config.getString(KEY_TITRANT_NAME),sTitrant);
		 sSample = validate(config.getString(KEY_SAMPLE_NAME),sSample);
		 
		 dilution = validate(dilution,KEY_DILUTION);
		 sample = validate(sample,KEY_SAMPLE);
		 solution = validate(solution,KEY_SOLUTION);
		 molTitrant = validate(molTitrant,KEY_MOL_MASS_TITRANT);
		 threshold = validate(threshold,KEY_THRESHOLD);
		 step = validate(step,KEY_STEP);
		 this.bufferSize = validate(this.bufferSize,KEY_LOG_SIZE);
		 xtick = validate(xtick,KEY_XTIC);
		 ytick = validate(ytick,KEY_YTIC);
		 paramN = validate(paramN,KEY_PARAM_N);
		 paramQ = validate(paramQ,KEY_PARAM_Q);
		 paramB = validate(paramB,KEY_PARAM_B);
		 this.xRotate = validate(xRotate,KEY_ROTATE_X);
		 this.molarMassAcid = validate(molarMassAcid,KEY_MOL_MASS_ACID);
	 }
	 
	 	private String validate(String input, String defaultValue) {
	 		if (input == null || input.isBlank()) {
	 			return defaultValue;
	 		} else {
	 			return input;
	 		}
	 	}
	 	
		private double validate(double defaultValue, String key) {
	 		Double input = null; 
	 		try {
	    		 input = config.getDouble(key,defaultValue);
	         } catch (NumberFormatException ex) {
	             data.getLogger().addWarn(data.getMessageValue(Constants.KEY_WRONG_NUMBER)+Constants.SPACE+key+Constants.DPOINT+input);
	             return defaultValue;
	         } catch (ConversionException ex) {
	             data.getLogger().addWarn(ex.getMessage());
	             return defaultValue;
	         }
			return input;
    }
    
    private int validate(int defaultValue, String key) {
    	Integer input = null; 
    	try {
    		 input = config.getInteger(key,defaultValue);
         } catch (NumberFormatException ex) {
             data.getLogger().addWarn(data.getMessageValue(Constants.KEY_WRONG_NUMBER)+Constants.SPACE+key+Constants.DPOINT+input);
             return defaultValue;
         } catch (ConversionException ex) {
             data.getLogger().addWarn(ex.getMessage());
             return defaultValue;
         }
    	return input;
    }
}
