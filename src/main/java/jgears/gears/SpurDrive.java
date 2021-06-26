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
import main.java.jgears.gui.ResultsUtils;

public class SpurDrive {
    // RECEIVE ALL ANGLES IN RADIANS!!!
    private boolean internal = false;
    private int z1, z2; // number of teeth
    private double i; // gear ratio
    private double i_in; // desired gear ratio
    private double alpha; // pressure angle
    private double beta; // helix angle
    private double m; // module
    private double a_star = 1.00; // addendum coefficient
    private double c_star = 0.25; // clearance coefficient
    private double r_star = 0.35; // root fillet coefficient
    private double b1, b2; // facewidth
    private double x1, x2 = 0.0; // profile shit coefficient/unit correction

    private boolean helixDir; // true for left
    private boolean doubleHelical;
    private boolean rack;

    private double a; // distance between axes, used to shift one gear from the center of the coordinate system
    private double m_t; // tangential module
    private double p; // normal pitch
    private double p_t; // axial pitch
    private double p_tB; // base pitch
    private double beta_b; // helix angle on the basic cylinder
    private double alpha_t; // axial pressure angel
    private double alpha_w; // rolling/working pressure angle ?
    private double alpha_tw; // axial rolling.working pressure angle
    private double d1, d2; // pitch diameter
    private double d_b1, d_b2; // circle base diameter
    private double d_a1, d_a2; // outside diameter
    private double d_f1, d_f2; // root diameter
    private double a_w; // real center distance
    private double delta_y; // feed factor/addendum lowering
    private double d_w1, d_w2; // work pitch diameter
    private double z_v1, z_v2; // virtual number of teeth
    private double d_n1, d_n2; // virtual pitch diameter
    private double d_bn1, d_bn2; // virtual base circle diameter
    private double d_an1, d_an2; // virtual outside diameter
    private double s1, s2; // tooth thickness
    private double s_c1, s_c2; // tooth width on the chord
    private double h_c1, h_c2; // addendum height above the chord
    private double s_a1, s_a2; // unit addendum width
    private double x_z1, x_z2; // minimum correction without tapering
    private double x_p1, x_p2; // minimum correction without undercut
    private double x_d1, x_d2; // minimum correction with the allowable undercut
    private double x_sum;
    private double W1, W2; // chordal dimension
    private double M1, M2; // size across rollers/balls
    private double d_M = 1.75; // wire/ball diameter
    private double z_W1, z_W2;
    private double u;
    private double mid1 = 0, mid2 = 0; // middle distance for double helical

    private double b_w; // operating width of gears
    private double b_r1, b_r2; // facewidth ratio
    private double ep, ep_alpha, ep_beta; // factor of mesh duration
    private double alpha_a1, alpha_a2;

    private double P1, P2; // power
    private double n1, n2; // speed
    private double eta; // efficiency
    private double minSafety; // minimum factor of safety
    private double sigma_Ab1, sigma_Ab2;
    private double M_k1, M_k2; // torque
    private double v; // circumferential speed
    private double F_t, F_r, F_a, F_n; // forces

    private double F_all1, F_all2; // allowable load
    private double S_1, S_2; // safety factor for each gear

    private String res1="", res2="", resStrength="", resReportGeom="", resReportStrength="";

    private Spur gear1, gear2;

    public SpurDrive(int z1, int z2, double alpha, double beta, double m, double a_star, double c_star, double r_star,
                     double b1, double b2, double x1, double x2, boolean helixDir, boolean doubleHelical, double mid1,
                     double mid2, boolean rack) {
        this.z1 = z1;
        this.z2 = z2;
        this.alpha = alpha;
        this.beta = beta;
        this.m = m;
        this.a_star = a_star;
        this.c_star = c_star;
        this.r_star = r_star;
        this.b1 = b1;
        this.b2 = b2;
        this.x1 = x1;
        this.x2 = x2;
        this.helixDir = helixDir;
        this.doubleHelical = doubleHelical;
        this.mid1 = mid1;
        this.mid2 = mid2;
        this.rack = rack;
        calculateParam();
    }

