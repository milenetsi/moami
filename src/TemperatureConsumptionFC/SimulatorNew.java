/**
 * MultiObjectivity in AmI
 * Masters Degree in Computer Science - UFSM
 * @author Milene S. Teixeira (Teixeira, M.S.)
 */

/*
-botar no git 


 * Artigo
escrever no artigo sobre service;pag 80
minha Aa tem como entrada o valor do controller? nao botei isso no modelo;
na tabela (artigo) do ev1, tenho q separar a parte q na verdade eh sitOn ( vt != inter...);
formula do intervalo mudou. Mudar no artigo (y eh o optimal);
artigo:consumo menor q in tervalo eh aceito;
ALGORITMO: (escrever no artigo)
    -contexto gera lista objetivos
    -sI recebe essa lista...

---
-conectar melhor os modulos. Tipo, classe TomadaInteligente deveria ler dados
1-Aa (esperar bolsistas)
-Organizar biblio (assin)
 

------------------------------
-TESTES: comparar 1h sem o controlador e 1h com o controlador
    -intervalo 10 min
    -teste manual (eu mudo ar)
    -teste automatico (ar muda sozinho)
    -fazer isso uns 10 dias. Incluindo dias bem quentes e bem frios
        -nisso: uso dados historicos destas horas (registrar exato inicio/fim) e vejo qnt$ deu de consumo
------------------------

FUTURE:
-tentar reduzir uso de memoria
-podia ter 1 grafico em tempo real q coloca o de temp em cima do de consumo conforme os valores de entrada?
-usar LinkedHashMap em aa

 */
package TemperatureConsumptionFC;

import LFuzzyLibrary.Sum;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import moamiappTC.AutomatizedActionImpl;
import moamiappTC.ContextoInteresseImpl;
import moamiappTC.InterestSituationImpl;
import moamiappTC.MuiltiobjectiveReasoningImpl;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Milene
 */
public class SimulatorNew extends javax.swing.JFrame {

    private ChartInputTemperature inputChart1;
    private ChartInputConsumption inputChart2;
    private TCFuzzyController controller;
    private Settings s;
    private Float settingValue;
    private Float oldSettingValue;
    private DataHandler dh;
    private Float temperature;
    private Float consumption;
    private ChartOutput outputChart;

    /**
     * Creates new form Simulator
     */
    public SimulatorNew() {
        initComponents();
        this.s = new Settings();
        this.dh = new DataHandler(s);
        this.temperature = this.dh.getTemperatureFromSensor();
        this.consumption = this.dh.getConsumption();
        //this.consumption = this.dh.getConsumption();//database
        this.txtTemp.setText(String.format("%.02f", this.temperature));
        this.txtCons.setText(String.format("%.02f", this.consumption));
        this.txtSett.setText("0.0");
        this.settingValue = 0.0f;
        this.oldSettingValue = 0.0f;

        Float tempF = (Float.valueOf(this.txtTemp.getText().replace(',', '.'))) * 1.0f;
        this.tempSlider.setValue(tempF.intValue());

        int consF = Math.round((Float.valueOf(this.txtCons.getText().replace(',', '.'))) * 1.0f);//* qnt?
        this.consSlider.setValue(consF);

        this.txtTemp.setEditable(false);
        this.txtCons.setEditable(false);
        this.txtSett.setEditable(false);

        //this.txtExtTemp.setText("33.5"); external
        //Float tempEF = (Float.valueOf(this.txtExtTemp.getText().replace(',', '.')) * 10.0f);
        //this.tempExtSlider.setValue(tempEF.intValue());
        this.initFC();
    }

    private void runInputChart() {
        //Input chart
        this.inputChart1 = new ChartInputTemperature("FC temperature", s);
        this.inputChart1.pack();
        RefineryUtilities.positionFrameRandomly(this.inputChart1);
        this.inputChart1.setVisible(true);
        //
        this.inputChart2 = new ChartInputConsumption("FC consumption", s);
        this.inputChart2.pack();
        RefineryUtilities.positionFrameRandomly(this.inputChart2);
        this.inputChart2.setVisible(true);
    }

