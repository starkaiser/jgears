package main.java.jgears.csg.imagej;

/**
 * Fork of
 * https://github.com/fiji/fiji/blob/master/src-plugins/3D_Viewer/src/main/java/customnode/STLLoader.java
 * 
 * TODO: license unclear
 */

import main.java.jgears.csg.vvecmath.ModifiableVector3d;
import main.java.jgears.csg.vvecmath.Vector3d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class STLLoader {

//        /**
//         * Load the specified stl file and returns the result as a hash map, mapping
//         * the object names to the corresponding <code>CustomMesh</code> objects.
//         */
//        public static Map<String, CustomMesh> load(String stlfile)
//                        throws IOException {
//                STLLoader sl = new STLLoader();
//                try {
//                        sl.parse(stlfile);
//                } catch (RuntimeException e) {
//                        System.out.println("error reading " + sl.name);
//                        throw e;
//                }
//                return sl.meshes;
//        }
//
//        private HashMap<String, CustomMesh> meshes;
    public STLLoader() {
    }

    String line;
    BufferedReader in;

    // attributes of the currently read mesh
    private ArrayList<Vector3d> vertices = new ArrayList<>();
    private final ModifiableVector3d normal = Vector3d.zero().asModifiable(); //to be used for file checking
    private FileInputStream fis;
    private int triangles;
//    private DecimalFormat decimalFormat = new DecimalFormat("0.0E0");



    public ArrayList<Vector3d> parse(File f) throws IOException {
        vertices.clear();

                // determine if this is a binary or ASCII STL
        // and send to the appropriate parsing method
        // Hypothesis 1: this is an ASCII STL
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        String[] words = line.trim().split("\\s+");
        if (line.indexOf('\0') < 0 && words[0].equalsIgnoreCase("solid")) {
            System.out.println("Looks like an ASCII STL");
            parseAscii(f);
            return vertices;
        }

        // Hypothesis 2: this is a binary STL
        FileInputStream fs = new FileInputStream(f);

                // bytes 80, 81, 82 and 83 form a little-endian int
        // that contains the number of triangles
        byte[] buffer = new byte[84];
        fs.read(buffer, 0, 84);
        triangles = (int) (((buffer[83] & 0xff) << 24)
                | ((buffer[82] & 0xff) << 16) | ((buffer[81] & 0xff) << 8) | (buffer[80] & 0xff));
        if (((f.length() - 84) / 50) == triangles) {
            System.out.println("Looks like a binary STL");
            parseBinary(f);
            return vertices;
        }
        System.err.println("File is not a valid STL");
        
        return vertices;
    }

    private void parseAscii(File f) {
        try {
            in = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        vertices = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                if (numbers[0].equals("vertex")) {
                    float x = parseFloat(numbers[1]);
                    float y = parseFloat(numbers[2]);
                    float z = parseFloat(numbers[3]);
                    Vector3d vertex = Vector3d.xyz(x, y, z);
                    vertices.add(vertex);
                } else if (numbers[0].equals("facet") && numbers[1].equals("normal")) {
                    normal.setX(parseFloat(numbers[2]));
                    normal.setY(parseFloat(numbers[3]));
                    normal.setZ(parseFloat(numbers[4]));
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseBinary(File f) {
        vertices = new ArrayList<Vector3d>();
        try {
            fis = new FileInputStream(f);
            for (int h = 0; h < 84; h++) {
                fis.read();// skip the header bytes
            }
            for (int t = 0; t < triangles; t++) {
                byte[] tri = new byte[50];
                for (int tb = 0; tb < 50; tb++) {
                    tri[tb] = (byte) fis.read();
                }
                normal.setX(leBytesToFloat(tri[0], tri[1], tri[2], tri[3]));
                normal.setY(leBytesToFloat(tri[4], tri[5], tri[6], tri[7]));
                normal.setZ(leBytesToFloat(tri[8], tri[9], tri[10], tri[11]));
                for (int i = 0; i < 3; i++) {
                    final int j = i * 12 + 12;
                    float px = leBytesToFloat(tri[j], tri[j + 1], tri[j + 2],
                            tri[j + 3]);
                    float py = leBytesToFloat(tri[j + 4], tri[j + 5],
                            tri[j + 6], tri[j + 7]);
                    float pz = leBytesToFloat(tri[j + 8], tri[j + 9],
                            tri[j + 10], tri[j + 11]);
                    Vector3d p = Vector3d.xyz(px, py, pz);
                    vertices.add(p);
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private float parseFloat(String string) throws ParseException {
//        //E+05 -> E05, e+05 -> E05
//        string = string.replaceFirst("[eE]\\+", "E");
//        //E-05 -> E-05, e-05 -> E-05
//        string = string.replaceFirst("e\\-", "E-");
//        return decimalFormat.parse(string).floatValue();
//    }
    
    private float parseFloat(String string) throws ParseException {

        return Float.parseFloat(string);
    }

    private float leBytesToFloat(byte b0, byte b1, byte b2, byte b3) {
        return Float.intBitsToFloat((((b3 & 0xff) << 24) | ((b2 & 0xff) << 16)
                | ((b1 & 0xff) << 8) | (b0 & 0xff)));
    }

}
