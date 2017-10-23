/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.Pair;

/**
 *
 * @author Milene
 */
public class Settings {

    private Pair<Integer, Integer> highestInputValue = new Pair(40, 280);
    private Pair<Integer, Integer> lowestInputValue = new Pair(0, 50);
    private Pair<Integer, Integer> optimalValueA = new Pair(23, 200);
    private Pair<Integer, Integer> optimalValueB = new Pair(23, 200);

    //Inputs
    private Float a1Input1 = 1.0f;
    private Float a2Input1 = 1.02f;
    private Float b1Input1 = 2.3f;
    private Float b2Input1 = 2.3f;
    private Float a1Input2 = 1.0f;
    private Float a2Input2 = 1.05f;
    private Float b1Input2 = 0.6f;
    private Float b2Input2 = 0.6f;
    private Pair<Integer, Integer> uInput = new Pair(4, 18);
    //Outputs
    private Float a1Output = 1.5f;
    private Float a2Output = 0.1f;
    private Integer uOutput = 20;
    private Integer highestOutputValue = 75;
    private Integer lowestOutputValue = -75;
    private Integer outOptimal = 0;

    //Cut
    private Pair<Float, Float> cutValue = new Pair(0.3f, 0.3f);

    public Settings() {
    }

    public Pair<Integer, Integer> getHighestInputValue() {
        return highestInputValue;
    }

    public void setHighestInputValue(Pair<Integer, Integer> highestInputValue) {
        this.highestInputValue = highestInputValue;
    }

    public Pair<Integer, Integer> getLowestInputValue() {
        return lowestInputValue;
    }

    public void setLowestInputValue(Pair<Integer, Integer> lowestInputValue) {
        this.lowestInputValue = lowestInputValue;
    }

    public Pair<Integer, Integer> getOptimalValueA() {
        return optimalValueA;
    }

    public void setOptimalValueA(Pair<Integer, Integer> optimalValueA) {
        this.optimalValueA = optimalValueA;
    }

    public Pair<Integer, Integer> getOptimalValueB() {
        return optimalValueB;
    }

    public void setOptimalValueB(Pair<Integer, Integer> optimalValueB) {
        this.optimalValueB = optimalValueB;
    }

    public Float getA1Input1() {
        return a1Input1;
    }

    public void setA1Input1(Float a1Input1) {
        this.a1Input1 = a1Input1;
    }

    public Float getA2Input1() {
        return a2Input1;
    }

    public void setA2Input1(Float a2Input1) {
        this.a2Input1 = a2Input1;
    }

    public Float getB1Input1() {
        return b1Input1;
    }

    public void setB1Input1(Float b1Input1) {
        this.b1Input1 = b1Input1;
    }

    public Float getB2Input1() {
        return b2Input1;
    }

    public void setB2Input1(Float b2Input1) {
        this.b2Input1 = b2Input1;
    }

    public Float getA1Input2() {
        return a1Input2;
    }

    public void setA1Input2(Float a1Input2) {
        this.a1Input2 = a1Input2;
    }

    public Float getA2Input2() {
        return a2Input2;
    }

    public void setA2Input2(Float a2Input2) {
        this.a2Input2 = a2Input2;
    }

    public Float getB1Input2() {
        return b1Input2;
    }

    public void setB1Input2(Float b1Input2) {
        this.b1Input2 = b1Input2;
    }

    public Float getB2Input2() {
        return b2Input2;
    }

    public void setB2Input2(Float b2Input2) {
        this.b2Input2 = b2Input2;
    }

    public Pair<Integer, Integer> getuInput() {
        return uInput;
    }

    public void setuInput(Pair<Integer, Integer> uInput) {
        this.uInput = uInput;
    }

    public Float getA1Output() {
        return a1Output;
    }

    public void setA1Output(Float a1Output) {
        this.a1Output = a1Output;
    }

    public Float getA2Output() {
        return a2Output;
    }

    public void setA2Output(Float a2Output) {
        this.a2Output = a2Output;
    }

    public Integer getuOutput() {
        return uOutput;
    }

    public void setuOutput(Integer uOutput) {
        this.uOutput = uOutput;
    }

    public Integer getHighestOutputValue() {
        return highestOutputValue;
    }

    public void setHighestOutputValue(Integer highestOutputValue) {
        this.highestOutputValue = highestOutputValue;
    }

    public Integer getLowestOutputValue() {
        return lowestOutputValue;
    }

    public void setLowestOutputValue(Integer lowestOutputValue) {
        this.lowestOutputValue = lowestOutputValue;
    }

    public Integer getOutOptimal() {
        return outOptimal;
    }

    public void setOutOptimal(Integer outOptimal) {
        this.outOptimal = outOptimal;
    }

    public Pair<Float, Float> getCutValue() {
        return cutValue;
    }

    public void setCutValue(Pair<Float, Float> cutValue) {
        this.cutValue = cutValue;
    }

}
