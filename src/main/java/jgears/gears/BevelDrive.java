package main.java.jgears.gears;

import main.java.jgears.gui.ResultsUtils;

public class BevelDrive {
    // RECEIVE ALL ANGLES IN RADIANS!!!
    private int z1, z2; // number of teeth
    private double i; // gear ratio
    private double u;
    private double i_in; // desired gear ratio
    private double alpha_t; // pressure angle
    private double beta_m; // helix angle
    private double axisAng; // axis angle sigma
    private double m_et; // module
    private double b; // face width, operating width of gears
    private double a_star = 1.00; // addendum coefficient
    private double c_star = 0.25; // clearance coefficient
    private double r_star = 0.35; // root fillet coefficient
    private double x1, x2 = 0.0; // profile shit coefficient/unit correction
    private double x_t1, x_t2 = 0.0; // unit change of tooth thickness

    private boolean helixDir = true; // true for left

    private double alpha_nm; // normal pressure angle in middle plane
    private double delta1, delta2; // pitch angle
    private double d_e1, d_e2; // outside pitch diameter
    private double R_e; // outside length of surface line on pitch cone
    private double R_m; // length of surface line on pitch one
    private double psi_R; // relative face width
    private double m_mt; // tangential module on the mean cone
    private double m_mn; // normal module on the mean cone
    private double m_en; // normal module at end
    private double d_m1, d_m2; // mean pitch diameter
    private double z_v1, z_v2; // equivalent number of teeth
    private double d_v1, d_v2; // equivalent pitch diameter
    private double d_vb1, d_vb2; // equivalent base diameter
    private double d_va1, d_va2; // equivalent outside diameter
    private double a_v; // equivalent center distance
    private double u_v; // virtual gear ratio
    private double z_n1, z_n2; // virtual number of teeth
    private double d_n1, d_n2; // virtual pitch diameter
    private double d_bn1, d_bn2; // virtual base diameter
    private double d_an1, d_an2; // virtual outside diameter
    private double beta_b; // virtual helix angle at base cylinder
    private double a_n; // virtual center distance
    private double k1, k2; // dedendum reduction
    private double h_ae1, h_ae2; // addendum
    private double h_fe1, h_fe2; // dedendum
    private double d_ae1, d_ae2; // outside diameter
    private double d_fe1, d_fe2; // root diameter
    private double d_ai1, d_ai2; // outside diameter at small end
    private double A_e1, A_e2; // vertex distance
    private double delta_a1, delta_a2; // outside bevel angle
    private double delta_f1, delta_f2; // cutting angle
    private double s_e1, s_e2; // tooth thickness (measured normally on the pitch diameter)
    private double s_ke1, s_ke2; // chordal face width (normal)
    private double h_ke1, h_ke2; // addendum height above the chord
    private double s_a1, s_a2; // unit addendum width (measured normally)
    private double ep, ep_alpha, ep_alpha_n, ep_beta; // factors of mesh duration
    private double x_z1, x_z2; // minimum correction without tapering
    private double x_p1, x_p2; // minimum correction without undercut
    private double x_d1, x_d2; // minimum correction TODO ...
    private double beta_e; // helix angle at end
    private double alpha_ne; // normal pressure angle at end

    private double P1, P2; // power
    private double n1, n2; // speed
    private double eta; // efficiency
    private double minSafety; // minimum factor of safety
    private double sigma_Ab1, sigma_Ab2;
    private double M_k1, M_k2; // torque
    private double v; // circumferential speed
    private double F_t, F_n, F_r1a, F_r1b, F_r2a, F_r2b, F_a1a, F_a1b, F_a2a, F_a2b; // forces

    private double F_all1, F_all2; // allowable load
    private double S_1, S_2; // safety factor for each gear

    private String res1="", res2="", resStrength="";

    private Bevel bevelDrive;

    public BevelDrive(int z1, int z2, double alpha_t, double beta_m, boolean helixDir, double axisAng, double m_et,
                      double b, double a_star, double c_star, double r_star, double x1, double x_t1) {
        this.z1 = z1;
        this.z2 = z2;
        this.alpha_t = alpha_t;
        this.beta_m = beta_m;
        this.helixDir = helixDir;
        this.axisAng = axisAng;
        this.m_et = m_et;
        this.b = b;
        this.a_star = a_star;
        this.c_star = c_star;
        this.r_star = r_star;
        this.x1 = x1;
        this.x_t1 = x_t1;
        calculateParam();
    }

