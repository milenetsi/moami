/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

/**
 *
 * @author milene
 */
public abstract class Core<A, B> {

    private HeytA<Float> floatAlgebra;
    private RulesBase rulesBase;

    public Core(RulesBase rulesBase) {
        this.rulesBase = rulesBase;
        this.floatAlgebra = new HeytAFloat();
    }

    //C = Lin (converse) ; R ; Lout
    public final LRel<A, B, Pair<Float, Float>> core(Modifier mInput, Modifier mOutput, GenericComposition<Pair<Float, Float>> compMethodIn, FiniteInputValues finiteInputValues, FiniteInputLinguistic finiteInputLinguistic, FiniteOutputLinguistic finiteOutputLinguistic, Finite finiteB) {

        GenericComposition<Pair<Float, Float>> genComp = new GenericComposition<Pair<Float, Float>>() {
            @Override
            public Pair<Float, Float> compositionMethod(Pair<Float, Float> p1, Pair<Float, Float> p2) {
                HeytAPair<Float, Float> hPair = new HeytAPair(floatAlgebra, floatAlgebra);
                return hPair.meet(p1, p2);
            }
        };
        Composition<A, FiniteInputLinguistic, FiniteOutputLinguistic, Pair<Float, Float>> compX = new Composition(finiteInputValues, finiteInputLinguistic, finiteOutputLinguistic);
        Composition<A, FiniteOutputLinguistic, B, Pair<Float, Float>> compY = new Composition(finiteInputValues, finiteOutputLinguistic, finiteB);
        LRel<FiniteInputLinguistic, A, Pair<Float, Float>> Lin = mInput.getL();
        LRel<A, FiniteInputLinguistic, Pair<Float, Float>> LinConverse = Lin.converse();
        LRel<FiniteOutputLinguistic, B, Pair<Float, Float>> Lout = mOutput.getL();

        //x = Lin (converse) ; R
        LRel<A, FiniteOutputLinguistic, Pair<Float, Float>> x = compX.composition(new HeytAPair(floatAlgebra, floatAlgebra), LinConverse, this.rulesBase.getRulesBase(), compMethodIn);

        //y = x ; Lout
        LRel<A, B, Pair<Float, Float>> y = compY.composition(new HeytAPair(floatAlgebra, floatAlgebra), x, Lout, genComp);
        
        return y;
    }

}
