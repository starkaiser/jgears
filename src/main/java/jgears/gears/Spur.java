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
package main.java.jgears.gears;
/**
 *
 * @author Sorin Cătălin Păștiță
 */
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.csg.jcsg.Cube;
import main.java.jgears.csg.jcsg.Cylinder;
import main.java.jgears.csg.jcsg.MeshContainer;
import main.java.jgears.csg.vvecmath.Transform;


public class Spur {
    // RECEIVE ALL ANGLES IN RADIANS!!!
    private double a = 0.0; // distance between axes, used to shift one gear from the center of the coordinate system
    private final double m; // module
    private final double z; // number of teeth
    private final double alpha; // pressure angle
    private final double b; // face width
    private final double helixAngle;
    private final boolean helixDir; // true for left
    private final boolean internal;
    private boolean doubleHelical = false;
    private final double mid; // middle distance gap for double helical gears
    private boolean isRack = false;

    private double d; // pitch diameter
    private double d_b; // base diameter
    private double d_a; // outside diameter
    private double d_f; // root diameter

    private final double h_a_star = 1.00; // addendum coefficient
    private final double c_star = 0.25; // clearance coefficient
    private final double r_star = 0.35; // root fillet coefficient

    private CSG gearCSG;
    private MeshView gearMeshView;
    private final Color color = Color.DEEPSKYBLUE;//Color.GHOSTWHITE;

    public Spur(double m, double alpha, double z, double b, boolean internal, boolean doubleHelical, double mid,
                double helixAngle, boolean helixDir) {
        this.m = m;
        this.z = z;
        this.alpha = alpha;
        this.b = b;
        this.internal = internal;
        this.doubleHelical = doubleHelical;
        this.mid = mid;
        this.helixAngle = helixAngle;
        this.helixDir = helixDir;
        calculateParam();
        generateCSG();
        generateMeshView();
    }

    public Spur(double a, boolean rack, double m, double alpha, double z, double b, boolean internal,
                boolean doubleHelical, double mid, double helixAngle, boolean helixDir) {
        this.a = a;
        this.isRack = rack;
        this.m = m;
        this.z = z;
        this.alpha = alpha;
        this.b = b;
        this.internal = internal;
        this.doubleHelical = doubleHelical;
        this.mid = mid;
        this.helixAngle = helixAngle;
        this.helixDir = helixDir;
        calculateParam();
        generateCSG();
        generateMeshView();
    }


