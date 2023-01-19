package blue.macroLab.mycmd.phcalc.calc;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import org.jscience.physics.amount.Amount;

import blue.macroLab.mycmd.phcalc.cons.Constants;
import blue.macroLab.mycmd.phcalc.model.Data;
import blue.macroLab.mycmd.phcalc.utils.Utility;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import javax.measure.quantity.*;

/** Calculates the equivalence point, its pH value and the concentration of the acid used
 * @author Anzela Minosi
 * @author macrolab.blue
 * @version 1.0
 * @since 1.0
*/
public class MyCalc {
	private MyTitrationData myTitration;
	private Data model;
	private MyLine linePH;
	private MyLine lineDx2;
	private double equivalencePoint = 0;
	private double pH_EP = 0;
	Unit<Volume> mL = SI.MILLI(NonSI.LITER);
	Unit<?> M =  SI.MOLE.divide(NonSI.LITER);
	Unit<Mass> Milligram = SI.MILLI(SI.GRAM);
	Unit<?> MG_PER_LITER = Milligram.divide(NonSI.LITER);
	Unit<?> MG_PER_MOLE = Milligram.divide(SI.MOLE);
	Unit<?> GRAM_PER_MOLE = SI.GRAM.divide(SI.MOLE);
	private Amount<AmountOfSubstance> concDilution;
	private Amount<AmountOfSubstance> conc;
	private Amount<?> concMGL;
	private Amount<Mass> concMG;
	private LinkedHashMap<String, String> map;
	private String tblHeadline;
	
	public MyCalc(MyTitrationData titration,Data m) {
		this.myTitration = titration;
		this.model = m;
	}
	
	/** 
	 * finds out the tangent of the second derivative when crosing the x axis
	 */
	public void setLines() {
		double threshold = model.getCalcSettings().getThreshold();
		double diff_pH = -1;
		double x_lower = -1;
		MyTitration prev = new MyTitration();
		for (MyTitration m: this.myTitration.getMyDataPoints()) {
			MyTitration row_i = m;
			Derivative dx2_i = m.getSndDrv();
			double x_upper = m.getPoint().getX();
			if (prev.getPoint() != null) {
				diff_pH = row_i.getPoint().getY() - prev.getPoint().getY();
				x_lower = prev.getPoint().getX();
			}
			if ( Math.abs(diff_pH) >= threshold && dx2_i != null) {
				this.linePH =  MyLine.lineFromPoints( row_i.getPoint(), prev.getPoint());
				this.lineDx2 = MyLine.lineFromPoints( row_i.getSndDrv().toPoint(x_upper), prev.getSndDrv().toPoint(x_lower));
				model.getLogger().addLog(this.model.getMessageValue(Constants.KEY_INF)+Constants.DPOINT+Constants.SPACE +x_lower );
				model.getLogger().addLog(this.model.getMessageValue(Constants.KEY_SUP)+Constants.DPOINT+Constants.SPACE +x_upper );
				model.getLogger().addLog(this.model.getMessageValue(Constants.KEY_PH_LINE)+Constants.DPOINT+Constants.SPACE+linePH.toString());
				model.getLogger().addLog(this.model.getMessageValue(Constants.KEY_DX_LINE)+Constants.DPOINT+Constants.SPACE+lineDx2.toString());
				break;
			}
			prev = new MyTitration(row_i.getPoint(),row_i.getFstDrv(),row_i.getSndDrv());
			x_lower = -1;
			diff_pH = -1;
		}
	}
	
	/** 
	 * calculates the equivalence point ant its pH value
	 */
	public void calcEP() {
		if (this.linePH != null && this.lineDx2 != null) {
			this.equivalencePoint =  this.lineDx2.solveX(0);
			this.pH_EP = linePH.solveY(equivalencePoint);
			String log = this.model.getMessageValue(Constants.KEY_EP) + Constants.SPACE + Constants.KEY_AT+ Constants.SPACE + this.model.getMessageValue(Constants.KEY_PH_LINE)+Constants.DPOINT;
			model.getLogger().addLog( log   +this.linePH.solveX(lineDx2));
		}
	}
	