    public SpurDrive(int z1, int z2, double alpha, double beta, double m, double a_star, double c_star, double r_star,
                     double b1, double b2, double x1, double x2, boolean helixDir, boolean doubleHelical, double mid1,
                     double mid2, boolean rack, double P1, double n1, double M_k1, double eta, double minSafety, double sigma_Ab1,
                     double sigma_Ab2) {
        this.z1 = z1;
        this.z2 = z2;
        this.alpha = alpha;
        this.beta = beta;
        this.m = m;
        this.a_star = a_star;
        this.c_star = c_star;
        this.r_star = r_star;
        this.b1 = b1;
        this.b2 = b2;
        this.x1 = x1;
        this.x2 = x2;
        this.helixDir = helixDir;
        this.doubleHelical = doubleHelical;
        this.mid1 = mid1;
        this.mid2 = mid2;
        this.rack = rack;
        this.P1 = P1;
        this.n1 = n1;
        this.M_k1 = M_k1;
        this.eta = eta;
        this.minSafety = minSafety;
        this.sigma_Ab1 = sigma_Ab1;
        this.sigma_Ab2 = sigma_Ab2;
        calculateParam();
        calculateStrengthParam();
    }

    private void calculateParam() {
        i = ((double)z2)/((double)z1);
        if (i >= 0)
            u = i;
        else
            u = 1.0 / i;
        m_t = m / Math.cos(beta); // tangential module
        p = Math.PI * m; // normal pitch
        p_t = Math.PI * m_t; // axial pitch
        if (beta == 0)
            alpha_t = alpha;
        else
            alpha_t = Math.atan(Math.tan(alpha) / Math.cos(beta)); // axial pressure angle

        p_tB = p_t * Math.cos(alpha_t); // base pitch
        beta_b = Math.asin(Math.sin(beta) * Math.cos(alpha)); // helix angle on the basic cylinder
        alpha_t = Math.atan(Math.tan(alpha) / Math.cos(beta)); // axial pressure angle
        x_sum = x1 +x2; // total profile shift

        double inv_alpha = Math.tan(alpha) - alpha;
        double inv_alpha_t = Math.tan(alpha_t) - alpha_t;
        double inv_alpha_w = inv_alpha + 2 * (x1 + x2) * Math.tan(alpha) / (z1 + z2);
        double inv_alpha_tw = inv_alpha_t + 2 * (x1 + x2) * Math.tan(alpha) / (z1 + z2);
        alpha_w = inverseInvolute(inv_alpha_w);
        alpha_tw = inverseInvolute(inv_alpha_tw);

        d1 = z1 * m_t;
        d2 = z2 * m_t;
        d_b1 = d1 * Math.cos(alpha_t);
        d_b2 = d2 * Math.cos(alpha_t);
        a = (d1 + d2) / 2.0;
        a_w = a * (Math.cos(alpha_t) / Math.cos(alpha_tw));
        delta_y = (x1 + x2) - (a_w - a) / m;
        d_a1 = d1 + 2. * m * (a_star + x1 - delta_y);
        d_a2 = d2 + 2. * m * (a_star + x2 - delta_y);
        d_f1 = d1 - 2. * m * (a_star + c_star -x1);
        d_f2 = d2 - 2. * m * (a_star + c_star -x2);

        d_w1 = 2 * a_w / (z2/z1 +1);
        d_w2 = 2*a_w - d_w1;

        z_v1 = z1 / Math.cos(beta) * Math.cos(beta_b) * Math.cos(beta_b);
        z_v2 = z2 / Math.cos(beta) * Math.cos(beta_b) * Math.cos(beta_b);
        d_n1 = z_v1*m;
        d_n2 = z_v2*m;
        d_bn1 = d_n1*Math.cos(alpha);
        d_bn2 = d_n2*Math.cos(alpha);
        d_an1 = d_n1 + d_a1 - d1;
        d_an2 = d_n2 + d_a2 - d2;

        s1 = p/2.0 + 2*m*x1*Math.tan(alpha);
        s2 = p/2.0 + 2*m*x2*Math.tan(alpha);
        s_c1 = s1 * Math.cos(alpha) * Math.cos(alpha);
        s_c2 = s2 * Math.cos(alpha) * Math.cos(alpha);

        h_c1 = (d_a1 - d1 - s_c1*Math.tan(alpha)) / 2.0;
        h_c2 = (d_a2 - d2 - s_c2*Math.tan(alpha)) / 2.0;

        alpha_a1 = Math.acos(d1/d_a1*Math.cos(alpha));
        alpha_a2 = Math.acos(d2/d_a2*Math.cos(alpha));
        s_a1 = (d_a1/m)*(s1/d1 + inv_alpha - (Math.tan(alpha_a1)-alpha_a1));
        s_a2 = (d_a2/m)*(s2/d2 + inv_alpha - (Math.tan(alpha_a2)-alpha_a2));

        b_w = Math.min(b1, b2);
        b_r1 = b_w / d1;
        b_r2 = b_w / d2;

        ep_beta = b_w*Math.sin(beta)/p;
        ep_alpha = (Math.sqrt(d_a1*d_a1 - d_b1*d_b1) + Math.sqrt(d_a2*d_a2 - d_b2*d_b2) - 2*a_w*Math.sin(alpha_tw)) / (2*p_tB);
        ep = ep_alpha + ep_beta;

        double a0_star = a_star + c_star - r_star*(1-Math.sin(alpha));
        x_z1 = a0_star - (1-Math.cos(alpha)/Math.cos(inv_alpha+Math.PI/2*z1)) / 2*Math.cos(beta) * z1;
        x_z2 = a0_star - (1-Math.cos(alpha)/Math.cos(inv_alpha+Math.PI/2*z2)) / 2*Math.cos(beta) * z2;

        x_p1 = a0_star - z_v1/2.0*Math.sin(alpha)*Math.sin(alpha);
        x_p2 = a0_star - z_v2/2.0*Math.sin(alpha)*Math.sin(alpha);
        x_d1 = 5.0/6.0*a0_star - z_v1/2.0*Math.sin(alpha)*Math.sin(alpha);
        x_d2 = 5.0/6.0*a0_star - z_v2/2.0*Math.sin(alpha)*Math.sin(alpha);

        z_W1 = Math.floor(z1/9.0+1);
        z_W2 = Math.floor(z2/9.0+1);
        W1 = ((z_W1 - 0.5)*Math.PI + z1*inv_alpha_t)*m*Math.cos(alpha) + 2*x1*m*Math.sin(alpha);
        W2 = ((z_W2 - 0.5)*Math.PI + z2*inv_alpha_t)*m*Math.cos(alpha) + 2*x2*m*Math.sin(alpha);

        double inv_alpha_D1 = inv_alpha_t + d_M/(m*z1*Math.cos(alpha)) + s1/(m*z1) - Math.PI/z1;
        double inv_alpha_D2 = inv_alpha_t + d_M/(m*z2*Math.cos(alpha)) + s2/(m*z2) - Math.PI/z2;
        double alpha_D1 = inverseInvolute(inv_alpha_D1);
        double alpha_D2 = inverseInvolute(inv_alpha_D2);
        double D_s1 = d_b1/Math.cos(alpha_D1);
        double D_s2 = d_b2/Math.cos(alpha_D2);

        if(z1%2 == 0)
            M1 = D_s1 + d_M;
        else
            M1 = D_s1*Math.cos(Math.toRadians(90.0/z1)) + d_M;

        if(z2%2 == 0)
            M2 = D_s2 + d_M;
        else
            M2 = D_s2*Math.cos(Math.toRadians(90.0/z2)) + d_M;

        res1 = String.format(ResultsUtils.PARALLELGEOM, this.p_tB, this.p, this.p_t, this.a, Math.toDegrees(this.alpha_t),
                                            Math.toDegrees(this.alpha_w), Math.toDegrees(this.alpha_tw), this.d1,
                                            this.d_b1, this.d_f1, this.d_a1, this.W1, this.z_W1, this.M1, this.d_M,
                                            this.s1, this.s_a1, this.s_c1, this.h_c1);
        res2 = String.format(ResultsUtils.PARALLELGEOM, this.p_tB, this.p, this.p_t, this.a, Math.toDegrees(this.alpha_t),
                                            Math.toDegrees(this.alpha_w), Math.toDegrees(this.alpha_tw), this.d2,
                                            this.d_b2, this.d_f2, this.d_a2, this.W2, this.z_W2, this.M2, this.d_M,
                                            this.s2, this.s_a2, this.s_c2, this.h_c2);
	resReportGeom = String.format(ResultsUtils.REPORTPARALLELCOMMON, this.i, 0.0, this.m,Math.toDegrees(this.beta), 
					    Math.toDegrees(this.alpha), this.a_w, this.a, this.x_sum, this.p, this.p_tB, 
					    Math.toDegrees(this.alpha_w), this.ep) +
			String.format(ResultsUtils.REPORTPARALLELINDPARAM, this.z1, this.z2, this.x1,this.x2, this.d1, 
					    this.d2, this.d_a1, this.d_a2, this.d_f1,
					    this.d_f2, this.d_b1, this.d_b2, this.d_w1, this.d_w2, this.b1,
					    this.b2, this.b_r1, this.b_r2, this.a_star, this.a_star, this.c_star,
					    this.c_star, this.r_star, this.r_star, this.s1, this.s2, 0.0, 0.0,
					    this.s_c1, this.s_c2, this.h_c1, this.h_c2, this.W1, this.W2,
					    this.z_W1, this.z_W2, this.M1, this.M2, this.d_M, this.d_M,
					    this.z_v1, this.z_v2, this.d_n1, this.d_n2,
					    this.d_an1, this.d_an2, this.d_bn1, this.d_b2, this.x_z1, this.x_z2,
					    this.x_p1, this.x_p2, this.x_d1, this.x_d2, 0.0, 0.0, this.s_a1,
					    this.s_a2, 0.0,0.0);
    }

