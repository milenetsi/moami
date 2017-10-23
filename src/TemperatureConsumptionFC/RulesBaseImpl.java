/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.FiniteInputLinguistic;
import LFuzzyLibrary.FiniteOutputLinguistic;
import LFuzzyLibrary.LRel;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.RulesBase;
import java.util.Hashtable;

/**
 * @author Milene
 */
public class RulesBaseImpl implements RulesBase {

    /**
     * if x is OT, then y = NC, if x is SW, then y = NS, if x is SC, then y =
     * PS, if x is LW, then y = NM, if x is LC, then y = PM, if x is TW, then y
     * = NB, if x is TC, then y = PB, if x is MW, or x is MC, then y = AL
     *
     * @return FiniteInputLinguistic,FiniteOutputLinguistic, F,F
     */
    public LRel<FiniteInputLinguistic, FiniteOutputLinguistic, Pair<Float, Float>> getRulesBase() {

        Hashtable<Pair<FiniteInputLinguistic, FiniteOutputLinguistic>, Pair<Float, Float>> newTable = new Hashtable();

        //Contains list of all possible pairs of linguistic input
        FiniteInputLinguisticImplPair fILP = new FiniteInputLinguisticImplPair();
        //FiniteInputLinguisticImplPair
        for (Pair<FiniteInputLinguisticImplT, FiniteInputLinguisticImplC> fl : fILP.getFiniteList()) {
            //OT
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.OT) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }
            //SW
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SW) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }
            //SC
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.SC) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }
            //LW
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LW) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }
            //LC
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NC), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.LC) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }
            //TW
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NS), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TW) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }
            //TC
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.TC) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }
            //MW
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MW) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }
            //MC
            if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.OC)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.SL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.LL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NM), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.TL)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.MH)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.AL), new Pair(1.0f, 1.0f));
            }else if(fl.getFirst().equals(FiniteInputLinguisticImplT.MC) && fl.getSecond().equals(FiniteInputLinguisticImplC.ML)){
                newTable.put(new Pair(fl, FiniteOutputLinguisticImpl.NB), new Pair(1.0f, 1.0f));
            }
        }

        LRel<FiniteInputLinguistic, FiniteOutputLinguistic, Pair<Float, Float>> lrelResult = new LRel();
        lrelResult.setTable(newTable);
        return lrelResult;
    }

}
