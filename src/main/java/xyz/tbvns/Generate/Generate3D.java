package xyz.tbvns.Generate;

import xyz.tbvns.Constant;
import xyz.tbvns.GeometryDash.GDObject;
import xyz.tbvns.Object.Color;
import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;
import xyz.tbvns.Utils;

import javax.vecmath.Point3d;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

        List<Color> colors = new ArrayList<>();

        Constant.faces = faces;
        final int[] id = {0};
        faces.forEach(f -> {
            if (!colors.contains(f.color)) {
                f.colorId = colors.size() + 1;
                colors.add(f.color);
                System.out.println(f.colorId);
            }

            id[0] +=1;
            if (f.points.size() == 3) {
                GDObject.addGradient(0f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(2), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000), f.colorId);
            } else {
                GDObject.addGradient(0f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(3), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000), f.colorId);
            }
        });


        for (int i = 0; i < colors.size(); i++) {
            Color c = colors.get(i);
            GDObject.addColor(0f, 200f, Math.round(c.red * 255), Math.round(c.green * 255), Math.round(c.blue * 255), i+1);
        }

        GDObject.send();

        Constant.points3d = new ArrayList<>();
        Constant.faces = new ArrayList<>();
        Constant.points = new ArrayList<>();
        GDObject.request = new ArrayList<>();
    }

    public void GenerateAnimKeyframe() throws FileNotFoundException {
        List<File> OBJs = new ArrayList<>();
        List<File> MTLs = new ArrayList<>();

        for (int i = 0; i < Constant.OBJ.listFiles().length; i++) {
            File f = Constant.OBJ.listFiles()[i];
            if (f.getAbsolutePath().endsWith(".obj")) {
                OBJs.add(f);
            }
        }

        for (int i = 0; i < Constant.MTL.listFiles().length; i++) {
            File f = Constant.MTL.listFiles()[i];
            if (f.getAbsolutePath().endsWith(".mtl")) {
                MTLs.add(f);
            }
        }

        if (Constant.OBJ.isDirectory()) {
            if (Constant.HasMTL && !Constant.MTL.isDirectory()) {
                return;
            }
            GDObject.addKeyframeTrigger();
            for (int i = 0; i < OBJs.size(); i++) {
                List<Vector3> points = new ReadOBJ().getPoints(OBJs.get(i));
                Constant.points3d = points;
                for (int i2 = 0; i2 < points.size(); i2++) {
                    Vector3 p = points.get(i2);

                    Point3d p1 = new Point3d(new double[]{p.x, p.z, p.y});
                    Point3d p2 = new Point3d(new double[]{0, 0, 0});

                    new Utils().projectPoint(p1, p2);

                    Constant.points.add(new Vector3((float) p2.x, (float) p2.y, (float) p2.z));

                    if (i == 0) {
                        GDObject.addBasic(725, (float) (p2.x + 1000), (float) (p2.y + 1000), i2+1);
                        GDObject.addKeyframe((float) (p2.x + 1000), (float) (p2.y + 1000), i2+1, 1, i);
                        System.out.println(i2 + "    " + i);
                    } else {
                        GDObject.addKeyframe((float) (p2.x + 1000), (float) (p2.y + 1000), i2+1, 1, i);
                        System.out.println(i2 + "    " + i);
                    }
                }

                List<Face> faces = new ReadOBJ().getFaceAnimation(OBJs.get(i), MTLs.get(i));

                List<Color> colors = new ArrayList<>();

                Constant.faces = faces;
                final int[] id = {0};
                faces.forEach(f -> {
                    if (!colors.contains(f.color)) {
                        f.colorId = colors.size() + 1;
                        colors.add(f.color);
                        System.out.println(f.colorId);
                    }

                    id[0] +=1;
                    if (f.points.size() == 3) {
                        GDObject.addGradient(0f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(2), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000), f.colorId);
                    } else {
                        GDObject.addGradient(0f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(3), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000), f.colorId);
                    }
                });


                for (int i2 = 0; i2 < colors.size(); i2++) {
                    Color c = colors.get(i2);
                    GDObject.addColor(i*10f, 200f, Math.round(c.red * 255), Math.round(c.green * 255), Math.round(c.blue * 255), i2+1);
                }

                Constant.points3d = new ArrayList<>();
                Constant.faces = new ArrayList<>();
                Constant.points = new ArrayList<>();
            }

            GDObject.send();

            GDObject.request = new ArrayList<>();
        }
    }

    public void GenerateAnimMove() throws FileNotFoundException {
        List<File> OBJs = new ArrayList<>();
        List<File> MTLs = new ArrayList<>();
        List<Vector3> oldPoint = new ArrayList<>();

        for (int i = 0; i < Constant.OBJ.listFiles().length; i++) {
            File f = Constant.OBJ.listFiles()[i];
            if (f.getAbsolutePath().endsWith(".obj")) {
                OBJs.add(f);
            }
        }

        for (int i = 0; i < Constant.MTL.listFiles().length; i++) {
            File f = Constant.MTL.listFiles()[i];
            if (f.getAbsolutePath().endsWith(".mtl")) {
                MTLs.add(f);
            }
        }

        if (Constant.OBJ.isDirectory()) {
            if (Constant.HasMTL && !Constant.MTL.isDirectory()) {
                return;
            }
            for (int i = 0; i < OBJs.size(); i++) {
                List<Vector3> points = new ReadOBJ().getPoints(OBJs.get(i));
                Constant.points3d = points;
                for (int i2 = 0; i2 < points.size(); i2++) {
                    Vector3 p = points.get(i2);

                    Point3d p1 = new Point3d(new double[]{p.x, p.z, p.y});
                    Point3d p2 = new Point3d(new double[]{0, 0, 0});

                    new Utils().projectPoint(p1, p2);

                    Constant.points.add(new Vector3((float) p2.x, (float) p2.y, (float) p2.z));

                    if (i == 0) {
                        GDObject.addBasic(725, (float) (p2.x), (float) (p2.y), i2+1);
                    } else {
                        Point3d p1old = new Point3d(new double[]{oldPoint.get(i2).x, oldPoint.get(i2).z, oldPoint.get(i2).y});
                        Point3d p2old = new Point3d(new double[]{0, 0, 0});

                        new Utils().projectPoint(p1old, p2old);

                        GDObject.addMove(i*10f, i2+1, (float) (p2old.x - p2.x), (float) (p2old.y - p2.y), 0f);

                        System.out.println((float) (p2old.x - p2.x) + "    " + (float) (p2old.y - p2.y));
                        System.out.println((float) (p2old.x) + "    " + (float) (p2old.y));
                        System.out.println((float) (p2.x) + "    " + (float) (p2.y));
                    }
                }

                oldPoint = points;

                List<Face> faces = new ReadOBJ().getFaceAnimation(OBJs.get(i), MTLs.get(i));

                List<Color> colors = new ArrayList<>();

                Constant.faces = faces;
                final int[] id = {0};
                AtomicInteger temp = new AtomicInteger(i);

                faces.forEach(f -> {
                    if (!colors.contains(f.color)) {
                        f.colorId = colors.size() + 1;
                        colors.add(f.color);
                        System.out.println(f.colorId);
                    }

                    id[0] +=1;
                    if (f.points.size() == 3) {
                        GDObject.addGradient(temp.get()*10f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(2), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000), f.colorId);
                    } else {
                        GDObject.addGradient(temp.get()*10f, 100f, 0, f.points.get(0), f.points.get(1), f.points.get(2), f.points.get(3), id[0], Math.round(Constant.points3d.get(f.points.get(0) - 1).z * 10000), f.colorId);
                    }
                });


                for (int i2 = 0; i2 < colors.size(); i2++) {
                    Color c = colors.get(i2);
                    GDObject.addColor(i*10f, 200f, Math.round(c.red * 255), Math.round(c.green * 255), Math.round(c.blue * 255), i2+1);
                }

                Constant.points3d = new ArrayList<>();
                Constant.faces = new ArrayList<>();
                Constant.points = new ArrayList<>();
            }

            GDObject.send();

            GDObject.request = new ArrayList<>();
        }

    }
}