    private void runOutputChart() {
        //Output chart
        this.outputChart = new ChartOutput("Setting - output", s);
        outputChart.pack();
        RefineryUtilities.centerFrameOnScreen(outputChart);
        outputChart.setVisible(true);
    }

    //Main thread
    private void threadFC(InterestSituationImpl sInterest, MuiltiobjectiveReasoningImpl moR, AutomatizedActionImpl Aa) {

        //Thread that runs the controller
        ScheduledExecutorService executorFC = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTaskFC = new Runnable() {
            public void run() {
                try {
                    //
                    System.out.println("*************************************\n");

                    temperature = dh.getTemperatureFromSensor();
                    txtTemp.setText(String.format("%.02f", temperature));
                    tempSlider.setValue((temperature).intValue());

                    consumption = dh.getConsumption();
                    txtCons.setText(String.format("%.02f", consumption));
                    consSlider.setValue(consumption.intValue());

                    Integer temp = (Integer) Math.round(temperature);
                    Integer cons = (Integer) Math.round(consumption);  //<<classe TomadaInteligente deveria ler isso

                    sInterest.getObjectives().get(0).setValue(temp);
                    sInterest.getObjectives().get(1).setValue(cons);

                    //If situation was detected
                    if (sInterest.situationOn()) {
                        System.out.println("Situation On!");
                        try {
                            //start reasoning
                            moR.getObjectives().get(0).setValue(temp);
                            moR.getObjectives().get(1).setValue(cons);
                            Sum output = moR.executeReasoning();

                            if (output != null) {//Interface
                                if (output.getValue().toString().equals("AL")) {
                                    lblUpdates.setForeground(Color.red);
                                    lblUpdates.setText("ALERT!!!");
                                    txtSett.setText("0.0");
                                    System.out.println("ALERT!!!");
                                } else {
                                    if ((Integer) output.getValue() > 0) {
                                        lblUpdates.setForeground(Color.blue);
                                        lblUpdates.setText("Increase temperature.");
                                    } else if ((Integer) output.getValue() < 0) {
                                        lblUpdates.setForeground(Color.red);
                                        lblUpdates.setText("Reduce temperature.");
                                    }

                                    Integer o = (Integer) output.getValue();
                                    oldSettingValue = settingValue;
                                    settingValue = o * 0.1f;
                                    txtSett.setText(String.format("%.02f", settingValue));
                                    System.out.println("RESULT: " + o);
                                }

                                try {
                                    BufferedWriter bw = new BufferedWriter(new FileWriter(new File("finalResults.txt"), true));
                                    bw.write(temp + "-" + cons + ":" + output.getValue() + ",   " + LocalDateTime.now());//File with every result sent to the next module (Aa)
                                    bw.newLine();
                                    bw.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(SimulatorNew.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                lblUpdates.setText("Empty set.");
                                System.out.println("Empty set.");
                            }

                            //Execute automatized action
                            Aa.setInput(output);
                            Aa.generateValues();
                            Aa.executeAa(output);
                        } catch (Exception ex) {
                            System.out.println("Exception: " + ex.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("exception " + e.getMessage());
                }
            }
        };
        executorFC.scheduleAtFixedRate(periodicTaskFC, 0, 10, TimeUnit.MINUTES);
    }

    public void modifyConsumption() {
        ScheduledExecutorService executorUT = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTaskUT = new Runnable() {
            public void run() {
                consumption = dh.getConsumption();
                txtCons.setText(String.format("%.02f", consumption));
                consSlider.setValue(consumption.intValue());
            }
        };
        executorUT.scheduleAtFixedRate(periodicTaskUT, 0, 12, TimeUnit.SECONDS);
    }

    /**
     * Retrieves previous setting and compares to the new one. If new one is
     * smaller: reduce according to the difference (old 6, new 5: diff -1) If
     * new one is greater: add according to the difference (old:-3, new 5: diff
     * +8) *
     */
    private void updateTempSett() {
        ScheduledExecutorService executorUT = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTaskUT = new Runnable() {
            public void run() {
                temperature = dh.getTemperatureFromSensor();

                txtTemp.setText(String.format("%.02f", temperature));
                tempSlider.setValue((temperature).intValue());
            }
        };
        executorUT.scheduleAtFixedRate(periodicTaskUT, 0, 5, TimeUnit.SECONDS);
    }

    //Kinda main method, calls all threads
    private void initFC() {
        //FC
        this.controller = new TCFuzzyController(this.s);
        runInputChart();
        runOutputChart();

        ContextoInteresseImpl cI = new ContextoInteresseImpl();//<<ta muito feio!
        InterestSituationImpl sInterest = new InterestSituationImpl("Valores inadequados detectados", cI.getObjectives(), s);
        MuiltiobjectiveReasoningImpl moR = new MuiltiobjectiveReasoningImpl(cI.getObjectives(), s);
        AutomatizedActionImpl Aa = new AutomatizedActionImpl("AlteraConfigAr");

        threadFC(sInterest, moR, Aa);

        //updateTempSett();
        //modifyConsumption();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnDoor = new javax.swing.JToggleButton();
        tempSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        txtTemp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSett = new javax.swing.JTextField();
        lblUpdates = new javax.swing.JLabel();
        txtCons = new javax.swing.JTextField();
        consSlider = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnDoor.setText("Door closed");
        btnDoor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnDoorStateChanged(evt);
            }
        });
        btnDoor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoorActionPerformed(evt);
            }
        });

