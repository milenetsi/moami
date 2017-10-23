/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.List;

/**
 * Contains list of values obtained after the cut
 * @author msantosteixeira
 */
public class FinalOutputSet<A,B> {
    private List<Pair<A,B>> finalSet;

    public FinalOutputSet(List<Pair<A, B>> finalSet) {
        this.finalSet = finalSet;
    }

    public List<Pair<A, B>> getFinalSet() {
        return finalSet;
    }

    public void setFinalSet(List<Pair<A, B>> finalSet) {
        this.finalSet = finalSet;
    }
    
}
