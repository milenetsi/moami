/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TemperatureFC;

import LFuzzyLibrary.Pair;
import LFuzzyLibrary.Sum;
import java.awt.Color;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author msantosteixeira
 */
public class Environment extends javax.swing.JFrame {

    private Date doorOpenedAt;
    private ChartInput inputChart;
    private ChartOutput outputChart;
    private TemperatureController tc;
    private Pair<Float, Float> cutValue;

    /**
     * Creates new form Environment
     */
    public Environment() {
        initComponents();
        this.doorOpenedAt = null;
        this.txtTemp.setText("25.5");
        this.txtSett.setText("3.0");

        Float tempF = (Float.valueOf(this.txtTemp.getText().replace(',', '.')) * 10.0f);
        this.tempSlider.setValue(tempF.intValue());

        this.txtTemp.setEditable(false);
        this.txtSett.setEditable(false);

        this.txtExtTemp.setText("33.5");
        Float tempEF = (Float.valueOf(this.txtExtTemp.getText().replace(',', '.')) * 10.0f);
        this.tempExtSlider.setValue(tempEF.intValue());

        //FC settings
        //Input
        this.spA1in.setValue(1.2f);
        this.spA2in.setValue(1.2f);
        this.spB1in.setValue(0.35f);
        this.spB2in.setValue(0.25f);
        this.spUin.setValue(6.0f);
        //Output
        this.spAOut.setValue(3.3f);
        this.spBOut.setValue(0.1f);
        this.spUOut.setValue(5.6f);
        //Cut
        this.spnCutA.setValue(0.1f);
        this.spnCutB.setValue(0.1f);
        this.cutValue = new Pair((Float) spnCutA.getValue(), (Float) spnCutB.getValue());

        this.initFC();
    }

    /**
     * Recalculate temp when door is open
     * @param externalTemperature
     * @param currentTemperature
     * @param timeDoorOpened
     * @return 
     */
    public Float modifyTemperature(Float externalTemperature, Float currentTemperature, Date timeDoorOpened) {
        long seconds = (new Date().getTime() - timeDoorOpened.getTime()) / 1000;
        //externalT - internalT * 0.seconds
        Float tempAdjustVal = (externalTemperature - currentTemperature) * ((seconds * 0.1f) * 0.1f);
        Float result = currentTemperature + tempAdjustVal;
        if ((currentTemperature < externalTemperature && result > externalTemperature) || (currentTemperature > externalTemperature && result < externalTemperature)) {
            result = externalTemperature;
        }
        result = (float) result.intValue();
        return result * 10.0f;
        //return Math.round((result * 10.0f)*100.0f)/100.0f;
    }

    private void runInputChart() {
        //Input chart
        Float uIn = ((Float) spUin.getValue()) * 10.0f;
        this.inputChart = new ChartInput("FC", (Float) spA1in.getValue(), (Float) spA2in.getValue(), (Float) spB1in.getValue(), (Float) spB2in.getValue(), (uIn).intValue());
        this.inputChart.pack();
        RefineryUtilities.positionFrameRandomly(this.inputChart);
        this.inputChart.setVisible(true);
    }

    private void runOutputChart() {
        //Output chart
        Float uOut = ((Float) spUOut.getValue()) * 10.0f;
        this.outputChart = new ChartOutput("FC", (Float) spAOut.getValue(), (Float) spBOut.getValue(), (uOut).intValue());
        outputChart.pack();
        RefineryUtilities.centerFrameOnScreen(outputChart);
        outputChart.setVisible(true);
    }

