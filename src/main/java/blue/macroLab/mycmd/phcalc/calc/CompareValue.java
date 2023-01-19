package blue.macroLab.mycmd.phcalc.calc;

import java.util.Comparator;

/** Compares the y values of two data points
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class CompareValue implements Comparator<MyTitration> {

	public int compare(MyTitration arg0, MyTitration arg1) {
		if (arg0.getPoint().getY() > arg1.getPoint().getY())
		      return 1;
		if (arg0.getPoint().getY() == arg1.getPoint().getY())
		      return 0;
		      return -1;
	}

}
