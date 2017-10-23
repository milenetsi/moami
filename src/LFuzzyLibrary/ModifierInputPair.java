package LFuzzyLibrary;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * Temperature controller Page 175 - Goguen
 *
 * @author msantosteixeira
 */
public abstract class ModifierInputPair extends Modifier<Pair<Integer, Integer>> {

    private Float a1;
    private Float a2;
    private Float b1;
    private Float b2;
    private Pair<Integer, Integer> u;
    private Pair<Integer, Integer> highestInputValue;
    private Pair<Integer, Integer> lowestInputValue;
    private Integer optimalValueA;
    private Integer optimalValueB;

    public ModifierInputPair(FiniteInputValues finiteValues, Float a1, Float a2, Float b1, Float b2, Pair<Integer, Integer> u,
            Pair<Integer, Integer> highestInputValue, Pair<Integer, Integer> lowestInputValue, Integer optimalValueA, Integer optimalValueB) {
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

    private LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> optimalValueA() {
        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv : this.getFiniteValues().getFiniteList()) {
            if (fv.getFirst().equals(this.optimalValueA)) {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(1.0f, 0.0f));
            } else {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 0.0f));
            }
        }
        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    private LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> optimalValueB() {
        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv : this.getFiniteValues().getFiniteList()) {
            if (fv.getSecond().equals(this.optimalValueB)) {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 1.0f));
            } else {
                newTable.put(new Pair(FiniteUnit.tt, fv), new Pair(0.0f, 0.0f));
            }
        }

        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    /**
     * ΞInteger(x,y) := (min(1, max(0,a1 − (b1 * |x − y|))),min(1,max(0,a2 − (b2
     * * |x − y|)))) a1 = a2 = 1.2,b1 = 0.35 and b2 = 0.25
     *
     */
    private LRel<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Float, Float>> approximateEquality() {
        Hashtable<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv1 : this.getFiniteValues().getFiniteList()) {
            for (Pair<Integer, Integer> fv2 : this.getFiniteValues().getFiniteList()) {
                Float f1 = this.getAlgebra().meet(1.0f, this.getAlgebra().join(0.0f, a1 - (b1 * Math.abs((fv1.getFirst() - fv2.getFirst()) * 0.1f))));
                Float f2 = this.getAlgebra().meet(1.0f, this.getAlgebra().join(0.0f, a2 - (b2 * Math.abs((fv1.getSecond() - fv2.getSecond()) * 0.1f))));
                newTable.put(new Pair(fv1, fv2), new Pair(f1, f2));
            }
        }
        LRel<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    //Q0 := more or less(aproxEquality,e24) U more or less(aproxEquality,e25.5)  ---> (e24 U e255);X
    public final LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> q0() {

        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> eA = this.optimalValueA();
        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> eB = this.optimalValueB();

        LRel<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Float, Float>> aproxEquality = approximateEquality();

        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv : this.getFiniteValues().getFiniteList()) {
            //e24 U e25
            Pair<FiniteUnit, Pair<Integer, Integer>> pKey = new Pair(FiniteUnit.tt, fv);
            Pair<Float, Float> p = this.gethPair().join(eA.getValue(pKey), eB.getValue(pKey));
            newTable.put(pKey, p);
        }
        //LRel resulting from the join eA and eB
        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);

        //;X     Composition returns an LSet
        Composition<FiniteUnit, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Float, Float>> comp = new Composition(FiniteUnit.tt, this.getFiniteValues(), this.getFiniteValues());

        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrelResult = comp.composition(this.gethPair(), lrel, aproxEquality, this.getGenComp());

