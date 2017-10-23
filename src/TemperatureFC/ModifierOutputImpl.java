package TemperatureFC;

import LFuzzyLibrary.Finite;
import LFuzzyLibrary.FiniteUnit;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import java.util.Hashtable;
import LFuzzyLibrary.ModifierOutput;
import LFuzzyLibrary.SumLeft;
import LFuzzyLibrary.SumRight;

/**
 * Temperature controller Page 175 - Goguen
 *
 * @author msantosteixeira
 */
public class ModifierOutputImpl extends ModifierOutput {

    private FiniteOutputValuesImpl finiteValues;

    public ModifierOutputImpl(FiniteOutputValuesImpl finiteValues, Integer optimalValue, Float a, Float b, Integer u, Integer lowestOutputValue, Integer highestOutputValue) {
        super(finiteValues, optimalValue, a, b, u, lowestOutputValue, highestOutputValue);
        this.finiteValues = finiteValues;
    }

    //S4 is Alert
    @Override
    public LRel<FiniteUnit, Object, Pair<Float, Float>> s4() {
        Hashtable<Pair<FiniteUnit, Object>, Pair<Float, Float>> newTable = new Hashtable();
        newTable.put(new Pair(FiniteUnit.tt, "AL"), new Pair(this.gethPair().top()));

        LRel<FiniteUnit, Object, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }

    @Override
    public LRel<Finite, Object, Pair<Float, Float>> getL() {
        //Prime values
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s0_ = s0();
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s1P_ = successorSPositive(s0_);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s2P_ = successorSPositive(s1P_);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s3P_ = successorSPositive(s2P_);
        //
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s1N_ = successorSNegative(s0_);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s2N_ = successorSNegative(s1N_);
        LRel<FiniteUnit, Integer, Pair<Float, Float>> s3N_ = successorSNegative(s2N_);

        LRel<FiniteUnit, Object, Pair<Float, Float>> s4 = s4();

        //Lout
        Hashtable<Pair<Finite, Object>, Pair<Float, Float>> newTable = new Hashtable();
        for (Object fvO : this.finiteValues.getFiniteList()) {
            Integer fv = (Integer) fvO;
            newTable.put(new Pair(FiniteOutputLinguisticImpl.NC, new SumLeft(fv)), s0_.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteOutputLinguisticImpl.PS, new SumLeft(fv)), s1P_.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteOutputLinguisticImpl.PM, new SumLeft(fv)), s2P_.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteOutputLinguisticImpl.PB, new SumLeft(fv)), s3P_.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteOutputLinguisticImpl.NS, new SumLeft(fv)), s1N_.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteOutputLinguisticImpl.NM, new SumLeft(fv)), s2N_.getValue(new Pair(FiniteUnit.tt, fv)));
            newTable.put(new Pair(FiniteOutputLinguisticImpl.NB, new SumLeft(fv)), s3N_.getValue(new Pair(FiniteUnit.tt, fv)));
        }
        newTable.put(new Pair(FiniteOutputLinguisticImpl.AL, new SumRight("AL")), s4.getValue(new Pair(FiniteUnit.tt, "AL")));

        LRel<Finite, Object, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }
}
