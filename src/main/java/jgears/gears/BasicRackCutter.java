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
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.csg.jcsg.Extrude;
import main.java.jgears.csg.vvecmath.Transform;
import main.java.jgears.csg.vvecmath.Vector3d;

public class BasicRackCutter {
    // RECEIVE ALL ANGLES IN RADIANS!!!
    private final double m; // module
    private final double z; // number of teeth
    private final double alpha; // pressure angle
    private final double b; // thickness
    private final double xDir; // direction of the extrusion vector

    private double h_a_star = 1.00; // addendum coefficient
    private double c_star = 0.25; // clearance coefficient
    private double r_star = 0.35; // root fillet coefficient

    public BasicRackCutter(double m, double z, double alpha, double b, double xDir){
        this.m = m;
        this.z = z;
        this.alpha = alpha;
        this.b = b;
        this.xDir = xDir;
    }

    public BasicRackCutter(double m, double z, double alpha, double b, double xDir,
                           double h_a_star, double c_star, double r_star) {
        this.m = m;
        this.z = z;
        this.alpha = alpha;
        this.b = b;
        this.xDir = xDir;
        this.h_a_star = h_a_star;
        this.c_star = c_star;
        this.r_star = r_star;
    }

    public CSG toCSG(){
        CSG rack = generateTooth();
        CSG tooth = generateTooth();
        for(int i = 1; i < z; i++) {
            rack = rack.union(tooth.transformed(Transform.unity().translateX(i * m * Math.PI))); // i*p
        }
        return rack;
    }

    private CSG generateTooth() {
        double p = m * Math.PI; // basic rack pitch
        double h_a = (h_a_star + c_star) * m; // addendum
        double h_f = (h_a_star + c_star) * m; // dedendum
        double h = h_a + h_f + 5.; // entire tooth depth
        double xa = h_a * Math.tan(alpha);
        double xd = h_f * Math.tan(alpha);

        double c = Math.sin(Math.toRadians(10))*r_star;
        double op = (1./Math.cos(Math.toRadians(35)))*r_star-c*Math.sqrt(2.); // distance from center of the circle to the corner point

        // TODO improve rack tooth fillet
        double circle_x = op * Math.sin(Math.toRadians(35.)); // shift on x axis for the circle center
        double ox1 = 0.25*p-xa- circle_x; // right circle x coordinate
        double oy = h_a - r_star; // y coordinate of the circles
        double ox2 = -0.25*p+xa+ circle_x; // left circle x coordinate

        double s1 = Math.sin(Math.toRadians(20.));
        double s2 = Math.sin(Math.toRadians(20. + 70./3.));
        double s3 = Math.sin(Math.toRadians(20. + 2.* 70./3.));

        double c1 = Math.cos(Math.toRadians(20.));
        double c2 = Math.cos(Math.toRadians(20. + 70./3.));
        double c3 = Math.cos(Math.toRadians(20. + 2.* 70./3.));

        return Extrude.points(Vector3d.xyz(xDir, 0, b),
                Vector3d.xy(-0.5*p, -h_f),     //1
                Vector3d.xy(-0.5*p, h_a-h),    //2
                Vector3d.xy(0.5*p, h_a-h),     //3
                Vector3d.xy(0.5*p, -h_f),      //4
                Vector3d.xy(0.25*p+xd, -h_f),  //5

                // right fillet
                Vector3d.xy(ox1+c1*r_star,oy+s1*r_star),
                Vector3d.xy(ox1+c2*r_star,oy+s2*r_star),
                Vector3d.xy(ox1+c3*r_star,oy+s3*r_star),
                Vector3d.xy(ox1,oy+r_star),

                // left fillet
                Vector3d.xy(ox2,oy+r_star),
                Vector3d.xy(ox2-c3*r_star,oy+s3*r_star),
                Vector3d.xy(ox2-c2*r_star,oy+s2*r_star),
                Vector3d.xy(ox2-c1*r_star,oy+s1*r_star),

                Vector3d.xy(-0.25*p-xd, -h_f));
    }
}
