/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;


/**
 *
 * @author msantosteixeira
 */
public interface DeffuzificationMethod<A,B> {
    public B deffuzificate(FinalOutputSet<A,B> finalSet) throws EmptySetException;
}