    public BevelDrive(int z1, int z2, double alpha_t, double beta_m, boolean helixDir, double axisAng, double m_et,
                      double b, double a_star, double c_star, double r_star, double x1, double x_t1, double P1,
                      double n1, double M_k1, double eta, double minSafety, double sigma_Ab1, double sigma_Ab2) {
        this.z1 = z1;
        this.z2 = z2;
        this.alpha_t = alpha_t;
        this.beta_m = beta_m;
        this.helixDir = helixDir;
        this.axisAng = axisAng;
        this.m_et = m_et;
        this.b = b;
        this.a_star = a_star;
        this.c_star = c_star;
        this.r_star = r_star;
        this.x1 = x1;
        this.x_t1 = x_t1;
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
        x2 = - x1;
        x_t2 = - x_t1;
        i = ((double)z2)/((double)z1);
        if (i >= 0)
            u = i;
        else
            u = 1.0 / i;
        alpha_nm = Math.atan(Math.tan(alpha_t)*Math.cos(beta_m));
        delta1 = Math.atan(Math.sin(axisAng)/(u+Math.cos(axisAng)));
        delta2 = axisAng - delta1;
        d_e1 = m_et * z1;
        d_e2 = m_et * z2;
        R_e = d_e1 / (2 * Math.sin(delta1));
        R_m = R_e - 0.5 * b;
        psi_R = b / R_e;
        m_mt = m_et * R_m / R_e;
        m_mn = m_mt * Math.cos(beta_m);

        d_m1 = m_mt * z1;
        d_m2 = m_mt * z2;

        double cosBeta_m3 = Math.pow(Math.cos(beta_m), 3);
        z_v1 = z1 / Math.cos(delta1);
        z_v2 = z2 / Math.cos(delta2);
        z_n1 = z_v1 / cosBeta_m3;
        z_n2 = z_v2 / cosBeta_m3;

        //k1 = 0.02 * (17 - z_n1);
        //k2 = 0.02 * (17 - z_n2);
        k1 = 0;
        k2 = 0;
        h_ae1 = m_et * (a_star + x1 - k1);
        h_ae2 = m_et * (a_star + x2 - k2);
        h_fe1 = m_et * (a_star + c_star - x1);
        h_fe2 = m_et * (a_star + c_star - x2);

        d_v1 = d_m1 / Math.cos(delta1);
        d_v2 = d_m2 / Math.cos(delta2);
        double cosAlpha_t = Math.cos(alpha_t);
        d_vb1 = d_v1 * cosAlpha_t;
        d_vb2 = d_v2 * cosAlpha_t;
        d_va1 = d_v1 + 2 * h_ae1 * m_mt / m_et;
        d_va2 = d_v2 + 2 * h_ae2 * m_mt / m_et;
        a_v = 0.5 * (d_v1 + d_v2);
        u_v = z_v2 / z_v1;

        d_n1 = z_n1 / cosBeta_m3;
        d_n2 = z_n2 / cosBeta_m3;
        d_bn1 = d_n1 * cosAlpha_t;
        d_bn2 = d_n2 * cosAlpha_t;
        d_an1 = d_n1 + 2 * h_ae1 * m_mt / m_et;
        d_an2 = d_n2 + 2 * h_ae2 * m_mt / m_et;
        beta_b = Math.asin(Math.sin(beta_m)*Math.cos(alpha_nm));
        a_n = 0.5 * (d_n1 + d_n2);

        d_ae1 = d_e1 + 2 * h_ae1 * Math.cos(delta1);
        d_ae2 = d_e2 + 2 * h_ae2 * Math.cos(delta2);
        d_fe1 = d_e1 - 2 * h_fe1 * Math.cos(delta1);
        d_fe2 = d_e2 - 2 * h_fe2 * Math.cos(delta2);
        d_ai1 = d_ae1 * (1 - psi_R);
        d_ai2 = d_ae2 * (1 - psi_R);

        A_e1 = R_e * Math.cos(delta1) - h_ae1 * Math.sin(delta1);
        A_e2 = R_e * Math.cos(delta2) - h_ae2 * Math.sin(delta2);
        delta_a1 = delta1 + Math.atan(h_ae1 / R_e);
        delta_a2 = delta2 + Math.atan(h_ae2 / R_e);
        delta_f1 = delta1 - Math.atan(h_fe1 / R_e);
        delta_f2 = delta2 - Math.atan(h_fe2 / R_e);
        s_e1 = m_et * (Math.PI/2.0 + 2 * x1 * Math.tan(alpha_t) + x_t1);
        s_e2 = m_et * (Math.PI/2.0 + 2 * x2 * Math.tan(alpha_t) + x_t2);
        double cosAlpha_t2 = Math.cos(alpha_t) * Math.cos(alpha_t);
        s_ke1 = s_e1 * cosAlpha_t2;
        s_ke2 = s_e2 * cosAlpha_t2;
        h_ke1 = h_ae1 - s_ke1 * Math.tan(alpha_t) / 2.0;
        h_ke2 = h_ae2 - s_ke2 * Math.tan(alpha_t) / 2.0;

        double alpha_a1 = Math.acos((d_e1/d_ae1)*Math.cos(alpha_t));
	double alpha_a2 = Math.acos((d_e2/d_ae2)*Math.cos(alpha_t));
        double inv_alpha_a1 = Math.tan(alpha_a1) - alpha_a1;
	double inv_alpha_a2 = Math.tan(alpha_a2) - alpha_a2;
        double inv_alpha_t = Math.tan(alpha_t) - alpha_t;
        double inv_alpha_nm = Math.tan(alpha_nm) - alpha_nm;
	
	beta_e = Math.asin(Math.sin(beta_m)*R_m/R_e);
        alpha_ne = Math.atan(Math.tan(alpha_t)*Math.cos(beta_e));
	m_en = m_et*Math.cos(beta_e);

        s_a1 = (d_ae1 / m_en) * (s_e1 / d_e1 + (inv_alpha_t - inv_alpha_a1) / Math.cos(delta1));
        s_a2 = (d_ae2 / m_en) * (s_e2 / d_e2 + (inv_alpha_t - inv_alpha_a2) / Math.cos(delta2));

        ep_alpha = (Math.sqrt(d_va1*d_v1 - d_vb1*d_vb1) + Math.sqrt(d_va2*d_va2 - d_vb2*d_vb2) - 2*a_v*Math.sin(alpha_t))/
                   (2*Math.PI*m_mt*Math.cos(alpha_t));
        ep_alpha_n = ep_alpha / (Math.cos(beta_b)*Math.cos(beta_b));
        ep_beta = (0.85 * b * Math.sin(beta_m)) / (Math.PI * m_mn);
        ep = ep_alpha_n + ep_beta;

        double h_a0 = a_star + c_star - r_star * (1 - Math.sin(alpha_t));
        x_z1 = h_a0 - ((1 - Math.cos(alpha_nm)/Math.cos(inv_alpha_nm+Math.PI/(2*z_v1)))/(2*Math.cos(beta_m))) * z_v1;
        x_z2 = h_a0 - ((1 - Math.cos(alpha_nm)/Math.cos(inv_alpha_nm+Math.PI/(2*z_v2)))/(2*Math.cos(beta_m))) * z_v2;
        x_p1 = h_a0 - (z_n1/2.0) * Math.sin(alpha_nm) * Math.sin(alpha_nm);
        x_p2 = h_a0 - (z_n2/2.0) * Math.sin(alpha_nm) * Math.sin(alpha_nm);
        x_d1 = (5.0/6.0)*h_a0 - (z_n1/2.0)*Math.sin(alpha_nm)*Math.sin(alpha_nm);
        x_d2 = (5.0/6.0)*h_a0 - (z_n2/2.0)*Math.sin(alpha_nm)*Math.sin(alpha_nm);
    

        this.res1 = String.format(ResultsUtils.BEVELGEOM, Math.toDegrees(alpha_nm), Math.toDegrees(alpha_t),
                Math.toDegrees(beta_b), Math.toDegrees(beta_e), Math.toDegrees(delta1), Math.toDegrees(delta_a1),
                Math.toDegrees(delta_f1), R_e, R_m, d_e1, d_fe1, d_ae1, d_m1, d_ai1, A_e1, 0.0, h_ae1+h_fe1, s_e1, s_ke1,
                h_ke1, z_n1, d_n1, d_an1, d_bn1, z_v1, d_v1, d_va1, d_vb1);

        this.res2 = String.format(ResultsUtils.BEVELGEOM, Math.toDegrees(alpha_nm), Math.toDegrees(alpha_t),
                Math.toDegrees(beta_b), Math.toDegrees(beta_e), Math.toDegrees(delta2), Math.toDegrees(delta_a2),
                Math.toDegrees(delta_f2), R_e, R_m, d_e2, d_fe2, d_ae2, d_m2, d_ai2, A_e2, 0.0, h_ae2+h_fe2, s_e2, s_ke2,
                h_ke2, z_n2, d_n2, d_an2, d_bn2, z_v2, d_v2, d_va2, d_vb2);
	
	//System.out.println(A1);
	//System.out.println(A2);
    }

