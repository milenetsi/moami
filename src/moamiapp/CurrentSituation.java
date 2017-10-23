/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

/**
 * Sa:(name, Ie, Fe)
 * @author Milene
 */
public abstract class CurrentSituation {
    
    private String name;
    private Event initialEvent;
    private Event finalEvent;

    public CurrentSituation() {
    }
    
    public CurrentSituation(String name, Event initialEvent, Event finalEvent) {
        this.name = name;
        this.initialEvent = initialEvent;
        this.finalEvent = finalEvent;
    }
    
    public abstract Boolean situationOn();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getInitialEvent() {
        return initialEvent;
    }

    public void setInitialEvent(Event initialEvent) {
        this.initialEvent = initialEvent;
    }

    public Event getFinalEvent() {
        return finalEvent;
    }

    public void setFinalEvent(Event finalEvent) {
        this.finalEvent = finalEvent;
    }
    
    
    
}
