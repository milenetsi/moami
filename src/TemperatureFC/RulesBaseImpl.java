/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.FiniteInputLinguistic;
import LFuzzyLibrary.FiniteOutputLinguistic;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.RulesBase;
import java.util.Hashtable;

/**
 *
 * @author Milene
 */
public class RulesBaseImpl implements RulesBase{
    
    /**
     * if x is OT, then y = NC, if x is SW, then y = NS, if x is SC, then y =
     * PS, if x is LW, then y = NM, if x is LC, then y = PM, if x is TW, then y
     * = NB, if x is TC, then y = PB, if x is MW, or x is MC, then y = AL
     *
     * @return FiniteInputLinguistic,FiniteOutputLinguistic, F,F
     */
    public LRel<FiniteInputLinguistic, FiniteOutputLinguistic, Pair<Float, Float>> getRulesBase() {

        Hashtable<Pair<FiniteInputLinguistic, FiniteOutputLinguistic>, Pair<Float, Float>> newTable = new Hashtable();

        newTable.put(new Pair(FiniteInputLinguisticImpl.OT, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.SW, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.SC, FiniteOutputLinguisticImpl.PS), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.LW, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.LC, FiniteOutputLinguisticImpl.PM), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.TW, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.TC, FiniteOutputLinguisticImpl.PB), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.MW, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
        newTable.put(new Pair(FiniteInputLinguisticImpl.MC, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));

        LRel<FiniteInputLinguistic, FiniteOutputLinguistic, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }
    
}
