package blue.macroLab.mycmd.phcalc.calc;
import java.util.Comparator;

/** Compares the x values of two data points
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class CompareData implements Comparator<MyTitration> {
	  public int compare(MyTitration a, MyTitration b) {
	    if (a.getPoint().getX() > b.getPoint().getX())
	      return 1;
	    if (a.getPoint().getX() == b.getPoint().getX())
	      return 0;
	      return -1;
	  }
	}
