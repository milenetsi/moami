/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.Finite;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.ModifierInputInteger;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.ModifierInputPair;
import java.util.Hashtable;


/**
 *
 * @author msantosteixeira
 */
public class ModifierInputImpl extends ModifierInputPair {

    private FiniteInputValuesImpl finiteValues;
    private ModifierInputInteger modifierTemperature;
    private ModifierInputInteger modifierConsumption;

    public ModifierInputImpl(FiniteInputValuesImpl finiteValues, Float a1, Float a2, Float b1, Float b2, Pair<Integer, Integer> u,
            Pair<Integer, Integer> highestInputValue, Pair<Integer, Integer> lowestInputValue, Integer optimalValueA, Integer optimalValueB, ModifierInputInteger modifier1, ModifierInputInteger modifier2) {
        super(finiteValues, a1, a2, b1, b2, u, highestInputValue, lowestInputValue, optimalValueA, optimalValueB);
        this.finiteValues = finiteValues;
        this.modifierTemperature = modifier1;
        this.modifierConsumption = modifier2;
    }

    /**
     * t-norm Hamacher product if a=b=0 => 0 else ab/a+b-ab
     *
     *
     * @param p1
     * @param p2
     * @return
     */
    private Pair<Float, Float> hamacherProduct(Pair<Float, Float> p1, Pair<Float, Float> p2) {
        Pair<Float, Float> newP = new Pair(0.0f, 0.0f);
        if (p1.getFirst() != 0.0f && p1.getSecond() != 0.0f) {
            Float val = (p1.getFirst() * p1.getSecond()) / (p1.getFirst() + p1.getSecond() - (p1.getFirst() * p1.getSecond()));
            newP.setFirst(val);
        }
        if (p2.getFirst() != 0.0f && p2.getSecond() != 0.0f) {
            Float val = (p2.getFirst() * p2.getSecond()) / (p2.getFirst() + p2.getSecond() - (p2.getFirst() * p2.getSecond()));
            newP.setSecond(val);
        }
        return newP;
    }

    /*//Lukasiewicz t-norm tL(x,y) := max{0,x + y − 1} for ∗
                 Float f1 = this.getAlgebra().join(0.0f, (fv1 + fv2) - 1.0f);*/
    private Pair<Float, Float> lukasiewicz(Pair<Float, Float> p1, Pair<Float, Float> p2) {
        Pair<Float, Float> newP = new Pair(0.0f, 0.0f);

        Float f1 = this.getAlgebra().join(0.0f, (p1.getFirst() + p1.getSecond()) - 1.0f);
        newP.setFirst(f1);

        Float f2 = this.getAlgebra().join(0.0f, (p2.getFirst() + p2.getSecond()) - 1.0f);
        newP.setSecond(f2);

        return newP;
    }

    @Override
    public LRel<Finite, Object, Pair<Float, Float>> getL() {
        //LRels with linguistic variables + membership values
        LRel<Finite, Object, Pair<Float, Float>> set1 = modifierTemperature.getL();
        LRel<Finite, Object, Pair<Float, Float>> set2 = modifierConsumption.getL();

        //Contains list of all possible pairs of linguistic input
        FiniteInputLinguisticImplPair fILP = new FiniteInputLinguisticImplPair();

        //Finally set Lin
        Hashtable<Pair<Finite, Object>, Pair<Float, Float>> newTable = new Hashtable();

        // try {
        Pair<Float, Float> p1;
        Pair<Float, Float> p2;
        Pair<Float, Float> newP;
        for (Pair<Integer, Integer> fv : this.finiteValues.getFiniteList()) {
            for (Pair<FiniteInputLinguisticImplT, FiniteInputLinguisticImplC> fl : fILP.getFiniteList()) {

                p1 = set1.getValue(new Pair(fl.getFirst(), fv.getFirst()));
                p2 = set2.getValue(new Pair(fl.getSecond(), fv.getSecond()));
                //
                newP = hamacherProduct(p1, p2);
                newTable.put(new Pair(fl, fv), newP);

            }
        }

        LRel<Finite, Object, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }

}
