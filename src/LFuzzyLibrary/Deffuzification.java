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
public abstract class Deffuzification<A, B, L> {

    private DeffuzificationMethod deffuz;

    public Deffuzification(DeffuzificationMethod deffuz) {
        this.deffuz = deffuz;
    }

    public abstract FinalOutputSet<A, B> cut(LRel<A, B, L> outputSet, L cutValue, A readingValue);

    /**
     * Method that returns final crisp value to be used
     *
     * @param finalSet LRel with resulting relation
     * @return final value
     */
    public final B calcOutput(FinalOutputSet<A, B> finalSet) throws EmptySetException {
        Object finalOutputValue = deffuz.deffuzificate(finalSet);
        B finalOutputValueB = (B) finalOutputValue;
        return finalOutputValueB;
    }

}
