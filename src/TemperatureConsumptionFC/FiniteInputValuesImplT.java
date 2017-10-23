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
public class FiniteInputValuesImplT implements FiniteInputValues<Integer> {

    @Override
    public List<Integer> getFiniteList() {
        Settings s = new Settings();
        List<Integer> list = new ArrayList();
        for (Integer i = s.getLowestInputValue().getFirst(); i <= s.getHighestInputValue().getFirst(); i++) {
            list.add(i);
        }
        return list;
    }
}
