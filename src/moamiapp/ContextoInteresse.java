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
public abstract class ContextoInteresse {

    private List<SemanticRule> semanticRules;
    private List<Objective> objectives;

    public ContextoInteresse() {
    }

    public List<SemanticRule> getSemanticRules() {
        return semanticRules;
    }

    public void setSemanticRules(List<SemanticRule> semanticRules) {
        this.semanticRules = semanticRules;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

}
