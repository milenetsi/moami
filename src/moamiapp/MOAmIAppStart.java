
package moamiapp;

import java.util.List;

/**
 *
 * @author Milene
 */
public class MOAmIAppStart<A> {
    
    /**
     * Given a context: 
     * a) define the multiple objectives;
     * b) detect the situation of interest;
     * c) get the output from reasoning on this multiobjetive situation;
     * d) execute an automatized action.
     * e) Hopefully you 'solved'the situation!
     * @param contextoInteresse
     * @param initialEvents
     * @param situationName
     * @param sIfinalEvent
     * @param moR
     * @param Aa 
     */
    public void start(ContextoInteresse contextoInteresse, InterestSituation sInterest, MuiltiobjectiveReasoning<A> moR, AutomatizedAction<A> Aa){
        
        List<Objective> objectives = contextoInteresse.getObjectives();
        sInterest.setObjectives(objectives);
        
        //If situation was detected
        if(sInterest.situationOn()){
            try {
                //start reasoning
                moR.setObjectives(objectives);
                A output = moR.executeReasoning();
                
                //Execute automatized action
                Aa.setInput(output);
                Aa.generateValues();
                Aa.executeAa(output);
            } catch (Exception ex) {
                System.out.println("Exception: "+ex.getMessage());
            }
        }
    
    }
}

