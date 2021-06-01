package main.java.jgears.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.jgears.csg.jcsg.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import main.java.jgears.gears.BasicRackCutter;

public class UiMainController implements Initializable {
    @FXML
    private VBox appRoot;
    @FXML
    private Pane viewContainer;
    @FXML
    private CheckMenuItem menuAxes, menuGizmo;

    private SubScene subScene; // scene for parts display
    private SubScene gizmoSubScene; // scene for corner axes
    private final Group viewGroup = new Group(); // group containind the parts to be displayed + the center axes
    private final Group partsGroup = new Group(); // contains all the 3D parts
    private final Group gizmoGroup = new Group(); // contains the newGizmo (corner axes)
    private final Group centerCoordSys = CoordSys.createCCS(); // center axes
    private final Group cornerCoordSys = CoordSys.createGCS(); // corner axes

    private final List<CSG> partsCSG = new ArrayList<>();

    // variables for scroll scaling
    private double x_scale = 1.0;
    private double y_scale = 1.0;
    private double z_scale = 1.0;
    private final double scaleRes = 0.2; // scale resolution
    private final double scaleResClick = 0.4; // scale resolution
    @FXML
    private void handleCheckAxes(ActionEvent event) {
        if(menuAxes.isSelected()) {
            viewGroup.getChildren().clear();
            viewGroup.getChildren().add(partsGroup);
            viewGroup.getChildren().add(centerCoordSys);
        }
        else {
            viewGroup.getChildren().clear();
            viewGroup.getChildren().add(partsGroup);
        }
    }
    @FXML
    private void handleCheckGizmo(ActionEvent event) {
        if(menuGizmo.isSelected()) {
            gizmoGroup.getChildren().clear();
            gizmoGroup.getChildren().add(cornerCoordSys);
        }
        else {
            gizmoGroup.getChildren().clear();
        }
    }

