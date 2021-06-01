package main.java.jgears.gui;

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
    private String calculationResult = "";
    private WebEngine reportEngine;
    private SpurDrive drive;
    private BevelDrive driveBevel;
    private SimpleDateFormat formatter;
    private Date date;

    private String material1, material2;
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        Window stage = rootPane.getScene().getWindow();
        fileChooser.setTitle("Save Report");
        fileChooser.setInitialFileName("SpurDriveReport");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML File", "*.html"));

        File file = fileChooser.showSaveDialog(stage);
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
        String formattedCommParam = String.format(ResultsUtils.REPORTPARALLELCOMMON, drive.getI(), 0.0, drive.getM(),
                Math.toDegrees(drive.getBeta()), Math.toDegrees(drive.getAlpha()), drive.getA_w(), drive.getA(),
                drive.getX_sum(), drive.getP(), drive.getP_tB(), Math.toDegrees(drive.getAlpha_w()), drive.getEp());
        String formattedIndParam = String.format(ResultsUtils.REPORTPARALLELINDPARAM, drive.getZ1(), drive.getZ2(), drive.getX1(),
                drive.getX2(), drive.getD1(), drive.getD2(), drive.getD_a1(), drive.getD_a2(), drive.getD_f1(),
                drive.getD_f2(), drive.getD_b1(), drive.getD_b2(), drive.getD_w1(), drive.getD_w2(), drive.getB1(),
                drive.getB2(), drive.getB_r1(), drive.getB_r2(), drive.getA_star(), drive.getA_star(), drive.getC_star(),
                drive.getC_star(), drive.getR_star(), drive.getR_star(), drive.getS1(), drive.getS2(),0.0, 0.0,
                drive.getS_c1(), drive.getS_c2(), drive.getH_c1(), drive.getH_c2(), drive.getW1(), drive.getW2(),
                drive.getZ_W1(), drive.getZ_W2(), drive.getM1(), drive.getM2(), drive.getD_M(), drive.getD_M(),
                drive.getZ_v1(), drive.getZ_v2(), drive.getD_n1(), drive.getD_n2(),
                drive.getD_an1(), drive.getD_an2(), drive.getD_bn1(), drive.getD_bn2(), drive.getX_z1(), drive.getX_z2(),
                drive.getX_p1(), drive.getX_p2(), drive.getX_d1(), drive.getX_d2(), 0.0, 0.0, drive.getS_a1(),
                drive.getS_a2(), 0.0,0.0);
        String formattedStrength = String.format(ResultsUtils.REPORTPARALLELSTRENGTHPARAM, material1, material2,
                drive.getSigma_Ab1(), drive.getSigma_Ab2(), drive.getF_all1(), drive.getF_all2(), drive.getS_1(),
                drive.getS_2(), drive.getP1(), drive.getP2(), drive.getN1(), drive.getN2(), drive.getM_k1(), drive.getM_k2(),
                drive.getF_r(), drive.getF_a(), drive.getF_t(), drive.getF_n(), drive.getV(), drive.getEta(), calculationResult);
        report = ResultsUtils.REPORTHEAD + 
		 formattedDriveType + 
		 String.format(ResultsUtils.REPORTDATE, formatter.format(date)) + 
		 ResultsUtils.REPORTLINE +
                 String.format(ResultsUtils.REPORTINFO, inputType, designGuide, driveType, methodStrengthCalculation,
                               typeLoadCalculation, typeStrengthCalculation) +
                 ResultsUtils.REPORTLINE  + 
		 formattedCommParam + 
		 formattedIndParam + 
		 ResultsUtils.REPORTLINE  + 
		 formattedStrength + 
		 ResultsUtils.REPORTEND;
        reportEngine.loadContent(report);
    }
    public void showReportBevel() {
	
	formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	String formattedDriveType = String.format(ResultsUtils.REPORTDRIVETYPE, "Bevel Gears Generator");
	String formattedCommParam = String.format(ResultsUtils.REPORTBEVELCOMMON, driveBevel.getI(), driveBevel.getM_et(),
				   driveBevel.getBeta_m(), driveBevel.getAlpha_t(), driveBevel.getAxisAng(), driveBevel.getAlpha_ne(),
				   driveBevel.getAlpha_nm(), driveBevel.getBeta_b(), driveBevel.getBeta_e(), driveBevel.getM_en(),
				   driveBevel.getEp(), driveBevel.getEp_alpha(), driveBevel.getEp_beta(), driveBevel.getU_v(),
				   driveBevel.getA_v(), driveBevel.getA_n(), driveBevel.getR_e(), driveBevel.getR_m(), driveBevel.getB());
	String formattedIndParam = String.format(ResultsUtils.REPORTBEVELINDPARAM, driveBevel.getZ1(), driveBevel.getZ2(),
				  driveBevel.getX1(), driveBevel.getX2(), driveBevel.getX_t1(), driveBevel.getX_t2(),
				  driveBevel.getD_e1(), driveBevel.getD_e2(), driveBevel.getD_m1(), driveBevel.getD_m2(),
				  driveBevel.getD_ae1(), driveBevel.getD_ae2(), driveBevel.getD_ai1(), driveBevel.getD_ai2(),
				  driveBevel.getD_fe1(), driveBevel.getD_fe2(), driveBevel.getA_e1(), driveBevel.getA_e2(),
				  0.0, 0.0, driveBevel.getDelta1(), driveBevel.getDelta2(), driveBevel.getDelta_a1(), driveBevel.getDelta2(),
				  driveBevel.getDelta_f1(), driveBevel.getDelta_f2(), driveBevel.getA_star(), driveBevel.getA_star(),
				  driveBevel.getC_star(), driveBevel.getC_star(), driveBevel.getR_star(), driveBevel.getR_star(),
				  0.0, 0.0, driveBevel.getS_e1(), driveBevel.getS_e2(), driveBevel.getS_ke1(), driveBevel.getS_ke2(),
				  driveBevel.getH_ke1(), driveBevel.getH_ke2(), driveBevel.getZ_v1(), driveBevel.getZ_v2(),
				  driveBevel.getD_v1(), driveBevel.getD_v2(), driveBevel.getD_va1(), driveBevel.getD_va2(),
				  driveBevel.getD_vb1(), driveBevel.getD_vb2(), driveBevel.getZ_n1(), driveBevel.getZ_n2(),
				  driveBevel.getD_n1(), driveBevel.getD_n2(), driveBevel.getD_an1(), driveBevel.getD_an2(),
				  driveBevel.getD_bn1(), driveBevel.getD_bn2(), driveBevel.getX_z1(), driveBevel.getX_z2(),
				  driveBevel.getX_p1(), driveBevel.getX_p2(), driveBevel.getX_d1(), driveBevel.getX_d2(),
				  0.0, 0.0, driveBevel.getS_a1(), driveBevel.getS_a2());
	
	String formattedStrength = String.format(ResultsUtils.REPORTBEVELSTRENGTHPARAM, material1, material2,
                driveBevel.getSigma_Ab1(), driveBevel.getSigma_Ab2(), driveBevel.getF_all1(), driveBevel.getF_all2(),
		driveBevel.getS_1(), driveBevel.getS_2(), driveBevel.getP1(), driveBevel.getP2(), driveBevel.getN1(),
		driveBevel.getN2(), driveBevel.getM_k1(), driveBevel.getM_k2(), driveBevel.getF_r1a(),
		driveBevel.getF_r1b(), driveBevel.getF_r2a(), driveBevel.getF_r2b(), driveBevel.getF_a1a(),
		driveBevel.getF_a1b(), driveBevel.getF_a2a(), driveBevel.getF_a2b(), driveBevel.getF_t(),
		driveBevel.getF_n(), driveBevel.getV(), driveBevel.getEta(), calculationResult);
	// costructing the whole report as HTML
	
	report = ResultsUtils.REPORTHEAD + 
		 formattedDriveType + 
		 String.format(ResultsUtils.REPORTDATE, formatter.format(date)) + 
		 ResultsUtils.REPORTLINE +
                 String.format(ResultsUtils.REPORTINFOBEVEL, inputType, methodStrengthCalculation,typeLoadCalculation, 
			       typeStrengthCalculation) +
                 ResultsUtils.REPORTLINE + 
		 formattedCommParam + 
		 formattedIndParam + 
		 ResultsUtils.REPORTLINE + 
		 formattedStrength +
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

    public void setCalculationResult(boolean calculationResult) {
        if(calculationResult)
            this.calculationResult = ResultsUtils.REPORTPOSITIVECHECK;
        else
            this.calculationResult = ResultsUtils.REPORTNEGATIVECHECK;
    }

    public void setResults(String material1, String material2) {
        this.material1 = material1;
        this.material2 = material2;
    }
}