    private void calculateStrengthParam() {
        P2 = P1 * eta; // output power
        n2 = n1 / i; // output speed
        M_k2 = M_k1 * i * eta; // output moment
        v = (Math.PI * d1 * n1) / 60000; // circumferential speed
        F_t = (2000 * M_k1) / d_w1; // tangential force
        F_r = F_t * Math.tan(alpha_tw); // radial force
        F_a = F_t * Math.tan(beta); // axial force
        F_n = F_t / (Math.cos(alpha_w) * Math.cos(beta)); // normal force

        F_all1 = Math.PI * 0.065 * sigma_Ab1 * b1 * m;
        S_1 = F_all1 / F_t;
        F_all2 = Math.PI * 0.065 * sigma_Ab2 * b2 * m;
        S_2 = F_all2 / F_t;
	
	String calculationResult = "";
	if ((S_1 >= minSafety) && (S_2 >= minSafety))
	    calculationResult = ResultsUtils.REPORTPOSITIVECHECK;
	else
	    calculationResult = ResultsUtils.REPORTNEGATIVECHECK;

        resStrength = String.format(ResultsUtils.PARALLELSTRENGTH, this.F_t, this.F_r, this.F_a, this.F_n, this.v, 
								   this.S_1, this.F_all1, this.S_2, this.F_all2);
	resReportStrength = String.format(ResultsUtils.REPORTPARALLELSTRENGTHPARAM, "User material", "User material",
					  this.sigma_Ab1, this.sigma_Ab2, this.F_all1, this.F_all2, this.S_1,
					  this.S_2, this.P1, this.P2, this.n1, this.n2, this.M_k1, this.M_k2,
					  this.F_r, this.F_a, this.F_t, this.F_n, this.v, this.eta, calculationResult);
    }

