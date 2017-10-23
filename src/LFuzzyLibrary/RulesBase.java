/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

/**
 *
 * @author Milene
 */
public interface RulesBase {

    /**
     * @return FiniteInputLinguistic,FiniteOutputLinguistic, F,F
     */
    public LRel<FiniteInputLinguistic, FiniteOutputLinguistic, Pair<Float, Float>> getRulesBase();

}
