/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiappTC;

import LFuzzyLibrary.Sum;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import moamiapp.AutomatizedAction;
import moamiapp.EventInternal;
import moamiapp.SemanticRule;
import moamiapp.Service;

/**
 *
 * @author Milene
 */
public class AutomatizedActionImpl extends AutomatizedAction<Sum> {

    public AutomatizedActionImpl(String name) {
        super(name);
    }

    @Override
    public void generateValues() {

        //Preconditions
        List srListPreconditions = new ArrayList<SemanticRule>();

        SemanticRule sr1 = new SemanticRule<Sum, Object>(this.getInput(), null, "temValor") {
            @Override
            public Boolean predicate(Sum entitySubject, Object entityObject) {
                return entitySubject.getValue() != entityObject;
            }
        };
        srListPreconditions.add(sr1);

        //Services
        Hashtable<Integer, Service<Sum>> servicesList = new Hashtable();
        Service<Sum> service = new Service<Sum>() {
            @Override
            public EventInternal executeService(Sum input) {

                //classe TomadaInteligente deveria mudar isso
                //<<recalc temp
                //<<AUMENTAR/REDUZIR TEMP DO AR. VER COMO VOU IMPLEMENTAR ISSO
                System.out.println("Nova temperatura: X");

                if (true) {//<<mudou temp
                    return new EventInternal<Boolean>("Configuracao alterada") {
                        @Override
                        public Boolean patternOfRecognition() {
                            System.out.println("Temperatura alterada!");
                            return true;
                        }
                    };
                }
                System.out.println("Temperatura nao pode ser alterada!");
                return null;
            }
        };
        servicesList.put(0, service);

        this.setValues(srListPreconditions, servicesList);
    }

    @Override
    public void setValues(List<SemanticRule> preconditions, Hashtable<Integer, Service<Sum>> services) {
        this.setPreconditions(preconditions);
        this.setServices(services);
    }

}
