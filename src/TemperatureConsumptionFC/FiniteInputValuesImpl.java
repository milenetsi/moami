/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.FiniteInputValues;
import LFuzzyLibrary.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * Temperature, Consumption
 *
 * @author msantosteixeira
 */
public class FiniteInputValuesImpl implements FiniteInputValues<Pair<Integer, Integer>> {

    @Override
    public List<Pair<Integer, Integer>> getFiniteList() {
        List<Pair<Integer, Integer>> list = new ArrayList();
        
        FiniteInputValuesImplT f1 = new FiniteInputValuesImplT();
        FiniteInputValuesImplC f2 = new FiniteInputValuesImplC();
        for (Integer i : f1.getFiniteList()) {
            for (Integer j : f2.getFiniteList()) {
                list.add(new Pair(i, j));
            }
        }
        return list;
    }
}
