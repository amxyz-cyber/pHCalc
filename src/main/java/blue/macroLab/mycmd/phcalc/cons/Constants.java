package blue.macroLab.mycmd.phcalc.cons;

import java.time.Year;

/** Constants
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class Constants {
	public static final int MAX_LOG_SIZE = 5000; 
	public static final int DISPLAY_WIDTH = 100;
	public static final int OPTION_WIDTH = DISPLAY_WIDTH*2/3;
	public static final int STEPS = 100; 
	public static final int CALC_STEPS = 5; 
	public static final int PRINT_DATA_STEPS = 3; 
	public static final int PRINT_RESULTS_STEPS = 6; 
    public static final int PRINT_ALL_STEPS = 7; 
	public static final int PLOT_CURVE_STEPS = 5; 
	public static final int PLOT_DERIVATIVES_STEPS = 5; 
	public static final int PLOT_ALL_STEPS = 7;
	public static final int LENGTH_TASK = 1500; 
	public static final int DELAY = 1000; 
	
	public static final String MESSAGE_PATH = "MessagesBundle";
	public static final String ERROR_DATA = "dataNull"; 
	
	// chars, patterns
	public static final String BULLET_POINT = "-";
	public static final String LP = "(";
	public static final String RP = ")";
	public static final String LS = "[";
	public static final String RS = "]";
	public static final String LA = "<";
	public static final String RA = ">";
	public static final String DPOINT = ":";
	public static final String COMMA = ",";
	public static final String SPACE = " ";
	public static final String NEW_LINE = "\n";
	public static final String HASH_TAG = "#";
	public static final String DECIMAL_FORMAT_1 = "#.";
	public static final String DELIMITATOR = "TABTAB";
	public static final String FUNC = "f";
	public static final String EQUALS = "=";
	public static final String ETC = "...";
	public static final String EMPTY = "EMPTY";
	public static final String WARN = "WARN";
	public static final String INFO = "INFO";
	
	
	// UI
	public static final String BEGINNING_MESSAGE = "Starting";
	public static final String COPYRIGHT = "Copyright(c) "+ Year.now().getValue();
	public static final char REQUIRED_MARKER = '*';
	public static final String SW_NAME = "pHCalc";
	public static final String VERSION = "1.0";
	public static final String OPTION_PARAM = "OPTION";
	public static final String COMMAND = "COMMAND";
	public static final String OPTIONAL = "optional";
	public static final String FORM = "form";
	public static final String ALT_USAGE = "  or:  ";
	public static final String FORM_1 = LP+"1st"+SPACE+FORM+RP ;
	public static final String FORM_2 = LP+"2nd"+SPACE+FORM+RP ;
	
	public static final String SHORT_OPTION = "-";
	public static final String OPTION = "--";
	public static final String VERSION_OPTION_SHORT = "V";
	public static final String VERSION_OPTION = "version";
	public static final String HELP_OPTION_SHORT = "h";
	public static final String HELP_OPTION = "help";
	public static final String VERBOSITY_OPTION_SHORT = "v";
	public static final String VERBOSITY_OPTION = "verbose";
	public static final String LANGUAGE_OPTION_SHORT = "l";
	public static final String LANGUAGE_OPTION = "language";
	public static final String LANGUAGE_PARAMETER = "language";
	public static final String VERSION_INFO = "display version info";
	public static final String HELP_INFO = "display this help message";
	public static final String CALC_SUBCOMMAND = "compute";
	public static final String PRINT_SUBCOMMAND = "print";
	public static final String PLOT_SUBCOMMAND = "plot";
	public static final String FILE_DESCR = "Saves the table in a file";
	public static final String FILE_PARAM_LABEL = "FILE";
	public static final String FILE_ARITY = "0..1";
	public static final String TYPE_OPTION_SHORT = "t";
	public static final String TYPE_OPTION = "type";
	public static final String LIST_OPTION_SHORT = "l";
	public static final String LIST_OPTION = "list";
	public static final String COLOR_OPTION_SHORT = "c";
	public static final String COLORS_OPTION = "colors";
	public static final String COLOR_OPTION = "color";
	public static final String COLOR_PARAM_LABEL = "COLOR";
	public static final String ASYMPTOTE_PARAM_LABEL = "ASYMPTOTE";
	public static final String TYPE_PARAM_LABEL = "TYPE";
	public static final String CURVE_OPTION = "curve";
	public static final String FIT_OPTION_SHORT = "f";
	public static final String FIT_OPTION = "fit";
	public static final String SMOOTH_OPTION_SHORT = "s";
	public static final String SMOOTH_OPTION = "smooth";
	public static final String XTICK_OPTION_SHORT = "x";
	public static final String XTICK_OPTION = "xtics";
	public static final String YTICK_OPTION_SHORT = "y";
	public static final String YTICK_OPTION = "ytics";
	public static final String FSTDEV_OPTION_SHORT = "1";
	public static final String FSTDEV_OPTION = "firstDerivative";
	public static final String SNDDEV_OPTION_SHORT = "2";
	public static final String SNDDEV_OPTION = "secondDerivative";
	public static final String ASYMPTOTE_OPTION_SHORT = "a";
	public static final String ASYMPTOTE_OPTION = "asymptote";
	
	// Keys
	public static final String KEY_TITLE_DATA = "titleData";
	public static final String KEY_PATH = "dataFilePath";
	public static final String KEY_OF = "of"; 
	public static final String KEY_THE = "the"; 
	public static final String KEY_SAMPLE = "sample"; 
	public static final String KEY_FIT = "curveFit";
	public static final String KEY_NOFIT = "noFit";
	public static final String KEY_PARAM = "param";
	public static final String KEY_SAMPLES = "samples";
	public static final String KEY_RESULTS_TABLE_HEADLINE = "titleTable";
	public static final String KEY_USED = "used";
	public static final String KEY_SOLUTION = "solution";
	public static final String KEY_AT = "at";
	public static final String KEY_DILUTION = "dilution";
	public static final String KEY_ANTE = "before";
	public static final String KEY_POST = "after";
	public static final String KEY_MOLMASS = "molarMass";
	public static final String KEY_CONS = "constants";
	public static final String KEY_EP = "ep";
	public static final String KEY_EP_ABV = "epABV";
	public static final String KEY_HINT_CALCULATION = "hintCalcPH";
	public static final String KEY_PH = "pH";
	public static final String KEY_MASS_VOL = "massVol";
	public static final String KEY_MASS = "mass";
	public static final String KEY_VERBOSITY_ERROR = "verbosityUnknown";
	public static final String KEY_FILE_WRITTEN = "written";
	public static final String KEY_WRITING_ERROR = "writingError";
	public static final String KEY_FILE_EXISTS = "fileExists";
	public static final String KEY_SUM_TITRATION = "sumTitrationData";
	public static final String KEY_SUM_FUNCTION = "sumFunction";
	public static final String KEY_SUM_EP = "sumEP";
	public static final String KEY_SUM_TABLE = "sumTable";
	public static final String KEY_SUM_MASS = "sumMass";
	public static final String KEY_SUM_PLOT = "sumPlot";
	public static final String KEY_SUM_FILE = "sumFile";
	public static final String KEY_SUM_APPEND = "sumAppend";
	public static final String KEY_SUM_CHART = "sumChart";
	public static final String KEY_SUM_FIT = "sumFit";
	public static final String KEY_INIT = "init";
	public static final String KEY_CREATE = "create";
	public static final String KEY_WRONG_NUMBER = "wrongNumberType";
	public static final String KEY_END = "finished";
	public static final String KEY_SUP = "sup";
	public static final String KEY_INF = "inf";
	public static final String KEY_PH_LINE = "pHline";
	public static final String KEY_DX_LINE = "dx2line";
	public static final String KEY_UNIT = "unit";
	public static final String KEY_TITRANT = "titrant";
	public static final String KEY_ACID = "acid";
	public static final String KEY_CONCENTRATION = "conc";
	public static final String KEY_AMOUNT = "amt";
	public static final String KEY_ORIGINAL = "org";
	public static final String KEY_CONVERT = "conv";
	public static final String KEY_NO_DILUTION = "noDil";
	public static final String KEY_ERROR_FILESET = "errorDataset";
	public static final String KEY_SET_VERBOSITY = "newVerbosity";
	public static final String KEY_IE = "ie";
	public static final String KEY_MYTABLE = "mytable";
	public static final String KEY_RESULTS_TBL = "resTable";
	public static final String KEY_DATA_TBL = "datTable";
	public static final String KEY_NUM_COLUMNS = "numCols";
	public static final String KEY_COLUMN_SIZE = "colSize";
	public static final String KEY_TBL_OFFSET = "tabOffset";
	public static final String KEY_SIZE_OF = "sizeOf";
	public static final String KEY_NUM_DATAROWS = "numDataRows";
	
}
