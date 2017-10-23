/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

import java.util.List;

/**
 * Si: (name, obSet, evSet, Fe)
 *
 * @author Milene
 */
public abstract class InterestSituation extends CurrentSituation {

    private List<Objective> objectives;
    private List<Event> initialEvents;
    private Event finalEvent;

    public InterestSituation(String name) {
        this.setName(name);
    }

    public InterestSituation(List<Event> initialEvents, String name, Event finalEvent) {
        super(name, initialEvents.get(0), finalEvent);
        this.initialEvents = initialEvents;
    }

    @Override
    public abstract Boolean situationOn();
    
    public abstract void setValues(List<Objective> objectives, List<Event> initialEvents, Event finalEvent);

    public List<Objective> getObjectives() {
        return objectives;
    }

    public List<Event> getInitialEvents() {
        return initialEvents;
    }

    public Event getFinalEvent() {
        return finalEvent;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public void setInitialEvents(List<Event> initialEvents) {
        this.initialEvents = initialEvents;
    }

    public void setFinalEvent(Event finalEvent) {
        this.finalEvent = finalEvent;
    }

}