    public void generateGear1(){
        gear1 = new Spur(m, alpha, z1, b1, false, doubleHelical, mid1, beta, helixDir);
    }
    public void generateGear2() {
	double distance;
	if(rack)
	    distance = this.d1 / 2.0;
	else
	    distance = a;
        gear2 = new Spur(distance, rack, m, alpha, z2, b2, false, doubleHelical, mid2, beta, !helixDir);
    }

    public Spur getGear1() {
        return gear1;
    }
    public Spur getGear2() {
        return gear2;
    }

    private double inverseInvolute(double inv) {
        double a = 0;
        double b;
        do {
            b = a;
            a = Math.atan(a + inv);
        } while (Math.abs(a - b) > 0.0000000000001);
        return b;
    }

    public String getRes1() {
        return res1;
    }

    public String getRes2() {
        return res2;
    }

    public String getResStrength() {
        return resStrength;
    }
    
    public String getResReportGeom() {
	return this.resReportGeom;
    }
    
    public String getResReportStrength() {
	return this.resReportStrength;
    }

    public double getX_sum() {
        return x_sum;
    }

    public int getZ1() {
        return z1;
    }

    public int getZ2() {
        return z2;
    }

    public double getI() {
        return i;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getM() {
        return m;
    }

    public double getA_star() {
        return a_star;
    }

    public double getC_star() {
        return c_star;
    }

    public double getR_star() {
        return r_star;
    }

    public double getB1() {
        return b1;
    }

    public double getB2() {
        return b2;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getA() {
        return a;
    }

    public double getM_t() {
        return m_t;
    }

    public double getP() {
        return p;
    }

    public double getP_t() {
        return p_t;
    }

    public double getP_tB() {
        return p_tB;
    }

    public double getBeta_b() {
        return beta_b;
    }

    public double getAlpha_t() {
        return alpha_t;
    }

    public double getAlpha_w() {
        return alpha_w;
    }

    public double getAlpha_tw() {
        return alpha_tw;
    }

    public double getD1() {
        return d1;
    }

    public double getD2() {
        return d2;
    }

    public double getD_b1() {
        return d_b1;
    }

    public double getD_b2() {
        return d_b2;
    }

    public double getD_a1() {
        return d_a1;
    }

    public double getD_a2() {
        return d_a2;
    }

    public double getD_f1() {
        return d_f1;
    }

    public double getD_f2() {
        return d_f2;
    }

    public double getA_w() {
        return a_w;
    }

    public double getDelta_y() {
        return delta_y;
    }

    public double getD_w1() {
        return d_w1;
    }

    public double getD_w2() {
        return d_w2;
    }

    public double getZ_v1() {
        return z_v1;
    }

    public double getZ_v2() {
        return z_v2;
    }

    public double getD_n1() {
        return d_n1;
    }

    public double getD_n2() {
        return d_n2;
    }

    public double getD_bn1() {
        return d_bn1;
    }

    public double getD_bn2() {
        return d_bn2;
    }

    public double getD_an1() {
        return d_an1;
    }

    public double getD_an2() {
        return d_an2;
    }

    public double getS1() {
        return s1;
    }

    public double getS2() {
        return s2;
    }

    public double getS_c1() {
        return s_c1;
    }

    public double getS_c2() {
        return s_c2;
    }

    public double getH_c1() {
        return h_c1;
    }

    public double getH_c2() {
        return h_c2;
    }

    public double getS_a1() {
        return s_a1;
    }

    public double getS_a2() {
        return s_a2;
    }

    public double getX_z1() {
        return x_z1;
    }

    public double getX_z2() {
        return x_z2;
    }

    public double getX_p1() {
        return x_p1;
    }

    public double getX_p2() {
        return x_p2;
    }

    public double getX_d1() {
        return x_d1;
    }

    public double getX_d2() {
        return x_d2;
    }

    public double getW1() {
        return W1;
    }

    public double getW2() {
        return W2;
    }

    public double getD_M() {
        return d_M;
    }

    public double getB_w() {
        return b_w;
    }

    public double getB_r1() {
        return b_r1;
    }

    public double getB_r2() {
        return b_r2;
    }

    public double getEp() {
        return ep;
    }

    public double getM1() {
        return M1;
    }

    public double getM2() {
        return M2;
    }

    public double getZ_W1() {
        return z_W1;
    }

    public double getZ_W2() {
        return z_W2;
    }


    public double getV() {
        return v;
    }

    public double getF_t() {
        return F_t;
    }

    public double getF_r() {
        return F_r;
    }

    public double getF_a() {
        return F_a;
    }

    public double getF_n() {
        return F_n;
    }

    public double getF_all1() {
        return F_all1;
    }

    public double getF_all2() {
        return F_all2;
    }

    public double getS_1() {
        return S_1;
    }

    public double getS_2() {
        return S_2;
    }

    public double getP1() {
        return P1;
    }

    public double getP2() {
        return P2;
    }

    public double getN1() {
        return n1;
    }

    public double getN2() {
        return n2;
    }

    public double getM_k1() {
        return M_k1;
    }

    public double getM_k2() {
        return M_k2;
    }

    public boolean isInternal() {
        return internal;
    }

    public double getEta() {
        return eta;
    }

    public double getSigma_Ab1() {
        return sigma_Ab1;
    }

    public double getSigma_Ab2() {
        return sigma_Ab2;
    }
}
