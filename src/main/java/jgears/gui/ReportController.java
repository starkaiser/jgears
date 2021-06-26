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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.java.jgears.gears.SpurDrive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.jgears.gears.BevelDrive;

public class ReportController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private WebView reportView;

    private String report = "";
    private String inputType;
    private String designGuide;
    private String driveType;
    private String methodStrengthCalculation;
    private String typeLoadCalculation;
    private String typeStrengthCalculation;
    private WebEngine reportEngine;
    private SpurDrive drive;
    private BevelDrive driveBevel;
    private SimpleDateFormat formatter;
    private Date date;

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        fileChooser.setTitle("Save Report");
        fileChooser.setInitialFileName("SpurDriveReport");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML File", "*.html"));

        File file = fileChooser.showSaveDialog(null);
	if (file == null) {
            return;
        }
        fileChooser.setInitialDirectory(file.getParentFile());
            try {
                FileWriter fileWriter;
                fileWriter = new FileWriter(file);
                fileWriter.write(report);
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(ReportController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date = new Date();
        reportEngine = reportView.getEngine();
        //fileChooser.setInitialDirectory(new File("C:\\temp"));
	//showReportSpur();
    }

    public void showReportSpur() {
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	String formattedDriveType = String.format(ResultsUtils.REPORTDRIVETYPE, "Parallel Gears Generator");
        report = ResultsUtils.REPORTHEAD + 
		 formattedDriveType + 
		 String.format(ResultsUtils.REPORTDATE, formatter.format(date)) + 
		 ResultsUtils.REPORTLINE +
                 String.format(ResultsUtils.REPORTINFO, inputType, designGuide, driveType, methodStrengthCalculation,
                               typeLoadCalculation, typeStrengthCalculation) +
                 ResultsUtils.REPORTLINE  + 
		 drive.getResReportGeom()+ 
		 ResultsUtils.REPORTLINE  + 
		 drive.getResReportStrength() + 
		 ResultsUtils.REPORTEND;
        reportEngine.loadContent(report);
    }
    public void showReportBevel() {
	
	formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	String formattedDriveType = String.format(ResultsUtils.REPORTDRIVETYPE, "Bevel Gears Generator");
	
	// costructing the whole report as HTML
	report = ResultsUtils.REPORTHEAD + 
		 formattedDriveType + 
		 String.format(ResultsUtils.REPORTDATE, formatter.format(date)) + 
		 ResultsUtils.REPORTLINE +
                 String.format(ResultsUtils.REPORTINFOBEVEL, inputType, methodStrengthCalculation,typeLoadCalculation, 
			       typeStrengthCalculation) +
                 ResultsUtils.REPORTLINE + 
		 driveBevel.getResReportGeom() + 
		 ResultsUtils.REPORTLINE + 
		 driveBevel.getResReportStrength() +
		 ResultsUtils.REPORTEND;
        reportEngine.loadContent(report); // load the HTML report
    }

    public void setDrive(SpurDrive drive) {
        this.drive = drive;
    }
    public void setDriveBevel(BevelDrive drive) {
	this.driveBevel = drive;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setDesignGuide(String designGuide) {
        this.designGuide = designGuide;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public void setMethodStrengthCalculation(String methodStrengthCalculation) {
        this.methodStrengthCalculation = methodStrengthCalculation;
    }

    public void setTypeLoadCalculation(String typeLoadCalculation) {
        this.typeLoadCalculation = typeLoadCalculation;
    }

    public void setTypeStrengthCalculation(String typeStrengthCalculation) {
        this.typeStrengthCalculation = typeStrengthCalculation;
    }

}