	/** 
	 * Calculates the concentration of the acid after it has been diluted.
	 * This concentration is being taken if no dilution has been taken place, too.
	 */
	public void setConcentrationAfterDilution() {
		Amount<Volume> amtEP = Amount.valueOf(equivalencePoint, mL);
		Amount<Volume> amtEPL = amtEP.to(NonSI.LITER);
		String epLog = this.model.getMessageValue(Constants.KEY_EP)+Constants.COMMA+Constants.SPACE;
		String tLog = this.model.getMessageValue(Constants.KEY_TITRANT)+Constants.COMMA+Constants.SPACE;
		String saLog = this.model.getMessageValue(Constants.KEY_SAMPLE) +Constants.SPACE+this.model.getMessageValue(Constants.KEY_ACID) +Constants.COMMA+Constants.SPACE;
		String acLog = getConcentrationLog(Constants.KEY_POST);
		model.getLogger().addLog(epLog +getAmountLog()+amtEPL.getEstimatedValue() );
		model.getLogger().addLog(epLog +this.getUnitLog()+amtEPL.getUnit() );
		Amount<AmountOfSubstance> molTitrant = Amount.valueOf(model.getCalcSettings().getMolTitrant(), SI.MOLE);
		model.getLogger().addLog(tLog+getUnitLog()+molTitrant.getUnit() );
		model.getLogger().addLog(tLog+getAmountLog()+molTitrant.getEstimatedValue()	);
		Amount<Volume> sample = Amount.valueOf(model.getCalcSettings().getSample(), mL).to(NonSI.LITER);
		model.getLogger().addLog(saLog+getAmountLog()+sample.getUnit() );
		model.getLogger().addLog(saLog+this.getUnitLog()+sample.getEstimatedValue()	);
		this.concDilution = amtEPL.times(molTitrant).divide(sample).to(SI.MOLE);
		model.getLogger().addLog(acLog+this.getUnitLog()+concDilution.getUnit() );
		model.getLogger().addLog(acLog+getAmountLog()+concDilution.getEstimatedValue());
	}
	
	/** 
	 * Calculates the concentration of the acid before it has been diluted.
	 */
	public void setConcentrationBeforeDilution() {
		if (model.getCalcSettings().getSolution() == 0 || model.getCalcSettings().getDilution() == 0) {
			conc = Amount.valueOf(0, SI.MOLE);
			model.getLogger().addLog(this.model.getMessageValue(Constants.KEY_NO_DILUTION)+Constants.DPOINT +conc.getEstimatedValue() );
		} else { 
			String amtDil = this.model.getMessageValue(Constants.KEY_DILUTION)+ Constants.COMMA + Constants.SPACE +this.getAmountLog();
			String amtOrgSol = this.model.getMessageValue(Constants.KEY_ORIGINAL)+ Constants.SPACE + this.model.getMessageValue(Constants.KEY_SOLUTION)+Constants.COMMA + Constants.SPACE +this.getAmountLog();
			String acLog = getConcentrationLog(Constants.KEY_ANTE);
			Amount<Volume> dilution = Amount.valueOf(model.getCalcSettings().getDilution(), mL).to(NonSI.LITER);
			model.getLogger().addLog(amtDil+dilution.getEstimatedValue());
			Amount<Volume> solution = Amount.valueOf(model.getCalcSettings().getSolution(), mL).to(NonSI.LITER);
			model.getLogger().addLog(amtOrgSol+solution.getEstimatedValue());
			this.conc = dilution.times(concDilution).divide(solution).to(SI.MOLE);
			model.getLogger().addLog(acLog+this.getUnitLog()+conc.getUnit() );
			model.getLogger().addLog(acLog+this.getAmountLog()+conc.getEstimatedValue());
			
		}
	}
	
	/** 
	 * Converts the final concentration into milligrams per Liter
	 */
	public void convertConcentration() {
		if (conc != null && conc.getEstimatedValue() > 0) {
			if (model.getCalcSettings().getMolarMass() > 0) {
				auxConvertConc(conc); 
			} else {
				concMGL = Amount.valueOf(0,MG_PER_LITER);
			}
		} else if (concDilution != null && concDilution.getEstimatedValue() > 0) {
			if (model.getCalcSettings().getMolarMass() > 0) {
				auxConvertConc(concDilution); 
			} else {
				concMGL = Amount.valueOf(0,MG_PER_LITER);
			} 
		}
	}
	
	/** 
	 * Converts the final concentration into milligrams 
	 */
	public void getMass() {
		Amount <Mass> zeroMass = Amount.valueOf(0,Milligram);
		if (conc != null && conc.getEstimatedValue() > 0) {
			if (model.getCalcSettings().getMolarMass() > 0) {
				calculateMass(conc,model.getCalcSettings().getSolution()); 
			} else {
				concMG = zeroMass;
			}
		} else if (concDilution != null && concDilution.getEstimatedValue() > 0) {
			if (model.getCalcSettings().getMolarMass() > 0) {
				calculateMass(concDilution,model.getCalcSettings().getSample()); 
			} else {
				concMG = zeroMass;
			} 
		}
	}
	
