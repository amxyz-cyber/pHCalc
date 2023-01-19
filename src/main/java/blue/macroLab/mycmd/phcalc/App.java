package blue.macroLab.mycmd.phcalc;

import picocli.CommandLine;
import org.fusesource.jansi.AnsiConsole;
import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.ui.CalcUI;
import blue.macroLab.mycmd.phcalc.ui.HelperFactory;
import blue.macroLab.mycmd.phcalc.ui.PHCalcTool;
import blue.macroLab.mycmd.phcalc.ui.PlotUI;
import blue.macroLab.mycmd.phcalc.ui.PrintUI;

public class App 
{
    public static void main( String[] args )
    {	
    	Data data = new Data("pH Calculator");
    	AnsiConsole.systemInstall();
    	CommandLine cLine = new CommandLine(new PHCalcTool(data));
    	data.getLogger().addLog(data.getMessageValue(Constants.KEY_INIT) + Constants.SPACE + Constants.CALC_SUBCOMMAND);
    	cLine.addSubcommand(Constants.CALC_SUBCOMMAND,    new CalcUI(data));
    	data.getLogger().addLog(data.getMessageValue(Constants.KEY_INIT) + Constants.SPACE + Constants.PRINT_SUBCOMMAND);
    	cLine.addSubcommand(Constants.PRINT_SUBCOMMAND,   new PrintUI(data));
    	data.getLogger().addLog(data.getMessageValue(Constants.KEY_INIT) + Constants.SPACE + Constants.PLOT_SUBCOMMAND);
    	cLine.addSubcommand(Constants.PLOT_SUBCOMMAND,   new PlotUI(data));
    	cLine.setUsageHelpAutoWidth(true);
    	//cLine.setUsageHelpWidth(Constants.DISPLAY_WIDTH);
    	cLine.setCaseInsensitiveEnumValuesAllowed(true);
    	cLine.setHelpFactory(HelperFactory.createCustomizedUsageHelp());
    	int exitCode = cLine.execute(args);
    	AnsiConsole.systemUninstall();
    	data.getLogger().setLog();
    	System.out.println(data.getLogger().getLog());
    }
    
}
