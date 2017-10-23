package LFuzzyLibrary;

import java.util.Hashtable;

/**
 * Temperature controller Page 175 - Goguen
 *
 * @author msantosteixeira
 */
public abstract class ModifierOutput extends Modifier<Integer>{

    private Integer optimalValue;
    private Float a;
    private Float b;
    private Integer u;
    private Integer lowestOutputValue;
    private Integer highestOutputValue;

    public ModifierOutput(Finite<Integer> finiteValues, Integer optimalValue, Float a, Float b,Integer u, Integer lowestOutputValue, Integer highestOutputValue) {
        super(finiteValues);
        this.optimalValue = optimalValue;
        this.a = a;
        this.b = b;
        this.u = u;
        this.lowestOutputValue = lowestOutputValue;
        this.highestOutputValue = highestOutputValue;
    }

    private LRel<FiniteUnit, Integer, Pair<Float, Float>> optimalOutput() {
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv : this.getFiniteValues().getFiniteList()) {
            if (fv.equals(optimalValue)) {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(1.0f, 1.0f));
            } else {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 0.0f));
            }
        }
        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    //ΞB(x,y) := (min(1, max(0,a − b|x − y|)),min(1,max(0,a − b|x − y|)))
    private LRel<Integer, Integer, Pair<Float, Float>> approximateEquality() {
        Hashtable<Pair<Integer, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv1 : this.getFiniteValues().getFiniteList()) {
            for (Integer fv2 : this.getFiniteValues().getFiniteList()) {
                Float f1 = this.getAlgebra().meet(1.0f, this.getAlgebra().join(0.0f, this.a - (this.b * Math.abs(fv1 - fv2))));
                Float f2 = this.getAlgebra().meet(1.0f, this.getAlgebra().join(0.0f, this.a - (this.b * Math.abs(fv1 - fv2))));
                newTable.put(new Pair(fv1, fv2), new Pair(f1, f2));
            }
        }
        LRel<Integer, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    //S0  := more or less(ΞB,e0) ---> X ; e0    --> approximateEqualityB Compos. optimalOutput0
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> s0() {
        LRel<FiniteUnit, Integer, Pair<Float, Float>> e0 = optimalOutput();
        LRel<Integer, Integer, Pair<Float, Float>> eB = approximateEquality();

        //;X     Composition 
        Composition<FiniteUnit, Integer, Integer, Pair<Float, Float>> comp = new Composition(FiniteUnit.tt, this.getFiniteValues(), this.getFiniteValues());

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = comp.composition(this.gethPair(), e0, eB, this.getGenComp());

        return lrelResult;
    }

    //Si +1 := Si ; sB    or //shifting... look at s0 +u
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> successorSPositive(LRel<FiniteUnit, Integer, Pair<Float, Float>> previousS) {
        //S1 => (tt, y) = S0 (tt, y+u)   (value in the previous S)
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();
        //u = u * 10;

        for (Integer fv : this.getFiniteValues().getFiniteList()) {
            Integer val = fv - u;
            if (val < this.lowestOutputValue) {
                val = Math.floorMod(val, this.highestOutputValue);
            }

            Pair<Float, Float> mDegree = previousS.getValue(new Pair(FiniteUnit.tt, val));
            newTable.put(new Pair(FiniteUnit.tt, fv), mDegree);
        }
        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);

        return lrelResult;
    }

    //S−  (i+1) := S−  i; s B   (converse)   or //shifting... look at s0 -u
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> successorSNegative(LRel<FiniteUnit, Integer, Pair<Float, Float>> previousS) {
        //S1 => (tt, y) = S0 (tt, y-u)   (value in the previous S)
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();
        //u = u * 10;

        for (Integer fv : this.getFiniteValues().getFiniteList()) {

            Integer val = fv + u;
            if (val > this.highestOutputValue) {
                val = Math.floorMod(val, this.lowestOutputValue);
            }
            
            Pair<Float, Float> mDegree = previousS.getValue(new Pair(FiniteUnit.tt, Integer.valueOf(val.intValue())));
            newTable.put(new Pair(FiniteUnit.tt, fv), mDegree);
        }

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);

        return lrelResult;
    }

    //S4 is Alert
    public abstract LRel<FiniteUnit, Object, Pair<Float, Float>> s4();

    
    
    //***************Getters and Setters**************************************//

    public Integer getOptimalValue() {
        return optimalValue;
    }

    public void setOptimalValue(Integer optimalValue) {
        this.optimalValue = optimalValue;
    }

    public Float getA() {
        return a;
    }

    public void setA(Float a) {
        this.a = a;
    }

    public Float getB() {
        return b;
    }

    public void setB(Float b) {
        this.b = b;
    }

    public Integer getU() {
        return u;
    }

    public void setU(Integer u) {
        this.u = u;
    }

    public Integer getLowestOutputValue() {
        return lowestOutputValue;
    }

    public void setLowestOutputValue(Integer lowestOutputValue) {
        this.lowestOutputValue = lowestOutputValue;
    }

    public Integer getHighestOutputValue() {
        return highestOutputValue;
    }

    public void setHighestOutputValue(Integer highestOutputValue) {
        this.highestOutputValue = highestOutputValue;
    }

    
    //************************************************************************//
}
