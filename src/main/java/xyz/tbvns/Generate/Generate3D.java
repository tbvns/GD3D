package xyz.tbvns.Generate;

import xyz.tbvns.Constant;
import xyz.tbvns.GeometryDash.GDObject;
import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;
import xyz.tbvns.Utils;

import javax.vecmath.Point3d;
import java.io.FileNotFoundException;
import java.util.List;

public class Generate3D {
    public void Generate() throws FileNotFoundException, InterruptedException {
        List<Vector3> points = new ReadOBJ().getPoints(Constant.OBJ);
        Constant.points3d = points;
        for (int i = 0; i < points.size(); i++) {
            Vector3 p = points.get(i);

            Point3d p1 = new Point3d(new double[]{p.x, p.z, p.y});
            Point3d p2 = new Point3d(new double[]{0, 0, 0});

            new Utils().projectPoint(p1, p2);

            Constant.points.add(new Vector3((float) p2.x, (float) p2.y, (float) p2.z));

            GDObject.addBasic(725, (float) (p2.x + 1000), (float) (p2.y + 1000), i+1);
        }

        List<Face> faces = new ReadOBJ().getFace(Constant.OBJ);
        Constant.faces = faces;
        final int[] id = {0};
        faces.forEach(f -> {
            id[0] +=1;
            if (f.points.size() == 3) {
                GDObject.addGradient(0f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(2), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000));
            } else {
                GDObject.addGradient(0f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(3), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000));
            }
        });

        GDObject.send();
    }
}
