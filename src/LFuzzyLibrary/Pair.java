/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.Objects;

/**
 *
 * @author msantosteixeira
 */
public class Pair<X, Y> {

    private X first;		// first component of type X
    private Y second;		// second component of type Y

    public <A extends X, B extends Y> Pair(A a, B b) {
        first = a;
        second = b;
    } // constructor

    public Pair(Pair<? extends X, ? extends Y> p) {
        first = p.getFirst();
        second = p.getSecond();
    } // constructor

    public X getFirst() {
        return first;
    } // getFirst

    public void setFirst(X x) {
        first = x;
    } // setFirst

    public Y getSecond() {
        return second;
    } // getSecond

    public void setSecond(Y y) {
        second = y;
    } // setSecond

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?>) {
            Pair<?, ?> p = (Pair<?, ?>) obj;
            return first.equals(p.getFirst()) && second.equals(p.getSecond());
        };
        return false;
    } // equals
    
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

}
