/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

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
