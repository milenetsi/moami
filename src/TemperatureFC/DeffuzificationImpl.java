/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureFC;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author msantosteixeira
 */
public class DeffuzificationImpl extends Deffuzification<Integer, Sum, Pair<Float, Float>> {

    public DeffuzificationImpl(DeffuzificationMethod deffuz) {
        super(deffuz);
    }

    /* (L \ C)!
    
    '(a,b) l' -> '(a,b) top if L <=l else bot'
     */
    public FinalOutputSet<Integer, Sum> cut(LRel<Integer, Sum, Pair<Float, Float>> outputSet, Pair<Float, Float> cutValue, Integer readingValue) {
        List<Pair<Integer, Sum>> finalList = new ArrayList();

        Set<Pair<Integer, Sum>> keys = outputSet.getTable().keySet();
        Iterator<Pair<Integer, Sum>> itr = keys.iterator();

        try {

            while (itr.hasNext()) {
                Pair<Integer, Sum> key = itr.next();
                Pair<Float, Float> pairValue = outputSet.getValue(key);

                //Get only values for corresponding input
                if (key.getFirst().equals(readingValue)) {
                    
                    //If values are >= then they should be in the final set
                    if ((pairValue.getFirst() >= cutValue.getFirst()) && (pairValue.getSecond() >= cutValue.getSecond())) {
                        finalList.add(key);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DeffuzificationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new FinalOutputSet(finalList);
    }

}
