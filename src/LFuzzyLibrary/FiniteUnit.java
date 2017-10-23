/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milene
 */
public enum FiniteUnit implements Finite<FiniteUnit> {

    tt; //Unit value
    
    @Override
    public List<FiniteUnit> getFiniteList() {
        List<FiniteUnit> list = new ArrayList();
        for (FiniteUnit f : FiniteUnit.values()) {
            list.add(f);
        }
        return list;
    }

}
