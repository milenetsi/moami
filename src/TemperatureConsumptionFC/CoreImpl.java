/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.Core;
import LFuzzyLibrary.GenericComposition;
import LFuzzyLibrary.HeytAFloat;
import LFuzzyLibrary.HeytAPair;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;

/**
 *
 * @author milene
 */
public class CoreImpl extends Core<Pair<Integer, Integer>, Sum> {

    public CoreImpl(RulesBaseImpl rulesBase) {
        super(rulesBase);
    }

    //C = Lin (converse) ; R ; Lout
    //public LRel<Pair<Integer, Integer>, Sum, Pair<Float, Float>> calcCore(Float a1, Float a2, Float b1, Float b2, Pair<Integer, Integer> u, Float aOut, Float bOut, Integer uOut) {
   
    public LRel<Pair<Integer, Integer>, Sum, Pair<Float, Float>> calcCore(Settings s) {
        GenericComposition compMethodIn = new GenericComposition<Pair<Float, Float>>() {
            @Override
            public Pair<Float, Float> compositionMethod(Pair<Float, Float> p1, Pair<Float, Float> p2) {
                HeytAPair<Float, Float> hPair = new HeytAPair(new HeytAFloat(), new HeytAFloat());
                return hPair.meet(p1, p2);
            }
        };
        ModifierInputImplT m1 = new ModifierInputImplT(new FiniteInputValuesImplT(), s.getA1Input1(), s.getA2Input1(), s.getB1Input1(), s.getB2Input1(),
                s.getuInput().getFirst(), s.getHighestInputValue().getFirst(), s.getLowestInputValue().getFirst(), s.getOptimalValueA().getFirst(), s.getOptimalValueB().getFirst());
        ModifierInputImplC m2 = new ModifierInputImplC(new FiniteInputValuesImplC(), s.getA1Input2(), s.getA2Input2(), s.getB1Input2(), s.getB2Input2(), s.getuInput().getSecond(), 
            s.getHighestInputValue().getSecond(), s.getLowestInputValue().getSecond(), s.getOptimalValueA().getSecond(), s.getOptimalValueB().getSecond());
        ModifierInputImpl mInput = new ModifierInputImpl(new FiniteInputValuesImpl(), s.getA1Input1(), s.getA2Input1(), s.getB1Input1(), s.getB2Input1(),
            s.getuInput(), s.getHighestInputValue(), s.getLowestInputValue(), s.getOptimalValueA().getFirst(), s.getOptimalValueA().getSecond(), m1, m2);
        ModifierOutputImpl mOutput = new ModifierOutputImpl(new FiniteOutputValuesImpl(), s.getOutOptimal(), s.getA1Output(), s.getA2Output(), s.getuOutput(), s.getLowestOutputValue(), s.getHighestOutputValue());

        return this.core(mInput, mOutput, compMethodIn, new FiniteInputValuesImpl(), new FiniteInputLinguisticImplPair(), FiniteOutputLinguisticImpl.AL, new FiniteSum());
    }

}
