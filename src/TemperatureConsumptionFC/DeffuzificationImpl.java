/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.DeffuzificationMethod;
import LFuzzyLibrary.Deffuzification;
import LFuzzyLibrary.FinalOutputSet;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author msantosteixeira
 */
public class DeffuzificationImpl extends Deffuzification<Pair<Integer, Integer>, Sum, Pair<Float, Float>> {

    public DeffuzificationImpl(DeffuzificationMethod deffuz) {
        super(deffuz);
    }

    /* (L \ C)!
    
    '(a,b) l' -> '(a,b) top if L <=l else bot'
     */
    public FinalOutputSet<Pair<Integer, Integer>, Sum> cut(LRel<Pair<Integer, Integer>, Sum, Pair<Float, Float>> outputSet, Pair<Float, Float> cutValue, Pair<Integer, Integer> readingValue) {
        List<Pair<Pair<Integer, Integer>, Sum>> finalList = new ArrayList();

        Set<Pair<Pair<Integer, Integer>, Sum>> keys = outputSet.getTable().keySet();
        Iterator<Pair<Pair<Integer, Integer>, Sum>> itr = keys.iterator();

        try {
            int count = 0;
            while (itr.hasNext()) {
                Pair<Pair<Integer, Integer>, Sum> key = itr.next();
                Pair<Float, Float> pairValue = outputSet.getValue(key);

                //Get only values for corresponding input
                if (key.getFirst().getFirst().equals(readingValue.getFirst()) && key.getFirst().getSecond().equals(readingValue.getSecond())) {
                    count++;

                    //If values are >= then they should be in the final set
                    if ((pairValue.getFirst() >= cutValue.getFirst()) && (pairValue.getSecond() >= cutValue.getSecond())) {
                        //System.out.println("pair: " + pairValue.getFirst() + " - " + pairValue.getSecond() + " key: " + key.getSecond().getValue().toString());
                        finalList.add(key);
                    } 
                }
            }
        } catch (Exception e) {
            // do something
        }

        return new FinalOutputSet(finalList);
    }

}
