/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.FiniteInputLinguistic;
import LFuzzyLibrary.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msantosteixeira
 */
public class FiniteInputLinguisticImplPair implements FiniteInputLinguistic<Pair<FiniteInputLinguisticImplT, FiniteInputLinguisticImplC>> {

    @Override
    public List<Pair<FiniteInputLinguisticImplT, FiniteInputLinguisticImplC>> getFiniteList() {
        List<Pair<FiniteInputLinguisticImplT, FiniteInputLinguisticImplC>> list = new ArrayList();

        for (FiniteInputLinguisticImplT lT : FiniteInputLinguisticImplT.values()) {
            for (FiniteInputLinguisticImplC lC : FiniteInputLinguisticImplC.values()) {
                list.add(new Pair(lT, lC));
            }
        }
        return list;
    }
    
};