	private void auxConvertConc(Amount<AmountOfSubstance> cTemp) {
				String cLog = this.model.getMessageValue(Constants.KEY_CONVERT)+Constants.SPACE + this.model.getMessageValue(Constants.KEY_CONCENTRATION)+Constants.COMMA+Constants.SPACE;
				Amount<?> concMolLiter = getMolPerLiter(cTemp);
				Amount<?> molMassMol = convertMolarMass();
				concMGL = molMassMol.times(concMolLiter).to(MG_PER_LITER);
				model.getLogger().addLog(cLog + this.getUnitLog() +concMGL.getUnit() );
				model.getLogger().addLog(cLog + this.getAmountLog() +concMGL.getEstimatedValue());
	}
	
	private void calculateMass(Amount<AmountOfSubstance> cTemp,double sampleSize) {
		String calcLog = this.model.getMessageValue(Constants.KEY_RESULTS_TABLE_HEADLINE) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_MASS) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_OF) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_THE) + Constants.SPACE+ this.model.getMessageValue(Constants.KEY_ACID) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_USED) + Constants.COMMA  + Constants.SPACE;
		Amount<Volume> volSample = Amount.valueOf(sampleSize, mL).to(NonSI.LITER);
		Amount<?> concMolLiter = getMolPerLiter(cTemp);
		Amount<?> molMassMol = convertMolarMass();
		this.concMG = concMolLiter.times(volSample).times(molMassMol).to(Milligram);
		model.getLogger().addLog(calcLog+this.getUnitLog()+concMG.getUnit() );
		model.getLogger().addLog(calcLog+this.getAmountLog()+concMG.getEstimatedValue());
	}
	
	private Amount<?> convertMolarMass(){
		String convMM = this.model.getMessageValue(Constants.KEY_MOLMASS) +   Constants.SPACE   + this.model.getMessageValue(Constants.KEY_ACID)+ Constants.COMMA + Constants.SPACE;				
		String convM = this.model.getMessageValue(Constants.KEY_MASS) +   Constants.SPACE   + this.model.getMessageValue(Constants.KEY_ACID)+ Constants.COMMA + Constants.SPACE;				
		Amount<AmountOfSubstance> mol1 = Amount.valueOf(1, SI.MOLE);
		Amount<Mass> molMass = Amount.valueOf(model.getCalcSettings().getMolarMass(), SI.GRAM).to(Milligram);
		model.getLogger().addLog(convMM + this.getUnitLog()+molMass.getUnit() );
		model.getLogger().addLog(convMM + this.getAmountLog()+molMass.getEstimatedValue());
		Amount<?> molMassMol = molMass.divide(mol1).to(MG_PER_MOLE);
		model.getLogger().addLog(convM+this.getUnitLog()+molMassMol.getUnit() );
		model.getLogger().addLog(convM+this.getAmountLog()+molMassMol.getEstimatedValue());
		return molMassMol;
	}
	
	private Amount<?> getMolPerLiter(Amount<AmountOfSubstance> substance) {
		String calcLog = this.model.getMessageValue(Constants.KEY_RESULTS_TABLE_HEADLINE) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_ACID) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_CONCENTRATION)  + Constants.COMMA  + Constants.SPACE;
		Amount<Volume> liter1 = Amount.valueOf(1, NonSI.LITER);
		Amount<?> concMolLiter = substance.divide(liter1).to(M);
		model.getLogger().addLog(calcLog+this.getUnitLog()+concMolLiter.getUnit() );
		model.getLogger().addLog(calcLog+this.getAmountLog()+concMolLiter.getEstimatedValue());
		return concMolLiter;
	}
	
	public void generateResultsData() {
		this.map = new LinkedHashMap<>();
		this.tblHeadline = this.model.getMessageValue(Constants.KEY_RESULTS_TABLE_HEADLINE) + Constants.SPACE  +this.model.getCalcSettings().getAcidName() + Constants.SPACE + this.model.getMessageValue(Constants.KEY_USED);
		map.put(this.model.getMessageValue(Constants.KEY_SAMPLES), this.model.getCalcSettings().getSampleName());
		map.put(this.model.getMessageValue(Constants.KEY_SOLUTION), this.model.getCalcSettings().getSample().toString() + Constants.SPACE+mL.toString());
		map.put(this.model.getCalcSettings().getTitrantFormula()+Constants.COMMA+ Constants.SPACE+this.model.getMessageValue(Constants.KEY_EP_ABV), Utility.toString(this.equivalencePoint,2) + Constants.SPACE+mL.toString());
		map.put(this.model.getMessageValue(Constants.KEY_PH)+Constants.COMMA+ Constants.SPACE+this.model.getMessageValue(Constants.KEY_EP_ABV), Utility.toString(this.pH_EP,2));
		map.put(this.model.getCalcSettings().getAcidFormula() + Constants.SPACE+ this.model.getMessageValue(Constants.KEY_POST)+Constants.SPACE + this.model.getMessageValue(Constants.KEY_DILUTION) , toString(this.concDilution,5));
		map.put(this.model.getCalcSettings().getAcidFormula() + Constants.SPACE+ this.model.getMessageValue(Constants.KEY_ANTE)+Constants.SPACE + this.model.getMessageValue(Constants.KEY_DILUTION) , toString(this.conc,5));
		map.put(this.model.getCalcSettings().getAcidFormula() + Constants.COMMA + Constants.SPACE+this.model.getMessageValue(Constants.KEY_MASS_VOL), toString(this.concMGL,3));
		map.put(this.model.getCalcSettings().getAcidFormula() + Constants.COMMA + Constants.SPACE+this.model.getMessageValue(Constants.KEY_MASS), toString(this.concMG,3));
		map.put(Constants.EMPTY, Constants.EMPTY);
	}
	
	public void generateConstantsData() {
		map.put(this.model.getMessageValue(Constants.KEY_CONS), null);
		map.put(this.model.getCalcSettings().getAcidFormula() + Constants.COMMA + Constants.SPACE + this.model.getMessageValue(Constants.KEY_MOLMASS),this.model.getCalcSettings().getMolarMass().toString()+Constants.SPACE+GRAM_PER_MOLE.toString());
		map.put(this.model.getCalcSettings().getTitrantFormula(),this.model.getCalcSettings().getMolTitrant().toString()  +Constants.SPACE+SI.MOLE.toString());
		map.put(this.model.getMessageValue(Constants.KEY_DILUTION) ,this.model.getCalcSettings().getDilution()  +Constants.SPACE+mL.toString());
		map.put(this.model.getMessageValue(Constants.KEY_SAMPLE) ,this.model.getCalcSettings().getSolution()  +Constants.SPACE+mL.toString());
	}
	
	public void generateEPData() {
		 this.map = new LinkedHashMap<>();
		 this.tblHeadline = this.model.getMessageValue(Constants.KEY_RESULTS_TABLE_HEADLINE) + Constants.SPACE  +this.model.getMessageValue(Constants.KEY_EP) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_HINT_CALCULATION);
		 map.put(this.model.getMessageValue(Constants.KEY_EP), Utility.toString(this.equivalencePoint,2) + Constants.SPACE+mL.toString());
		 map.put(this.model.getMessageValue(Constants.KEY_PH), Utility.toString(this.pH_EP,2));
		 map.put(Constants.FUNC+Constants.LP+mL.toString()+Constants.RP+Constants.DPOINT+Constants.SPACE+Constants.KEY_PH, lineDx2.toString());
	}
	
	public String toString(Amount<?> amount, int n) {
		String precision = Utility.repeat(Constants.HASH_TAG, n);
		DecimalFormat df = new DecimalFormat(Constants.DECIMAL_FORMAT_1+precision);
		StringBuilder sB = new StringBuilder();
		sB.append( df.format(amount.getEstimatedValue()));
		sB.append( Constants.SPACE +amount.getUnit() );
		return sB.toString();
	}
	
	public String getAmountLog() {
		return this.model.getMessageValue(Constants.KEY_AMOUNT)+Constants.DPOINT+Constants.SPACE;
	}
	
	public String getUnitLog() {
		return this.model.getMessageValue(Constants.KEY_UNIT)+Constants.DPOINT+Constants.SPACE;
	}
	
	public String getConcentrationLog(String prep) {
		return this.model.getMessageValue(Constants.KEY_ACID) +Constants.SPACE+this.model.getMessageValue(Constants.KEY_CONCENTRATION) + Constants.SPACE + this.model.getMessageValue(prep) + Constants.SPACE + this.model.getMessageValue(Constants.KEY_DILUTION) +Constants.COMMA+Constants.SPACE;
	}
	
	public double getEquivalencePoint() {
		return equivalencePoint;
	}

	public double getPH() {
		return pH_EP;
	}

	public MyLine getPHFunction() {
		return lineDx2;
	}

	public Amount<AmountOfSubstance> getDilutedConcentration() {
		return concDilution;
	}

	public Amount<?> getConcentrationPerLiter() {
		return concMGL;
	}

	public Amount<Mass> getConcentrationMG() {
		return concMG;
	}

	public Amount<AmountOfSubstance> getUnthinnedConcentration() {
		return conc;
	}

	public LinkedHashMap<String, String> getMap() {
		return map;
	}

	public String getTblHeadline() {
		return tblHeadline;
	}
}
