/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

/**
 * Se: ({I}, {O})
 * @author Milene
 */
public abstract class Service<A> {
    
    public abstract EventInternal executeService(A input);
    
}
