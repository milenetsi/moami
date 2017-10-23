/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.Hashtable;

/**
 * @author msantosteixeira
 */
public class HeytALSet<A, L> extends HeytA<LSet<A, L>> {

    private HeytA<L> algebra;
    private Finite<A> finiteValues;

    public HeytALSet(Finite<A> finiteValues, HeytA<L> algebra) {
        this.finiteValues = finiteValues;
        this.algebra = algebra;
    }

    public HeytALSet() {
    }

    public HeytA<L> getAlgebra() {
        return algebra;
    }

    public void setAlgebra(HeytA<L> algebra) {
        this.algebra = algebra;
    }

    public Finite<A> getFiniteValues() {
        return finiteValues;
    }

    public void setFiniteValues(Finite<A> finiteValues) {
        this.finiteValues = finiteValues;
    }
    
    @Override
    public LSet<A, L> meet(LSet<A, L> param1, LSet<A, L> param2) {
        Hashtable<A, L> newTable = new Hashtable();

        for (A finiteValue : finiteValues.getFiniteList()) {

            //If both param1 AND param2 have the key, insert the meet value
            if (param1.getTable().containsKey(finiteValue) && param2.getTable().containsKey(finiteValue)) {
                
                L meetValue = algebra.meet(param1.getValue(finiteValue), param2.getValue(finiteValue));

                //Add key + new HeytA with join result to the table
                newTable.put(finiteValue, meetValue);
            }
        }

        //Create new LSet with the new table
        LSet<A, L> newLSet = new LSet(newTable);
        return newLSet;
    }

    @Override
    public LSet<A, L> join(LSet<A, L> param1, LSet<A, L> param2) {
        Hashtable<A, L> newTable = new Hashtable();

        for (A finiteValue : finiteValues.getFiniteList()) {

            //If either param1 OR param2 have the key, insert the join value
            if (param1.getTable().containsKey(finiteValue) || param2.getTable().containsKey(finiteValue)) {
                //If same key exists in param2, use algebra join
                L joinValue = algebra.join(param1.getValue(finiteValue), param2.getValue(finiteValue));

                //Add key + new HeytA with join result to the table
                newTable.put(finiteValue, joinValue);
            }

        }

        //Create new LSet with the new table
        LSet<A, L> newLSet = new LSet(newTable);
        return newLSet;
    }

    @Override
    public LSet<A, L> psComp(LSet<A, L> param1, LSet<A, L> param2) {
        Hashtable<A, L> newTable = new Hashtable();

        for (A finiteValue : finiteValues.getFiniteList()) {

            if (param1.getTable().containsKey(finiteValue) && !param2.getTable().containsKey(finiteValue)) {
                //Param1 has key and param2 doesn't => psComp(p1, bot)
                L psCValue = algebra.psComp(param1.getValue(finiteValue), algebra.bot());
                newTable.put(finiteValue, psCValue);
            } else if (param1.getTable().containsKey(finiteValue) && param2.getTable().containsKey(finiteValue)) {
                //Both have key
                L psCValue = algebra.psComp(param1.getValue(finiteValue), param2.getValue(finiteValue));
                newTable.put(finiteValue, psCValue);
            } else {
                //Param1 does not have the key => Top
                newTable.put(finiteValue, algebra.top());
            }
        }

        //Create new LSet with the new table
        LSet<A, L> newLSet = new LSet(newTable);
        return newLSet;
    }

    @Override
    public LSet<A, L> bot() {
        //Bottom is the empty hTable
        Hashtable<A, L> table = new Hashtable();
        LSet<A, L> newLSet = new LSet(table);
        return newLSet;
    }

    @Override
    public LSet<A, L> top() {
        //Takes var Finite (list of all possible values) and map them to top
        Hashtable<A, L> table = new Hashtable();

        for (A finiteValue : finiteValues.getFiniteList()) {
            table.put(finiteValue, algebra.top());
        }

        LSet<A, L> newLSet = new LSet(table);
        return newLSet;
    }
    
}
