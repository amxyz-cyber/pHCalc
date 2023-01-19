package blue.macroLab.mycmd.phcalc.settings;

import java.io.File;

import com.panayotis.gnuplot.style.NamedPlotColor;

import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.utils.Utility;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ChartSettings Model Object
 * <P>It contains the values obtained from the <em>config file</em>
 * <P>and are used for plotting a complete chart. 
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
 */
public class ChartSettings {
	private String _dataFile = "";
	private String _fileName = "";
	private String _unit = "";
	private String _chartTitle = "";
	private String _xLabel = "";
	private String _yLabel = "";
	private String _folder = "";
	private String _path = "";
	private String _colorMeasurement = "";
	private String _colorFirstDrv = "";
	private String _colorSecondDrv = "";
	private String _colorFunc0 = "";
	private String _colorFuncEP = "";
	private int _step = 0;
	private double _xtick = 0;
	private double _xRotate = 0;
	private double _ytick = 0;
	private double _xMin = 0;
	private double _xMax = 0;
	private double _yMin = 0;
	private double _yMax = 0;
	private double _paramN = 0;
	private double _paramB = 0;
	private double _paramQ = 0;
	
	private NamedPlotColor colorLine;
	private NamedPlotColor colorFirst;
	private NamedPlotColor colorSecond;
	private NamedPlotColor colorConst0;
	private NamedPlotColor colorConstEP;
	
	private SimpleStringProperty dataFile;
	private SimpleStringProperty fileName;
	private SimpleStringProperty unit; 
	private SimpleStringProperty xLabel; 
	private SimpleStringProperty yLabel; 
	private SimpleStringProperty chartTitle; 
	private SimpleStringProperty folder;
	private SimpleStringProperty path;
	private SimpleStringProperty colorMeasurement;
	private SimpleStringProperty colorFirstDrv;
	private SimpleStringProperty colorSecondDrv;
	private SimpleStringProperty colorFunc0;
	private SimpleStringProperty colorFuncEP;
	private SimpleIntegerProperty step;
	
	private SimpleDoubleProperty ytick;
	private SimpleDoubleProperty xtick;
	private SimpleDoubleProperty xMin;
	private SimpleDoubleProperty xMax;
	private SimpleDoubleProperty yMin;
	private SimpleDoubleProperty yMax;
	private SimpleDoubleProperty paramN;
	private SimpleDoubleProperty paramB;
	private SimpleDoubleProperty paramQ;
	private SimpleDoubleProperty xRotate;
	
	private Data data;
	
	
	public ChartSettings(Data m)  {
		data = m;
		this.setColorMeasurement("ROYALBLUE");
		this.setColorFirstDrv("FOREST_GREEN");
		this.setColorSecondDrv("ORANGE_RED");
		this.setColorFunc0("BLACK");
		this.setColorFuncEP("BLACK");
		this.setColorLine("");
		this.setColorFirst("");
		this.setColorSecond("");
		this.setColorConst0("");
		this.setColorConstEP("");
		this.setXMin(0);
		this.setYMin(0);
		this.setYMax(14);
	}
	
	public final Integer getStep() {
		if(step == null) {
			return _step;
		} else 
		return step.get();
	}
	
	public final IntegerProperty stepProperty() {
		if(step == null) {
			step = new SimpleIntegerProperty(this,"step",_step);
		}
		return step; 
	}
	
	public final void setStep(int value) {
		if(step == null) {
			_step = value;
		} else {
			step.set(value);
		}
	}
	
	public final void setYtick(double value) {
		if(ytick == null) {
			_ytick = value;
		} else {
			ytick.set(value);
		}
	}
	
	public final Double getYtick() {
		if(ytick == null) {
			return _ytick;
		} else 
		return ytick.get();
	}
	
	public final DoubleProperty ytickProperty() {
		if(ytick == null) {
			ytick = new SimpleDoubleProperty(this,"ytick",_ytick);
		}
		return ytick; 
	}
	
	public final void setXRotate(double value) {
		if(xRotate == null) {
			_xRotate = value;
		} else {
			xRotate.set(value);
		}
	}
	
	public final Double getXRotate() {
		if(xRotate == null) {
			return _xRotate;
		} else 
		return xRotate.get();
	}
	
