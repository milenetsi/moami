/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

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
        Settings s = new Settings();
        List<Integer> list = new ArrayList();
        for (Integer i=s.getLowestOutputValue(); i<=s.getHighestOutputValue(); i++) {
            list.add(i);
        }
        return list;
    }

}
