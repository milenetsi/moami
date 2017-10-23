/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.DeffuzificationMethod;
import LFuzzyLibrary.EmptySetException;
import LFuzzyLibrary.FinalOutputSet;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
import LFuzzyLibrary.SumRight;
import java.util.Random;

/**
 *
 * @author msantosteixeira
 */
public class DeffuzificationMethodImpl implements DeffuzificationMethod<Pair<Integer, Integer>, Sum> {

    /**
     * If list contains at least one alert value, then final result is ALERT,
     * otherwise: return element in the middle of list f : P(B+AL) -> B+AL AL E
     * M -> f(M) = AL
     *
     * @param list
     * @return
     */
    @Override
    public Sum deffuzificate(FinalOutputSet<Pair<Integer, Integer>, Sum> finalSet) throws EmptySetException {
        if (finalSet.getFinalSet().size() == 0) {
            throw new EmptySetException("Empty set. Impossible to deffuzificate!");
        }
        //Check if set has an Alert
        for (Pair<Pair<Integer, Integer>, Sum> val : finalSet.getFinalSet()) {
            if (val.getSecond() instanceof SumRight) {
                System.out.println("Alert found!");
                return val.getSecond();
            }
        }
        //No Alert, so return element in the middle of the set
        int index = 0;
        if (finalSet.getFinalSet().size() > 1) {
            //index = Math.round(finalSet.getFinalSet().size() / 2);
            Random rand = new Random();
            index = rand.nextInt(Math.round(finalSet.getFinalSet().size()));
        }
        return finalSet.getFinalSet().get(index).getSecond();
    }

}
