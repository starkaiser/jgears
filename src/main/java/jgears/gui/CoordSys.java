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
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.csg.jcsg.Cylinder;
import main.java.jgears.csg.jcsg.MeshContainer;

public final class CoordSys {
    // 10 slices initially - reminder
    private static CSG cyl = new Cylinder(2,2,30,6).toCSG(); // create the "arm" of the arrow
    private static MeshContainer meshCylContainer = cyl.toJavaFXMesh(); // convert to MeshContainer

    // create one "arm" display object for each axis
    private static final MeshView cylViewX = meshCylContainer.getAsMeshViews().get(0);
    private static final MeshView cylViewY = meshCylContainer.getAsMeshViews().get(0);
    private static final MeshView cylViewZ = meshCylContainer.getAsMeshViews().get(0);

    private static CSG cone = new Cylinder(6,0.1,10,6).toCSG(); // create the tip of the arrow
    private static MeshContainer meshConeContainer = cone.toJavaFXMesh(); // convert to MeshContainer

    // create one tip display object for each axis
    private static final MeshView coneViewX = meshConeContainer.getAsMeshViews().get(0);
    private static final MeshView coneViewY = meshConeContainer.getAsMeshViews().get(0);
    private static final MeshView coneViewZ = meshConeContainer.getAsMeshViews().get(0);

    private static CSG line = new Cylinder(0.5,0.5,8000,3).toCSG();
    private static MeshContainer meshLineContainer = line.toJavaFXMesh();

    private static final MeshView lineViewX = meshLineContainer.getAsMeshViews().get(0);
    private static final MeshView lineViewY = meshLineContainer.getAsMeshViews().get(0);
    private static final MeshView lineViewZ = meshLineContainer.getAsMeshViews().get(0);

    // colors for each axis
    private static final PhongMaterial mX = new PhongMaterial(Color.RED);
    private static final PhongMaterial mY = new PhongMaterial(Color.LIMEGREEN);
    private static final PhongMaterial mZ = new PhongMaterial(Color.DODGERBLUE);

    public static Group createGCS(){
        Group ucs = new Group(); // the user coordinate system node

        cylViewX.setCullFace(CullFace.NONE);
        cylViewY.setCullFace(CullFace.NONE);
        cylViewZ.setCullFace(CullFace.NONE);
        coneViewX.setCullFace(CullFace.NONE);
        coneViewY.setCullFace(CullFace.NONE);
        coneViewZ.setCullFace(CullFace.NONE);

        // set the material for each object
        cylViewX.setMaterial(mX);
        cylViewY.setMaterial(mY);
        cylViewZ.setMaterial(mZ);
        coneViewX.setMaterial(mX);
        coneViewY.setMaterial(mY);
        coneViewZ.setMaterial(mZ);

        // move and rotate the objects from the origin to create the UCS figure
        cylViewX.setRotationAxis(Rotate.Y_AXIS);
        cylViewX.rotateProperty().set(90);
        cylViewX.translateXProperty().set(15);
        cylViewX.translateZProperty().set(-15);

        cylViewY.setRotationAxis(Rotate.X_AXIS);
        cylViewY.rotateProperty().set(90);
        cylViewY.translateYProperty().set(15);
        cylViewY.translateZProperty().set(-15);

        coneViewX.translateXProperty().set(29);
        coneViewX.setRotationAxis(Rotate.Y_AXIS);
        coneViewX.rotateProperty().set(90);
        coneViewX.translateZProperty().set(-5);

        coneViewY.translateYProperty().set(29);
        coneViewY.setRotationAxis(Rotate.X_AXIS);
        coneViewY.rotateProperty().set(-90);
        coneViewY.translateZProperty().set(-5);

        coneViewZ.translateZProperty().set(29);

        // add the objects to the node and then return it
        ucs.getChildren().add(cylViewX);
        ucs.getChildren().add(cylViewY);
        ucs.getChildren().add(cylViewZ);
        ucs.getChildren().add(coneViewX);
        ucs.getChildren().add(coneViewY);
        ucs.getChildren().add(coneViewZ);
        return ucs;
    }

    public static Group createCCS(){
        Group ccs = new Group();

        lineViewX.setCullFace(CullFace.NONE);
        lineViewY.setCullFace(CullFace.NONE);
        lineViewZ.setCullFace(CullFace.NONE);

        lineViewX.setMaterial(mX);
        lineViewY.setMaterial(mY);
        lineViewZ.setMaterial(mZ);

        lineViewX.setRotationAxis(Rotate.Y_AXIS);
        lineViewX.rotateProperty().set(90);
        lineViewX.translateZProperty().set(-4000);

        lineViewY.setRotationAxis(Rotate.X_AXIS);
        lineViewY.rotateProperty().set(90);
        lineViewY.translateZProperty().set(-4000);

        lineViewZ.translateZProperty().set(-4000);

        ccs.getChildren().addAll(lineViewX,lineViewY,lineViewZ);
        return ccs;
    }
}