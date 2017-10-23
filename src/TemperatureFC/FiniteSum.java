/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.FiniteOutputValues;
import LFuzzyLibrary.Sum;
import LFuzzyLibrary.SumLeft;
import LFuzzyLibrary.SumRight;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all output values + Alert
 * @author msantosteixeira
 */
public class FiniteSum implements FiniteOutputValues<Sum> {
    
    
    @Override
    public List<Sum> getFiniteList() {
        List<Sum> list = new ArrayList();
        
        FiniteOutputValuesImpl fOutValues = new FiniteOutputValuesImpl();
        for (Integer v : fOutValues.getFiniteList()) {
            list.add(new SumLeft(v));
        }
        list.add(new SumRight<String>("AL"));
        return list;
    }

}
