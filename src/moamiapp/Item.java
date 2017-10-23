/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiapp;

/**
 *
 * @author Milene
 */
public class Item<A> {
    private String name;
    private A value;

    public Item(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public A getValue() {
        return value;
    }

    public void setValue(A value) {
        this.value = value;
    }
    
    
}
