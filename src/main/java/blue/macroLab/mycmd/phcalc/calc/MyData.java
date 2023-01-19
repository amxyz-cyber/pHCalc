package blue.macroLab.mycmd.phcalc.calc;

import java.io.File;
import java.io.IOException;
import com.panayotis.gnuplot.dataset.FileDataSet;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;

/** Reads in the data file
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyData {
	private String fileData;
	private FileDataSet dataset;
	private File file;
	private Data data;
	
	public MyData(String d,Data m) {
		this.fileData = d;
		this.data = m;
	    
	}
	
	public MyData(Data m) {
		this.data = m;    
	}
	
	public void setDataset() {
		try{
		file = new File(fileData);
		this.dataset = new FileDataSet(file);
        } catch (IOException ex) {
            this.data.getLogger().addWarn(this.data.getMessageValue(Constants.KEY_ERROR_FILESET));
        	this.data.getLogger().addWarn(ex.getMessage());
        }
	}

	public FileDataSet getDataset() {
		return dataset;
	}
	
	/** 
	 * Gets a value from the data set
	 * 
	 * @param iCol specifies the column id
	 * @param index specifies the row id
	 * @return the value from a specific row at a given column
	 */
	public Double getDataPoint(int iCol, int index ) {
		String sDataPoint = this.dataset.getPointValue(index, iCol);
		Double dVal = Double.parseDouble(sDataPoint);
		return dVal;
	}

	public File getFile() {
		return file;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

}
