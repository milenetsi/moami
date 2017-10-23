/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.FiniteInputValues;
import java.util.ArrayList;
import java.util.List;

/**
 * Temperature, Consumption
 *
 * @author msantosteixeira
 */
public class FiniteInputValuesImplC implements FiniteInputValues<Integer> {

    @Override
    public List<Integer> getFiniteList() {
        Settings s = new Settings();
        List<Integer> list = new ArrayList();
        for (Integer j = s.getLowestInputValue().getSecond(); j <= s.getHighestInputValue().getSecond(); j++) {
            list.add(j);
        }
        return list;
    }
}