        tempSlider.setMaximum(45);
        tempSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        tempSlider.setValue(23);
        tempSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tempSliderStateChanged(evt);
            }
        });

        jLabel1.setText("Temperature");

        txtTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTempActionPerformed(evt);
            }
        });

        jLabel3.setText("Setting");

        txtSett.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSettActionPerformed(evt);
            }
        });

        txtCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsActionPerformed(evt);
            }
        });

        consSlider.setMaximum(280);
        consSlider.setMinimum(50);
        consSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        consSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                consSliderStateChanged(evt);
            }
        });

        jLabel2.setText("Consumption");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtSett, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCons, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(consSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(tempSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUpdates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnDoor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tempSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(lblUpdates, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(btnDoor))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnDoorStateChanged

    }//GEN-LAST:event_btnDoorStateChanged

    private void btnDoorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoorActionPerformed
        if (this.btnDoor.isSelected()) {
            //set time was opened
            this.btnDoor.setForeground(Color.red);
            this.btnDoor.setText("Door open");
            this.lblUpdates.setForeground(Color.red);
            this.lblUpdates.setText("Door opened. Temperature may change.");
        } else {
            //set time opened as null
            this.btnDoor.setForeground(Color.darkGray);
            this.btnDoor.setText("Door closed");
            this.lblUpdates.setText("");
        }
    }//GEN-LAST:event_btnDoorActionPerformed

    private void tempSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tempSliderStateChanged
        Float val = tempSlider.getValue() * 1.0f;
        dh.setTemperature(val);
        txtTemp.setText(String.format("%.02f", val));
        temperature = val;
    }//GEN-LAST:event_tempSliderStateChanged

    private void txtTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTempActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTempActionPerformed

    private void txtSettActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSettActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSettActionPerformed

    private void txtConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsActionPerformed

    private void consSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_consSliderStateChanged
        Float val = consSlider.getValue() * 1.0f;
        dh.setConsumptionKw(val);//* 0.1f
        txtCons.setText(String.format("%.02f", val));//* 0.1f
        consumption = val;
    }//GEN-LAST:event_consSliderStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimulatorNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimulatorNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimulatorNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimulatorNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimulatorNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnDoor;
    private javax.swing.JSlider consSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblUpdates;
    private javax.swing.JSlider tempSlider;
    private javax.swing.JTextField txtCons;
    private javax.swing.JTextField txtSett;
    private javax.swing.JTextField txtTemp;
    // End of variables declaration//GEN-END:variables
}
