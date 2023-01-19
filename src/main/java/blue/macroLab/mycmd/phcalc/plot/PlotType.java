package blue.macroLab.mycmd.phcalc.plot;

import com.panayotis.gnuplot.style.Smooth;
import com.panayotis.gnuplot.style.Style;

/** PlotType Enums
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public enum PlotType {
	
	PH("Measurement",1,Smooth.FREQUENCY,Smooth.SBEZIER,Style.LINES,3),
	Fst_Derivative ("f'",2,Smooth.FREQUENCY,Smooth.CSPLINES,Style.LINES,3),
	Snd_Derivative ("f''",3,Smooth.FREQUENCY,Smooth.CSPLINES,Style.LINES,3),
	CONST("0",-1,Smooth.FREQUENCY,Smooth.FREQUENCY,Style.LINES,1),
	EP("Equivalence Point",-1,Smooth.FREQUENCY,Smooth.CSPLINES,Style.DOTS,3);
	
	private final String label;
    private final int column;
    private final Smooth smooth;
    private final Smooth edged;
    private final Style stl;
    private final int lineWidth;
    
    /**
	 * Constructor.
	 * 
	 * Saves the properties for predefined curve
	 */
    PlotType(String label, int col,Smooth edged,Smooth smooth,Style stl, int w) {
        this.label = label;
        this.column = col;
        this.smooth = smooth;
        this.edged = edged;
        this.stl = stl;
        this.lineWidth = w;
    }

    public String getLabel() {
        return this.label;
    }
    public int getColumn() {
        return this.column;
    }

	public Smooth getSmooth() {
		return smooth;
	}

	public Smooth getEdged() {
		return edged;
	}

	public Style getStl() {
		return stl;
	}

	public int getWidth() {
		return lineWidth;
	}
    
}
