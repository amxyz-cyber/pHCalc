package blue.macroLab.mycmd.phcalc.ui;

import java.io.File;

import blue.macroLab.mycmd.phcalc.calc.MyCalc;
import blue.macroLab.mycmd.phcalc.calc.MyTitrationData;
import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.table.MyTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/** Calc subcommand 
 * implemented for the command line user interface.
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
@Command(
			name = Constants.CALC_SUBCOMMAND,
			description = "Calculate the equivalence point as well as its pH value",
			helpCommand = true,
			descriptionHeading = "%n@|magenta,bg(white),bold,underline Description|@:%n",
			parameterListHeading = "%n@|green,bg(white),bold,underline Parameters|@:%n",
			optionListHeading = "%n@|green,bg(white),bold,underline Options|@:%n",
			//header = Constants.CALC_SUBCOMMAND + Constants.SPACE+ Constants.COMMAND+Constants.NEW_LINE,
			showEndOfOptionsDelimiterInUsageHelp = true,
			customSynopsis = {
					        Constants.SW_NAME + Constants.SPACE + Constants.LS + Constants.OPTION_PARAM + Constants.RS + Constants.SPACE + Constants.CALC_SUBCOMMAND+  Constants.SPACE + Constants.LS +Constants.LA +Constants.FILE_PARAM_LABEL+Constants.RS +Constants.RA ,
					        Constants.ALT_USAGE + Constants.SW_NAME + Constants.SPACE + Constants.LS + Constants.OPTION_PARAM + Constants.RS + Constants.SPACE + Constants.CALC_SUBCOMMAND,
						  }
		)
public class CalcUI extends PHCalcTool implements Runnable {
	
	public CalcUI(Data m) {
		super(m);
	}
	
	@Parameters(paramLabel = Constants.FILE_PARAM_LABEL, description = Constants.FILE_DESCR + Constants.SPACE + Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL,arity = Constants.FILE_ARITY)
    File file;
	
	public void run() {
		this.verbose(Constants.CALC_STEPS);
		MyTitrationData myTitration = setData();
		MyCalc calc = calcPoints(myTitration);
    	calc.generateEPData();
    	getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_TABLE));
    	MyTable tab = new MyTable(1,calc.getMap(),calc.getTblHeadline(), getModel());
    	display(file, tab.toString());
    	cancel();
	}
}
