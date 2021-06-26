/*
 * Copyright (C) 2021 Sorin Cătălin Păștiță
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.jgears.gui;
/**
 *
 * @author Sorin Cătălin Păștiță
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.gears.SpurDrive;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.jgears.gui.GuiFormatter.*;

public class SpurController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<Double> addendum1, addendum2, clearance1,
                             clearance2, rootFillet1, rootFillet2;
    @FXML
    private CheckBox helicalCheckBox, calculationCheckBox, gear2CheckBox, pinionCheckBox,
                     gearCheckBox;
    @FXML
    private TextField gearRatio, module, pressureAngle, centerDistance, nrTeeth1, nrTeeth2, facewidth1, facewidth2,
                      profileShift1, profileShift2, helixAngle, middleDistance1, middleDistance2, totalProfileShift,
                      power1, power2, speed1, speed2, torque1, torque2, efficiency, pinionParam1,
                      gearParam1, safety;
    @FXML
    private WebView resultsDesignTab, resultsCalculationTab;
    @FXML
    private TextArea outputTerminal;
    @FXML
    private RadioButton gearRatioRadioBtn, nTeethRadioBtn, centerDistanceRadioBtn, profileShiftRadioBtn,
                        pinionGearRadioBtn, pinionRackRadioBtn, dimension1RadioBtn, dimension2RadioBtn,
                        pstRadioBtn, tspRadioBtn, ptsRadioBtn;
    @FXML
    private ToggleButton helixDir;
    @FXML
    private Tab calculationTab;

    private final TextFormatter<Double> gearRatioFormatter = new TextFormatter<>(converterUl);
    private final TextFormatter<Double> moduleFormatter = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> pressureAngleFormatter = new TextFormatter<>(converterDeg);
    private final TextFormatter<Double> centerDistanceFormatter = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> faceWidthFormatter1 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> faceWidthFormatter2 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> middleDistanceFormatter1 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> middleDistanceFormatter2 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> profileShiftFormatter1 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> profileShiftFormatter2 = new TextFormatter<>(converterMm);
    private final TextFormatter<Integer> nrTeethFormatter1 = new TextFormatter<>(converterUlInt);
    private final TextFormatter<Integer> nrTeethFormatter2 = new TextFormatter<>(converterUlInt);
    private final TextFormatter<Double> helixAngleFormatter = new TextFormatter<>(converterDeg);
    private final TextFormatter<Double> totalProfileShiftFormatter = new TextFormatter<>(converterMm);

    private final TextFormatter<Double> powerFormatter1 = new TextFormatter<>(converterKW);
    private final TextFormatter<Double> powerFormatter2 = new TextFormatter<>(converterKW);
    private final TextFormatter<Double> speedFormatter1 = new TextFormatter<>(converterRpm);
    private final TextFormatter<Double> speedFormatter2 = new TextFormatter<>(converterRpm);
    private final TextFormatter<Double> torqueFormatter1 = new TextFormatter<>(converterNm);
    private final TextFormatter<Double> torqueFormatter2 = new TextFormatter<>(converterNm);
    private final TextFormatter<Double> efficiencyFormatter = new TextFormatter<>(converterUl);
    private final TextFormatter<Double> pinionParamFormatter1 = new TextFormatter<>(converterMPa);
    private final TextFormatter<Double> gearParamFormatter1 = new TextFormatter<>(converterMPa);
    private final TextFormatter<Double> safetyFormatter = new TextFormatter<>(converterUl);

    private final ToggleGroup inputType = new ToggleGroup();
    private final ToggleGroup designGuide = new ToggleGroup();
    private final ToggleGroup driveType = new ToggleGroup();
    private final ToggleGroup dimensions = new ToggleGroup();
    private final ToggleGroup typeLoadCalculation = new ToggleGroup();

    private String res1="", res2="";
    private Group partsGroup;
    private List<CSG> partsCSG;
    private WebEngine resultsDesignEngine, resultsCalculationEngine;
    private SpurDrive drive;

    @FXML
    private void handleInputType(ActionEvent event) {
        if (nTeethRadioBtn.isSelected()) {
            gearRatio.setDisable(true);
            nrTeeth2.setDisable(!gearCheckBox.isSelected());
        } else if (gearRatioRadioBtn.isSelected()) {
            gearRatio.setDisable(false);
            nrTeeth2.setDisable(true);
        }
    }
    @FXML
    private void handleDriveType(ActionEvent event) {
        if (pinionGearRadioBtn.isSelected()) {
            gearCheckBox.setText("Gear");
            dimension2RadioBtn.setText("Gear");
            gear2CheckBox.setText("Gear");
        } else if (pinionRackRadioBtn.isSelected()) {
            gearCheckBox.setText("Rack");
            dimension2RadioBtn.setText("Rack");
            gear2CheckBox.setText("Rack");
        }
    }
    @FXML
    private void handleDesignGuide(ActionEvent event) {
        if(centerDistanceRadioBtn.isSelected()) {
            centerDistance.setDisable(false);
            totalProfileShift.setDisable(true);
        }
        else {
            centerDistance.setDisable(true);
            totalProfileShift.setDisable(false);
        }
    }
    @FXML
    private void handleDimensions(ActionEvent event) {
        if(dimension1RadioBtn.isSelected())
            resultsDesignEngine.loadContent(res1);
        else if(dimension2RadioBtn.isSelected())
            resultsDesignEngine.loadContent(res2);

    }


    @FXML
    private void handlePinionCheck(ActionEvent event) {
        if (pinionCheckBox.isSelected()) {
            nrTeeth1.setDisable(false);
            profileShift1.setDisable(false);
            facewidth1.setDisable(false);
            middleDistance1.setDisable(!helicalCheckBox.isSelected());
        } else {
            nrTeeth1.setDisable(true);
            profileShift1.setDisable(true);
            facewidth1.setDisable(true);
            middleDistance1.setDisable(true);
        }
    }
    @FXML
    private void handleGearCheck(ActionEvent event) {
        if (gearCheckBox.isSelected()) {
            nrTeeth2.setDisable(!nTeethRadioBtn.isSelected());
            //profileShift2.setDisable(false);
            facewidth2.setDisable(false);
            middleDistance2.setDisable(!helicalCheckBox.isSelected());
        } else {
            nrTeeth2.setDisable(true);
            //profileShift2.setDisable(true);
            facewidth2.setDisable(true);
            middleDistance2.setDisable(true);
        }
    }
    @FXML
    private void handleGear2Check(ActionEvent event) {
        if(gear2CheckBox.isSelected()){
            addendum2.setDisable(false);
            clearance2.setDisable(false);
            rootFillet2.setDisable(false);

            addendum2.valueProperty().unbind();
            clearance2.valueProperty().unbind();
            rootFillet2.valueProperty().unbind();
        }
        else {
            addendum2.setDisable(true);
            clearance2.setDisable(true);
            rootFillet2.setDisable(true);

            addendum2.valueProperty().bind(addendum1.valueProperty());
            clearance2.valueProperty().bind(clearance1.valueProperty());
            rootFillet2.valueProperty().bind(rootFillet1.valueProperty());
        }
    }


    @FXML
    private void handleHelicalCheck(ActionEvent event) {
        if(helicalCheckBox.isSelected()) {
            middleDistance1.setDisable(!pinionCheckBox.isSelected());
            middleDistance2.setDisable(!gearCheckBox.isSelected());
        } else {
            middleDistance1.setDisable(true);
            middleDistance2.setDisable(true);
        }
    }
    @FXML
    private void handleCalculationCheck(ActionEvent event) {
        if(calculationCheckBox.isSelected())
            calculationTab.setDisable(false);
        else
            calculationTab.setDisable(true);

    }


    @FXML
    private void handleCalculate(ActionEvent event) {
        calculateParameters();
        //partsGroup.getChildren().clear();
        /*
        double inv = Math.tan(Math.toRadians(20))-20.0*Math.PI/180.0 +2.0*2.0*Math.tan(Math.toRadians(20))/39.0;
        double x0 = 0;
        double x1;
        do {
            x1 = x0;
            x0 = Math.atan(x0 + inv);
        } while (Math.abs(x0 - x1) > 0.0000000000001);
        System.out.println(Math.toDegrees(x1));*/
    }
    @FXML
    private void handleGenerate(ActionEvent event) {
        boolean pinionEnable = pinionCheckBox.isSelected();
        boolean gearEnable = gearCheckBox.isSelected();
        calculateParameters();
        partsGroup.getChildren().clear();
        partsCSG.clear();

        if (pinionEnable) {
            drive.generateGear1();
            partsGroup.getChildren().add(drive.getGear1().getGearMeshView());
            partsCSG.add(drive.getGear1().getCSG());
        }
        if (gearEnable) {
            drive.generateGear2();
            partsGroup.getChildren().add(drive.getGear2().getGearMeshView());
            partsCSG.add(drive.getGear2().getCSG());
        }
    }
    @FXML
    private void handleLoadReport(ActionEvent event) {
        if(drive != null)
            loadWindow("/main/java/jgears/gui/resources/TechnicalReport.fxml", "Technical Report");
    }
    @FXML
    private void handleSaveTemplate(ActionEvent event) {
        JSONArray jsonObj = new JSONArray();
        JSONObject pinionObj = new JSONObject();
        JSONObject gearObj = new JSONObject();
        JSONObject commonObj = new JSONObject();

        // common parameters
        commonObj.put("gearRatio", gearRatioFormatter.getValue());
        commonObj.put("module", moduleFormatter.getValue());
        commonObj.put("pressureAngle", pressureAngleFormatter.getValue());
        commonObj.put("centerDistance", centerDistanceFormatter.getValue());
        commonObj.put("helixAngle", helixAngleFormatter.getValue());
        commonObj.put("totalProfileShift", totalProfileShiftFormatter.getValue());
        commonObj.put("efficiency", efficiencyFormatter.getValue());
        commonObj.put("safety", safetyFormatter.getValue());

        // pinion parameters
        pinionObj.put("nrTeeth", nrTeethFormatter1.getValue());
        pinionObj.put("faceWidth", faceWidthFormatter1.getValue());
        pinionObj.put("profileShift", profileShiftFormatter1.getValue());
        pinionObj.put("middleDistance", middleDistanceFormatter1.getValue());
        pinionObj.put("power", powerFormatter1.getValue());
        pinionObj.put("speed", speedFormatter1.getValue());
        pinionObj.put("torque", torqueFormatter1.getValue());
        pinionObj.put("pinionParam", pinionParamFormatter1.getValue());
        pinionObj.put("addendum", addendum1.getValue());
        pinionObj.put("clearance", clearance1.getValue());
        pinionObj.put("rootFillet", rootFillet1.getValue());

        // gear parameters
        gearObj.put("nrTeeth", nrTeethFormatter2.getValue());
        gearObj.put("faceWidth", faceWidthFormatter2.getValue());
        gearObj.put("profileShift", profileShiftFormatter2.getValue());
        gearObj.put("middleDistance", middleDistanceFormatter2.getValue());
        gearObj.put("power", powerFormatter2.getValue());
        gearObj.put("speed", speedFormatter2.getValue());
        gearObj.put("torque", torqueFormatter2.getValue());
        gearObj.put("gearParam", gearParamFormatter1.getValue());
        gearObj.put("addendum", addendum2.getValue());
        gearObj.put("clearance", clearance2.getValue());
        gearObj.put("rootFillet", rootFillet2.getValue());

        // the json file
        jsonObj.add(commonObj);
        jsonObj.add(pinionObj);
        jsonObj.add(gearObj);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Template");
        fileChooser.setInitialFileName("spurDrive.json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "*.json"));

        File file = fileChooser.showSaveDialog(null);
	if (file == null) {
            return;
        }
        fileChooser.setInitialDirectory(file.getParentFile());
            try {
                FileWriter fileWriter;
                fileWriter = new FileWriter(file);
                fileWriter.write(jsonObj.toJSONString());
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(SpurController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        

        //System.out.println(jsonObj);
    }
    @FXML
    private void handleLoadTemplate(ActionEvent event) {
        JSONParser jsonObj = new JSONParser();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Template");
        File file = fileChooser.showSaveDialog(null);
	if (file == null) {
            return;
        }

            try {
                FileReader reader = new FileReader(file);
                Object obj = jsonObj.parse(reader);
                JSONArray paramList = (JSONArray) obj;
                JSONObject parameters = (JSONObject) paramList.get(0);
                gearRatioFormatter.setValue((double)parameters.get("gearRatio"));
                moduleFormatter.setValue((double)parameters.get("module"));
                pressureAngleFormatter.setValue((double)parameters.get("pressureAngle"));
                centerDistanceFormatter.setValue((double)parameters.get("centerDistance"));
                helixAngleFormatter.setValue((double)parameters.get("helixAngle"));
                totalProfileShiftFormatter.setValue((double)parameters.get("totalProfileShift"));
                efficiencyFormatter.setValue((double)parameters.get("efficiency"));
                safetyFormatter.setValue((double)parameters.get("safety"));

                parameters = (JSONObject) paramList.get(1);
                //System.out.println(parameters.get("nrTeeth"));
                //nrTeethFormatter1.setValue((Integer)parameters.get("nrTeeth"));
                faceWidthFormatter1.setValue((double)parameters.get("faceWidth"));
                profileShiftFormatter1.setValue((double)parameters.get("profileShift"));
                middleDistanceFormatter1.setValue((double)parameters.get("middleDistance"));
                powerFormatter1.setValue((double)parameters.get("power"));
                speedFormatter1.setValue((double)parameters.get("speed"));
                torqueFormatter1.setValue((double)parameters.get("torque"));
                pinionParamFormatter1.setValue((double)parameters.get("pinionParam"));
                addendum1.setValue((double)parameters.get("addendum"));
                clearance1.setValue((double)parameters.get("clearance"));
                rootFillet1.setValue((double)parameters.get("rootFillet"));

                parameters = (JSONObject) paramList.get(2);
                //nrTeethFormatter2.setValue((Integer)parameters.get("nrTeeth"));
                faceWidthFormatter2.setValue((double)parameters.get("faceWidth"));
                profileShiftFormatter2.setValue((double)parameters.get("profileShift"));
                middleDistanceFormatter2.setValue((double)parameters.get("middleDistance"));
                powerFormatter2.setValue((double)parameters.get("power"));
                speedFormatter2.setValue((double)parameters.get("speed"));
                torqueFormatter2.setValue((double)parameters.get("torque"));
                gearParamFormatter1.setValue((double)parameters.get("gearParam"));
                if (gear2CheckBox.isSelected()) {
                    addendum2.setValue((double)parameters.get("addendum"));
                    clearance2.setValue((double)parameters.get("clearance"));
                    rootFillet2.setValue((double)parameters.get("rootFillet"));
                }
                //System.out.println(paramList);
            } catch (ParseException | IOException ex) {
                Logger.getLogger(SpurController.class.getName()).
                    log(Level.SEVERE, null, ex);
            }
        
    }


    @FXML
    private void handlePinionMatCheck(ActionEvent event) {}
    @FXML
    private void handleGearMatCheck(ActionEvent event) {}
    @FXML
    private void handleTypeLoad(ActionEvent event) {
        if(pstRadioBtn.isSelected()) {
            power1.setDisable(false);
            speed1.setDisable(false);
            torque1.setDisable(true);
        } else if(tspRadioBtn.isSelected()) {
            power1.setDisable(true);
            speed1.setDisable(false);
            torque1.setDisable(false);
        } else if(ptsRadioBtn.isSelected()) {
            power1.setDisable(false);
            speed1.setDisable(true);
            torque1.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNodesDesignTab();
        initNodesCalculationTab();
    }

    private void initNodesDesignTab() {
        resultsDesignEngine = resultsDesignTab.getEngine();

        calculationCheckBox.setSelected(true);

        gearRatio.setTextFormatter(gearRatioFormatter);
        gearRatio.setText("1");
        gearRatio.setAlignment(Pos.CENTER_LEFT);
        gearRatio.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (gearRatio.isFocused() && !gearRatio.getText().isEmpty()) {
                gearRatio.selectAll();
            }
        }));
        gearRatio.setDisable(true);

        module.setTextFormatter(moduleFormatter);
        module.setText("1");
        module.setAlignment(Pos.CENTER_LEFT);
        module.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (module.isFocused() && !module.getText().isEmpty()) {
                module.selectAll();
            }
        }));

        centerDistance.setTextFormatter(centerDistanceFormatter);
        centerDistance.setText("15");
        centerDistance.setAlignment(Pos.CENTER_LEFT);
        centerDistance.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (centerDistance.isFocused() && !centerDistance.getText().isEmpty()) {
                centerDistance.selectAll();
            }
        }));
        centerDistance.setDisable(true);

        pressureAngle.setTextFormatter(pressureAngleFormatter);
        pressureAngle.setText("20");
        pressureAngle.setAlignment(Pos.CENTER_LEFT);
        pressureAngle.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (pressureAngle.isFocused() && !pressureAngle.getText().isEmpty()) {
                pressureAngle.selectAll();
            }
        }));

        addendum1.setConverter(converterUl);
        addendum2.setConverter(converterUl);
        clearance1.setConverter(converterUl);
        clearance2.setConverter(converterUl);
        rootFillet1.setConverter(converterUl);
        rootFillet2.setConverter(converterUl);

        addendum1.getItems().addAll(0.8, 1.0, 1.2);
        addendum2.getItems().addAll(0.8, 1.0, 1.2);
        clearance1.getItems().addAll(0.157, 0.2, 0.25, 0.3, 0.35, 0.4);
        clearance2.getItems().addAll(0.157, 0.2, 0.25, 0.3, 0.35, 0.4);
        rootFillet1.getItems().addAll(0.2, 0.25, 0.3, 0.35, 0.375, 0.4, 0.45, 0.5);
        rootFillet2.getItems().addAll(0.2, 0.25, 0.3, 0.35, 0.375, 0.4, 0.45, 0.5);

        addendum1.setValue(1.0);
        addendum2.setValue(1.0);
        clearance1.setValue(0.25);
        clearance2.setValue(0.25);
        rootFillet1.setValue(0.35);
        rootFillet2.setValue(0.35);

        addendum2.setDisable(true);
        clearance2.setDisable(true);
        rootFillet2.setDisable(true);

        addendum2.valueProperty().bind(addendum1.valueProperty());
        clearance2.valueProperty().bind(clearance1.valueProperty());
        rootFillet2.valueProperty().bind(rootFillet1.valueProperty());

        inputType.getToggles().addAll(gearRatioRadioBtn, nTeethRadioBtn);
        nTeethRadioBtn.setSelected(true);

        designGuide.getToggles().addAll(centerDistanceRadioBtn, profileShiftRadioBtn);
        profileShiftRadioBtn.setSelected(true);

        driveType.getToggles().addAll(pinionGearRadioBtn, pinionRackRadioBtn);
        pinionGearRadioBtn.setSelected(true);

        dimensions.getToggles().addAll(dimension1RadioBtn, dimension2RadioBtn);
        dimension1RadioBtn.setSelected(true);


        nrTeeth1.setTextFormatter(nrTeethFormatter1);
        nrTeeth1.setText("10");
        nrTeeth1.setAlignment(Pos.CENTER_LEFT);
        nrTeeth1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (nrTeeth1.isFocused() && !nrTeeth1.getText().isEmpty()) {
                nrTeeth1.selectAll();
            }
        }));
        nrTeeth2.setTextFormatter(nrTeethFormatter2);
        nrTeeth2.setText("20");
        nrTeeth2.setAlignment(Pos.CENTER_LEFT);
        nrTeeth2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (nrTeeth2.isFocused() && !nrTeeth2.getText().isEmpty()) {
                nrTeeth2.selectAll();
            }
        }));

        facewidth1.setTextFormatter(faceWidthFormatter1);
        facewidth1.setText("10.0000");
        facewidth1.setAlignment(Pos.CENTER_LEFT);
        facewidth1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (facewidth1.isFocused() && !facewidth1.getText().isEmpty()) {
                facewidth1.selectAll();
            }
        }));
        facewidth2.setTextFormatter(faceWidthFormatter2);
        facewidth2.setText("10.0000");
        facewidth2.setAlignment(Pos.CENTER_LEFT);
        facewidth2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (facewidth2.isFocused() && !facewidth2.getText().isEmpty()) {
                facewidth2.selectAll();
            }
        }));

        profileShift1.setTextFormatter(profileShiftFormatter1);
        profileShift1.setText("0.0000");
        profileShift1.setAlignment(Pos.CENTER_LEFT);
        profileShift1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (profileShift1.isFocused() && !profileShift1.getText().isEmpty()) {
                profileShift1.selectAll();
            }
        }));
        profileShift2.setTextFormatter(profileShiftFormatter2);
        profileShift2.setText("0.0000");
        profileShift2.setAlignment(Pos.CENTER_LEFT);
        profileShift2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (profileShift2.isFocused() && !profileShift2.getText().isEmpty()) {
                profileShift2.selectAll();
            }
        }));

        middleDistance1.setTextFormatter(middleDistanceFormatter1);
        middleDistance1.setText("0.0000");
        middleDistance1.setAlignment(Pos.CENTER_LEFT);
        middleDistance1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (middleDistance1.isFocused() && !middleDistance1.getText().isEmpty()) {
                middleDistance1.selectAll();
            }
        }));
        middleDistance2.setTextFormatter(middleDistanceFormatter2);
        middleDistance2.setText("0.0000");
        middleDistance2.setAlignment(Pos.CENTER_LEFT);
        middleDistance2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (middleDistance2.isFocused() && !middleDistance2.getText().isEmpty()) {
                middleDistance2.selectAll();
            }
        }));

        helixAngle.setTextFormatter(helixAngleFormatter);
        helixAngle.setText("0");
        helixAngle.setAlignment(Pos.CENTER_LEFT);
        helixAngle.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (helixAngle.isFocused() && !helixAngle.getText().isEmpty()) {
                helixAngle.selectAll();
            }
        }));
        totalProfileShift.setTextFormatter(totalProfileShiftFormatter);
        totalProfileShift.setText("0");
        totalProfileShift.setAlignment(Pos.CENTER_LEFT);
        totalProfileShift.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (totalProfileShift.isFocused() && !totalProfileShift.getText().isEmpty()) {
                totalProfileShift.selectAll();
            }
        }));

        pinionCheckBox.setSelected(true);
        gearCheckBox.setSelected(true);

        middleDistance1.setDisable(true);
        middleDistance2.setDisable(true);
    }
    private void initNodesCalculationTab() {
        resultsCalculationEngine = resultsCalculationTab.getEngine();

        typeLoadCalculation.getToggles().addAll(pstRadioBtn, tspRadioBtn, ptsRadioBtn);
        pstRadioBtn.setSelected(true);

        safety.setTextFormatter(safetyFormatter);
        safety.setText("1.5");
        safety.setAlignment(Pos.CENTER_LEFT);
        safety.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (safety.isFocused() && !safety.getText().isEmpty()) {
                safety.selectAll();
            }
        }));

        power1.setTextFormatter(powerFormatter1);
        power1.setText("0.01");
        power1.setAlignment(Pos.CENTER_LEFT);
        power1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (power1.isFocused() && !power1.getText().isEmpty()) {
                power1.selectAll();
            }
        }));

        power2.setTextFormatter(powerFormatter2);
        power2.setText("1.5");
        power2.setAlignment(Pos.CENTER_LEFT);
        power2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (power2.isFocused() && !power2.getText().isEmpty()) {
                power2.selectAll();
            }
        }));

        speed1.setTextFormatter(speedFormatter1);
        speed1.setText("100");
        speed1.setAlignment(Pos.CENTER_LEFT);
        speed1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (speed1.isFocused() && !speed1.getText().isEmpty()) {
                speed1.selectAll();
            }
        }));

        speed2.setTextFormatter(speedFormatter2);
        speed2.setText("1.5");
        speed2.setAlignment(Pos.CENTER_LEFT);
        speed2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (speed2.isFocused() && !speed2.getText().isEmpty()) {
                speed2.selectAll();
            }
        }));

        torque1.setTextFormatter(torqueFormatter1);
        torque1.setText("1.5");
        torque1.setAlignment(Pos.CENTER_LEFT);
        torque1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (torque1.isFocused() && !torque1.getText().isEmpty()) {
                torque1.selectAll();
            }
        }));
        torque1.setDisable(true);

        torque2.setTextFormatter(torqueFormatter2);
        torque2.setText("1.5");
        torque2.setAlignment(Pos.CENTER_LEFT);
        torque2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (torque2.isFocused() && !torque2.getText().isEmpty()) {
                torque2.selectAll();
            }
        }));

        efficiency.setTextFormatter(efficiencyFormatter);
        efficiency.setText("0.98");
        efficiency.setAlignment(Pos.CENTER_LEFT);
        efficiency.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (efficiency.isFocused() && !efficiency.getText().isEmpty()) {
                efficiency.selectAll();
            }
        }));

        pinionParam1.setTextFormatter(pinionParamFormatter1);
        pinionParam1.setText("150.0");
        pinionParam1.setAlignment(Pos.CENTER_LEFT);
        pinionParam1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (pinionParam1.isFocused() && !pinionParam1.getText().isEmpty()) {
                pinionParam1.selectAll();
            }
        }));

        gearParam1.setTextFormatter(gearParamFormatter1);
        gearParam1.setText("150.0");
        gearParam1.setAlignment(Pos.CENTER_LEFT);
        gearParam1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (gearParam1.isFocused() && !gearParam1.getText().isEmpty()) {
                gearParam1.selectAll();
            }
        }));

        //power2.setStyle("-fx-background-color: #AAAAAA;");
    }

    private void calculateParameters() {
        double m = moduleFormatter.getValue();
        double alpha = pressureAngleFormatter.getValue();
        double hAngle = helixAngleFormatter.getValue();

        double a_star = addendum1.selectionModelProperty().getValue().getSelectedItem();
        double c_star = clearance1.selectionModelProperty().getValue().getSelectedItem();
        double r_star = rootFillet1.selectionModelProperty().getValue().getSelectedItem();

        int z1 = nrTeethFormatter1.getValue();
        double b1 = faceWidthFormatter1.getValue();

        int z2;
        if (nTeethRadioBtn.isSelected()) {
            z2 = nrTeethFormatter2.getValue();
            gearRatioFormatter.setValue(((double)z2)/((double)z1));
        }
        else {
            z2 = (int)(gearRatioFormatter.getValue() * z1);
            nrTeeth2.setText(String.valueOf(z2));
        }

        double b2 = faceWidthFormatter2.getValue();
        boolean doubleHelEnable = helicalCheckBox.isSelected();
        double mid1 = middleDistanceFormatter1.getValue();
        double mid2 = middleDistanceFormatter2.getValue();
        boolean rackEnabled = pinionRackRadioBtn.isSelected();

        // TODO x1 x2
	double x1 = 0.0, x2 = 0.0, x_sum;
	if (profileShiftRadioBtn.isSelected()) {
	    x1 = profileShiftFormatter1.getValue();
	    x_sum = totalProfileShiftFormatter.getValue();
	    x2 = x_sum - x1;
	} else {
	    
	}
	
        if(!calculationCheckBox.isSelected())
            drive = new SpurDrive(z1,z2,Math.toRadians(alpha),Math.toRadians(hAngle),m,a_star,c_star,r_star,b1,b2, x1, x2,
                    !(helixDir.isSelected()), doubleHelEnable, mid1, mid2, rackEnabled);
        else {
            double P1 = 0, n1 = 0, M_k1 = 0;
            if (pstRadioBtn.isSelected()) {
                P1 = powerFormatter1.getValue();
                n1 = speedFormatter1.getValue();
                M_k1 = (30000 * P1) / (Math.PI * n1);
            } else if (tspRadioBtn.isSelected()) {
                n1 = speedFormatter1.getValue();
                M_k1 = torqueFormatter1.getValue();
                P1 = (M_k1 * Math.PI * n1) / 30000.0;
            } else if (ptsRadioBtn.isSelected()) {
                P1 = powerFormatter1.getValue();
                M_k1 = torqueFormatter1.getValue();
                n1 = (30000 * P1) / (Math.PI * M_k1);
            }
            double eta = efficiencyFormatter.getValue();
            double minSafety = safetyFormatter.getValue();
            double abs1 = pinionParamFormatter1.getValue();
            double abs2 = gearParamFormatter1.getValue();
            drive = new SpurDrive(z1,z2,Math.toRadians(alpha),Math.toRadians(hAngle),m,a_star,c_star,r_star,b1,b2, x1, x2,
                    !(helixDir.isSelected()), doubleHelEnable, mid1, mid2, rackEnabled, P1, n1, M_k1, eta, minSafety, abs1, abs2);
            powerFormatter2.setValue(drive.getP2());
            speedFormatter2.setValue(drive.getN2());
            torqueFormatter2.setValue(drive.getM_k2());

            powerFormatter1.setValue(P1);
            speedFormatter1.setValue(n1);
            torqueFormatter1.setValue(M_k1);
        }

        this.res1 = drive.getRes1();
        this.res2 = drive.getRes2();

        if(dimension1RadioBtn.isSelected())
            resultsDesignEngine.loadContent(res1);
        else
            resultsDesignEngine.loadContent(res2);

        resultsCalculationEngine.loadContent(drive.getResStrength());

        if((drive.getS_1() > safetyFormatter.getValue()) && (drive.getS_2() > safetyFormatter.getValue()))
            outputMessage("Calculation checks!");
        else
            outputMessage("Calculation does NOT check!");
    }
    private void outputMessage(String msg) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        outputTerminal.appendText(dateFormat.format(date) + "> " + msg + "\n");
    }
    private void loadWindow (String loc, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
            Parent parent = loader.load();

            ReportController reportController = loader.getController();
            reportController.setDrive(this.drive);
            if(gearRatioRadioBtn.isSelected())
                reportController.setInputType("Gear Ration");
            else
                reportController.setInputType("Number of Teeth");
            if(centerDistanceRadioBtn.isSelected())
                reportController.setDesignGuide("Center Distance");
            else
                reportController.setDesignGuide("Total Profile Shift");
            if(pinionGearRadioBtn.isSelected())
                reportController.setDriveType("Pinion and Gear");
            else
                reportController.setDriveType("Pinion and Rack");

            reportController.setMethodStrengthCalculation("Simple design (Anticipates that the total circumferential force can only be carried by one tooth)");
            reportController.setTypeStrengthCalculation("Check Calculation");

            if(pstRadioBtn.isSelected())
                reportController.setTypeLoadCalculation("Torque calculation for the specified power and speed");
            else if(tspRadioBtn.isSelected())
                reportController.setTypeLoadCalculation("Power calculation for the specified torque and speed");
            else
                reportController.setTypeLoadCalculation("Speed calculation for the specified power and torque");

            reportController.showReportSpur();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(rootPane.getScene().getWindow());
            stage.setTitle(title);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setParts(Group partsGroup, List<CSG> partsCSG) {
        this.partsGroup = partsGroup;
        this.partsCSG = partsCSG;
    }
}