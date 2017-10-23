/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.DeffuzificationMethod;
import LFuzzyLibrary.EmptySetException;
import LFuzzyLibrary.FinalOutputSet;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
import LFuzzyLibrary.SumRight;

/**
 *
 * @author msantosteixeira
 */
public class DeffuzificationMethodImpl implements DeffuzificationMethod<Integer, Sum> {

 
    /**
     * If list contains at least one alert value, then final result is ALERT,
     * otherwise: return element in the middle of list
     * f : P(B+AL) -> B+AL
     * AL E M -> f(M) = AL
     * @param list
     * @return 
     */
    @Override
    public Sum deffuzificate(FinalOutputSet<Integer, Sum> finalSet) throws EmptySetException {
        if (finalSet.getFinalSet().size() == 0) {
            System.out.println("Empty set. Impossible to deffuzificate!");
            throw new EmptySetException("Empty set. Impossible to deffuzificate!");
        }
        //Check if has an Alert
        for (Pair<Integer, Sum> val : finalSet.getFinalSet()) {
            if (val.getSecond() instanceof SumRight) {
                return val.getSecond();
            }
        }
        //No Alert, so return element in the middle
        int index = 0;
        if (finalSet.getFinalSet().size() > 1) {
            index = Math.round(finalSet.getFinalSet().size() / 2);
        }
        return finalSet.getFinalSet().get(index).getSecond();
    }


}
