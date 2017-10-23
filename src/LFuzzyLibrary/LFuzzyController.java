/**
 * L-Fuzzy Controller
 * Based on the book: Winter, M., 2007. Goguen categories: a categorical approach
 *              to L-fuzzy relations (Vol. 25). Springer Science & Business Media.
 * Implementation by: Milene Santos Teixeira - UFSM (RS-Brazil) & Brock University (On-Canada)
 * Project supervised by: Michael Winter - Brock University (On-Canada)
 * Acknowledgment: ELAP program (Emerging Leaders in America)
 */
package LFuzzyLibrary;

/**
 * To implement a Fuzzy Controller: An object of this class should be
 * instantiated, being given all parameters required in the constructor. After
 * that, the two public method (inference) should be called.
 *
 * @author msantosteixeira
 */
public class LFuzzyController<A> {

    //Classes to be implemented
    private GenericComposition<Pair<Float, Float>> compositionMethod;
    private Modifier mInput;
    private ModifierOutput mOutput;
    private FiniteInputValues finiteInputValues;
    private FiniteInputLinguistic finiteInputLinguistic;
    private FiniteOutputLinguistic finiteOutputLinguistic;
    private FiniteOutputValues finiteSum;
    private Core coreObj;
    private Deffuzification deffuzification;
    //
    private LRel<A, Sum, Pair<Float, Float>> finalSet;

    public LFuzzyController(Core coreObj, GenericComposition<Pair<Float, Float>> compositionMethod,
            Modifier mInput, ModifierOutput mOutput, FiniteInputValues finiteInputValues,
            FiniteInputLinguistic finiteInputLinguistic, FiniteOutputLinguistic finiteOutputLinguistic, FiniteOutputValues finiteSum,
            Deffuzification deffuzification) {
        this.coreObj = coreObj;
        this.compositionMethod = compositionMethod;
        this.mInput = mInput;
        this.mOutput = mOutput;
        this.finiteInputValues = finiteInputValues;
        this.finiteInputLinguistic = finiteInputLinguistic;
        this.finiteOutputLinguistic = finiteOutputLinguistic;
        this.finiteSum = finiteSum;
        this.deffuzification = deffuzification;
        this.core(mInput, mOutput);
    }

    /**
     * Processes all data (input and output MFs and M.degrees) and sets result
     * as the final set
     */
    public final void core(Modifier mInput1, ModifierOutput mOutput1) {
        try {
            this.finalSet = coreObj.core(mInput1, mOutput1, compositionMethod, finiteInputValues, finiteInputLinguistic, finiteOutputLinguistic, finiteSum);

        } catch (Exception e) {
            System.out.println("exception in core: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Depends on: -core: must be calculated before; -cutValue: Pair of Floats.
     * Cut for each attribute -readingValue: Value to be processed (from
     * sensors, external systems, etc...)
     *
     * @return
     */
    public final Object inference(Pair<Float, Float> cutValue, A readingValue) {
        Object result = null;
        try {
            FinalOutputSet<Integer, Sum> finalSetCut = this.deffuzification.cut(this.finalSet, cutValue, readingValue);
            result = this.deffuzification.calcOutput(finalSetCut);
        } catch (EmptySetException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("exception in inference: " + e.getMessage());
        }
        return result;
    }

    //Gets and Sets
    public GenericComposition<Pair<Float, Float>> getCompositionMethod() {
        return compositionMethod;
    }

    public void setCompositionMethod(GenericComposition<Pair<Float, Float>> compositionMethod) {
        this.compositionMethod = compositionMethod;
    }

    public Modifier getmInput() {
        return mInput;
    }

    public void setmInput(ModifierInputInteger mInput) {
        this.mInput = mInput;
    }

    public ModifierOutput getmOutput() {
        return mOutput;
    }

    public void setmOutput(ModifierOutput mOutput) {
        this.mOutput = mOutput;
    }

    public Finite getFiniteInputValues() {
        return finiteInputValues;
    }

    public void setFiniteInputValues(FiniteInputValues finiteInputValues) {
        this.finiteInputValues = finiteInputValues;
    }

    public Finite getFiniteInputLinguistic() {
        return finiteInputLinguistic;
    }

    public void setFiniteInputLinguistic(FiniteInputLinguistic finiteInputLinguistic) {
        this.finiteInputLinguistic = finiteInputLinguistic;
    }

    public Finite getFiniteOutputLinguistic() {
        return finiteOutputLinguistic;
    }

    public void setFiniteOutputLinguistic(FiniteOutputLinguistic finiteOutputLinguistic) {
        this.finiteOutputLinguistic = finiteOutputLinguistic;
    }

    public Finite getFiniteSum() {
        return finiteSum;
    }

    public void setFiniteSum(FiniteOutputValues finiteSum) {
        this.finiteSum = finiteSum;
    }

    public Core getCoreObj() {
        return coreObj;
    }

    public void setCoreObj(Core coreObj) {
        this.coreObj = coreObj;
    }

    public LRel<A, Sum, Pair<Float, Float>> getFinalSet() {
        return finalSet;
    }

    public void setFinalSet(LRel<A, Sum, Pair<Float, Float>> finalSet) {
        this.finalSet = finalSet;
    }

    public Deffuzification getDeffuzification() {
        return deffuzification;
    }

    public void setDeffuzification(Deffuzification deffuzification) {
        this.deffuzification = deffuzification;
    }

}
