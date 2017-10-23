/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Aa : (name, {Sp}, {Se}, {E})
 *
 * @author Milene
 */
public abstract class AutomatizedAction<A> {

    private String name;
    private List<SemanticRule> preconditions;
    private Hashtable<Integer, Service<A>> services;//LinkedHashMap
    private A input;//Output of the MOR 

    public AutomatizedAction(String name) {
        this.name = name;
    }

    public AutomatizedAction(String name, A input) {
        this.name = name;
        this.input = input;
    }

    public abstract void setValues(List<SemanticRule> preconditions, Hashtable<Integer, Service<A>> services);
    public abstract void generateValues();

    private Boolean verifyPreconditions() {
        for (SemanticRule x : preconditions) {
            if (!x.predicate(x.getEntitySubject(), x.getEntityObject())) {
                return false;
            }
        }
        return true;
    }

    public List<EventInternal> executeAa(A input) throws Exception {

        List<EventInternal> outputEvents = new ArrayList();
        if (verifyPreconditions()) {
            //Executes services in the order pre-defined
            for (Integer i = 0; i < services.size(); i++) {
                Service<A> service = services.get(i);
                outputEvents.add(service.executeService(input));
            }
        } else {
            throw new Exception("Preconditions not satisfied! Can't execute automatized action!");
        }
        return outputEvents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SemanticRule> getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(List<SemanticRule> preconditions) {
        this.preconditions = preconditions;
    }

    public Hashtable<Integer, Service<A>> getServices() {
        return services;
    }

    public void setServices(Hashtable<Integer, Service<A>> services) {
        this.services = services;
    }

    public A getInput() {
        return input;
    }

    public void setInput(A input) {
        this.input = input;
    }
    
    

}
