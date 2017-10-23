/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.FiniteInputLinguistic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milene
 */
public enum FiniteInputLinguisticImplT implements FiniteInputLinguistic<FiniteInputLinguisticImplT> {
    OT, // optimal temperature
    SW, // slightly too warm
    SC, // slightly too cold
    LW, // little bit too warm
    LC, // little bit too cold
    TW, // too warm
    TC, // too cold
    MW, // much too warm
    MC,; // much too cold// much too cold


    @Override
    public List<FiniteInputLinguisticImplT> getFiniteList() {
        List<FiniteInputLinguisticImplT> list = new ArrayList();
        for (FiniteInputLinguisticImplT f : FiniteInputLinguisticImplT.values()) {
            list.add(f);
        }
        return list;
    }
}
