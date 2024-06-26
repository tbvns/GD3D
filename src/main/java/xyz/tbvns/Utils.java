package xyz.tbvns;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;

import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {
    public static int xScreenCenter = 320/2;
    public static int yScreenCenter = 240/2;
    public static Vector3d screenPosition = new Vector3d( 0, 0, 20 );
    public static Vector3d viewAngle = new Vector3d( 180, 180, 90);
    private static final double DEG_TO_RAD = 0.017453292;
    public static double modelScale = 20;

    double CT = Math.cos( DEG_TO_RAD * viewAngle.x );
    double ST = Math.sin( DEG_TO_RAD * viewAngle.x );
    double CP = Math.cos( DEG_TO_RAD * viewAngle.y );
    double SP = Math.sin( DEG_TO_RAD * viewAngle.y );

    public void projectPoint(Point3d input, Point3d output) {
        double x = screenPosition.x + input.x * CT - input.y * ST;
        double y = screenPosition.y + input.x * ST * SP + input.y * CT * SP
                + input.z * CP;
        double temp = viewAngle.z / (screenPosition.z + input.x * ST * CP
                + input.y * CT * CP - input.z * SP );

        output.x = xScreenCenter + modelScale * temp * x;
        output.y = yScreenCenter - modelScale * temp * y;
        output.z = 0;
    }

    public static boolean isColliding(Vector3 point) {
        List<Face> faces = Constant.faces;
        boolean[] isCovered = new boolean[]{false};

        faces.forEach(face -> {
            if (face.Has4Point) {
                double[] PerspectivePoint = new double[]{point.x, point.y, point.z};
                Point3d p = new Point3d();
                new Utils().projectPoint(new Point3d(PerspectivePoint), p);

                List<Coordinate> cord = new ArrayList<>();
                face.points.forEach(fp -> {
                    Vector3 localpoint = Constant.points.get(fp-1);

                    double[] p1 = new double[]{localpoint.x, localpoint.y, localpoint.z};
                    Point3d p2 = new Point3d();
                    new Utils().projectPoint(new Point3d(p1), p2);

                    cord.add(new Coordinate(p2.x, p2.y));
                });


                cord.add(cord.get(0));
                GeometryFactory geometryFactory = new GeometryFactory();
                Polygon polygon = geometryFactory.createPolygon(cord.toArray(new Coordinate[]{}));
                float facez = getFacez(face);

                if (!isCovered[0] && point.z > facez) {
                    isCovered[0] = (geometryFactory.createPoint(new Coordinate(p.x, p.y)).coveredBy(polygon));
                }
            }
        });
        return isCovered[0];
    }

    private static float getFacez(Face face) {
        float facez;
        if (face.Has4Point) {
            facez = (Constant.points3d.get(face.points.get(0) -1).z + Constant.points3d.get(face.points.get(1) -1).z + Constant.points3d.get(face.points.get(2) -1).z + Constant.points3d.get(face.points.get(3) -1).z)/4;
        } else {
            facez = (Constant.points3d.get(face.points.get(0) -1).z + Constant.points3d.get(face.points.get(1) -1).z + Constant.points3d.get(face.points.get(2) -1).z)/3;
        }
        return facez;
    }

    public static float getZ(Face face) {
        //TODO: FIX THIS SHIT ITS BROKEN AF PLS HELP ME I THINK I AM GOING TO COMMIT SUICIDE
        //d = √[(x2 − x1)2 + (y2 − y1)2 + (z2 − z1)2]
        List<Vector3> points = new ArrayList<>();
        face.points.forEach(p -> {
            Vector3 point = Constant.points3d.get(p - 1);
            points.add(point);
        });

        List<Double> dist = new ArrayList<>();

        points.forEach(p -> {
            dist.add(Math.sqrt(
                    (Math.pow((p.x - screenPosition.x), 2)) +
                    (Math.pow((p.y - screenPosition.y), 2)) +
                    (Math.pow((p.z - screenPosition.z), 2)))
            );
        });

        AtomicReference<Float> average = new AtomicReference<Float>(0f);

        dist.forEach(d -> {
            average.set((float) (average.get() + d));
        });

        System.out.println(average);
        average.set(average.get() / dist.size());
        System.out.println(dist);
        System.out.println(average);

        return average.get();
    }

    public static List<File> sort(List<File> files) {
        HashMap<Integer, File> fileSorted = new HashMap<>();

        files.forEach(file -> {
            String name = file.getName();
            name = name.replaceAll("[^0-9]+", "");
            int id = Integer.parseInt(name);
            fileSorted.put(id, file);
        });

        List<File> fileReturned = new ArrayList<>();

        for (int i = 0; i < fileSorted.size(); i++) {
            try {
                if (fileSorted.get(i) != null) {
                    fileReturned.add(fileSorted.get(i));
                }
            } catch (Exception e) {
                JFrame frame = new JFrame("Warning:");
                JOptionPane.showMessageDialog(frame,
                        "The frame " + i + " is missing !",
                        "Warning !",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        return fileReturned;
    }
}
