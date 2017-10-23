/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * @author msantosteixeira
 */
public class LRel<A, B, L> extends LSet<Pair<A, B>, L> {
    
    public LRel() {
    }
    
    /**
     * @return New LSet containing table which key is the converse of the one
     * received by parameter. Ex: key A B => key B A
     */
    public LRel<B, A, L> converse() {//LRel<A, B, L> param
        Hashtable<Pair<B, A>, L> newTable = new Hashtable();

        Set<Pair<A, B>> keys = this.getTable().keySet();
        Iterator<Pair<A, B>> itr = keys.iterator();

        while (itr.hasNext()) {
            Pair<A, B> key = itr.next();

            Pair<B, A> newKey = new Pair(key.getSecond(), key.getFirst());
            newTable.put(newKey, this.getValue(key));
        }
        //Create new LRel with the new table
        LRel<B, A, L> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

}