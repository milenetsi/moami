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
public class SumRight<B> implements Sum<B> {

    private B rightElement;

    public SumRight() {
    }

    public SumRight(B rightElement) {
        this.rightElement = rightElement;
    }

    public B getRightElement() {
        return rightElement;
    }

    public void setRightElement(B rightElement) {
        this.rightElement = rightElement;
    }

    @Override
    public B getValue() {
        return this.getRightElement();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SumRight<?>) {
            SumRight<?> p = (SumRight<?>) obj;
            return rightElement.equals(p.getRightElement());
        };
        return false;
    } // equals

    @Override
    public int hashCode() {
        return Objects.hash(rightElement);
    }

}
