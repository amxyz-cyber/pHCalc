package blue.macroLab.mycmd.phcalc.model;

/** Verbosity Enums
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public enum Verbosity {
	ALL("log everything",4),
	WARNING("log the warnings",3),
	RESUMEE("print only a summary of the steps",2),
	PROGRESS("display progress bar",1),
	NONE("don't log anything at all",0);
	
    private final String label;
    private final int verbosityLevel;

    Verbosity(String s,int v) {
        this.label = s;
        this.verbosityLevel = v;
    }

	public String getLabel() {
		return this.label;
	}

	public int getLevel() {
		return verbosityLevel;
	}
	
	public static Verbosity getVerbosity(int n) {
		Verbosity verb = null;
		for (Verbosity v: Verbosity.values()) {
				if (v.getLevel() == n) {
					verb = v;
					break;
				} else {
					continue;
				}
		}
		return verb;
	}
}
