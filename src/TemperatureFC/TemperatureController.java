/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.GenericComposition;
import LFuzzyLibrary.HeytAFloat;
import LFuzzyLibrary.HeytAPair;
import LFuzzyLibrary.LFuzzyController;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;

/**
 *
 * @author msantosteixeira
 */
public class TemperatureController {

    private LFuzzyController<Integer> fc;
    //

    public LFuzzyController getFc() {
        return fc;
    }

    public void setFc(LFuzzyController fc) {
        this.fc = fc;
    }

    public TemperatureController(Float a1Input, Float a2Input, Float b1Input, Float b2Input, Float uInput, Float aOutput, Float bOutput, Float uOutput) {

        Float u1 = (uInput * 10.0f);
        Float u2 = (uOutput * 10.0f);

        GenericComposition genComp = new GenericComposition<Pair<Float, Float>>() {
            @Override
            public Pair<Float, Float> compositionMethod(Pair<Float, Float> p1, Pair<Float, Float> p2) {
                HeytAPair<Float, Float> hPair = new HeytAPair(new HeytAFloat(), new HeytAFloat());
                return hPair.meet(p1, p2);
            }
        };
        
        ModifierInputImpl mInput = new ModifierInputImpl(new FiniteInputValuesImpl(), a1Input, a2Input, b1Input, b2Input, u1.intValue(), 500, 0, 240, 255);
        ModifierOutputImpl mOutput = new ModifierOutputImpl(new FiniteOutputValuesImpl(), 0, aOutput, bOutput, u2.intValue(), -200, 200);
        
        this.fc = new LFuzzyController(new CoreImpl(new RulesBaseImpl()), genComp, mInput, mOutput, 
                new FiniteInputValuesImpl(), FiniteInputLinguisticImpl.LC, FiniteOutputLinguisticImpl.AL, 
                new FiniteSum(), new DeffuzificationImpl(new DeffuzificationMethodImpl()));
    }


    /**
     * Depends on: core, cutValue, readingValue
     * @return 
     */
    public Sum inference(Pair<Float, Float> cutValue, Integer readingValue) {
        
        Object result = fc.inference(cutValue, readingValue);
        
        return (Sum) result;
    }

}
