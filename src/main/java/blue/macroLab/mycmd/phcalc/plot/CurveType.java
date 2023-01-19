package blue.macroLab.mycmd.phcalc.plot;

/** CurveType Enums
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public enum CurveType {
	EDGED("Edged Curves"),
	SMOOTHED("Smoothed Curves"),
	NONE("No smooth"),
	FITTED("Curve fitter"),
	UNFITTED("Without curve fit"),
	USE_XTICKS("xtics"),
	USE_YTICKS("ytics");
	
	private final String label;
	
	/**
	 * Constructor.
	 * 
	 * Saves the properties for a multiplot
	 */
    CurveType(String label) {
    	this.label = label;
    }

	public String getLabel() {
		return label;
	}

}