//        Set<Pair<FiniteUnit, Pair<Integer, Integer>>> keys = lrelResult.getTable().keySet();
//        Iterator<Pair<FiniteUnit, Pair<Integer, Integer>>> itr = keys.iterator();
//        while (itr.hasNext()) {
//            Pair<FiniteUnit, Pair<Integer, Integer>> key = itr.next();
//            Pair<Float, Float> v = lrelResult.getTable().get(key);
//
//            if (v.getFirst() > 0.0f || v.getSecond() > 0.0f) {
//                System.out.println("oiif " + key.getSecond().getFirst() + " - " + key.getSecond().getSecond() + " :" + v.getFirst() + " || " + v.getSecond());
//            }
//        }
        return lrelResult;
    }

    //Qi+1 := Qi; sInteger    or //shifting... look at q0 +u
    public final LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> successorQPositive(LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> previousQ) {
        //Q1 => (tt, y) = Q0 (tt, y+u)   (value in the previous Q)
        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();
        //u = u * 10;  
        for (Pair<Integer, Integer> fv : this.getFiniteValues().getFiniteList()) {

            Pair<Integer, Integer> val = new Pair(fv.getFirst() - u.getFirst(), fv.getSecond() - u.getSecond());

            if (val.getFirst() < this.lowestInputValue.getFirst()) {
                val.setFirst(Math.floorMod(val.getFirst(), this.highestInputValue.getFirst()));
            }
            if (val.getSecond() < this.lowestInputValue.getSecond()) {
                val.setSecond(Math.floorMod(val.getSecond(), this.highestInputValue.getSecond()));
            }

            Pair<Float, Float> mDegree = previousQ.getValue(new Pair(FiniteUnit.tt, val));

            newTable.put(new Pair(FiniteUnit.tt, fv), mDegree);

        }

        /*//; Composition 
         Integeromposition<FiniteUnit, Integer, Integer, Pair<Float, Float>> comp = new Integeromposition(FiniteUnit.tt, this.fiV, this.fiV);
         LRel<FiniteUnit, Integer, Pair<Float, Float>> lrelResult = comp.composition(this.gethPair(), previousQ, calcSInteger(u));*/
        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);

        return lrelResult;
    }

    //Q−(i+1) := Q−i; sInteger(converse)   or //shifting... look at q0 -u
    public final LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> successorQNegative(LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> previousQ) {
        //Q1 => (tt, y) = Q0 (tt, y-u)   (value in the previous Q)
        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv : this.getFiniteValues().getFiniteList()) {

            Pair<Integer, Integer> val = new Pair(fv.getFirst() + u.getFirst(), fv.getSecond() + u.getSecond());

            if (val.getFirst() > this.highestInputValue.getFirst()) {
                val.setFirst(Math.floorMod(val.getFirst(), this.highestInputValue.getFirst()));
            }
            if (val.getSecond() > this.highestInputValue.getSecond()) {
                val.setSecond(Math.floorMod(val.getSecond(), this.highestInputValue.getSecond()));
            }

            Pair<Float, Float> mDegree = previousQ.getValue(new Pair(FiniteUnit.tt, val));

            newTable.put(new Pair(FiniteUnit.tt, fv), mDegree);

//            if (mDegree.getFirst() > 0.0f || mDegree.getSecond() > 0.0f) {
//                System.out.println("oiif " + fv.getFirst() + " - " + fv.getSecond() + " :" + mDegree.getFirst() + " || " + mDegree.getSecond());
//            }
        }

        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);

        return lrelResult;
    }

    //Q4 := greater than∗(EInteger,Q3)   Or // x >y (1-a, 1-b) where q3(tt, y) = (a,b)
    public final LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> qFinalPositive(LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> qPreviousP) {
        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv1 : this.getFiniteValues().getFiniteList()) {
            Pair<Float, Float> val = new Pair(this.gethPair().top());
            for (Pair<Integer, Integer> fv2 : this.getFiniteValues().getFiniteList()) {
                if (fv2.getFirst() >= fv1.getFirst()) {
                    Pair<Float, Float> mDegree = qPreviousP.getValue(new Pair(FiniteUnit.tt, fv2));

                    Pair<Float, Float> v = this.gethPair().meet(val, new Pair(1 - mDegree.getFirst(), 1 - mDegree.getSecond()));
                    val.setFirst(v.getFirst());
                }
                if (fv2.getFirst() >= fv1.getFirst() && fv2.getSecond() >= fv1.getSecond()) {
                    Pair<Float, Float> mDegree = qPreviousP.getValue(new Pair(FiniteUnit.tt, fv2));

                    Pair<Float, Float> v = this.gethPair().meet(val, new Pair(1 - mDegree.getFirst(), 1 - mDegree.getSecond()));
                    val.setSecond(v.getSecond());
                }
                /*//Lukasiewicz t-norm tL(x,y) := max{0,x + y − 1} for ∗
                 Float f1 = this.getAlgebra().join(0.0f, (fv1 + fv2) - 1.0f);*/

//                if (val.getFirst() > 0.0f || val.getSecond() > 0.0f) {
//                    System.out.println("oiif " + fv1.getFirst() + " - " + fv1.getSecond() + " :" + val.getFirst() + " || " + val.getSecond());
//                }
            }
            newTable.put(new Pair(FiniteUnit.tt, fv1), val);
        }

        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    //Q−4 := less than∗(EInteger,Q−3) Or // x <= y (1-a, 1-b) where q3(tt, y) = (a,b)
    public final LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> qFinalNegative(LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> qPreviousN) {
        Hashtable<Pair<FiniteUnit, Pair<Integer, Integer>>, Pair<Float, Float>> newTable = new Hashtable();

        for (Pair<Integer, Integer> fv1 : this.getFiniteValues().getFiniteList()) {
            Pair<Float, Float> val = new Pair(this.gethPair().top());
            for (Pair<Integer, Integer> fv2 : this.getFiniteValues().getFiniteList()) {
                if (fv2.getFirst() <= fv1.getFirst()) {
                    Pair<Float, Float> mDegree = qPreviousN.getValue(new Pair(FiniteUnit.tt, fv2));
                    Pair<Float, Float> v = this.gethPair().meet(val, new Pair(1 - mDegree.getFirst(), 1 - mDegree.getSecond()));
                    val.setFirst(v.getFirst());
                }
                if (fv2.getSecond() <= fv1.getSecond()) {
                    Pair<Float, Float> mDegree = qPreviousN.getValue(new Pair(FiniteUnit.tt, fv2));
                    Pair<Float, Float> v = this.gethPair().meet(val, new Pair(1 - mDegree.getFirst(), 1 - mDegree.getSecond()));
                    val.setSecond(v.getSecond());
                }

                /*//Lukasiewicz t-norm tL(x,y) := max{0,x + y − 1} for ∗
                 Float f1 = this.getAlgebra().join(0.0f, (fv1 + fv2) - 1.0f);*/
            }
            newTable.put(new Pair(FiniteUnit.tt, fv1), val);
//               if (val.getFirst() > 0.0f || val.getSecond() > 0.0f) {
//                    System.out.println("oiif " + fv1.getFirst() + " - " + fv1.getSecond() + " :" + val.getFirst() + " || " + val.getSecond());
//                }
        }

        LRel<FiniteUnit, Pair<Integer, Integer>, Pair<Float, Float>> lrel = new LRel();
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
    //public abstract LRel<FiniteInputLinguistic, Pair<Integer,Integer>, Pair<Float, Float>> getLIn();
    public abstract LRel<Finite, Object, Pair<Float, Float>> getL();

}
