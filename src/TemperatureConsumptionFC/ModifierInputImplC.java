/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.Finite;
import LFuzzyLibrary.FiniteUnit;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.ModifierInputInteger;
import LFuzzyLibrary.Pair;
import java.util.Hashtable;

/**
 *
 * @author msantosteixeira
 */
public class ModifierInputImplC extends ModifierInputInteger {

    private FiniteInputValuesImplC finiteValues;

    public ModifierInputImplC(FiniteInputValuesImplC finiteValues, Float a1, Float a2, Float b1, Float b2, Integer u,
            Integer highestInputValue, Integer lowestInputValue, Integer optimalValueA, Integer optimalValueB) {
        super(finiteValues, a1, a2, b1, b2, u, highestInputValue, lowestInputValue, optimalValueA, optimalValueB);
        this.finiteValues = finiteValues;
    }

    @Override
    public LRel<Finite, Object, Pair<Float, Float>> getL() {
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q0 = q0();
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q1P = successorQPositive(q0);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q2P = successorQPositive(q1P);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q3P = successorQPositive(q2P);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q4P = qFinalPositive(q3P);
        //
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q1N = successorQNegative(q0);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q2N = successorQNegative(q1N);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q3N = successorQNegative(q2N);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> q4N = qFinalNegative(q3N);

        //Finally set Lin
        Hashtable<Pair<Finite, Object>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv : this.finiteValues.getFiniteList()) {
            newTable.put(new Pair(FiniteInputLinguisticImplC.OC, fv), q0.getValue(new Pair(FiniteUnit.tt, fv)));//OT

            newTable.put(new Pair(FiniteInputLinguisticImplC.SH, fv), q1P.getValue(new Pair(FiniteUnit.tt, fv)));//SW

            newTable.put(new Pair(FiniteInputLinguisticImplC.LH, fv), q2P.getValue(new Pair(FiniteUnit.tt, fv)));//LW

            newTable.put(new Pair(FiniteInputLinguisticImplC.TH, fv), q3P.getValue(new Pair(FiniteUnit.tt, fv)));//TW

            newTable.put(new Pair(FiniteInputLinguisticImplC.MH, fv), q4P.getValue(new Pair(FiniteUnit.tt, fv)));//MW

            newTable.put(new Pair(FiniteInputLinguisticImplC.SL, fv), q1N.getValue(new Pair(FiniteUnit.tt, fv)));//SC

            newTable.put(new Pair(FiniteInputLinguisticImplC.LL, fv), q2N.getValue(new Pair(FiniteUnit.tt, fv)));//LC

            newTable.put(new Pair(FiniteInputLinguisticImplC.TL, fv), q3N.getValue(new Pair(FiniteUnit.tt, fv)));//TC

            newTable.put(new Pair(FiniteInputLinguisticImplC.ML, fv), q4N.getValue(new Pair(FiniteUnit.tt, fv)));//MC

        }

        LRel<Finite, Object, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }

}
