/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.Finite;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msantosteixeira
 */
public class FiniteOutputValuesImpl implements Finite<Integer> {

    @Override
    public List<Integer> getFiniteList() {
        List<Integer> list = new ArrayList();
        for (Integer i=-200; i<=200; i++) {
            list.add(i);
        }
        return list;
    }

}
