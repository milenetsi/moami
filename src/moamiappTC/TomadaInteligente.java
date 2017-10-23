/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moamiappTC;

import moamiapp.Item;

/**
 *
 * @author Milene
 */
public class TomadaInteligente extends Item<TomadaInteligente> {

    private SensorTemperature sensorTemperature;
    private SensorConsumption sensorConsumption;

    public TomadaInteligente(String name, SensorTemperature sensorTemperature, SensorConsumption sensorConsumption) {
        super(name);
        this.sensorTemperature = sensorTemperature;
        this.sensorConsumption = sensorConsumption;
    }

    public Float readTemperature() {
        //<<todo
        return null;
    }

    public Float readConsumption() {
        //<<todo
        return null;
    }

    public Boolean modifyTemperature() {
        //<<todo
        return false;
    }
}