	public final DoubleProperty xRotateProperty() {
		if(xRotate == null) {
			xRotate = new SimpleDoubleProperty(this,"xRotate",_xRotate);
		}
		return xRotate; 
	}
	
	public final void setXtick(double value) {
		if(xtick == null) {
			_xtick = value;
		} else {
			xtick.set(value);
		}
	}
	
	public final Double getXtick() {
		if(xtick == null) {
			return _xtick;
		} else 
		return xtick.get();
	}
	
	public final DoubleProperty xtickProperty() {
		if(xtick == null) {
			xtick = new SimpleDoubleProperty(this,"xtick",_xtick);
		}
		return xtick; 
	}
	
	public final void setXMin(double value) {
		if(xMin == null) {
			_xMin = value;
		} else {
			xMin.set(value);
		}
	}
	
	public final Double getXMin() {
		if(xMin == null) {
			return _xMin;
		} else 
		return xMin.get();
	}
	
	public final DoubleProperty xMinProperty() {
		if(xMin == null) {
			xMin = new SimpleDoubleProperty(this,"xMin",_xMin);
		}
		return xMin; 
	}
	
	public final void setXMax(double value) {
		if(xMax == null) {
			_xMax = value;
		} else {
			xMax.set(value);
		}
	}
	
	public final Double getXMax() {
		if(xMax == null) {
			return _xMax;
		} else 
		return xMax.get();
	}
	
	public final DoubleProperty xMaxProperty() {
		if(xMax == null) {
			xMax = new SimpleDoubleProperty(this,"xMax",_xMax);
		}
		return xMax; 
	}
	
	public final void setYMin(double value) {
		if(yMin == null) {
			_yMin = value;
		} else {
			yMin.set(value);
		}
	}
	
	public final Double getYMin() {
		if(yMin == null) {
			return _yMin;
		} else 
		return yMin.get();
	}
	
	public final DoubleProperty yMinProperty() {
		if(yMin == null) {
			yMin = new SimpleDoubleProperty(this,"yMin",_yMin);
		}
		return yMin; 
	}
	
	public final void setYMax(double value) {
		if (yMax == null) {
			_yMax = value;
		} else {
			yMax.set(value);
		}
	}

	public final Double getYMax() {
		if (yMax == null) {
			return _yMax;
		} else
			return yMax.get();
	}

	public final DoubleProperty yMaxProperty() {
		if (yMax == null) {
			yMax = new SimpleDoubleProperty(this, "yMax", _yMax);
		}
		return yMax;
	}
	
	public final void setParamN(double value) {
		if (paramN == null) {
			_paramN = value;
		} else {
			paramN.set(value);
		}
	}

	public final Double getParamN() {
		if (paramN == null) {
			return _paramN;
		} else
			return paramN.get();
	}

	public final DoubleProperty paramNProperty() {
		if (paramN == null) {
			paramN = new SimpleDoubleProperty(this, "paramN", _paramN);
		}
		return paramN;
	}
	
	public final void setParamB(double value) {
		if (paramB == null) {
			_paramB = value;
		} else {
			paramB.set(value);
		}
	}

	public final Double getParamB() {
		if (paramB == null) {
			return _paramB;
		} else
			return paramB.get();
	}

	public final DoubleProperty paramBProperty() {
		if (paramB == null) {
			paramB = new SimpleDoubleProperty(this, "paramB", _paramB);
		}
		return paramB;
	}
	
	public final void setParamQ(double value) {
		if (paramQ == null) {
			_paramQ = value;
		} else {
			paramQ.set(value);
		}
	}

	public final Double getParamQ() {
		if (paramQ == null) {
			return _paramQ;
		} else
			return paramQ.get();
	}

	public final DoubleProperty paramQProperty() {
		if (paramQ == null) {
			paramQ = new SimpleDoubleProperty(this, "paramQ", _paramQ);
		}
		return paramQ;
	}

	public final String getColorMeasurement() {
		if(colorMeasurement == null) {
			return _colorMeasurement;
		} else 
		return colorMeasurement.get();
	}
	
