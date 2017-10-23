/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.FiniteOutputLinguistic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msantosteixeira
 */
public enum FiniteOutputLinguisticImpl implements FiniteOutputLinguistic<FiniteOutputLinguisticImpl> {

    NC, // no change
    PS, // positive small
    NS, // negative small
    PM, // positive medium
    NM, // negative medium
    PB, // positive big
    NB, // negative big
    AL; // alert// alert

    @Override
    public List<FiniteOutputLinguisticImpl> getFiniteList() {
        List<FiniteOutputLinguisticImpl> list = new ArrayList();
        for (FiniteOutputLinguisticImpl f : FiniteOutputLinguisticImpl.values()) {
            list.add(f);
        }
        return list;
    }
}
