package LFuzzyLibrary;

import java.util.Hashtable;

/**
 * Temperature controller Page 175 - Goguen
 *
 * @author msantosteixeira
 */
public abstract class ModifierInputInteger extends Modifier<Integer> {

    private Float a1;
    private Float a2;
    private Float b1;
    private Float b2;
    private Integer u;
    private Integer highestInputValue;
    private Integer lowestInputValue;
    private Integer optimalValueA;
    private Integer optimalValueB;

    public ModifierInputInteger(Finite<Integer> finiteValues, Float a1, Float a2, Float b1, Float b2, Integer u,
            Integer highestInputValue, Integer lowestInputValue, Integer optimalValueA, Integer optimalValueB) {
        super(finiteValues);
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
        this.u = u;
        this.highestInputValue = highestInputValue;
        this.lowestInputValue = lowestInputValue;
        this.optimalValueA = optimalValueA;
        this.optimalValueB = optimalValueB;
    }

    private LRel<FiniteUnit, Integer, Pair<Float, Float>> optimalValueA() {
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv : this.getFiniteValues().getFiniteList()) {
            if (fv.equals(this.optimalValueA)) {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(1.0f, 0.0f));
            } else {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 0.0f));
            }
        }
        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    private LRel<FiniteUnit, Integer, Pair<Float, Float>> optimalValueB() {
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv : this.getFiniteValues().getFiniteList()) {
            if (fv.equals(this.optimalValueB)) {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 1.0f));
            } else {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 0.0f));
            }
        }

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    /**
     * Identify how much each number is close to X. I.E.: how much 7 is close to 8 (0.8)
     * ΞInteger(x,y) := (min(1, max(0,a1 − (b1 * |x − y|))),min(1,max(0,a2 − (b2
     * * |x − y|)))) a1 = a2 = 1.2,b1 = 0.35 and b2 = 0.25
     *
     * @param a1
     * @param a2
     * @param b1
     * @param b2
     */
    private LRel<Integer, Integer, Pair<Float, Float>> approximateEquality() {
        Hashtable<Pair<Integer, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv1 : this.getFiniteValues().getFiniteList()) {
            for (Integer fv2 : this.getFiniteValues().getFiniteList()) {
                Float f1 = this.getAlgebra().meet(1.0f, this.getAlgebra().join(0.0f, a1 - (b1 * Math.abs((fv1 * 0.1f) - (fv2 * 0.1f)))));
                Float f2 = this.getAlgebra().meet(1.0f, this.getAlgebra().join(0.0f, a2 - (b2 * Math.abs((fv1 * 0.1f) - (fv2 * 0.1f)))));
                newTable.put(new Pair(fv1, fv2), new Pair(f1, f2));
            }
        }
        LRel<Integer, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    //Q0 := more or less(ΞInteger,e24) U more or less(ΞInteger,e25.5)  ---> (e24 U e255);X
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> q0() {
        LRel<FiniteUnit, Integer, Pair<Float, Float>> eA = this.optimalValueA();
        LRel<FiniteUnit, Integer, Pair<Float, Float>> eB = this.optimalValueB();
        LRel<Integer, Integer, Pair<Float, Float>> eInteger = approximateEquality();

        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv : this.getFiniteValues().getFiniteList()) {
            //e24 U e25
            Pair<FiniteUnit, Integer> pKey = new Pair(FiniteUnit.tt, fv);
            Pair<Float, Float> p = this.gethPair().join(eA.getValue(pKey), eB.getValue(pKey));
            newTable.put(pKey, p);
        }
        //LRel resulting from the join eA and eB
        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);

        //;X     Integeromposition returns an LSet
        Composition<FiniteUnit, Integer, Integer, Pair<Float, Float>> comp = new Composition(FiniteUnit.tt, this.getFiniteValues(), this.getFiniteValues());

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = comp.composition(this.gethPair(), lrel, eInteger, this.getGenComp());

        return lrelResult;
    }

    //Qi+1 := Qi; sInteger    or //shifting... look at q0 +u
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> successorQPositive(LRel<FiniteUnit, Integer, Pair<Float, Float>> previousQ) {
        //Q1 => (tt, y) = Q0 (tt, y+u)   (value in the previous Q)
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();
        //u = u * 10;
        for (Integer fv : this.getFiniteValues().getFiniteList()) {

            Integer val = fv - u;
            if (val < this.lowestInputValue) {
                val = Math.floorMod(val, this.highestInputValue);
                if (val < this.lowestInputValue) {
                    val = this.highestInputValue - (this.lowestInputValue - val);
                }                
            }

            Pair<Float, Float> mDegree = previousQ.getValue(new Pair(FiniteUnit.tt, val));

            newTable.put(new Pair(FiniteUnit.tt, fv), mDegree);
        }

        /*//; Integeromposition 
         Integeromposition<FiniteUnit, Integer, Integer, Pair<Float, Float>> comp = new Integeromposition(FiniteUnit.tt, this.fiV, this.fiV);
         LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = comp.composition(this.gethPair(), previousQ, calcSInteger(u));*/
        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);

        return lrelResult;
    }

    //Q−(i+1) := Q−i; sInteger(converse)   or //shifting... look at q0 -u
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> successorQNegative(LRel<FiniteUnit, Integer, Pair<Float, Float>> previousQ) {
        //Q1 => (tt, y) = Q0 (tt, y-u)   (value in the previous Q)
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();
        //u = u * 10;

        for (Integer fv : this.getFiniteValues().getFiniteList()) {

            Integer val = fv + u;
            if (val > highestInputValue) {
                val = Math.floorMod(val, this.highestInputValue);
                if (val < this.lowestInputValue) {
                    val = this.lowestInputValue + (val);
                }            
            }

            Pair<Float, Float> mDegree = previousQ.getValue(new Pair(FiniteUnit.tt, val));
            newTable.put(new Pair(FiniteUnit.tt, fv), mDegree);
        }

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);

        return lrelResult;
    }

    //Q4 := greater than∗(EInteger,Q3)   Or // x >y (1-a, 1-b) where q3(tt, y) = (a,b)
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> qFinalPositive(LRel<FiniteUnit, Integer, Pair<Float, Float>> qPreviousP) {
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv1 : this.getFiniteValues().getFiniteList()) {
            Pair<Float, Float> val = new Pair(this.gethPair().top());
            for (Integer fv2 : this.getFiniteValues().getFiniteList()) {
                if (fv2 >= fv1) {
                    Pair<Float, Float> mDegree = qPreviousP.getValue(new Pair(FiniteUnit.tt, fv2));

                    val = this.gethPair().meet(val, new Pair(1 - mDegree.getFirst(), 1 - mDegree.getSecond()));

                }
                /*//Lukasiewicz t-norm tL(x,y) := max{0,x + y − 1} for ∗
                 Float f1 = this.getAlgebra().join(0.0f, (fv1 + fv2) - 1.0f);*/
            }
            newTable.put(new Pair(FiniteUnit.tt, fv1), val);
        }

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    //Q−4 := less than∗(EInteger,Q−3) Or // x <= y (1-a, 1-b) where q3(tt, y) = (a,b)
    public final LRel<FiniteUnit, Integer, Pair<Float, Float>> qFinalNegative(LRel<FiniteUnit, Integer, Pair<Float, Float>> qPreviousN) {
        Hashtable<Pair<FiniteUnit, Integer>, Pair<Float, Float>> newTable = new Hashtable();

        for (Integer fv1 : this.getFiniteValues().getFiniteList()) {
            Pair<Float, Float> val = new Pair(this.gethPair().top());
            for (Integer fv2 : this.getFiniteValues().getFiniteList()) {
                if (fv2 <= fv1) {
                    Pair<Float, Float> mDegree = qPreviousN.getValue(new Pair(FiniteUnit.tt, fv2));
                    val = this.gethPair().meet(val, new Pair(1 - mDegree.getFirst(), 1 - mDegree.getSecond()));
                }

                /*//Lukasiewicz t-norm tL(x,y) := max{0,x + y − 1} for ∗
                 Float f1 = this.getAlgebra().join(0.0f, (fv1 + fv2) - 1.0f);*/
            }
            newTable.put(new Pair(FiniteUnit.tt, fv1), val);
        }

        LRel<FiniteUnit, Integer, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    /**
     * This method must be implemented. To do so, call the methods: -q0; -as
     * many successors positive and negative as necessary as MFs -qFinalPositive
     * and qFinalNegative
     *
     * @return an LRel containing all these values must be returned. The Finite
     * in this return, should be a linguistic input Finite
     */
    //public abstract LRel<FiniteInputLinguistic, Integer, Pair<Float, Float>> getLIn();
    public abstract LRel<Finite, Object, Pair<Float, Float>> getL();

}
