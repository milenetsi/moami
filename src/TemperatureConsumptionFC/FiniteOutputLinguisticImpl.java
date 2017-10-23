/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

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
    AL; // alert

    @Override
    public List<FiniteOutputLinguisticImpl> getFiniteList() {
        List<FiniteOutputLinguisticImpl> list = new ArrayList();
        for (FiniteOutputLinguisticImpl f : FiniteOutputLinguisticImpl.values()) {
            list.add(f);
        }
        return list;
    }
}