    private void calculateStrengthParam() {
        P2 = P1 * eta; // output power
        n2 = n1 / i; // output speed
        M_k2 = M_k1 * i * eta; // output moment
        v = (Math.PI * d_m1 * n1) / 60000; // circumferential speed
        F_t = (2000 * M_k1) / d_m1; // tangential force

        // radial forces
        F_r1a = F_t * (Math.tan(alpha_nm) * Math.cos(delta1) - Math.sin(beta_m) * Math.sin(delta1)) / Math.cos(beta_m);
        F_r1b = F_t * (Math.tan(alpha_nm) * Math.cos(delta2) + Math.sin(beta_m) * Math.sin(delta2)) / Math.cos(beta_m);
        F_r2a = F_t * (Math.tan(alpha_nm) * Math.cos(delta1) + Math.sin(beta_m) * Math.sin(delta1)) / Math.cos(beta_m);
        F_r2b = F_t * (Math.tan(alpha_nm) * Math.cos(delta2) - Math.sin(beta_m) * Math.sin(delta2)) / Math.cos(beta_m);

        // axial forces
        F_a1a = F_t * (Math.tan(alpha_nm) * Math.sin(delta1) + Math.sin(beta_m) * Math.cos(delta1)) / Math.cos(beta_m);
        F_a1b = F_t * (Math.tan(alpha_nm) * Math.sin(delta2) - Math.sin(beta_m) * Math.cos(delta2)) / Math.cos(beta_m);
        F_a2a = F_t * (Math.tan(alpha_nm) * Math.sin(delta1) - Math.sin(beta_m) * Math.cos(delta1)) / Math.cos(beta_m);
        F_a2b = F_t * (Math.tan(alpha_nm) * Math.sin(delta2) + Math.sin(beta_m) * Math.cos(delta2)) / Math.cos(beta_m);
        F_n = F_t / (Math.cos(alpha_nm) * Math.cos(beta_m)); // normal force

        double K = 1.4;
        if (beta_m > 0)
            K = 1.25;


        F_all1 = Math.PI * 0.065 * sigma_Ab1 * b * m_en * (Math.pow(1 - 0.5 * psi_R, 2) / K); // allowable force for pinion
        S_1 = F_all1 / F_t; // safety factor for pinion
        F_all2 = Math.PI * 0.065 * sigma_Ab2 * b * m_en * (Math.pow(1 - 0.5 * psi_R, 2) / K); // allowable force for gear
        S_2 = F_all2 / F_t; // safety factor for gear

        resStrength = String.format(ResultsUtils.BEVELSTRENGTH, this.F_t, this.F_n, this.v, this.F_r1a, this.F_r2a, 
								this.F_a1a, this.F_a2a, this.S_1, this.F_all1, this.F_r1b, 
								this.F_r2b, this.F_a1b, this.F_a2b, this.S_2, this.F_all2);
	
	//double m_en_res=231.714 / (Math.PI * 0.065 * sigma_Ab1 * b * (Math.pow(1 - 0.5 * psi_R, 2) / K));
	//System.out.println(m_en_res);
	//System.out.println(m_et*Math.cos(beta_e));

    }

