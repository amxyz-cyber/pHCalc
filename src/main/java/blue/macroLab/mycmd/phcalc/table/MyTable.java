package blue.macroLab.mycmd.phcalc.table;

import java.util.LinkedHashMap;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.model.MyMessage;
import blue.macroLab.mycmd.phcalc.utils.Utility;

/** MyTable Model Object
 * Generates a string table with n columns
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyTable {
	public static final String BORDER = "+";
	public static final String COL_SEP = "|";
	public static final String ROW_SEP = "-";
	public static final String HEADER_BORDER = "=";
	public static final int TABLE_WIDTH = 100;
	public static final int KEY_COLUMN = 30;
	private boolean hasCapitals = false;
	private int numColumns=0;
	private int columnSize=0;
	private int diff = 0;
	private int widestColumn = 0;
	private int dimension = 0;
	private StringBuilder sb;
	private LinkedHashMap<String, String> map;
	private double [][] dataRows;
	private String [] headers;
	private String header;
	private MyMessage logger;
	private Data model;
	
	/**
	  * Constructor: used for a 2 column table
	  * 
	  * @param numCol Number of Columns
	  * @param map Contains the calculated results  
	  * @param header Table headline
	  * @param model Data Model Object 
	  */
	public MyTable(int numCol,LinkedHashMap<String, String> map,String header, Data model) {
		initMyTable(model,numCol,header);
		String initLog = model.getMessageValue(Constants.KEY_CREATE) + Constants.SPACE + model.getMessageValue(Constants.KEY_RESULTS_TBL);
		logger.addLog(initLog);
		this.map = map;
		calcColumnSize(KEY_COLUMN);
		calcTableOffset(KEY_COLUMN);
		generateTable();
	}
	
	/**
	  * Constructor: used for a table with n columns
	  * 
	  * @param rows Data rows
	  * @param header Table headline
	  * @param headers Column headers
	  * @param model Data Model Object 
	  */
	public MyTable(double [][] rows  ,String header,String [] headers, Data model) {
		initMyTable(model,rows[0].length,header);
		String initLog = model.getMessageValue(Constants.KEY_CREATE) + Constants.SPACE + model.getMessageValue(Constants.KEY_DATA_TBL);
		logger.addLog(initLog);
		this.dataRows = rows;
		this.headers = headers;
		this.dimension = rows[0].length;
		calcColumnSize();
		calcTableOffset();
		getMaxHeader();
		generateTableData();
	}
	
	/** 
	  * Helper function for initializing the constructor
	  * @param model Data model object
	  * @param numCol Number of Columns
	  * @param header Table headline
	  */
	private void initMyTable(Data model,int numCol,String header) {
		this.model = model;
		this.logger = model.getLogger();
		String initLog = model.getMessageValue(Constants.KEY_INIT) + Constants.SPACE + model.getMessageValue(Constants.KEY_MYTABLE);
		logger.addLog(initLog);
		this.numColumns = numCol;
		this.header = header;
		logger.addLog(model.getMessageValue(Constants.KEY_NUM_COLUMNS)+Constants.DPOINT + Constants.SPACE +numColumns);
		sb = new StringBuilder();
		
	}
	
	private void calcColumnSize(int columnWidth) {
		columnSize = (TABLE_WIDTH - 2 - this.numColumns-1 - columnWidth+1) / this.numColumns ;
		logger.addLog(model.getMessageValue(Constants.KEY_COLUMN_SIZE) +Constants.DPOINT + Constants.SPACE +this.columnSize);
		
	}
	
	private void calcColumnSize() {
		columnSize = (TABLE_WIDTH - (this.numColumns+1)) / this.numColumns ;
		logger.addLog(model.getMessageValue(Constants.KEY_COLUMN_SIZE) +Constants.DPOINT + Constants.SPACE +this.columnSize);
		
	}
	
	private void calcTableOffset(int columnWidth) {
		diff = TABLE_WIDTH - (columnWidth + ((this.numColumns)*this.columnSize) + 2 + this.numColumns);
		logger.addLog(model.getMessageValue(Constants.KEY_TBL_OFFSET) +Constants.DPOINT + Constants.SPACE+this.diff);
	}
	
	private void calcTableOffset() {
		diff = TABLE_WIDTH -  ((this.numColumns)*this.columnSize) - (this.numColumns+1);
		logger.addLog(model.getMessageValue(Constants.KEY_TBL_OFFSET) +Constants.DPOINT + Constants.SPACE+this.diff);
	}
	
	/** 
	  * Generates a table with 2 columns.
	  * Used for the results table
	  */
	private void generateTable() {
		logger.addLog(model.getMessageValue(Constants.KEY_SIZE_OF) + Constants.SPACE + model.getMessageValue(Constants.KEY_RESULTS_TBL) +Constants.DPOINT + Constants.SPACE+this.map.size());
		generateTblTop();
		generateTblHeader();
		for(String key : map.keySet()) {
			if (key.equals(Constants.EMPTY)) {
				generateRow();
				hasCapitals = true;
			}else if (map.get(key) == null && hasCapitals) {
				generateRow(key);
			} else {
				String value = map.get(key);
				generateRow(key,value);
			}
		}
		generateTblBottom();
	}
	
	/** 
	  * Generates a table with n columns.
	  * Used for the data rows
	  */
	private void generateTableData() {
		int size = TABLE_WIDTH-2;
		logger.addLog(model.getMessageValue(Constants.KEY_NUM_DATAROWS) +Constants.DPOINT + Constants.SPACE+this.dataRows.length);
		generateTblTop();
		generateTblHeader();
		generateTblHeaders();
		
		for (int i =0; i < this.dataRows.length; i++) {
			 for (int j = 0; j < this.dimension; j++) {
				 double dVal = dataRows[i][j];
				 String s = Utility.toString(dVal, 2);
				 generateColumn(j, s);
			 }
			 generateRowBottom(size);
		}
		generateTblBottom();
	}
	
	/**
	 * Generates the table headline
	 */
	private void generateTblHeader() {
		int size = TABLE_WIDTH-2;
		String [] wrappedHeader = Utility.cut(this.header,size);
		for (int i = 0; i < wrappedHeader.length; i++) {
			String h = wrappedHeader[i];
			String padded = Utility.pad( h.toUpperCase(), Constants.SPACE, size);
			sb.append(BORDER+padded+BORDER);
			Utility.addNewLine(sb);
		}
		String bottomHeader = Utility.repeat(HEADER_BORDER , size);  
		sb.append(BORDER+bottomHeader+BORDER);
		Utility.addNewLine(sb);
	}
	
	/** 
	  * Generates the column headers
	  */
	private void generateTblHeaders() {
		int size = TABLE_WIDTH-2;
		
		for (int i = 0; i < this.headers.length; i++) {
			String s = headers[i];
			generateColumn(i, s);
		}
		generateRowBottom(size);
	}
	
	private void generateRowBottom(int size) {
		String row_bottom = Utility.repeat(ROW_SEP, size);  
		sb.append(BORDER+row_bottom+BORDER);
		Utility.addNewLine(sb);
	}
	
	private void generateTblBottom() {
		String bottom = Utility.repeat(BORDER , TABLE_WIDTH);  
		sb.append(bottom);
		Utility.addNewLine(sb);
	}
	
	private void generateTblTop() {
		String top = Utility.repeat(BORDER, TABLE_WIDTH);  
		sb.append(top);
		Utility.addNewLine(sb);
	}
	
	/**
	 * Generates a row with 2 columns
	 * @param k Key
	 * @param v Value
	 */
	private void generateRow(String k,String v) {
		String [] wrappedKey = null;
		if (k.length() <= KEY_COLUMN) {
			wrappedKey = new String[]{k};
		} else {
			wrappedKey = Utility.cut(k,KEY_COLUMN);
		}
		String wrappedValue = fitString(v, this.columnSize + diff );
		String line = "";		
		for (int i = 0; i < wrappedKey.length; i++) {
			String key = wrappedKey[i];
			String padded = Utility.pad(key, Constants.SPACE, KEY_COLUMN);
			String colKey = BORDER+padded+COL_SEP;  
			if (i == wrappedKey.length - 1) {
				line = colKey;
			} else {
				sb.append(colKey);
				Utility.addNewLine(sb);
			}
		}
		sb.append(line+wrappedValue+BORDER);
		Utility.addNewLine(sb);
		String row_bottom = Utility.repeat(ROW_SEP, TABLE_WIDTH-2);  
		sb.append(BORDER+row_bottom+BORDER);
		Utility.addNewLine(sb);
	}
	
	/**
	 * Generates a row with only one value
	 * @param k Key
	 */
	private void generateRow(String k) {
		int size = TABLE_WIDTH-2;
		String [] wrappedHeader = Utility.cut(k,size);
		for (int i = 0; i < wrappedHeader.length; i++) {
			String h = wrappedHeader[i];
			String padded = Utility.pad( h.toUpperCase(), Constants.SPACE, size);
			sb.append(BORDER+padded+BORDER);
			Utility.addNewLine(sb);
		}
		generateRowBottom(size); 
		hasCapitals=false;
	}
	
	/**
	 * Generates an empty row
	 */
	private void generateRow() {
		String row = Utility.repeat(Constants.SPACE, TABLE_WIDTH-2);  
		sb.append(BORDER+row+BORDER);
		Utility.addNewLine(sb);
		String row_bottom = Utility.repeat(ROW_SEP, TABLE_WIDTH-2);  
		sb.append(BORDER+row_bottom+BORDER);
		Utility.addNewLine(sb);
	}
	
	private void generateColumn(int i, String s) {
		if (i == 0) {
			sb.append(BORDER);
		}
		
		if(i == this.widestColumn) {
			String padded = fitString(s, this.columnSize+diff);
			sb.append(padded);
		} else {
			String padded = fitString(s, this.columnSize);
			sb.append(padded);
		}
		
		if (i == this.dimension - 1) {
			sb.append(BORDER);
			Utility.addNewLine(sb);
		} else {
			sb.append(COL_SEP);
		}
	}
	
	/**
	 * looks for the biggest column header
	 */
	private void getMaxHeader() {
		int max = 0;
		int size = this.headers.length;
		for (int i = 0; i < size; i++) {
			int len = headers[i].length();
			if(len > max) {
				max = len;
				this.widestColumn = i;
			}
		}
	}
	
	/**
	 * Fits the string by cutting it and padding it with spaces
	 * @param s String
	 * @param size Size of the column or row
	 * @return fitted String
	 */
	private String fitString(String s, int size) {
		String cut = Utility.cutOff(s, size);
		return Utility.pad(cut, Constants.SPACE, size);
	}
	
	/**
	 * Converts the table to a string
	 * @return table as a string
	 */
	public String toString() {
		return sb.toString();
	}
}