    private void generateCSG() {
        double xDir = Math.tan(helixAngle) * b;
        if (!doubleHelical) {
            // generate the basic rack cutter
            CSG rackCutter;
            if (helixDir)
                rackCutter = new BasicRackCutter(m, z + 2, alpha, b, -xDir).toCSG();
            else
                rackCutter = new BasicRackCutter(m, z + 2, alpha, b, xDir).toCSG();

            if (isRack == false) {
                // if it is a gear, rotate the rack around a cylinder and make sequential differences
                CSG gear = new Cylinder(d_a / 2.0, b, (int)z).toCSG();
                for (int i = 0; i <= 380; i += 10) { // 9, 10 - for high performance, 2 - high resolution
                    gear = gear.difference(rackCutter.
                            transformed(Transform.unity().translate(-i / 360.0 * d * Math.PI, -d / 2.0, 0.0)).
                            transformed(Transform.unity().rotZ(-i)));
                }
                this.gearCSG = gear.transformed(Transform.unity().translateX(this.a)).
				    transformed(Transform.unity().translateZ(-b / 2.));
            }
            else {
                // if it is a rack create a cube and make a difference with the rack cutter
                double h_a = (h_a_star + c_star) * m; // addendum
                double h_f = (h_a_star + c_star) * m; // dedendum
                double h = h_a + h_f + 5.; // entire tooth depth
                CSG rack = new Cube(z*m * Math.PI,h, b).toCSG().
                        transformed(Transform.unity().translateX((z*m * Math.PI)/2.0-(m*Math.PI)/2.0)).
                        transformed(Transform.unity().translateZ(b/2.0)).
                        transformed(Transform.unity().translateY(h/2.0-h_a));
                rack = rack.difference(rackCutter);
                this.gearCSG = rack.transformed(Transform.unity().rotZ(90)).
                        transformed(Transform.unity().translateZ(-b/2.0)).
                        transformed(Transform.unity().translateX(5)).
                        transformed(Transform.unity().translateY(2*m*Math.PI));//.transformed(Transform.unity().translateX(this.a));
            }
        }
	// double helical gears generation
        else { // TODO improve
            // generate two inclined rack cutters and unite them, then perform the cutting
            CSG rackCutter;
            if (helixDir)
                rackCutter = new BasicRackCutter(m, z + 2, alpha, b/2., -xDir).toCSG().
                        union(new BasicRackCutter(m, z + 2, alpha, b/2., xDir).toCSG().
                                transformed(Transform.unity().translate(-xDir, 0, b/2.)));
            else
                rackCutter = new BasicRackCutter(m, z + 2, alpha, b/2., xDir).toCSG().
                        union(new BasicRackCutter(m, z + 2, alpha, b/2., -xDir).toCSG().
                                transformed(Transform.unity().translate(xDir, 0, b/2.)));

            if (isRack == false) {
                CSG gear;
                if(mid > 0) {
                    CSG gearPart = new Cylinder(d_a / 2.0, (b - mid) / 2.0, 20).toCSG().
                            transformed(Transform.unity().translateZ((b - mid) / 2.0 + mid));
                    CSG midPart = new Cylinder((d_f / 2.0) - c_star * m, mid, 20).toCSG().
                            transformed(Transform.unity().translateZ((b - mid) / 2.0));
                    gear = new Cylinder(d_a / 2.0, (b - mid) / 2.0, 20).toCSG().union(midPart, gearPart);
                }
                else
                   gear = new Cylinder(d_a / 2.0, b, 20).toCSG();
                for (int i = 0; i <= 380; i += 20) { // 9, 10 - for high performance, 2 - high resolution
                    gear = gear.difference(rackCutter.
                            transformed(Transform.unity().translate(-i / 360.0 * d * Math.PI, -d / 2.0, 0.0)).
                            transformed(Transform.unity().rotZ(-i)));
                }
                this.gearCSG = gear.transformed(Transform.unity().translateX(this.a)).
                        transformed(Transform.unity().translateZ(-b / 2.));
            } else {
                double h_a = (h_a_star + c_star) * m; // addendum
                double h_f = (h_a_star + c_star) * m; // dedendum
                double h = h_a + h_f + 5.; // entire tooth depth
                CSG rack = new Cube(z*m * Math.PI,h, b).toCSG().
                        transformed(Transform.unity().translateX((z*m * Math.PI)/2.0-(m*Math.PI)/2.0)).
                        transformed(Transform.unity().translateZ(b/2.0)).
                        transformed(Transform.unity().translateY(h/2.0-h_a));
                rack = rack.difference(rackCutter);
                this.gearCSG = rack.transformed(Transform.unity().rotZ(90)).
                        transformed(Transform.unity().translateZ(-b/2.0)).
                        transformed(Transform.unity().translateX(5)).
                        transformed(Transform.unity().translateY(2*m*Math.PI));//.transformed(Transform.unity().translateX(this.a));
            }
        }
    }

    private void generateMeshView() {
        MeshContainer meshContainer = gearCSG.toJavaFXMesh();
        gearMeshView = meshContainer.getAsMeshViews().get(0);
        gearMeshView.setMaterial(new PhongMaterial(color));
        gearMeshView.setCullFace(CullFace.BACK);
    }

    private void calculateParam() {
        d = m * z;
        d_b = d * Math.cos(alpha);
        d_a = d + 2 * m;
        d_f = d - 2.5 * m;
    }

    public MeshView getGearMeshView() {
        return gearMeshView;
    }
    public CSG getCSG() {
        return gearCSG;
    }
}