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
public class Composition<A, B, C, L> {

    private Finite<A> finiteA;
    private Finite<B> finiteB;
    private Finite<C> finiteC;

    public Composition(Finite<A> finiteA, Finite<B> finiteB, Finite<C> finiteC) {
        this.finiteA = finiteA;
        this.finiteB = finiteB;
        this.finiteC = finiteC;
    }

    public LRel<A, C, L> composition(HeytA<L> algebra, LRel<A, B, L> param1, LRel<B, C, L> param2, GenericComposition<L> genComp) {

        Hashtable<Pair<A, C>, L> newTable = new Hashtable();
        //For all B values

        for (A valueA : finiteA.getFiniteList()) {
            for (C valueC : finiteC.getFiniteList()) {
                L res = algebra.bot();
                for (B valueB : finiteB.getFiniteList()) {

                    //
                    Pair<A, B> key1 = new Pair(valueA, valueB);
                    Pair<B, C> key2 = new Pair(valueB, valueC);

                    if (param1.getTable().containsKey(key1) && param2.getTable().containsKey(key2)) {
                        //res = join ( p1 meet p2)
                        res = algebra.join(res, genComp.compositionMethod(param1.getValue(key1), param2.getValue(key2)));
                    }
                }
                newTable.put(new Pair(valueA, valueC), res);
            }
        }

        //Create new LRel with the new table
        LRel<A, C, L> lrel = new LRel();
        lrel.setTable(newTable);
        return lrel;
    }

    public LSet<A, L> toLSet(LRel<FiniteUnit, A, L> lRelObject) {
        Hashtable<A, L> newTable = new Hashtable();

        Set<Pair<FiniteUnit, A>> keys = lRelObject.getTable().keySet();
        Iterator<Pair<FiniteUnit, A>> itr = keys.iterator();

        //Iterate through this list, adding only the second value to the hashtable
        while (itr.hasNext()) {
            Pair<FiniteUnit, A> key = itr.next();
            newTable.put(key.getSecond(), lRelObject.getValue(key));
        }

        //Create new LSet with the a table containing A values
        LSet<A, L> newLSet = new LSet(newTable);
        return newLSet;
    }

}
