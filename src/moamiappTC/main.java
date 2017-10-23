/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiappTC;

import TemperatureConsumptionFC.Settings;
import LFuzzyLibrary.Sum;
import moamiapp.MOAmIAppStart;

/**
 *
 * @author Milene
 */
public class main {

    public static void main(String[] args) {
        Settings s = new Settings();

        MOAmIAppStart<Sum> moami = new MOAmIAppStart<Sum>();
        ContextoInteresseImpl cI = new ContextoInteresseImpl();
        InterestSituationImpl sInterest = new InterestSituationImpl("Valores inadequados detectados", cI.getObjectives(), s);
        MuiltiobjectiveReasoningImpl moR = new MuiltiobjectiveReasoningImpl(cI.getObjectives(), s);
        AutomatizedActionImpl Aa = new AutomatizedActionImpl("AlteraConfigAr");

        moami.start(cI, sInterest, moR, Aa);

    }

}
