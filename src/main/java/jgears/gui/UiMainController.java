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
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
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
    private void handleExportStl(ActionEvent event) throws IOException {
        //FileUtil.write(Paths.get("drive.stl"), partsCSG.get(0).toStlString());
	if (partsCSG == null) {
	    /*
            Action response = Dialogs.create()
                    .title("Error")
                    .message("Cannot export STL. There is no geometry :(")
                    .lightweight()
                    .showError();*/

            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export STL File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "STL files (*.stl)",
                        "*.stl"));

        File f = fileChooser.showSaveDialog(null);

        if (f == null) {
            return;
        }

        String fName = f.getAbsolutePath();

        if (!fName.toLowerCase().endsWith(".stl")) {
            fName += ".stl";
        }

        try {
            main.java.jgears.csg.jcsg.FileUtil.write(
                    Paths.get(fName), partsCSG.get(0).toStlString());
        } catch (IOException ex) {
            Logger.getLogger(UiMainController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handleImportStl(ActionEvent event) {
	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("STL File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "STL files", "*.stl"));

        File f = fileChooser.showOpenDialog(null);

        if (f == null) {
            return;
        }

        String fName = f.getAbsolutePath();

        try {
	    partsCSG.clear();
	    partsGroup.getChildren().clear();
            partsCSG.add(main.java.jgears.csg.jcsg.STL.file(Paths.get(fName)));
	    
	    partsGroup.getChildren().add(generateMeshView(partsCSG.get(0)));
        } catch (IOException ex) {
            Logger.getLogger(UiMainController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
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
        
        CSG cube = new Cube(100, 100, 100).toCSG();
        MeshContainer meshContainer = cube.toJavaFXMesh();
        final MeshView meshView = meshContainer.getAsMeshViews().get(0);
        PhongMaterial m = new PhongMaterial(Color.DEEPSKYBLUE);
        meshView.setMaterial(m);
        meshView.setCullFace(CullFace.BACK);
        partsGroup.getChildren().add(meshView);
	partsCSG.add(cube);

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
                    scriptController.setParts(partsGroup, partsCSG);
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
		ScriptController scriptController = loader.getController();
		stage.setOnCloseRequest((WindowEvent t) -> {
		scriptController.getExecutor().shutdown();
		stage.close();
	});
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UiMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private MeshView generateMeshView(CSG object) {
        MeshContainer meshContainer = object.toJavaFXMesh();
	MeshView objectMeshView;
        objectMeshView = meshContainer.getAsMeshViews().get(0);
        objectMeshView.setMaterial(new PhongMaterial(Color.DEEPSKYBLUE));
        objectMeshView.setCullFace(CullFace.BACK);
	return objectMeshView;
    }
}
