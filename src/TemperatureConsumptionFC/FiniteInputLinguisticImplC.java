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
public enum FiniteInputLinguisticImplC implements FiniteInputLinguistic<FiniteInputLinguisticImplC> {
    OC, // optimal consumption
    SH, // slightly too high
    SL, // slightly too low
    LH, // little bit too high
    LL, // little bit too low
    TH, // too high
    TL, // too low
    MH, // much too high
    ML; // much too low// much too low

    @Override
    public List<FiniteInputLinguisticImplC> getFiniteList() {
        List<FiniteInputLinguisticImplC> list = new ArrayList();
        for (FiniteInputLinguisticImplC f : FiniteInputLinguisticImplC.values()) {
            list.add(f);
        }
        return list;
    }
}