    private void updateTempThread() {
        //Thread that updates temperature if door open
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTask = new Runnable() {
            public void run() {
                if (doorOpenedAt != null) {
                    Float newTemp = modifyTemperature(Float.valueOf(txtExtTemp.getText().replace(',', '.')), Float.valueOf(txtTemp.getText().replace(',', '.')), doorOpenedAt);
                    Float newTempInt = newTemp;// * 10.0f;
                    System.out.println(newTemp+" temp "+newTempInt+ "text: "+String.format("%.02f", newTempInt));
                    txtTemp.setText(String.format("%.02f", newTempInt));
                    tempSlider.setValue((newTempInt).intValue());
                }
            }
        };
        executor.scheduleAtFixedRate(periodicTask, 0, 3, TimeUnit.SECONDS);
    }

    //Main thread
    private void threadFC() {

        //Thread that runs the controller
        ScheduledExecutorService executorFC = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTaskFC = new Runnable() {
            public void run() {

                try {
                    //
                    String valT = (txtTemp.getText()).replace(',', '.');
                    Float tempVal = Float.valueOf(valT) * 10.0f;
                    Integer readingValue = tempVal.intValue();

                    System.out.println("running" + Math.random());

                    Sum output = tc.inference(cutValue, readingValue);
                    if (output != null) {

                        if (output.getValue().toString().equals("AL")) {
                            lblUpdates.setForeground(Color.red);
                            lblUpdates.setText("ALERT!!!");
                            txtSett.setText("0.0");
                        } else {
                            if ((Integer) output.getValue() > 0) {
                                lblUpdates.setForeground(Color.red);
                                lblUpdates.setText("Increase temperature.");
                            } else if ((Integer) output.getValue() < 0) {
                                lblUpdates.setForeground(Color.blue);
                                lblUpdates.setText("Reduce temperature.");
                            }

                            Integer o = (Integer) output.getValue();
                            txtSett.setText(String.format("%.02f", (o.floatValue() * 0.1)));
                        }
                    } else {
                        lblUpdates.setText("Empty set.");
                    }
                } catch (Exception e) {
                    System.out.println("exception " + e.getMessage());
                }
            }
        };
        executorFC.scheduleAtFixedRate(periodicTaskFC, 0, 5, TimeUnit.SECONDS);
    }

    //Thread that updates temperature according to setting
    private void updateTempSett() {
        ScheduledExecutorService executorUT = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicTaskUT = new Runnable() {
            public void run() {
                Float val = Float.valueOf(txtSett.getText().replace(',', '.'));
                if (val != 0.0f) {
                    Float oldTemp = Float.valueOf(txtTemp.getText().replace(',', '.'));
                    Float newTemp = (oldTemp + val) * 10.0f;
                    
                    txtTemp.setText(String.format("%.02f", newTemp));
                    tempSlider.setValue((newTemp).intValue());
                }
            }
        };
        executorUT.scheduleAtFixedRate(periodicTaskUT, 0, 10, TimeUnit.SECONDS);
    }

