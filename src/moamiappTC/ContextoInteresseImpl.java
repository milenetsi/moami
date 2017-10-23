/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */
package moamiappTC;

import java.util.ArrayList;
import java.util.List;
import moamiapp.ContextoInteresse;
import moamiapp.Objective;
import moamiapp.SemanticRule;
import moamiapp.Item;

/**
 *
 * @author Milene
 */
public class ContextoInteresseImpl extends ContextoInteresse {

    public ContextoInteresseImpl() {
        this.setSemanticRules(generateSemanticRulesList());
        this.setObjectives(generateObjectivesList());
    }

    public List<SemanticRule> generateSemanticRulesList() {
        List srList = new ArrayList<SemanticRule>();

        SensorTemperature sTemp = new SensorTemperature("TemperaturaX");
        SensorConsumption sCons = new SensorConsumption("ConsumoDeEnergiaX");
        Item ArcondicionadoX = new Item("ArcondicionadoX");
        TomadaInteligente TomadaInteligenteX = new TomadaInteligente("TomadaInteligenteX", sTemp, sCons);
        Item Ambiente = new Item("Ambiente");

        SemanticRule sr1 = new SemanticRule<Item, Item>(Ambiente, ArcondicionadoX, "tem") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr1);

        SemanticRule sr2 = new SemanticRule<Item, Item>(Ambiente, TomadaInteligenteX, "tem") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr2);

        SemanticRule sr3 = new SemanticRule<Item, Item>(TomadaInteligenteX, sTemp, "temSensor") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr3);

        SemanticRule sr4 = new SemanticRule<Item, Item>(TomadaInteligenteX, sCons, "temSensor") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr4);

        SemanticRule sr5 = new SemanticRule<Item, Item>(TomadaInteligenteX, ArcondicionadoX, "tem") {
            @Override
            public Boolean predicate(Item entitySubject, Item entityObject) {
                return true;//<<meio sem proposito
            }
        };
        srList.add(sr5);

        return srList;
    }

    public List<Objective> generateObjectivesList() {
        List objectivesList = new ArrayList<Objective>();

        objectivesList.add(new Objective<Integer>("temperature"));
        objectivesList.add(new Objective<Integer>("consumption"));

        return objectivesList;
    }

}
