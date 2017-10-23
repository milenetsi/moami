/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiappTC;

import TemperatureConsumptionFC.FiniteInputValuesImplC;
import TemperatureConsumptionFC.FiniteInputValuesImplT;
import TemperatureConsumptionFC.Settings;
import LFuzzyLibrary.FiniteInputValues;
import LFuzzyLibrary.HeytA;
import LFuzzyLibrary.HeytAFloat;
import LFuzzyLibrary.Pair;
import java.util.ArrayList;
import java.util.List;
import moamiapp.Event;
import moamiapp.InterestSituation;
import moamiapp.Objective;

/**
 *
 * @author Milene
 */
public class InterestSituationImpl extends InterestSituation {

    private List<Pair<Integer, Float>> intervalT;
    private List<Pair<Integer, Float>> intervalC;

    public InterestSituationImpl(String name, List<Objective> objectives, Settings s) {
        super(name);

        //Interval Temp
        intervalT = calcInterval(new FiniteInputValuesImplT(), 0.7f, s.getA1Input1(), s.getB1Input1(), s.getOptimalValueA().getFirst());
        //Interval Cons
        intervalC = calcInterval(new FiniteInputValuesImplC(), 0.7f, s.getA1Input2(), s.getB1Input2(), s.getOptimalValueA().getSecond());

        //Ie
        List initialEventsList = new ArrayList<Event>();
        initialEventsList.add(new Ev1("ev1", intervalT, intervalC));

        //Set values
        this.setValues(objectives, initialEventsList, new Ev2("ev2"));
    }

    @Override
    public Boolean situationOn() {
        //If the value of the objective is not in the expected interval, then the MO reasoning process should start
        boolean valueReadIsFine = false;

        //Check if temperature presents an accepted value
        Objective<Integer> oTemp = this.getObjectives().get(0);
        if (oTemp.getName().equals("temperature")) {
            for (Pair<Integer, Float> val : intervalT) {
                if (val.getFirst().equals(oTemp.getValue())) {
                    valueReadIsFine = true;//Value read is within the accepted interval
                    break;
                }
            }
        }

        //Temperature was ok, now let's check if concumption is fine
        if (valueReadIsFine) {
            Objective<Integer> oCons = this.getObjectives().get(1);
            if (oCons.getName().equals("consumption")) {
                for (Pair<Integer, Float> val : intervalC) {
                    if (oCons.getValue()<=val.getFirst()) {
                        return false; //Both are fine!Situation is not on!
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void setValues(List<Objective> objectives, List<Event> initialEvents, Event finalEvent) {
        this.setObjectives(objectives);
        this.setInitialEvents(initialEvents);
        this.setFinalEvent(finalEvent);
    }

    /**
     * Luk... min(1, max (0, a- (b * |x-y|)))
     *
     * @return
     */
    private List<Pair<Integer, Float>> calcInterval(FiniteInputValues<Integer> finiteValues, Float minMembDegree, Float a1, Float b1, Integer optimal) {
        HeytA<Float> algebra = new HeytAFloat();
        List<Pair<Integer, Float>> interval = new ArrayList<>();

        for (Integer fv1 : finiteValues.getFiniteList()) {
            Float f1 = algebra.meet(1.0f, algebra.join(0.0f, a1 - (b1 * Math.abs((fv1 * 0.1f) - (optimal * 0.1f)))));
            if (f1 >= minMembDegree) {
                interval.add(new Pair(fv1, f1));
            }
        }
        return interval;
    }
}