    //Kinda main method, calls all threads
    private void initFC() {
        //FC
        this.tc = new TemperatureController((Float) spA1in.getValue(), (Float) spA2in.getValue(), (Float) spB1in.getValue(), (Float) spB2in.getValue(), (Float) spUin.getValue(), (Float) spAOut.getValue(), (Float) spBOut.getValue(), (Float) spUOut.getValue());

        runInputChart();

        runOutputChart();

        updateTempThread();

        threadFC();

        updateTempSett();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tempExtSlider = new javax.swing.JSlider();
        txtExtTemp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        spAOut = new javax.swing.JSpinner();
        spBOut = new javax.swing.JSpinner();
        spUOut = new javax.swing.JSpinner();
        spA1in = new javax.swing.JSpinner();
        spA2in = new javax.swing.JSpinner();
        spB1in = new javax.swing.JSpinner();
        spB2in = new javax.swing.JSpinner();
        spUin = new javax.swing.JSpinner();
        btUpdateIn = new javax.swing.JButton();
        btUpdateOut = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        spnCutA = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        spnCutB = new javax.swing.JSpinner();
        btCutUpdate = new javax.swing.JButton();

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

        tempSlider.setMaximum(500);
        tempSlider.setOrientation(javax.swing.JSlider.VERTICAL);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUpdates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnDoor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(110, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSett, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(tempSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tempSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUpdates, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(btnDoor))
        );

        jLabel2.setText("Output values:");

        jLabel4.setText("Input values:");

        tempExtSlider.setMaximum(500);
        tempExtSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        tempExtSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tempExtSliderStateChanged(evt);
            }
        });

        txtExtTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExtTempActionPerformed(evt);
            }
        });

        jLabel5.setText("External Temperature");

        jLabel6.setText("a1:");

        jLabel7.setText("a2:");

        jLabel8.setText("b1:");

        jLabel9.setText("b2:");

        jLabel10.setText("a:");

        jLabel11.setText("b:");

        jLabel12.setText("u:");

        jLabel13.setText("u:");

        spAOut.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.1f));

        spBOut.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.1f));

        spUOut.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, 20.0f, 0.1f));

        spA1in.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 0.1f));
        spA1in.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spA1inStateChanged(evt);
            }
        });

        spA2in.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.1f));

        spB1in.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.1f));

        spB2in.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.1f));

        spUin.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));

        btUpdateIn.setText("Ok");
        btUpdateIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateInActionPerformed(evt);
            }
        });

        btUpdateOut.setText("Ok");
        btUpdateOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateOutActionPerformed(evt);
            }
        });

        jLabel14.setText("Cut");

        jLabel15.setText("a:");

        spnCutA.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));

        jLabel17.setText("b:");

        spnCutB.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)));

        btCutUpdate.setText("Ok");
        btCutUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCutUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtExtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spUin))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spB2in))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spB1in))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spA2in))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spA1in))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btUpdateIn)))
                        .addGap(121, 121, 121)
                        .addComponent(tempExtSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btUpdateOut)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(spUOut))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(spBOut))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(spAOut))
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btCutUpdate)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel17))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(spnCutA, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnCutB, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14))))
                                .addGap(43, 43, 43))))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jLabel4)
                    .addContainerGap(209, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtExtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(spA1in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(spA2in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(spB1in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(spB2in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(spUin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btUpdateIn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tempExtSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spAOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(spBOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(spUOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btUpdateOut)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnCutA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnCutB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCutUpdate))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jLabel4)
                    .addContainerGap(373, Short.MAX_VALUE)))
        );

        jLabel5.getAccessibleContext().setAccessibleName("External \nTemperature");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSettActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSettActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSettActionPerformed

    private void btnDoorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnDoorStateChanged

    }//GEN-LAST:event_btnDoorStateChanged

    private void btnDoorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoorActionPerformed
        if (this.btnDoor.isSelected()) {
            //set time was opened
            this.doorOpenedAt = new Date();
            this.btnDoor.setForeground(Color.red);
            this.btnDoor.setText("Door open");
            this.lblUpdates.setForeground(Color.red);
            this.lblUpdates.setText("Door opened. Temperature may change.");
        } else {
            this.doorOpenedAt = null;
            //set time opened as null
            this.btnDoor.setForeground(Color.darkGray);
            this.btnDoor.setText("Door closed");
            this.lblUpdates.setText("");
        }
    }//GEN-LAST:event_btnDoorActionPerformed

    private void tempSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tempSliderStateChanged
        Float val = tempSlider.getValue() * 0.1f;
        txtTemp.setText(String.format("%.02f", val));
    }//GEN-LAST:event_tempSliderStateChanged

    private void tempExtSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tempExtSliderStateChanged
        Float val = tempExtSlider.getValue() * 0.1f;
        txtExtTemp.setText(String.format("%.02f", val));
    }//GEN-LAST:event_tempExtSliderStateChanged

    private void txtExtTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExtTempActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExtTempActionPerformed

    private void spA1inStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spA1inStateChanged


    }//GEN-LAST:event_spA1inStateChanged

    private void btUpdateInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateInActionPerformed
        this.inputChart.setVisible(false);
        this.inputChart = null;
        Float uIn = ((Float) spUin.getValue()) * 10.0f;
        Float uOut = ((Float) spUOut.getValue()) * 10.0f;

        //Update core
        ModifierInputImpl mInput = new ModifierInputImpl(new FiniteInputValuesImpl(), ((Float) spA1in.getValue()), ((Float) spA2in.getValue()), ((Float)spB1in.getValue()), ((Float) spB2in.getValue()), uIn.intValue(), 500, 0, 240, 255);
        ModifierOutputImpl mOutput = new ModifierOutputImpl(new FiniteOutputValuesImpl(), 0, ((Float) spAOut.getValue()), ((Float) spBOut.getValue()), uOut.intValue(), -200, 200);
        tc.getFc().core(mInput, mOutput);
        
        this.inputChart = new ChartInput("FC", (Float) spA1in.getValue(), (Float) spA2in.getValue(), (Float) spB1in.getValue(), (Float) spB2in.getValue(), uIn.intValue());
        this.inputChart.pack();
        RefineryUtilities.positionFrameRandomly(this.inputChart);
        this.inputChart.setVisible(true);
    }//GEN-LAST:event_btUpdateInActionPerformed

    private void btUpdateOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateOutActionPerformed
        this.outputChart.setVisible(false);
        this.outputChart = null;
        Float uIn = ((Float) spUin.getValue()) * 10.0f;
        Float uOut = ((Float) spUOut.getValue()) * 10.0f;

        //Update core
        ModifierInputImpl mInput = new ModifierInputImpl(new FiniteInputValuesImpl(), ((Float) spA1in.getValue()), ((Float) spA2in.getValue()), ((Float)spB1in.getValue()), ((Float) spB2in.getValue()), uIn.intValue(), 500, 0, 240, 255);
        ModifierOutputImpl mOutput = new ModifierOutputImpl(new FiniteOutputValuesImpl(), 0, ((Float) spAOut.getValue()), ((Float) spBOut.getValue()), uOut.intValue(), -200, 200);
        tc.getFc().core(mInput, mOutput);
        
        this.outputChart = new ChartOutput("FC", (Float) spAOut.getValue(), (Float) spBOut.getValue(), uOut.intValue());
        this.outputChart.pack();
        RefineryUtilities.centerFrameOnScreen(this.outputChart);
        this.outputChart.setVisible(true);
    }//GEN-LAST:event_btUpdateOutActionPerformed

    private void btCutUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCutUpdateActionPerformed
        this.cutValue = new Pair((Float) spnCutA.getValue(), (Float) spnCutB.getValue());
    }//GEN-LAST:event_btCutUpdateActionPerformed

    private void txtTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTempActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTempActionPerformed

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
            java.util.logging.Logger.getLogger(Environment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Environment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Environment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Environment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Environment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCutUpdate;
    private javax.swing.JButton btUpdateIn;
    private javax.swing.JButton btUpdateOut;
    private javax.swing.JToggleButton btnDoor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUpdates;
    private javax.swing.JSpinner spA1in;
    private javax.swing.JSpinner spA2in;
    private javax.swing.JSpinner spAOut;
    private javax.swing.JSpinner spB1in;
    private javax.swing.JSpinner spB2in;
    private javax.swing.JSpinner spBOut;
    private javax.swing.JSpinner spUOut;
    private javax.swing.JSpinner spUin;
    private javax.swing.JSpinner spnCutA;
    private javax.swing.JSpinner spnCutB;
    private javax.swing.JSlider tempExtSlider;
    private javax.swing.JSlider tempSlider;
    private javax.swing.JTextField txtExtTemp;
    private javax.swing.JTextField txtSett;
    private javax.swing.JTextField txtTemp;
    // End of variables declaration//GEN-END:variables
}