    public void generateBevelDrive(boolean gear1, boolean gear2, boolean assemblyType, boolean doubleHelical){
	double beta_m;
	// direction of the pinion 
	if (!helixDir)
	    beta_m = this.beta_m;
	else
	    beta_m = -this.beta_m;
        if(gear1 && gear2)
            this.bevelDrive = new Bevel(m_et, z1, z2, axisAng, b, alpha_t, beta_m, doubleHelical, assemblyType);
        else if(gear1 && !gear2)
            this.bevelDrive = new Bevel(m_et, z1, delta1, b, alpha_t, beta_m, doubleHelical);
        else if(!gear1 && gear2)
            this.bevelDrive = new Bevel(m_et, z2, delta2, b, alpha_t, beta_m, doubleHelical);
    }

    public Bevel getBevelDrive() {
        return bevelDrive;
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

    public double getS_1() {
        return S_1;
    }

    public double getS_2() {
        return S_2;
    }

    public double getP2() {
        return P2;
    }

    public double getN2() {
        return n2;
    }

    public double getM_k2() {
        return M_k2;
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

    public double getU() {
        return u;
    }

    public double getI_in() {
        return i_in;
    }

    public double getAlpha_t() {
        return alpha_t;
    }

    public double getBeta_m() {
        return beta_m;
    }

    public double getAxisAng() {
        return axisAng;
    }

    public double getM_et() {
        return m_et;
    }
    
     public double getM_en() {
        return m_en;
    }

    public double getB() {
        return b;
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

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getX_t1() {
        return x_t1;
    }

    public double getX_t2() {
        return x_t2;
    }

    public boolean isHelixDir() {
        return helixDir;
    }

    public double getAlpha_nm() {
        return alpha_nm;
    }

    public double getDelta1() {
        return delta1;
    }

    public double getDelta2() {
        return delta2;
    }

    public double getD_e1() {
        return d_e1;
    }

    public double getD_e2() {
        return d_e2;
    }

    public double getR_e() {
        return R_e;
    }

    public double getR_m() {
        return R_m;
    }

    public double getPsi_R() {
        return psi_R;
    }

    public double getM_mt() {
        return m_mt;
    }

    public double getM_mn() {
        return m_mn;
    }

    public double getD_m1() {
        return d_m1;
    }

    public double getD_m2() {
        return d_m2;
    }

    public double getZ_v1() {
        return z_v1;
    }

    public double getZ_v2() {
        return z_v2;
    }

    public double getD_v1() {
        return d_v1;
    }

    public double getD_v2() {
        return d_v2;
    }

    public double getD_vb1() {
        return d_vb1;
    }

    public double getD_vb2() {
        return d_vb2;
    }

    public double getD_va1() {
        return d_va1;
    }

    public double getD_va2() {
        return d_va2;
    }

    public double getA_v() {
        return a_v;
    }

    public double getU_v() {
        return u_v;
    }

    public double getZ_n1() {
        return z_n1;
    }

    public double getZ_n2() {
        return z_n2;
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

    public double getBeta_b() {
        return beta_b;
    }

    public double getA_n() {
        return a_n;
    }

    public double getK1() {
        return k1;
    }

    public double getK2() {
        return k2;
    }

    public double getH_ae1() {
        return h_ae1;
    }

    public double getH_ae2() {
        return h_ae2;
    }

    public double getH_fe1() {
        return h_fe1;
    }

    public double getH_fe2() {
        return h_fe2;
    }

    public double getD_ae1() {
        return d_ae1;
    }

    public double getD_ae2() {
        return d_ae2;
    }

    public double getD_fe1() {
        return d_fe1;
    }

    public double getD_fe2() {
        return d_fe2;
    }

    public double getD_ai1() {
        return d_ai1;
    }

    public double getD_ai2() {
        return d_ai2;
    }

    public double getA_e1() {
        return A_e1;
    }

    public double getA_e2() {
        return A_e2;
    }

    public double getDelta_a1() {
        return delta_a1;
    }

    public double getDelta_a2() {
        return delta_a2;
    }

    public double getDelta_f1() {
        return delta_f1;
    }

    public double getDelta_f2() {
        return delta_f2;
    }

    public double getS_e1() {
        return s_e1;
    }

    public double getS_e2() {
        return s_e2;
    }

    public double getS_ke1() {
        return s_ke1;
    }

    public double getS_ke2() {
        return s_ke2;
    }

    public double getH_ke1() {
        return h_ke1;
    }

    public double getH_ke2() {
        return h_ke2;
    }

    public double getS_a1() {
        return s_a1;
    }

    public double getS_a2() {
        return s_a2;
    }

    public double getEp() {
        return ep;
    }

    public double getEp_alpha() {
        return ep_alpha;
    }

    public double getEp_alpha_n() {
        return ep_alpha_n;
    }

    public double getEp_beta() {
        return ep_beta;
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

    public double getBeta_e() {
        return beta_e;
    }

    public double getAlpha_ne() {
        return alpha_ne;
    }
    
    public double getV() {
        return v;
    }

    public double getF_t() {
        return F_t;
    }

    public double getF_r1a() {
        return F_r1a;
    }
    public double getF_r1b() {
        return F_r1b;
    }
    public double getF_r2a() {
        return F_r2a;
    }
    public double getF_r2b() {
        return F_r2b;
    }

    public double getF_a1a() {
        return F_a1a;
    }
    public double getF_a1b() {
        return F_a1b;
    }
    public double getF_a2a() {
        return F_a2a;
    }
    public double getF_a2b() {
        return F_a2b;
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

    public double getP1() {
        return P1;
    }

    public double getN1() {
        return n1;
    }

    public double getM_k1() {
        return M_k1;
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
