/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package moamiappTC;

import java.util.ArrayList;
import java.util.List;
import moamiapp.Event;
import moamiapp.Item;
import moamiapp.SemanticRule;

/**
 *
 * @author Milene
 */
public class Ev2 extends Event<Boolean> {

    public Ev2(String name) {
        super(name);

        List srList = new ArrayList<SemanticRule>();
        SemanticRule sr1 = new SemanticRule<Item, Item>(new Item("Rever1"), new Item("Rever2"), "executaAcao") {//<<?
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr1);
        this.setSemanticRules(srList);
    }

    @Override
    public Boolean patternOfRecognition() {
        return true;//<<?
    }

}