	public final StringProperty colorMeasurementProperty() {
		if(colorMeasurement == null) {
			colorMeasurement = new SimpleStringProperty(this,"colorMeasurement",_colorMeasurement);
		}
		return colorMeasurement; 
	}
	
	public final void setColorMeasurement(String value) {
		if(colorMeasurement == null) {
			_colorMeasurement = value;
		} else {
			colorMeasurement.set(value);
		}
	}
	
	public final String getColorFirstDrv() {
		if(colorFirstDrv == null) {
			return _colorFirstDrv;
		} else 
		return colorFirstDrv.get();
	}
	
	public final StringProperty colorFirstDrvProperty() {
		if(colorFirstDrv == null) {
			colorFirstDrv = new SimpleStringProperty(this,"colorFirstDrv",_colorFirstDrv);
		}
		return colorFirstDrv; 
	}
	
	public final void setColorFirstDrv(String value) {
		if(colorFirstDrv == null) {
			_colorFirstDrv = value;
		} else {
			colorFirstDrv.set(value);
		}
	}
	
	public final String getColorSecondDrv() {
		if(colorSecondDrv == null) {
			return _colorSecondDrv;
		} else 
		return colorSecondDrv.get();
	}
	
	public final StringProperty colorSecondDrvProperty() {
		if(colorSecondDrv == null) {
			colorSecondDrv = new SimpleStringProperty(this,"colorSecondDrv",_colorSecondDrv);
		}
		return colorSecondDrv; 
	}
	
	public final void setColorSecondDrv(String value) {
		if(colorSecondDrv == null) {
			_colorSecondDrv = value;
		} else {
			colorSecondDrv.set(value);
		}
	}
	
	public final String getColorFunc0() {
		if(colorFunc0 == null) {
			return _colorFunc0;
		} else 
		return colorFunc0.get();
	}
	
	public final StringProperty colorFunc0Property() {
		if(colorFunc0 == null) {
			colorFunc0 = new SimpleStringProperty(this,"colorFunc0",_colorFunc0);
		}
		return colorFunc0; 
	}
	
	public final void setColorFunc0(String value) {
		if(colorFunc0 == null) {
			_colorFunc0 = value;
		} else {
			colorFunc0.set(value);
		}
	}
	
	public final String getColorFuncEP() {
		if(colorFuncEP == null) {
			return _colorFuncEP;
		} else 
		return colorFuncEP.get();
	}
	
	public final StringProperty colorFuncEPProperty() {
		if(colorFuncEP == null) {
			colorFuncEP = new SimpleStringProperty(this,"colorFuncEP",_colorFuncEP);
		}
		return colorFuncEP; 
	}
	
	public final void setColorFuncEP(String value) {
		if(colorFuncEP == null) {
			_colorFuncEP = value;
		} else {
			colorFuncEP.set(value);
		}
	}
	
	
	public final String getFileName() {
		if(fileName == null) {
			return _fileName;
		} else 
		return fileName.get();
	}
	
	public final StringProperty fileNameProperty() {
		if(fileName == null) {
			fileName = new SimpleStringProperty(this,"fileName",_fileName);
		}
		return fileName; 
	}
	
	public final void setFileName(String value) {
		if(fileName == null) {
			_fileName = value;
		} else {
			fileName.set(value);
		}
	}
	
	public final String getFolder() {
		if(folder == null) {
			return _folder;
		} else 
		return folder.get();
	}
	
	public final StringProperty folderProperty() {
		if(folder == null) {
			folder = new SimpleStringProperty(this,"folder",_folder);
		}
		return folder; 
	}
	
	public final void setFolder(String value) {
		if(folder == null) {
			_folder = value;
		} else {
			folder.set(value);
		}
	}
	
	public final String getPath() {
		if(path == null) {
			return _path;
		} else 
		return path.get();
	}
	
	public final StringProperty pathProperty() {
		if(path == null) {
			path = new SimpleStringProperty(this,"path",_path);
		}
		return path; 
	}
	
	public final void setPath() {
		String value = "";
		if (!getFolder().isBlank() ) {
			value += getFolder();
			value += File.separator;
			value += getFileName();
		} else {
			value = getFileName();
		}
		  
		if(path == null) {
			_path = value;
		} else {
			path.set(value);
		}
	}
	
