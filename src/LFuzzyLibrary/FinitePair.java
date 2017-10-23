/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.List;

/**
 *
 * @author msantosteixeira
 */
public class FinitePair<A, B> implements Finite<Pair<A, B>> {

    private List<Pair<A, B>> list;

    public FinitePair(List<Pair<A, B>> list) {
        this.list = list;
    }

    //@Override
    public List<Pair<A, B>> getFiniteList() {
        return this.list;
    }
}
