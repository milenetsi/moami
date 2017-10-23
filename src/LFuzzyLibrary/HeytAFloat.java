/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LFuzzyLibrary;

/**
 *
 * @author milene
 */
public class HeytAFloat extends HeytA<Float> {
    
    //Default values, can be setted
    private Float minValue = 0.00f;
    private Float maxValue = 1.00f;

    public HeytAFloat() {
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public Float meet(Float param1, Float param2){
        return Math.min(param1, param2);
    }

    public Float join(Float param1, Float param2){
        return Math.max(param1, param2);
    }

    public Float psComp(Float param1, Float param2){
        Float psC = param2;
        if (param1 <= param2){
            psC = this.top();
        }
        return psC;
    }

    public Float bot(){
        return this.minValue;
    }

    public Float top(){
        return this.maxValue;
    }
}
