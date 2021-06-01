/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.jgears.csg.quickhull3d;

import main.java.jgears.csg.jcsg.CSG;
import main.java.jgears.csg.jcsg.Polygon;
import main.java.jgears.csg.jcsg.PropertyStorage;
import main.java.jgears.csg.vvecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class HullUtil {

    private HullUtil() {
        throw new AssertionError("Don't instantiate me!", null);
    }

    public static CSG hull(List<Vector3d> points, PropertyStorage storage) {

        Point3d[] hullPoints = points.stream().map((vec) -> new Point3d(vec.x(), vec.y(), vec.z())).toArray(Point3d[]::new);

        QuickHull3D hull = new QuickHull3D();
        hull.build(hullPoints);
        hull.triangulate();

        int[][] faces = hull.getFaces();

        List<Polygon> polygons = new ArrayList<>();

        List<Vector3d> vertices = new ArrayList<>();

        for (int[] verts : faces) {

            for (int i : verts) {
                vertices.add(points.get(hull.getVertexPointIndices()[i]));
            }

            polygons.add(Polygon.fromPoints(vertices, storage));

            vertices.clear();
        }

        return CSG.fromPolygons(polygons);
    }

    public static CSG hull(CSG csg, PropertyStorage storage) {

        List<Vector3d> points = new ArrayList<>(csg.getPolygons().size() * 3);

        csg.getPolygons().forEach((p) -> p.vertices.forEach((v) -> points.add(v.pos)));

        return hull(points, storage);
    }
}
