package blue.macroLab.mycmd.phcalc.ui;

import java.io.File;

import blue.macroLab.mycmd.phcalc.calc.MyCalc;
import blue.macroLab.mycmd.phcalc.calc.MyTitrationData;
import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.table.MyTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/** Print Subcommand
 * implemented for the command line user interface.
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
enum TABLE_TYPE { all, results, data }
@Command(
		 name = Constants.PRINT_SUBCOMMAND, 
		 description = "Print the calculations in a neatly formatted table",
		 helpCommand = true,
		 sortOptions = false,
		 sortSynopsis = false,
		 requiredOptionMarker = Constants.REQUIRED_MARKER, 
		 abbreviateSynopsis = true,
		 descriptionHeading = "%n@|magenta,bg(white),bold,underline Description|@:%n",
		 parameterListHeading = "%n@|green,bg(white),bold,underline Parameters|@:%n",
		 optionListHeading = "%n@|green,bg(white),bold,underline Options|@:%n",
		 //header = Constants.PRINT_SUBCOMMAND + Constants.SPACE+ Constants.COMMAND+Constants.NEW_LINE,
		 showEndOfOptionsDelimiterInUsageHelp = true,
		 customSynopsis = {
					        Constants.SW_NAME + Constants.SPACE + Constants.LS + Constants.OPTION_PARAM + Constants.RS + Constants.SPACE + Constants.PRINT_SUBCOMMAND+ Constants.SPACE + Constants.LS+ Constants.SHORT_OPTION+Constants.TYPE_OPTION_SHORT+ Constants.EQUALS + Constants.LA + Constants.TYPE_PARAM_LABEL + Constants.RA + Constants.RS + Constants.SPACE +Constants.LS +Constants.LA + Constants.FILE_PARAM_LABEL + Constants.RA+Constants.RS,
					        Constants.ALT_USAGE + Constants.SW_NAME + Constants.SPACE + Constants.LS + Constants.OPTION_PARAM + Constants.RS + Constants.SPACE + Constants.PRINT_SUBCOMMAND+ Constants.SPACE + Constants.LS +Constants.SHORT_OPTION+Constants.TYPE_OPTION_SHORT+ Constants.EQUALS + Constants.LA + Constants.TYPE_PARAM_LABEL + Constants.RA+Constants.RS,
						  }
		 )
public class PrintUI extends PHCalcTool implements Runnable {

	public PrintUI(Data model) {
		super(model);
	}
	
	@Option(names = { Constants.SHORT_OPTION+Constants.TYPE_OPTION_SHORT, Constants.OPTION+Constants.TYPE_OPTION}, description = "Specify which type of calculation should be printed in a table: ${COMPLETION-CANDIDATES}",required = true,paramLabel = Constants.LA + Constants.TYPE_PARAM_LABEL + Constants.RA  )
	TABLE_TYPE type = null;
	
	@Parameters(paramLabel = Constants.FILE_PARAM_LABEL, description = Constants.FILE_DESCR + Constants.SPACE + Constants.HASH_TAG + Constants.SPACE + Constants.OPTIONAL,arity = Constants.FILE_ARITY)
    File file;
	
	public void run() {
		
		switch(type) {
			case data:
				this.verbose(Constants.PRINT_DATA_STEPS);
				MyTitrationData myTitration = setData();
				MyTable tabData = printData(myTitration);
				getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_TABLE));
				display(file, tabData.toString());
				cancel();
				break;
			case results:
				this.verbose(Constants.PRINT_RESULTS_STEPS);
				MyTitrationData myTitrationSummary = setData();
				MyTable tabSummary =  printResults(myTitrationSummary);
				getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_TABLE));
				display(file, tabSummary.toString());
				cancel();
				break;
			default:
				this.verbose(Constants.PRINT_ALL_STEPS);
				MyTitrationData myTitrationAll = setData();
				MyTable tabDataAll = printData(myTitrationAll);
				MyTable tabRes =  printResults(myTitrationAll);
				getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_TABLE));
				getModel().getLogger().summarize(this.getModel().getMessageValue(Constants.KEY_SUM_APPEND));
				String sTab = printAll(tabDataAll, tabRes);
				display(file, sTab);
				cancel();
				break;
		}
	}
	
	private MyTable printData(MyTitrationData myTitration) {
        myTitration.generateTableData();
        double [][] rows = myTitration.toData();
        MyTable tab = new MyTable(rows  ,myTitration.getTblHeadline(),myTitration.getTblHeaders(),this.getModel());
        return tab;
        
	}
	
	private MyTable printResults(MyTitrationData myTitration) {
		MyCalc calc = calcPoints(myTitration);
		this.calcMass(calc);
		calc.generateResultsData();
    	calc.generateConstantsData();
    	MyTable tab = new MyTable(1,calc.getMap(),calc.getTblHeadline(), this.getModel());
    	return tab;
	}
	
	private String printAll(MyTable tabData, MyTable tabRes) {
		StringBuilder sb = new StringBuilder();
		sb.append(tabData.toString());
		sb.append(Constants.NEW_LINE);
		sb.append(tabRes.toString());
		return sb.toString();
	}
}
