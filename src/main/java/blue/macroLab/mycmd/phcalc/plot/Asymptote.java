package blue.macroLab.mycmd.phcalc.plot;

/** Asymptote Enums
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public enum Asymptote {
	// used if the curve is somehow symmetric, i.e. not too steep or flat
	SYMMETRIC_CURVE(1.0,1.0),
	// used if the curve seems to be flat
	FLAT_CURVE(0.5,0.1),
	// curve is steep
	STEEP_CURVE(1.1,1.0),
	// calculates the params N and B
	ADVANCED_CALC(-1,-1),
	// calculates the params N and B
	SIMPLE_CALC(-1,-1),
	// uses the parameters from the config file
	NONE(-1,-1);
	
    private final double paramN;
    private final double paramB;

    Asymptote(double n,double b) {
        this.paramN = n;
        this.paramB = b;
    }

	public double getN() {
		return paramN;
	}
	
	public double getB() {
		return paramB;
	}

}
