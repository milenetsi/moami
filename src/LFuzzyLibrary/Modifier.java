package LFuzzyLibrary;

import java.util.Hashtable;


/**
 * Temperature controller Page 175 - Goguen
 *
 * @author msantosteixeira
 */
public abstract class Modifier<A> {

    private Finite<A> finiteValues;
    private HeytA<Float> algebra;
    private HeytAPair<Float,Float> hPair;
    private GenericComposition<Pair<Float, Float>> genComp;

    
    public Modifier(Finite<A> finiteValues) {
        this.finiteValues = finiteValues;
        this.algebra = new HeytAFloat();
        this.hPair = new HeytAPair(algebra, algebra);
        this.genComp = new GenericComposition<Pair<Float, Float>>() {
            @Override
            public Pair<Float, Float> compositionMethod(Pair<Float, Float> p1, Pair<Float, Float> p2) {
                HeytAPair<Float, Float> hPair = new HeytAPair(new HeytAFloat(), new HeytAFloat());
                return hPair.meet(p1, p2);
            }
        };
    }
    
    public abstract LRel<Finite, Object, Pair<Float, Float>> getL();

    public Finite<A> getFiniteValues() {
        return finiteValues;
    }

    public void setFiniteValues(Finite<A> finiteValues) {
        this.finiteValues = finiteValues;
    }

    public HeytA<Float> getAlgebra() {
        return algebra;
    }

    public void setAlgebra(HeytA<Float> algebra) {
        this.algebra = algebra;
    }

    public HeytAPair<Float, Float> gethPair() {
        return hPair;
    }

    public void sethPair(HeytAPair<Float, Float> hPair) {
        this.hPair = hPair;
    }

    public GenericComposition<Pair<Float, Float>> getGenComp() {
        return genComp;
    }

    public void setGenComp(GenericComposition<Pair<Float, Float>> genComp) {
        this.genComp = genComp;
    }
    
    
  
}
