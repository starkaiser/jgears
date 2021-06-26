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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.gears.BevelDrive;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import static main.java.jgears.gui.GuiFormatter.*;
import static main.java.jgears.gui.GuiFormatter.converterUl;

public class BevelController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<Double> addendum1, addendum2, clearance1, clearance2, rootFillet1, rootFillet2;
    @FXML
    private CheckBox doubleHelicalCheckBox, calculationCheckBox, gear2CheckBox, pinionCheckBox,
            gearCheckBox;
    @FXML
    private TextField nrTeeth1, nrTeeth2, displacement1, displacement2, profileShift1, profileShift2, helixAngle,
            shaftAngle, facewidth, power1, power2, speed1, speed2, torque1, torque2, efficiency, pinionParam1,
            gearParam1, safety, module, gearRatio, pressureAngle;
    @FXML
    private WebView resultsDesignTab, resultsCalculationTab;
    @FXML
    private TextArea outputTerminal;
    @FXML
    private RadioButton gearRatioRadioBtn, nTeethRadioBtn, dimension1RadioBtn, dimension2RadioBtn, pstRadioBtn,
            tspRadioBtn, ptsRadioBtn, togetherRadioBtn, printingRadioBtn;
    @FXML
    private ToggleButton helixDir;
    @FXML
    private Tab calculationTab;

    private final TextFormatter<Double> faceWidthFormatter = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> shaftAngleFormatter = new TextFormatter<>(converterDeg);
    private final TextFormatter<Double> helixAngleFormatter = new TextFormatter<>(converterDeg);
    private final TextFormatter<Integer> nrTeethFormatter1 = new TextFormatter<>(converterUlInt);
    private final TextFormatter<Integer> nrTeethFormatter2 = new TextFormatter<>(converterUlInt);
    private final TextFormatter<Double> profileShiftFormatter1 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> profileShiftFormatter2 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> displacementFormatter1 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> displacementFormatter2 = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> moduleFormatter = new TextFormatter<>(converterMm);
    private final TextFormatter<Double> gearRatioFormatter = new TextFormatter<>(converterUl);
    private final TextFormatter<Double> pressureAngleFormatter = new TextFormatter<>(converterDeg);

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
    private final TextFormatter<Integer> durabilityFormatter = new TextFormatter<>(converterHrInt);

    private final ToggleGroup inputType = new ToggleGroup();
    private final ToggleGroup dimensions = new ToggleGroup();
    private final ToggleGroup typeLoadCalculation = new ToggleGroup();
    private final ToggleGroup assemblyType = new ToggleGroup();

    private String res1="", res2="";
    private Group partsGroup;
    private List<CSG> partsCSG;
    private WebEngine resultsDesignEngine, resultsCalculationEngine;
    private BevelDrive drive;

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
        } else {
            nrTeeth1.setDisable(true);
            profileShift1.setDisable(true);
        }
    }
    @FXML
    private void handleGearCheck(ActionEvent event) {
        if (gearCheckBox.isSelected()) {
            nrTeeth2.setDisable(!nTeethRadioBtn.isSelected());
            //profileShift2.setDisable(false);
        } else {
            nrTeeth2.setDisable(true);
            //profileShift2.setDisable(true);
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
        /*
        boolean pinionEnable = pinionCheckBox.isSelected();
        boolean gearEnable = gearCheckBox.isSelected();
        calculateParameters();
        partsGroup.getChildren().clear();

        if (pinionEnable) {
            drive.generateGear1();
            partsGroup.getChildren().add(drive.getGear1());
        }
        if (gearEnable) {
            drive.generateGear2();
            partsGroup.getChildren().add(drive.getGear2());
        }*/
        calculateParameters();
	if (drive.getBeta_m() > 0)
	    drive.generateBevelDrive(pinionCheckBox.isSelected(), gearCheckBox.isSelected(), togetherRadioBtn.isSelected(),
				     doubleHelicalCheckBox.isSelected());
	else
	    drive.generateBevelDrive(pinionCheckBox.isSelected(), gearCheckBox.isSelected(), togetherRadioBtn.isSelected(),
				     false);
        partsGroup.getChildren().clear();
        partsGroup.getChildren().add(drive.getBevelDrive().getGearMeshView());
        partsCSG.clear();
        partsCSG.add(drive.getBevelDrive().getGearCSG());
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
        commonObj.put("facewidth", faceWidthFormatter.getValue());
        commonObj.put("helixAngle", helixAngleFormatter.getValue());
        commonObj.put("shaftAngle", shaftAngleFormatter.getValue());
        commonObj.put("efficiency", efficiencyFormatter.getValue());
        commonObj.put("safety", safetyFormatter.getValue());

        // pinion parameters
        pinionObj.put("nrTeeth", nrTeethFormatter1.getValue());
        pinionObj.put("profileShift", profileShiftFormatter1.getValue());
        pinionObj.put("displacement", displacementFormatter1.getValue());
        pinionObj.put("power", powerFormatter1.getValue());
        pinionObj.put("speed", speedFormatter1.getValue());
        pinionObj.put("torque", torqueFormatter1.getValue());
        pinionObj.put("pinionParam", pinionParamFormatter1.getValue());
        pinionObj.put("addendum", addendum1.getValue());
        pinionObj.put("clearance", clearance1.getValue());
        pinionObj.put("rootFillet", rootFillet1.getValue());

        // gear parameters
        gearObj.put("nrTeeth", nrTeethFormatter2.getValue());
        gearObj.put("profileShift", profileShiftFormatter2.getValue());
        gearObj.put("displacement", displacementFormatter2.getValue());
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
        fileChooser.setInitialFileName("bevelDrive.json");
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
                Logger.getLogger(BevelController.class
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
        if (file != null) {
            try {
                FileReader reader = new FileReader(file);
                Object obj = jsonObj.parse(reader);
                JSONArray paramList = (JSONArray) obj;
                JSONObject parameters = (JSONObject) paramList.get(0);
                gearRatioFormatter.setValue((double)parameters.get("gearRatio"));
                moduleFormatter.setValue((double)parameters.get("module"));
                pressureAngleFormatter.setValue((double)parameters.get("pressureAngle"));
                faceWidthFormatter.setValue((double)parameters.get("facewidth"));
                helixAngleFormatter.setValue((double)parameters.get("helixAngle"));
                shaftAngleFormatter.setValue((double)parameters.get("shaftAngle"));
                efficiencyFormatter.setValue((double)parameters.get("efficiency"));
                safetyFormatter.setValue((double)parameters.get("safety"));

                parameters = (JSONObject) paramList.get(1);
                //System.out.println(parameters.get("nrTeeth"));
                //nrTeethFormatter1.setValue((Integer)parameters.get("nrTeeth"));
                profileShiftFormatter1.setValue((double)parameters.get("profileShift"));
                displacementFormatter1.setValue((double)parameters.get("displacement"));
                powerFormatter1.setValue((double)parameters.get("power"));
                speedFormatter1.setValue((double)parameters.get("speed"));
                torqueFormatter1.setValue((double)parameters.get("torque"));
                pinionParamFormatter1.setValue((double)parameters.get("pinionParam"));
                addendum1.setValue((double)parameters.get("addendum"));
                clearance1.setValue((double)parameters.get("clearance"));
                rootFillet1.setValue((double)parameters.get("rootFillet"));

                parameters = (JSONObject) paramList.get(2);
                //nrTeethFormatter2.setValue((Integer)parameters.get("nrTeeth"));
                profileShiftFormatter2.setValue((double)parameters.get("profileShift"));
                displacementFormatter2.setValue((double)parameters.get("displacement"));
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
		Logger.getLogger(BevelController.class.getName()).
                    log(Level.SEVERE, null, ex);
            }
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
    @FXML
    private void handleAssemblyType(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNodesDesignTab();
        initNodesCalculationTab();
    }

    private void initNodesDesignTab() {
        gearRatio.setDisable(true);
        resultsDesignEngine = resultsDesignTab.getEngine();

        assemblyType.getToggles().addAll(togetherRadioBtn, printingRadioBtn);
        togetherRadioBtn.setSelected(true);

        calculationCheckBox.setSelected(true);

        module.setTextFormatter(moduleFormatter);
        module.setText("1.75");
        module.setAlignment(Pos.CENTER_LEFT);
        module.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (module.isFocused() && !module.getText().isEmpty()) {
                module.selectAll();
            }
        }));

        gearRatio.setTextFormatter(gearRatioFormatter);
        gearRatio.setText("2");
        gearRatio.setAlignment(Pos.CENTER_LEFT);
        gearRatio.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (gearRatio.isFocused() && !gearRatio.getText().isEmpty()) {
                gearRatio.selectAll();
            }
        }));

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
        clearance1.setValue(0.20);
        clearance2.setValue(0.20);
        rootFillet1.setValue(0.25);
        rootFillet2.setValue(0.25);

        addendum2.setDisable(true);
        clearance2.setDisable(true);
        rootFillet2.setDisable(true);

        addendum2.valueProperty().bind(addendum1.valueProperty());
        clearance2.valueProperty().bind(clearance1.valueProperty());
        rootFillet2.valueProperty().bind(rootFillet1.valueProperty());

        inputType.getToggles().addAll(gearRatioRadioBtn, nTeethRadioBtn);
        nTeethRadioBtn.setSelected(true);

        dimensions.getToggles().addAll(dimension1RadioBtn, dimension2RadioBtn);
        dimension1RadioBtn.setSelected(true);

        facewidth.setTextFormatter(faceWidthFormatter);
        facewidth.setText("10");
        facewidth.setAlignment(Pos.CENTER_LEFT);
        facewidth.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (facewidth.isFocused() && !facewidth.getText().isEmpty()) {
                facewidth.selectAll();
            }
        }));

        nrTeeth1.setTextFormatter(nrTeethFormatter1);
        nrTeeth1.setText("22");
        nrTeeth1.setAlignment(Pos.CENTER_LEFT);
        nrTeeth1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (nrTeeth1.isFocused() && !nrTeeth1.getText().isEmpty()) {
                nrTeeth1.selectAll();
            }
        }));
        nrTeeth2.setTextFormatter(nrTeethFormatter2);
        nrTeeth2.setText("44");
        nrTeeth2.setAlignment(Pos.CENTER_LEFT);
        nrTeeth2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (nrTeeth2.isFocused() && !nrTeeth2.getText().isEmpty()) {
                nrTeeth2.selectAll();
            }
        }));

        displacement1.setTextFormatter(displacementFormatter1);
        displacement1.setText("0.0000");
        displacement1.setAlignment(Pos.CENTER_LEFT);
        displacement1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (displacement1.isFocused() && !displacement1.getText().isEmpty()) {
                displacement1.selectAll();
            }
        }));
        displacement2.setTextFormatter(displacementFormatter2);
        displacement2.setText("0.0000");
        displacement2.setAlignment(Pos.CENTER_LEFT);
        displacement2.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (displacement2.isFocused() && !displacement2.getText().isEmpty()) {
                displacement2.selectAll();
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

        helixAngle.setTextFormatter(helixAngleFormatter);
        helixAngle.setText("10");
        helixAngle.setAlignment(Pos.CENTER_LEFT);
        helixAngle.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (helixAngle.isFocused() && !helixAngle.getText().isEmpty()) {
                helixAngle.selectAll();
            }
        }));
        shaftAngle.setTextFormatter(shaftAngleFormatter);
        shaftAngle.setText("90");
        shaftAngle.setAlignment(Pos.CENTER_LEFT);
        shaftAngle.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (shaftAngle.isFocused() && !shaftAngle.getText().isEmpty()) {
                shaftAngle.selectAll();
            }
        }));

        pinionCheckBox.setSelected(true);
        gearCheckBox.setSelected(true);
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
        power1.setText("0.001");
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
        speed1.setText("10");
        speed1.setAlignment(Pos.CENTER_LEFT);
        speed1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (speed1.isFocused() && !speed1.getText().isEmpty()) {
                speed1.selectAll();
            }
        }));

        speed2.setTextFormatter(speedFormatter2);
        speed2.setText("5");
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
        pinionParam1.setText("105.0");
        pinionParam1.setAlignment(Pos.CENTER_LEFT);
        pinionParam1.focusedProperty().addListener((ov, t, t1) -> Platform.runLater(() -> {
            if (pinionParam1.isFocused() && !pinionParam1.getText().isEmpty()) {
                pinionParam1.selectAll();
            }
        }));

        gearParam1.setTextFormatter(gearParamFormatter1);
        gearParam1.setText("105.0");
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
        double axisAng = shaftAngleFormatter.getValue();

        double a_star = addendum1.selectionModelProperty().getValue().getSelectedItem();
        double c_star = clearance1.selectionModelProperty().getValue().getSelectedItem();
        double r_star = rootFillet1.selectionModelProperty().getValue().getSelectedItem();

        int z1 = nrTeethFormatter1.getValue();
        int z2;
        if (nTeethRadioBtn.isSelected()) {
            z2 = nrTeethFormatter2.getValue();
            gearRatioFormatter.setValue(((double)z2)/((double)z1));
        }
        else {
            z2 = (int)(gearRatioFormatter.getValue() * z1);
            nrTeeth2.setText(String.valueOf(z2));
        }
        double b = faceWidthFormatter.getValue();


        // TODO x1 x2 - profile shifts
        if(!calculationCheckBox.isSelected())
            drive = new BevelDrive(z1,z2,Math.toRadians(alpha),Math.toRadians(hAngle),!(helixDir.isSelected()),
                    Math.toRadians(axisAng),m,b,a_star,c_star,r_star,0, 0);
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

            drive = new BevelDrive(z1,z2,Math.toRadians(alpha),Math.toRadians(hAngle),!(helixDir.isSelected()),
                    Math.toRadians(axisAng),m,b,a_star,c_star,r_star,0, 0, P1, n1, M_k1, eta, minSafety, abs1, abs2);
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
            reportController.setDriveBevel(this.drive);
            if(gearRatioRadioBtn.isSelected())
                reportController.setInputType("Gear Ration");
            else
                reportController.setInputType("Number of Teeth");

            reportController.setMethodStrengthCalculation("Simple design (Anticipates that the total circumferential force can only be carried by one tooth)");
            reportController.setTypeStrengthCalculation("Check Calculation");

            if(pstRadioBtn.isSelected())
                reportController.setTypeLoadCalculation("Torque calculation for the specified power and speed");
            else if(tspRadioBtn.isSelected())
                reportController.setTypeLoadCalculation("Power calculation for the specified torque and speed");
            else
                reportController.setTypeLoadCalculation("Speed calculation for the specified power and torque");

            reportController.showReportBevel();
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
    void setParts(Group partsGroup, List<CSG> partsCSG) {
        this.partsGroup = partsGroup;
        this.partsCSG = partsCSG;
    }
}
