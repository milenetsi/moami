/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiappTC;

import TemperatureConsumptionFC.Settings;
import TemperatureConsumptionFC.TCFuzzyController;
import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
import java.util.List;
import moamiapp.MuiltiobjectiveReasoning;
import moamiapp.Objective;

/**
 *
 * @author Milene
 */
public class MuiltiobjectiveReasoningImpl extends MuiltiobjectiveReasoning<Sum> {

    private TCFuzzyController controller;
    private Settings settings;

    public MuiltiobjectiveReasoningImpl(List<Objective> objectives, Settings s) {
        super(objectives);
        this.settings = s;
        this.controller = new TCFuzzyController(s);
    }

    @Override
    public Sum executeReasoning() {     
        return controller.inference(settings.getCutValue(), new Pair(this.getObjectives().get(0).getValue(), this.getObjectives().get(1).getValue()));
    }

}
