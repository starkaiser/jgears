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

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.csg.jcsg.Cylinder;
import main.java.jgears.csg.jcsg.MeshContainer;
import main.java.jgears.csg.jcsg.Polyhedron;
import main.java.jgears.csg.vvecmath.Transform;
import main.java.jgears.csg.vvecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class Bevel {
    // RECEIVE ALL ANGLES IN RADIANS!!!
    final private CSG gearCSG;
    private MeshView gearMeshView;
    final private Color color = Color.DEEPSKYBLUE; //Color.rgb(0,150,201); - the blue color of the program
    private final double play = 0.05;

    // constructor for testing
    public Bevel() {
        this.gearCSG = bevel_gear_pair(1, 30, 16, Math.toRadians(80), 5, Math.toRadians(20), 0, true);
        generateMeshView();
    }

    // constructor for a single gear
    public Bevel(double module, double z, double delta, double b, double alpha_t, double beta_m, boolean doubleHelical) {
        if(!doubleHelical) {
            this.gearCSG = bevel_gear(module, z, delta, b, alpha_t, beta_m);
            generateMeshView();
        }
	else {
            this.gearCSG = bevel_herringbone_gear(module, z, delta, b, alpha_t, beta_m);
	    generateMeshView();
	}
    }

    // constructor for two gears
    public Bevel(double module, double z1, double z2, double axisAng, double b, double alpha_t, double beta_m,
                 boolean doubleHelical, boolean assemblyType) {
        if(!doubleHelical) {
            this.gearCSG = bevel_gear_pair(module, z2, z1, axisAng, b, alpha_t, beta_m, assemblyType);
            generateMeshView();
        }
	else {
            this.gearCSG = bevel_herringbone_gear_pair(module, z2, z1, axisAng, b, alpha_t, beta_m, assemblyType);
	    generateMeshView();
	}
    }

    private double sphere_ev(double theta0, double theta) {
	return 1.0/Math.sin(theta0)*Math.acos(Math.cos(theta)/Math.cos(theta0))-Math.acos(Math.tan(theta0)/Math.tan(theta));
    }
    private Vector3d sphere_to_cartesian(double x, double y, double z) {
        Vector3d vector = Vector3d.xyz(x*Math.sin(y)*Math.cos(z),
                                       x*Math.sin(y)*Math.sin(z),
                                       x*Math.cos(y));
        return vector;
    }

    // single normal gear
    private CSG bevel_gear(double module, double teeth_number, double partial_cone_angle, double tooth_width,double pressure_angle, 
			 double helix_angle) {

        double d_outside = module * teeth_number;			
        double r_outside = d_outside / 2.0;	
        double rg_outside = r_outside/Math.sin(partial_cone_angle);	
        double rg_inside = rg_outside - tooth_width;		
        //double r_inside = r_outside*rg_inside/rg_outside;
        double alpha_spur = Math.atan(Math.tan(pressure_angle)/Math.cos(helix_angle));
        double delta_b = Math.asin(Math.cos(alpha_spur)*Math.sin(partial_cone_angle));	
        double da_outside = (module <1.0) ? d_outside + (module * 2.2) * Math.cos(partial_cone_angle): 
					    d_outside + module * 2.0 * Math.cos(partial_cone_angle);
        double ra_outside = da_outside/ 2.0;
        double delta_a = Math.asin(ra_outside/rg_outside);
        double c = module / 6.0;						
        double df_outside = d_outside - (module +c) * 2.0 * Math.cos(partial_cone_angle);
        double rf_outside = df_outside / 2.0;
        double delta_f = Math.asin(rf_outside/rg_outside);
        double rkf = rg_outside*Math.sin(delta_f);	
        double height_f = rg_outside*Math.cos(delta_f);	


        double height_k = (rg_outside-tooth_width)/Math.cos(partial_cone_angle);
        double rk = (rg_outside-tooth_width)/Math.sin(partial_cone_angle);	
        double rfk = rk*height_k*Math.tan(delta_f)/(rk+height_k*Math.tan(delta_f));

        double height_fk = rk*height_k/(height_k*Math.tan(delta_f)+rk);

        double phi_r = sphere_ev(delta_b, partial_cone_angle);


        double gamma_g = 2.0*Math.atan(tooth_width*Math.tan(helix_angle)/(2.0*rg_outside-tooth_width));
        double gamma = 2.0*Math.asin(rg_outside/r_outside*Math.sin(gamma_g/2.0));

        double step = (delta_a - delta_b)/16.0;
        double tau = 360.0/teeth_number;	
        double start = Math.max(delta_b, delta_f);

        double mirrpoint = Math.toRadians((180.0*(1.0- play))/teeth_number+2.0*Math.toDegrees(phi_r));

        CSG gear;

        List<List<Integer>> faces = new ArrayList<>();
        List<Integer> face1 = new ArrayList<>();
        face1.add(0);
        face1.add(1);
        face1.add(2);
        List<Integer> face2 = new ArrayList<>();
        face2.add(0);
        face2.add(2);
        face2.add(3);
        List<Integer> face3 = new ArrayList<>();
        face3.add(0);
        face3.add(4);
        face3.add(1);
        List<Integer> face4 = new ArrayList<>();
        face4.add(1);
        face4.add(4);
        face4.add(5);
        List<Integer> face5 = new ArrayList<>();
        face5.add(1);
        face5.add(5);
        face5.add(2);
        List<Integer> face6 = new ArrayList<>();
        face6.add(2);
        face6.add(5);
        face6.add(6);
        List<Integer> face7 = new ArrayList<>();
        face7.add(2);
        face7.add(6);
        face7.add(3);
        List<Integer> face8 = new ArrayList<>();
        face8.add(3);
        face8.add(6);
        face8.add(7);
        List<Integer> face9 = new ArrayList<>();
        face9.add(0);
        face9.add(3);
        face9.add(7);
        List<Integer> face10 = new ArrayList<>();
        face10.add(0);
        face10.add(7);
        face10.add(4);
        List<Integer> face11 = new ArrayList<>();
        face11.add(4);
        face11.add(6);
        face11.add(5);
        List<Integer> face12 = new ArrayList<>();
        face12.add(4);
        face12.add(7);
        face12.add(6);

        faces.add(face1);
        faces.add(face2);
        faces.add(face3);
        faces.add(face4);
        faces.add(face5);
        faces.add(face6);
        faces.add(face7);
        faces.add(face8);
        faces.add(face9);
        faces.add(face10);
        faces.add(face11);
        faces.add(face12);

        double delta = start;
        //double impUnion = 0.001;
        double flankpoint_under = sphere_ev(delta_b, delta);
        double flankpoint_over = sphere_ev(delta_b, delta+step);
        List<Vector3d> listVect = new ArrayList<>();
        listVect.add(sphere_to_cartesian(rg_outside, delta, flankpoint_under));
        listVect.add(sphere_to_cartesian(rg_inside, delta, flankpoint_under+gamma));
        listVect.add(sphere_to_cartesian(rg_inside, delta, mirrpoint-flankpoint_under+gamma));
        listVect.add(sphere_to_cartesian(rg_outside, delta, mirrpoint-flankpoint_under));
        listVect.add(sphere_to_cartesian(rg_outside, delta+step, flankpoint_over));
        listVect.add(sphere_to_cartesian(rg_inside, delta+step, flankpoint_over+gamma));
        listVect.add(sphere_to_cartesian(rg_inside, delta+step, mirrpoint-flankpoint_over+gamma));
        listVect.add(sphere_to_cartesian(rg_outside, delta+step, mirrpoint-flankpoint_over));
        CSG tooth = new Polyhedron(listVect,faces).toCSG();

        if(delta_b > delta_f) {
            flankpoint_under = 1*mirrpoint;
            flankpoint_over = sphere_ev(delta_f, start);
            listVect.clear();
            listVect.add(sphere_to_cartesian(rg_outside, start*1.001, flankpoint_under));
            listVect.add(sphere_to_cartesian(rg_inside, start*1.001, flankpoint_under+gamma));
            listVect.add(sphere_to_cartesian(rg_inside, start*1.001, mirrpoint-flankpoint_under+gamma));
            listVect.add(sphere_to_cartesian(rg_outside, start*1.001, mirrpoint-flankpoint_under));
            listVect.add(sphere_to_cartesian(rg_outside, delta_f, flankpoint_under));
            listVect.add(sphere_to_cartesian(rg_inside, delta_f, flankpoint_under+gamma));
            listVect.add(sphere_to_cartesian(rg_inside, delta_f, mirrpoint-flankpoint_under+gamma));
            listVect.add(sphere_to_cartesian(rg_outside, delta_f, mirrpoint-flankpoint_under));
            tooth = tooth.dumbUnion(new Polyhedron(listVect,faces).toCSG());
        }

        for(delta=start+step; delta <= delta_a-step; delta+=step) {
            flankpoint_under = sphere_ev(delta_b, delta);
            flankpoint_over = sphere_ev(delta_b, delta+step);
            listVect.clear();
            listVect.add(sphere_to_cartesian(rg_outside, delta, flankpoint_under));
            listVect.add(sphere_to_cartesian(rg_inside, delta, flankpoint_under+gamma));
            listVect.add(sphere_to_cartesian(rg_inside, delta, mirrpoint-flankpoint_under+gamma));
            listVect.add(sphere_to_cartesian(rg_outside, delta, mirrpoint-flankpoint_under));
            listVect.add(sphere_to_cartesian(rg_outside, delta+step, flankpoint_over));
            listVect.add(sphere_to_cartesian(rg_inside, delta+step, flankpoint_over+gamma));
            listVect.add(sphere_to_cartesian(rg_inside, delta+step, mirrpoint-flankpoint_over+gamma));
            listVect.add(sphere_to_cartesian(rg_outside, delta+step, mirrpoint-flankpoint_over));
            tooth = tooth.dumbUnion(new Polyhedron(listVect,faces).toCSG());

            if(delta_b > delta_f) {
                flankpoint_under = 1*mirrpoint;
                flankpoint_over = sphere_ev(delta_f, start);
                listVect.clear();
                listVect.add(sphere_to_cartesian(rg_outside, start*1.001, flankpoint_under));
                listVect.add(sphere_to_cartesian(rg_inside, start*1.001, flankpoint_under+gamma));
                listVect.add(sphere_to_cartesian(rg_inside, start*1.001, mirrpoint-flankpoint_under+gamma));
                listVect.add(sphere_to_cartesian(rg_outside, start*1.001, mirrpoint-flankpoint_under));
                listVect.add(sphere_to_cartesian(rg_outside, delta_f, flankpoint_under));
                listVect.add(sphere_to_cartesian(rg_inside, delta_f, flankpoint_under+gamma));
                listVect.add(sphere_to_cartesian(rg_inside, delta_f, mirrpoint-flankpoint_under+gamma));
                listVect.add(sphere_to_cartesian(rg_outside, delta_f, mirrpoint-flankpoint_under));
                tooth = tooth.dumbUnion(new Polyhedron(listVect,faces).toCSG());
            }
            //System.out.println(Math.toDegrees(delta));
        }
        gear = tooth;

        for(double rot = 0; rot<=360; rot+=tau) {
            gear = gear.dumbUnion(tooth.transformed(Transform.unity().rotZ(-rot)));
        }

        CSG base = new Cylinder(rkf, rfk, height_f-height_fk, 30).
		       toCSG().transformed(Transform.unity().rotY(180)).
                               transformed(Transform.unity().translateZ(height_f));

        CSG finalGear = base.dumbUnion(gear).transformed(Transform.unity().rotY(180)).
                                             transformed(Transform.unity().translateZ(height_f)).
                                             transformed(Transform.unity().rotZ(-(Math.toDegrees(phi_r) + 
										  90*(1-play)/teeth_number)));
        return finalGear;
    }
    // drive normal gears
    private CSG bevel_gear_pair(double module, double gear_teeth, double pinion_teeth, double axis_angle,
                             double tooth_width, double pressure_angle, double helix_angle, boolean together_built) {
        double r_gear = module*gear_teeth/2.0;
        double delta_gear = Math.atan(Math.sin(axis_angle)/(pinion_teeth/gear_teeth+Math.cos(axis_angle)));
        double delta_pinion = Math.atan(Math.sin(axis_angle)/(gear_teeth/pinion_teeth+Math.cos(axis_angle)));
        double rg = r_gear/Math.sin(delta_gear);
        double c = module / 6.0;
        double df_pinion = Math.PI*rg*Math.toDegrees(delta_pinion)/90.0 - 2.0 * (module + c);
        double rf_pinion = df_pinion / 2.0;
        double delta_f_pinion = Math.toRadians(rf_pinion/(Math.PI*rg) * 180.0);	
        double rkf_pinion = rg*Math.sin(delta_f_pinion);
        double height_f_pinion= rg*Math.cos(delta_f_pinion);

        double df_gear = Math.PI*rg*Math.toDegrees(delta_gear)/90.0 - 2.0 * (module + c);
        double rf_gear = df_gear / 2.0;
        double delta_f_gear = Math.toRadians(rf_gear/(Math.PI*rg) * 180.0);
        double rkf_gear = rg*Math.sin(delta_f_gear);
        double  height_f_gear = rg*Math.cos(delta_f_gear);	

        double twist;
        if(pinion_teeth % 2 == 0)
            twist = 180.0*(1.0-play)/gear_teeth;
        else
            twist = 0;

        CSG gearDrive = bevel_gear(module, gear_teeth, delta_gear, tooth_width, pressure_angle, helix_angle).
			transformed(Transform.unity().rotZ(-twist));
        if(together_built)
            gearDrive = gearDrive.dumbUnion(bevel_gear(module, pinion_teeth, delta_pinion, tooth_width, pressure_angle, -helix_angle).
                    transformed(Transform.unity().rotY(Math.toDegrees(-axis_angle))).
                    transformed(Transform.unity().translate(-height_f_pinion*Math.cos(Math.toRadians(90-Math.toDegrees(axis_angle))),
                                                            0,
                                                            height_f_gear-height_f_pinion*Math.sin(Math.toRadians(90-Math.toDegrees(axis_angle))))));
        else
            gearDrive = gearDrive.dumbUnion(bevel_gear(module, pinion_teeth, delta_pinion, tooth_width, pressure_angle, -helix_angle).
                    transformed(Transform.unity().translate(rkf_pinion*2+module+rkf_gear,0,0)));
        return gearDrive;
    }
    
    // single double helical gear
    private CSG bevel_herringbone_gear(double module, double teeth_number, double partial_cone_angle, double tooth_width,
				       double pressure_angle, double helix_angle){	
	tooth_width = tooth_width / 2.0;
	
	double d_outside = module * teeth_number;
	double r_outside = d_outside / 2.0;
	double rg_outside = r_outside/Math.sin(partial_cone_angle);
	double c = module / 6.0;	
	double df_outside = d_outside - (module +c) * 2.0 * Math.cos(partial_cone_angle);
	double rf_outside = df_outside / 2.0;
	double delta_f = Math.asin(rf_outside/rg_outside);
	double height_f = rg_outside*Math.cos(delta_f);

	double gamma_g = 2.0*Math.toDegrees(Math.atan(tooth_width*Math.tan(helix_angle)/(2.0*rg_outside-tooth_width)));
	double gamma = 2.0*Math.toDegrees(Math.asin(rg_outside/r_outside*Math.sin(Math.toRadians(gamma_g)/2.0)));
	
	double height_k = (rg_outside-tooth_width)/Math.cos(partial_cone_angle);
	double rk = (rg_outside-tooth_width)/Math.sin(partial_cone_angle);
	//double rfk = rk*height_k*Math.tan(delta_f)/(rk+height_k*Math.tan(delta_f));

	double height_fk = rk*height_k/(height_k*Math.tan(delta_f)+rk);
	
	double module_inside = module*(1.0-tooth_width/rg_outside);
	
	CSG firstHalf = bevel_gear(module, teeth_number, partial_cone_angle, tooth_width, pressure_angle, helix_angle);
	CSG secondHalf = bevel_gear(module_inside, teeth_number, partial_cone_angle, tooth_width, 
				  pressure_angle, -helix_angle).transformed(Transform.unity().rotZ(gamma)).
								transformed(Transform.unity().translateZ(height_f-height_fk));
	return firstHalf.dumbUnion(secondHalf);
    }
    // drive double helical gears
    private CSG bevel_herringbone_gear_pair(double module, double gear_teeth, double pinion_teeth, double axis_angle,
					    double tooth_width, double pressure_angle, double helix_angle, 
					    boolean together_built){
	double r_gear = module*gear_teeth/2.0;
        double delta_gear = Math.atan(Math.sin(axis_angle)/(pinion_teeth/gear_teeth+Math.cos(axis_angle)));
        double delta_pinion = Math.atan(Math.sin(axis_angle)/(gear_teeth/pinion_teeth+Math.cos(axis_angle)));
        double rg = r_gear/Math.sin(delta_gear);
        double c = module / 6.0;	
        double df_pinion = Math.PI*rg*Math.toDegrees(delta_pinion)/90.0 - 2.0 * (module + c);
        double rf_pinion = df_pinion / 2.0;
        double delta_f_pinion = Math.toRadians(rf_pinion/(Math.PI*rg) * 180.0);	
        double rkf_pinion = rg*Math.sin(delta_f_pinion);
        double height_f_pinion = rg*Math.cos(delta_f_pinion);

        double df_gear = Math.PI*rg*Math.toDegrees(delta_gear)/90.0 - 2.0 * (module + c);
        double rf_gear = df_gear / 2.0;
        double delta_f_gear = Math.toRadians(rf_gear/(Math.PI*rg) * 180.0);
        double rkf_gear = rg*Math.sin(delta_f_gear);
        double  height_f_gear = rg*Math.cos(delta_f_gear);	

        //System.out.println(Math.toDegrees(delta_rad+delta_ritzel));

        double twist;
        if(pinion_teeth % 2 == 0)
            twist = 180.0*(1.0-play)/gear_teeth;
        else
            twist = 0;
	
	CSG gearDrive = bevel_herringbone_gear(module, gear_teeth, delta_gear, tooth_width, pressure_angle, helix_angle).
			transformed(Transform.unity().rotZ(-twist));
	if(together_built)
            gearDrive = gearDrive.dumbUnion(bevel_herringbone_gear(module, pinion_teeth, delta_pinion, tooth_width, pressure_angle, -helix_angle).
                    transformed(Transform.unity().rotY(Math.toDegrees(-axis_angle))).
                    transformed(Transform.unity().translate(-height_f_pinion*Math.cos(Math.toRadians(90-Math.toDegrees(axis_angle))),
                                                            0,
                                                            height_f_gear-height_f_pinion*Math.sin(Math.toRadians(90-Math.toDegrees(axis_angle))))));
        else
            gearDrive = gearDrive.dumbUnion(bevel_herringbone_gear(module, pinion_teeth, delta_pinion, tooth_width, pressure_angle, -helix_angle).
                    transformed(Transform.unity().translate(rkf_pinion*2+module+rkf_gear,0,0)));
        return gearDrive;
    }

    private void generateMeshView() {
        MeshContainer meshContainer = gearCSG.toJavaFXMesh();
        gearMeshView = meshContainer.getAsMeshViews().get(0);
        gearMeshView.setMaterial(new PhongMaterial(color));
        gearMeshView.setCullFace(CullFace.BACK);
    }
    public CSG getGearCSG() {
        return gearCSG;
    }
    public MeshView getGearMeshView() {
        return gearMeshView;
    }
}