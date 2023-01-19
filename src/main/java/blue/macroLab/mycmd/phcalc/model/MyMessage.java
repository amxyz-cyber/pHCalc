package blue.macroLab.mycmd.phcalc.model;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.utils.Utility;
import javafx.beans.property.*;

/** Represents a logger using StringBuilder
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyMessage {
	private String _log ="";
	private SimpleStringProperty log;
	private StringBuilder buffer;
	private StringBuilder bufferWarn;
	private StringBuilder bufferSummary;
	private Verbosity verbosity;
	private Data model;
	
	public MyMessage(Data m) {
		resetBuffer();
		this.verbosity = Verbosity.NONE;
		this.model = m;
	}
	
	public void resetBuffer() {
		this.buffer = new StringBuilder();
		this.bufferWarn = new StringBuilder();
		this.bufferSummary = new StringBuilder();
	}
	
	public void setMinimumBuffer(int bufferSize) {
		this.buffer.ensureCapacity(bufferSize);
		this.bufferWarn.ensureCapacity(bufferSize);
		this.bufferSummary.ensureCapacity(bufferSize);
	}
	
	public void setVerbosity(Verbosity verbosity) {
		this.verbosity = verbosity;
		addLog(this.model.getMessageValue(Constants.KEY_SET_VERBOSITY)+Constants.SPACE +verbosity.getLevel()+this.model.getMessageValue(Constants.KEY_IE)+Constants.SPACE +verbosity.getLabel()); 
	}
	
	public final String getLog() {
		if(log == null) {
			return _log;
		} else 
		return log.get();
	}
	
	public final void setLog() {
		if(log == null) {
			_log = this.getBuffer();
		} else {
		log.set(this.getBuffer());
		}
	}
	
	public final StringProperty logProperty() {
		if(log == null) {
			log = new SimpleStringProperty(this,"log",_log);
		}
		return log; 
	}
	
	public void addLog(String value) {
			String s = Constants.INFO + Constants.DPOINT + Constants.DPOINT +Constants.SPACE+value;
			log(s,this.buffer);
	}
	
	public void addWarn(String value) {
			String s = Constants.WARN + Constants.DPOINT+Constants.SPACE+value;
			log(s,this.bufferWarn);
	}
	
	public void summarize(String value) {
			String s = Constants.BULLET_POINT + Constants.DPOINT+Constants.SPACE+value;
			log(s,this.bufferSummary);
	}

	private void log(String value,StringBuilder sb) {
			Utility.addNewLine(sb);
			sb.append(value);
	}
	
	public <N> void logValue(String param,N value) {
		String s = model.getMessageValue(Constants.KEY_PARAM) + Constants.SPACE + Constants.LP + param + Constants.RP + Constants.DPOINT + Constants.SPACE + value;
		addLog(s);
	}

	private String getBuffer() {
		String sBuffer = "";
		switch(verbosity) {
			case ALL:
				sBuffer = this.buffer.toString();
				break;
			case WARNING:
				sBuffer = this.bufferWarn.toString();
				break;
			case RESUMEE:
				sBuffer = this.bufferSummary.toString();
				break;
			default:
				break;
		}
		return sBuffer;
	}
}
