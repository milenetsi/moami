/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

import java.util.Date;
import java.util.List;

/**
 * By pattern, Event is considered 'internal/inner event'
 *
 * @author Milene
 */
public abstract class Event<A> {

    private String name;
    private Boolean isInternal;
    private Date time;
    private List<SemanticRule> semanticRules;

    public abstract A patternOfRecognition();

    public Event(String name) {
        this.name = name;
    }

    public Event(String name, Boolean isInternal, Date time, List<SemanticRule> semanticRules) {
        this.name = name;
        this.isInternal = isInternal;
        this.time = time;
        this.semanticRules = semanticRules;
    }

    public Event(String name, Date time, List<SemanticRule> semanticRules) {
        this.name = name;
        this.time = time;
        this.semanticRules = semanticRules;
    }

    public Event(String name, Boolean isInternal, List<SemanticRule> semanticRules) {
        this.name = name;
        this.isInternal = isInternal;
        this.semanticRules = semanticRules;
    }

    public Event(String name, List<SemanticRule> semanticRules) {
        this.name = name;
        this.semanticRules = semanticRules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(Boolean isInternal) {
        this.isInternal = isInternal;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<SemanticRule> getSemanticRules() {
        return semanticRules;
    }

    public void setSemanticRules(List<SemanticRule> semanticRules) {
        this.semanticRules = semanticRules;
    }

}
