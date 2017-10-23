package LFuzzyLibrary;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class LSet<A, L> {

    private Hashtable<A, L> table;
    
    public LSet(){
    }

    public LSet(Hashtable<A, L> table) {
        this.table = table;
    }

    public Hashtable<A, L> getTable() {
        return table;
    }

    public void setTable(Hashtable<A, L> table) {
        this.table = table;
    }

    public L getValue(A key) {
        return this.table.get(key);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Hashtable<?, ?>)) {
            return false;
        }

        Hashtable<A, L> hTable = (Hashtable<A, L>) obj;
        if (this.table.size() == hTable.size()) {

            Set<A> keys = this.table.keySet();
            Iterator<A> itr = keys.iterator();

            while (itr.hasNext()) {
                // Getting Key
                A key = itr.next();
                if (hTable.containsKey(key)) {
                    if (!hTable.get(key).equals(this.table.get(key))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public LRel<FiniteUnit, A, L> toLRel() {
        Hashtable<Pair<FiniteUnit, A>, L> newTable = new Hashtable();
        
        Set<A> keys = this.getTable().keySet();
        Iterator<A> itr = keys.iterator();

        while (itr.hasNext()) {
            A key = itr.next();
            Pair<FiniteUnit, A> newPair = new Pair(FiniteUnit.tt, key);
            newTable.put(newPair, this.getValue(key));
        }

        LRel l = new LRel();
        l.setTable(newTable);
        return l;
    }

}
