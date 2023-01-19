package blue.macroLab.mycmd.phcalc.settings;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * CalcSettings Model Object
 * <P>It contains the values obtained from the <em>config file</em>.
 * <P>The stored values are used for doing all the calculations.
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
 */
public class CalcSettings {
	 private double _dilution=0;
	 private double _sample=0;
	 private double _solution=0;
	 private double _molTitrant=0;
	 private double _threshold=0;
	 private double _molarMass=0;
	 private String _acidName="";
	 private String _acidFormula="";
	 private String _titrantFormula="";
	 private String _sampleName="";
	 
	 
	 private SimpleStringProperty acidName;
	 private SimpleStringProperty sampleName;
	 private SimpleStringProperty acidFormula;
	 private SimpleStringProperty titrantFormula; 
	 private SimpleDoubleProperty dilution;
	 private SimpleDoubleProperty sample;
	 private SimpleDoubleProperty solution;
	 private SimpleDoubleProperty molTitrant;
	 private SimpleDoubleProperty threshold;
	 private SimpleDoubleProperty molarMass;
	 
	 public CalcSettings()  {
	 }
	 
	 public final Double getDilution() {
			if(dilution == null) {
				return _dilution;
			} else 
			return dilution.get();
		}
		
		public final DoubleProperty dilutionProperty() {
			if(dilution == null) {
				dilution = new SimpleDoubleProperty(this,"dilution",_dilution);
			}
			return dilution; 
		}
		
		public final void setDilution(double value) {
			if(dilution == null) {
				_dilution = value;
			} else {
				dilution.set(value);
			}
		}
		
		public final String getAcidName() {
			if(acidName == null) {
				return _acidName;
			} else 
			return acidName.get();
		}
		
		public final StringProperty acidNameProperty() {
			if(acidName == null) {
				acidName = new SimpleStringProperty(this,"acidName",_acidName);
			}
			return acidName; 
		}
		
		public final void setAcidName(String value) {
			if(acidName == null) {
				_acidName = value;
			} else {
				acidName.set(value);
			}
		}
		
		public final String getSampleName() {
			if (sampleName == null) {
				return _sampleName;
			} else
				return sampleName.get();
		}

		public final StringProperty sampleNameProperty() {
			if (sampleName == null) {
				sampleName = new SimpleStringProperty(this, "sampleName", _sampleName);
			}
			return sampleName;
		}

		public final void setSampleName(String value) {
			if (sampleName == null) {
				_sampleName = value;
			} else {
				sampleName.set(value);
			}
		}
		
		public final String getAcidFormula() {
			if(acidFormula == null) {
				return _acidFormula;
			} else 
			return acidFormula.get();
		}
		
		public final StringProperty acidFormulaProperty() {
			if(acidFormula == null) {
				acidFormula = new SimpleStringProperty(this,"acidFormula",_acidFormula);
			}
			return acidFormula; 
		}
		
		public final void setAcidFormula(String value) {
			if(acidFormula == null) {
				_acidFormula = value;
			} else {
				acidFormula.set(value);
			}
		}
		
		public final String getTitrantFormula() {
			if(titrantFormula == null) {
				return _titrantFormula;
			} else 
			return titrantFormula.get();
		}
		
		public final StringProperty titrantFormulaProperty() {
			if(titrantFormula == null) {
				titrantFormula = new SimpleStringProperty(this,"titrantFormula",_titrantFormula);
			}
			return titrantFormula; 
		}
		
		public final void setTitrantFormula(String value) {
			if(titrantFormula == null) {
				_titrantFormula = value;
			} else {
				titrantFormula.set(value);
			}
		}
		
		public final Double getMolarMass() {
			if(molarMass == null) {
				return _molarMass;
			} else 
			return molarMass.get();
		}
		
		public final DoubleProperty molarMassProperty() {
			if(molarMass == null) {
				molarMass = new SimpleDoubleProperty(this,"molarMass",_molarMass);
			}
			return molarMass; 
		}
		
		public final void setMolarMass(double value) {
			if(molarMass == null) {
				_molarMass = value;
			} else {
				molarMass.set(value);
			}
		}
		
		public final Double getThreshold() {
			if(threshold == null) {
				return _threshold;
			} else 
			return threshold.get();
		}
		
		public final DoubleProperty thresholdProperty() {
			if(threshold == null) {
				threshold = new SimpleDoubleProperty(this,"threshold",_threshold);
			}
			return threshold; 
		}
		
		public final void setThreshold(double value) {
			if(threshold == null) {
				_threshold = value;
			} else {
				threshold.set(value);
			}
		}
		
		public final Double getSample() {
			if(sample == null) {
				return _sample;
			} else 
			return sample.get();
		}
		
		public final DoubleProperty sampleProperty() {
			if(sample == null) {
				sample = new SimpleDoubleProperty(this,"sample",_sample);
			}
			return sample; 
		}
		
		public final void setSample(double value) {
			if(sample == null) {
				_sample= value;
			} else {
				sample.set(value);
			}
		}
		
		public final Double getSolution() {
			if(solution == null) {
				return _solution;
			} else 
			return solution.get();
		}
		
		public final DoubleProperty solutionProperty() {
			if(solution == null) {
				solution = new SimpleDoubleProperty(this,"solution",_solution);
			}
			return solution; 
		}
		
		public final void setSolution(double value) {
			if(solution == null) {
				_solution= value;
			} else {
				solution.set(value);
			}
		}
		
		public final Double getMolTitrant() {
			if(molTitrant == null) {
				return _molTitrant;
			} else 
			return molTitrant.get();
		}
		
		public final DoubleProperty molTitrantProperty() {
			if(molTitrant == null) {
				molTitrant = new SimpleDoubleProperty(this,"molTitrant",_molTitrant);
			}
			return molTitrant; 
		}
		
		public final void setMolTitrant(double value) {
			if(molTitrant == null) {
				_molTitrant= value;
			} else {
				molTitrant.set(value);
			}
		}
		
}