    @FXML
    private void handleExportObj(ActionEvent event) {}
    @FXML
    private void handleExportStl(ActionEvent event) throws IOException {
        FileUtil.write(Paths.get("drive.stl"), partsCSG.get(0).toStlString());
    }
    @FXML
    private void handleImportObj(ActionEvent event) {
	
    }
    @FXML
    private void handleImportStl(ActionEvent event) {
	
    }
    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage)appRoot.getScene().getWindow()).close();
    }

    @FXML
    private void handleIsometricView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        viewGroup.getTransforms().addAll(new Rotate(135, 0, 0, 0, Rotate.X_AXIS), new Rotate(-45, 0, 0, 0, Rotate.Z_AXIS));
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        cornerCoordSys.getTransforms().clear();
        cornerCoordSys.getTransforms().addAll(new Rotate(135, 0, 0, 0, Rotate.X_AXIS), new Rotate(-45, 0, 0, 0, Rotate.Z_AXIS));
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleFrontView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        viewGroup.getTransforms().add(new Rotate(90, 0, 0, 0, Rotate.X_AXIS));
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        cornerCoordSys.getTransforms().clear();
        cornerCoordSys.getTransforms().add(new Rotate(90, 0, 0, 0, Rotate.X_AXIS));
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleBackView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        viewGroup.getTransforms().addAll(new Rotate(-90, 0, 0, 0, Rotate.X_AXIS), new Rotate(-180, 0, 0, 0, Rotate.Y_AXIS));
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        cornerCoordSys.getTransforms().clear();
        cornerCoordSys.getTransforms().addAll(new Rotate(-90, 0, 0, 0, Rotate.X_AXIS), new Rotate(-180, 0, 0, 0, Rotate.Y_AXIS));
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleLeftView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        viewGroup.getTransforms().addAll(new Rotate(90, 0, 0, 0, Rotate.X_AXIS), new Rotate(90, 0, 0, 0, Rotate.Z_AXIS));
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        cornerCoordSys.getTransforms().clear();
        cornerCoordSys.getTransforms().addAll(new Rotate(90, 0, 0, 0, Rotate.X_AXIS), new Rotate(90, 0, 0, 0, Rotate.Z_AXIS));
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleRightView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        viewGroup.getTransforms().addAll(new Rotate(90, 0, 0, 0, Rotate.X_AXIS), new Rotate(-90, 0, 0, 0, Rotate.Z_AXIS));
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);
	
        cornerCoordSys.getTransforms().clear();
        cornerCoordSys.getTransforms().addAll(new Rotate(90, 0, 0, 0, Rotate.X_AXIS), new Rotate(-90, 0, 0, 0, Rotate.Z_AXIS));
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleTopView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        viewGroup.getTransforms().add(new Rotate(180, 0, 0, 0, Rotate.X_AXIS));
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        cornerCoordSys.getTransforms().clear();
        cornerCoordSys.getTransforms().add(new Rotate(180, 0, 0, 0, Rotate.X_AXIS));
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleBottomView (ActionEvent event) {
        viewGroup.getTransforms().clear();
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        cornerCoordSys.getTransforms().clear();
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);
    }
    @FXML
    private void handleZoomIn (ActionEvent event) {
        x_scale += scaleResClick;
        y_scale += scaleResClick;
        z_scale += scaleResClick;
            
        partsGroup.getTransforms().clear();
        partsGroup.getTransforms().add(new Scale(x_scale, y_scale, z_scale));
    }
    @FXML
    private void handleZoomOut (ActionEvent event) {
	x_scale -= scaleResClick;
        y_scale -= scaleResClick;
        z_scale -= scaleResClick;
            
        partsGroup.getTransforms().clear();
        partsGroup.getTransforms().add(new Scale(x_scale, y_scale, z_scale));
    }

    @FXML
    private void loadSpurWindow(ActionEvent event) { loadWindow("/main/java/jgears/gui/resources/Spur.fxml", "spur"); }
    @FXML
    private void loadBevelWindow(ActionEvent event) { loadWindow("/main/java/jgears/gui/resources/Bevel.fxml", "bevel"); }
    @FXML
    private void loadScriptWindow(ActionEvent event) { loadWindow("/main/java/jgears/gui/resources/ScriptEditor.fxml", "script"); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuAxes.setSelected(true);
        menuGizmo.setSelected(true);

        subScene = new SubScene(viewGroup, 100, 100, true, SceneAntialiasing.BALANCED);
        gizmoSubScene = new SubScene(gizmoGroup, 100, 100, true, SceneAntialiasing.BALANCED);

        subScene.widthProperty().bind(viewContainer.widthProperty());
        subScene.heightProperty().bind(viewContainer.heightProperty());

        gizmoSubScene.widthProperty().bind(viewContainer.widthProperty());
        gizmoSubScene.heightProperty().bind(viewContainer.heightProperty());

        //PerspectiveCamera subSceneCamera = new PerspectiveCamera(false);
        //PerspectiveCamera gizmoSubSceneCamera = new PerspectiveCamera(false);
        ParallelCamera subSceneCamera = new ParallelCamera();
        ParallelCamera gizmoSubSceneCamera = new ParallelCamera();

        subScene.setCamera(subSceneCamera);
        gizmoSubScene.setCamera(gizmoSubSceneCamera);

        viewContainer.getChildren().addAll(subScene, gizmoSubScene);

        // create the default part
        /*
        CSG cube = new BasicRackCutter(1.5, 10 + 2, Math.toRadians(20), 10, -Math.tan(0) * 10).toCSG();
        MeshContainer meshContainer = cube.toJavaFXMesh();
        final MeshView meshView = meshContainer.getAsMeshViews().get(0);
        PhongMaterial m = new PhongMaterial(Color.DEEPSKYBLUE);
        meshView.setMaterial(m);
        meshView.setCullFace(CullFace.BACK);
        partsGroup.getChildren().add(meshView);*/

        // add the CCS and the part to the group
        viewGroup.getChildren().add(partsGroup);
        viewGroup.getChildren().add(centerCoordSys);
        viewGroup.layoutXProperty().bind(viewContainer.widthProperty().divide(2));
        viewGroup.layoutYProperty().bind(viewContainer.heightProperty().divide(2));
        // bindings for objects rotations
        VFX3DUtil.addMouseBehavior(viewGroup, viewContainer, MouseButton.PRIMARY);

        // add the gizmo to the gizmoGroup and position it to the lower left corner
        gizmoGroup.layoutXProperty().bind(viewContainer.minWidthProperty());
        gizmoGroup.layoutYProperty().bind(viewContainer.minHeightProperty());
        cornerCoordSys.layoutXProperty().bind(viewContainer.minWidthProperty().add(50));
        cornerCoordSys.layoutYProperty().bind(viewContainer.heightProperty().subtract(50));
        gizmoGroup.getChildren().add(cornerCoordSys);
        // bindings for gizmo rotations
        VFX3DUtil.addMouseBehavior(cornerCoordSys, viewContainer, MouseButton.PRIMARY);

        // scale parts with scroll
        viewContainer.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            if (delta > 0) {
                x_scale += scaleRes;
                y_scale += scaleRes;
                z_scale += scaleRes;
            } else {
                x_scale -= scaleRes;
                y_scale -= scaleRes;
                z_scale -= scaleRes;
            }
            partsGroup.getTransforms().clear();
            partsGroup.getTransforms().add(new Scale(x_scale, y_scale, z_scale));
        });
    }

    private void loadWindow(String loc, String type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
            Parent parent = loader.load();
            String title = "";

            switch (type) {
                case "spur":
                    SpurController spurController = loader.getController();
                    spurController.setParts(partsGroup, partsCSG);
                    title = "Parallel Gear Drives Generator";
                    break;
                case "bevel":
                    BevelController bevelController = loader.getController();
                    bevelController.setParts(partsGroup,partsCSG);
                    title = "Bevel Gear Drives Generator";
                    break;
                case "script":
                    ScriptController scriptController = loader.getController();
                    scriptController.setPartsGroup(partsGroup);
                    title = "Script";
                    break;
            }

            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(appRoot.getScene().getWindow());
            stage.setResizable(false);
            stage.setTitle(title);
            Scene scene = new Scene(parent);
            if(type.equals("script")){
                scene.getStylesheets().add(getClass().getResource("/main/java/jgears/gui/resources/java-keywords.css").toExternalForm());
                stage.setResizable(true);
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}