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
public abstract class HeytA<LA> {
    
    public HeytA() {
    }    

    public abstract LA meet(LA param1, LA param2);

    public abstract LA join(LA param1, LA param2);

    public abstract LA psComp(LA param1, LA param2);

    public abstract LA bot();

    public abstract LA top();

    //LUB : list LA -> LA := fun ls => fold_right Join Bot ls;
    public LA lub(LA[] list) {
        LA result = this.bot();
        for (int i = 0; i < list.length; i++) {
            result = this.join(result, list[i]);
        }
        return result;
    }

    //GLB : list LA -> LA := fun ls => fold_right Meet Top ls;
    public LA glb(LA[] list) {
        LA result = this.top();
        for (int i = 0; i < list.length; i++) {
            result = this.meet(result, list[i]);
        }
        return result;
    }

    //Order : LA -> LA -> Prop := fun x y => Meet x y = x
    public Boolean order(LA param1, LA param2) {
        return param1.equals(this.meet(param1, param2));
    }
}
