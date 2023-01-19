package blue.macroLab.mycmd.phcalc.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.panayotis.gnuplot.style.NamedPlotColor;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;

/** Utility
 * contains functions used across the command line tool
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class Utility {
	
	public static boolean fileExists(File f) {
		return f.exists();
	}
	
	public static boolean fileExists(String sFile) {
		return new File(sFile).exists();
	}
	
	public static void writeText(File to,String text,Data model) {
    	CharSink bsink = Files.asCharSink(to,Charset.defaultCharset());
    	try {
    		bsink.write(text);
			model.getLogger().addLog(model.getMessageValue(Constants.KEY_FILE_WRITTEN));
		} catch (IOException e) {
			model.getLogger().addWarn(model.getMessageValue(Constants.KEY_WRITING_ERROR) + Constants.NEW_LINE+e.getMessage());
		}
    }
	
	public static String toString(double amount, int n) {
		String precision = Utility.repeat(Constants.HASH_TAG, n);
		DecimalFormat df = new DecimalFormat(Constants.DECIMAL_FORMAT_1+precision);
		return df.format(amount);
	}
	
	public static void addNewLine(StringBuilder r) {
		r.append(System.getProperty("line.separator"));
	}
	
	public static NamedPlotColor getPlotColor(String s) {
		NamedPlotColor mycolor = null;
		for (NamedPlotColor c: NamedPlotColor.values()) {
			if (c.toString().equals(s)) {
				mycolor = c;
				break;
			}
		}
		return mycolor;
		}
	
	public static String pad(String s, String c, int size) {
		int n = s.length();
		int diff = size - n;
		if (diff > 0) {
			String repeated = repeat(c, diff);
			return s+repeated;
		} else {
			return s;
		}
	}
	
	public static String repeat(String c, int n) {
		return new String(new char[n]).replace("\0", c);
	}
	
	public static String cutOff(String s, int length) {
		if (s.length() > length && length >= 0 ) {
			return s.substring(0, length);
		} else {
			return s;
		}
	}
	
	public static String [] cut(String s,int width) {
		String [] arr = s.split(Constants.SPACE);
		StringBuilder sb = new StringBuilder();
		int size = arr.length;
		String line = "";
		for (int i = 0; i < size;i++) {
			String str_i = arr[i];
			if (i > 0 && line.length() + str_i.length() < width) {
				line += Constants.SPACE + str_i;
			} else if (str_i.length() > width) {
				String part1 = str_i.substring(0, width);
				line += Constants.SPACE + str_i.substring( width);
				sb.append(part1+Constants.DELIMITATOR);
			} else {
				sb.append(line+Constants.DELIMITATOR);
				line = str_i;
			}
		}
		sb.append(line+Constants.DELIMITATOR);
		return sb.toString().split(Constants.DELIMITATOR);
	}
}
