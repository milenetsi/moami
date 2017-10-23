/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

import java.util.List;

/**
 *
 * @author Milene
 */
public abstract class MuiltiobjectiveReasoning<A> {

    private List<Objective> objectives;
    private A output;

    public MuiltiobjectiveReasoning(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public MuiltiobjectiveReasoning() {
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public A getOutput() {
        return output;
    }

    public void setOutput(A output) {
        this.output = output;
    }

    public abstract A executeReasoning();

}
