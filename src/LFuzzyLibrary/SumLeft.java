/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

import java.util.Objects;

/**
 *
 * @author milene
 */
public class SumLeft<A> implements Sum<A> {

    private A leftElement;

    public SumLeft() {
    }

    public SumLeft(A leftElement) {
        this.leftElement = leftElement;
    }

    public A getLeftElement() {
        return leftElement;
    }

    public void setLeftElement(A leftElement) {
        this.leftElement = leftElement;
    }

    @Override
    public A getValue() {
        return this.getLeftElement();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SumLeft<?>) {
            SumLeft<?> p = (SumLeft<?>) obj;
            return leftElement.equals(p.getLeftElement());
        };
        return false;
    } // equals

    @Override
    public int hashCode() {
        return Objects.hash(leftElement);
    }

}
