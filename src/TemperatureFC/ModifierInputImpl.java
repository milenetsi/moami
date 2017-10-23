package TemperatureFC;

import LFuzzyLibrary.Finite;
import LFuzzyLibrary.FiniteUnit;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.ModifierInputInteger;
import java.util.Hashtable;

/**
 * Temperature controller Page 175 - Goguen
 *
 * @author msantosteixeira
 */
public class ModifierInputImpl extends ModifierInputInteger {

    private FiniteInputValuesImpl finiteValues;

    public ModifierInputImpl(FiniteInputValuesImpl finiteValues, Float a1, Float a2, Float b1, Float b2, Integer u,
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

        for (Object fvI : this.finiteValues.getFiniteList()) {
            Integer fv = (Integer) fvI;
            newTable.put(new Pair(FiniteInputLinguisticImpl.OT, fv), q0.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteInputLinguisticImpl.SW, fv), q1P.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteInputLinguisticImpl.LW, fv), q2P.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteInputLinguisticImpl.TW, fv), q3P.getValue(new Pair(FiniteUnit.tt, fv)));

            newTable.put(new Pair(FiniteInputLinguisticImpl.MW, fv), q4P.getValue(new Pair(FiniteUnit.tt, fv)));

            newTable.put(new Pair(FiniteInputLinguisticImpl.SC, fv), q1N.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteInputLinguisticImpl.LC, fv), q2N.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteInputLinguisticImpl.TC, fv), q3N.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteInputLinguisticImpl.MC, fv), q4N.getValue(new Pair(FiniteUnit.tt, fv)));
        }

        LRel<Finite, Object, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }

}
