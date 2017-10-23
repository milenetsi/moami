/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureFC;

import LFuzzyLibrary.Core;
import LFuzzyLibrary.GenericComposition;
import LFuzzyLibrary.HeytA;
import LFuzzyLibrary.HeytAFloat;
import LFuzzyLibrary.HeytAPair;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
import java.util.Hashtable;

/**
 *
 * @author milene
 */
public class CoreImpl extends Core<Integer,Sum>  {

    private HeytA<Float> floatAlgebra;

    public CoreImpl(RulesBaseImpl rulesBase) {
        super(rulesBase);
        this.floatAlgebra = new HeytAFloat();
    }



    //C = Lin (converse) ; R ; Lout
    public LRel<Integer, Sum, Pair<Float, Float>> calcCore(Float a1, Float a2, Float b1, Float b2, Integer u, Float aOut, Float bOut, Integer uOut){
        GenericComposition compMethodIn = new GenericComposition<Pair<Float, Float>>() {
            @Override
            public Pair<Float, Float> compositionMethod(Pair<Float, Float> p1, Pair<Float, Float> p2) {
                HeytAPair<Float, Float> hPair = new HeytAPair(new HeytAFloat(), new HeytAFloat());
                return hPair.meet(p1, p2);
            }
        };
        
        ModifierInputImpl mInput = new ModifierInputImpl(new FiniteInputValuesImpl(), a1, a2, b1, b2, u, 500, 0, 240, 255);
        ModifierOutputImpl mOutput = new ModifierOutputImpl(new FiniteOutputValuesImpl(), 0, aOut, bOut, uOut, -200, 200);
        
        return this.core(mInput, mOutput, compMethodIn, new FiniteInputValuesImpl(), FiniteInputLinguisticImpl.LC, FiniteOutputLinguisticImpl.AL, new FiniteSum());
    }

}
