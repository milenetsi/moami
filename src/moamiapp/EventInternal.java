/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Milene
 */
public abstract class EventInternal<A> extends Event{
    
    public abstract A patternOfRecognition();

    public EventInternal(String name, Boolean isInternal, Date time, List semanticRules) {
        super(name, true, time, semanticRules);
    }

    public EventInternal(String name, Boolean isInternal, List semanticRules) {
        super(name, true, semanticRules);
    }

    public EventInternal(String name) {
        super(name);
        this.setIsInternal(Boolean.TRUE);
    }
    
    
    
}
