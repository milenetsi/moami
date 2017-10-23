/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.FiniteInputValues;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msantosteixeira
 */
public class FiniteInputValuesImpl implements FiniteInputValues<Integer> {

    @Override 
    public List<Integer> getFiniteList() {
        List<Integer> list = new ArrayList();
        for (Integer i=0; i<=500; i++) {
            list.add(i);
        }
        return list;
    }
}
