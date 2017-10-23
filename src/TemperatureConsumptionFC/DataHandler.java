/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */

package TemperatureConsumptionFC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataHandler {

    private static final String FILENAME = "dataTemperature";
    private Float consumptionKw;
    private Float temperature;
    private Settings s;
    private Database d;

    public DataHandler(Settings s) {
        this.d = new Database();
        this.s = s;
        this.temperature = getTemperatureFromSensor();
        this.consumptionKw = getConsumption();
    }

    public Float getTemperatureFromSensor() {

        this.temperature = d.retrieveTemperatureData(10);
        //Adjustment of values
        if (this.temperature > s.getHighestInputValue().getFirst()) {
            this.temperature = s.getHighestInputValue().getFirst() * 1.0f;
        }
        if (this.temperature < s.getLowestInputValue().getFirst()) {
            this.temperature = s.getLowestInputValue().getFirst() * 1.0f;
        }
        return this.temperature;
    }

    /**
     * FOR SIMULATION PURPOSES Current file has temperatures from somewhere in
     * USA (5min interval) Values are low. That's why I added 13 to the actual
     * value
     *
     * @return
     */
    public Float getTemperatureFromFile() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File fr = new File(FILENAME + ".txt");
        File fw = new File(FILENAME + "new-" + Math.random() + ".txt");

        Float temp = 23.0f;//<<

        try {
            br = new BufferedReader(new FileReader(fr));
            bw = new BufferedWriter(new FileWriter(fw));

            String sCurrentLine;

            boolean isFirst = true;
            while ((sCurrentLine = br.readLine()) != null) {
                if (isFirst) {
                    String val = sCurrentLine;
                    temp = Float.parseFloat(val) + 13.0f;//<<  
                    isFirst = false;
                } else {
                    bw.write(sCurrentLine);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();
            //boolean s = fr.renameTo(new File(FILENAME +"old-"+ Math.random()+ ".txt"));
            fr.delete();
            fw.renameTo(new File(FILENAME + ".txt"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Adjustment of values
        if (temp > s.getHighestInputValue().getFirst()) {
            temp = s.getHighestInputValue().getFirst() * 1.0f;
        }
        if (temp < s.getLowestInputValue().getFirst()) {
            temp = s.getLowestInputValue().getFirst() * 1.0f;
        }
        this.temperature = temp;
        return temp;
    }

    /**
     * Busca no banco
     */
    public Float getConsumption() {
        Float sumKw = 0.0f;
        List<Float> dataCons = d.retrieveConsumptionData(10);
        if (dataCons.size() > 0) {
            for (Float val : dataCons) {
                if (val != -1.0f) {
                    sumKw = sumKw + val;
                }
            }
        } else {
            return 80.0f;
        }

        //Divide por 10 e round pra caber no intervalo em Settings
        Float averageKW = (sumKw / dataCons.size()) / 10.0f;
        System.out.println("    -Cons average: " + averageKW);
        BigDecimal sumVsNumber = new BigDecimal(averageKW).setScale(0, BigDecimal.ROUND_HALF_UP);

        Float cons = sumVsNumber.floatValue();
        //System.out.println("average r: " + cons);

        //Adjustment of values
        if (cons > (s.getHighestInputValue().getSecond() * 1.0f)) {
            cons = s.getHighestInputValue().getSecond() * 1.0f;
        }
        if (cons < (s.getLowestInputValue().getSecond() * 1.0f)) {
            cons = s.getLowestInputValue().getSecond() * 1.0f;
        }
        this.consumptionKw = cons;
        return cons;
    }

    public Float getConsumptionKw() {
        return consumptionKw;
    }

    public void setConsumptionKw(Float consumptionKw) {
        this.consumptionKw = consumptionKw;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

}
