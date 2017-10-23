/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.FiniteInputLinguistic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msantosteixeira
 */
public enum FiniteInputLinguisticImpl implements FiniteInputLinguistic<FiniteInputLinguisticImpl> {

    OT, // optimal temperature
    SW, // slightly too warm
    SC, // slightly too cold
    LW, // little bit too warm
    LC, // little bit too cold
    TW, // too warm
    TC, // too cold
    MW, // much too warm
    MC; // much too cold// much too cold

    @Override
    public List<FiniteInputLinguisticImpl> getFiniteList() {
        List<FiniteInputLinguisticImpl> list = new ArrayList();
        for( FiniteInputLinguisticImpl f : FiniteInputLinguisticImpl.values()){
            list.add(f);
        }
        return list;
    }
};
