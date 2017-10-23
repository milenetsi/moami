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
public class HeytAPair<A, B> extends HeytA<Pair<A, B>> {

    private HeytA<A> algebraA;
    private HeytA<B> algebraB;

    public HeytAPair(HeytA<A> algebraA, HeytA<B> algebraB) {
        this.algebraA = algebraA;
        this.algebraB = algebraB;
    }

    public HeytAPair() {
    }

    public HeytA<A> getAlgebraA() {
        return algebraA;
    }

    public void setAlgebraA(HeytA<A> algebraA) {
        this.algebraA = algebraA;
    }

    public HeytA<B> getAlgebraB() {
        return algebraB;
    }

    public void setAlgebraB(HeytA<B> algebraB) {
        this.algebraB = algebraB;
    }

    @Override
    public Pair<A, B> meet(Pair<A, B> param1, Pair<A, B> param2) {
        //pair = meet A1 B1, meet A2 B2
        A first = algebraA.meet(param1.getFirst(), param2.getFirst());
        B second = algebraB.meet(param1.getSecond(), param2.getSecond());
        return new Pair<A, B>(first, second);
    }

    @Override
    public Pair<A, B> join(Pair<A, B> param1, Pair<A, B> param2) {
        //pair = join A1 B1, join A2 B2
        A first = algebraA.join(param1.getFirst(), param2.getFirst());
        B second = algebraB.join(param1.getSecond(), param2.getSecond());
        return new Pair<A, B>(first, second);
    }

    @Override
    public Pair<A, B> psComp(Pair<A, B> param1, Pair<A, B> param2) {
        //pair = psComp A1 B1, psComp A2 B2
        A first = algebraA.psComp(param1.getFirst(), param2.getFirst());
        B second = algebraB.psComp(param1.getSecond(), param2.getSecond());
        return new Pair<A, B>(first, second);
    }

    @Override
    public Pair<A, B> bot() {
        return new Pair<A, B>(algebraA.bot(), algebraB.bot());
    }

    @Override
    public Pair<A, B> top() {
        return new Pair<A, B>(algebraA.top(), algebraB.top());
    }

}