	public final String getDataFile() {
		if(dataFile == null) {
			return _dataFile;
		} else 
		return dataFile.get();
	}
	
	public final StringProperty dataFileProperty() {
		if(dataFile == null) {
			dataFile = new SimpleStringProperty(this,"dataFile",_dataFile);
		}
		return dataFile; 
	}
	
	public final void setDataFile(String value) {
		if(dataFile == null) {
			_dataFile = value;
		} else {
			dataFile.set(value);
		}
	}
	
	public final String getChartTitle() {
		if(chartTitle == null) {
			return _chartTitle;
		} else 
		return chartTitle.get();
	}
	
	public final StringProperty chartTitleProperty() {
		if(chartTitle == null) {
			chartTitle = new SimpleStringProperty(this,"chartTitle",_chartTitle);
		}
		return chartTitle; 
	}
	
	public final void setChartTitle(String value) {
		if(chartTitle == null) {
			_chartTitle = value;
		} else {
			chartTitle.set(value);
		}
	}
	
	public final String getXLabel() {
		if(xLabel == null) {
			return _xLabel;
		} else 
		return xLabel.get();
	}
	
	public final StringProperty xLabelProperty() {
		if(xLabel == null) {
			xLabel = new SimpleStringProperty(this,"xLabel",_xLabel);
		}
		return xLabel; 
	}
	
	public final void setXLabel(String value) {
		if(xLabel == null) {
			_xLabel = value;
		} else {
			xLabel.set(value);
		}
	}
	
	public final String getYLabel() {
		if(yLabel == null) {
			return _yLabel;
		} else 
		return yLabel.get();
	}
	
	public final StringProperty yLabelProperty() {
		if(yLabel == null) {
			yLabel = new SimpleStringProperty(this,"yLabel",_yLabel);
		}
		return yLabel; 
	}
	
	public final void setYLabel(String value) {
		if(yLabel == null) {
			_yLabel = value;
		} else {
			yLabel.set(value);
		}
	}
	
	public final String getUnit() {
		if(unit == null) {
			return _unit;
		} else 
		return unit.get();
	}
	
	public final StringProperty unitProperty() {
		if(unit == null) {
			unit = new SimpleStringProperty(this,"unit",_unit);
		}
		return unit; 
	}
	
	public final void setUnit(String value) {
		if(unit == null) {
			_unit = value;
		} else {
			unit.set(value);
		}
	}

	public NamedPlotColor getColorLine() {
		return colorLine;
	}
	
	public void setColorLine(String color) {
		this.colorLine = auxSetColor(color,this.getColorMeasurement());
	}
	
	public void setColorLine(NamedPlotColor color) {
		this.colorLine = color;
	}

	private NamedPlotColor auxSetColor(String colorName,String standardColor) {
		NamedPlotColor c = null;
		try {
		if (colorName.isEmpty()) {
			c = Utility.getPlotColor(standardColor);
		} else {
			c = Utility.getPlotColor(colorName);
		}
			
		}catch(Exception e) {
			this.data.getLogger().addLog(e.getMessage());
		}
		return c;
	}

	public NamedPlotColor getColorFirst() {
		return colorFirst;
	}

	public void setColorFirst(String color) {
		this.colorFirst = auxSetColor(color,this.getColorFirstDrv());
	}
	
	public void setColorFirst(NamedPlotColor color) {
		this.colorFirst = color;
	}
	
	public NamedPlotColor getColorSecond() {
		return colorSecond;
	}

	public void setColorSecond(String color) {
		this.colorSecond = auxSetColor(color,this.getColorSecondDrv());
	}
	
	public void setColorSecond(NamedPlotColor color) {
		this.colorSecond = color;
	}
	
	public NamedPlotColor getColorConst0() {
		return colorConst0;
	}

	public void setColorConst0(String color) {
		this.colorConst0 = auxSetColor(color,this.getColorFunc0());
	}
	
	public NamedPlotColor getColorConstEP() {
		return colorConstEP;
	}

	public void setColorConstEP(String color) {
		this.colorConstEP = auxSetColor(color,this.getColorFuncEP());
	}
	
	public void setColorConstEP(NamedPlotColor color) {
		this.colorConstEP = color;
	}
}
