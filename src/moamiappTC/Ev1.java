/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiappTC;

import LFuzzyLibrary.Pair;
import java.util.ArrayList;
import java.util.List;
import moamiapp.Event;
import moamiapp.Item;
import moamiapp.SemanticRule;

/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
public class Ev1 extends Event<List<List<Pair<Integer, Float>>>> {

    private List<Pair<Integer, Float>> optimalConsInterval;
    private List<Pair<Integer, Float>> optimalTempInterval;

    public Ev1(String name, List<Pair<Integer, Float>> optimalConsInterval, List<Pair<Integer, Float>> optimalTempInterval) {
        super(name);
        this.optimalConsInterval = optimalConsInterval;
        this.optimalTempInterval = optimalTempInterval;

        List srList = new ArrayList<SemanticRule>();
        //<<regras nao deveriam se conectar as regras do contexto?
        SemanticRule sr1 = new SemanticRule<Item, Item>(new Item("ValorConsumoDeEnergiaX"), new Item("ConsumoDeEnergiaX"), "temValorColetado") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr1);

        SemanticRule sr2 = new SemanticRule<Item, Item>(new Item("ValorTemperaturaX"), new Item("TemperaturaX"), "temValorColetado") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr2);

        this.setSemanticRules(srList);
    }

    @Override
    public List<List<Pair<Integer, Float>>> patternOfRecognition() {
        List l = new ArrayList<List<Integer>>();
        l.add(optimalTempInterval);
        l.add(optimalConsInterval);
        return l;
    }

}
