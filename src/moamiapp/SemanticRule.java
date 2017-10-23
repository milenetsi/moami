/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

/**
 * {R}   = <Es, p, Eo>
 * @author Milene
 */
public abstract class SemanticRule<A,B> {
    private A entitySubject;
    private B entityObject;
    private String predicateName;

    public SemanticRule(A entitySubject, B entityObject, String predicateName) {
        this.entitySubject = entitySubject;
        this.entityObject = entityObject;
        this.predicateName = predicateName;
    }

    public SemanticRule() {
    }
    
    public abstract Boolean predicate(A entitySubject, B entityObject);

    public A getEntitySubject() {
        return entitySubject;
    }

    public void setEntitySubject(A entitySubject) {
        this.entitySubject = entitySubject;
    }

    public B getEntityObject() {
        return entityObject;
    }

    public void setEntityObject(B entityObject) {
        this.entityObject = entityObject;
    }

    public String getPredicateName() {
        return predicateName;
    }

    public void setPredicateName(String predicateName) {
        this.predicateName = predicateName;
    }

   
    
    
    
}
