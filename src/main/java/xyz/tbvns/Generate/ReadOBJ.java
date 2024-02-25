package xyz.tbvns.Generate;

import xyz.tbvns.Constant;
import xyz.tbvns.Object.Color;
import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ReadOBJ {
    public ArrayList<Vector3> getPoints(File OBJFile) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(OBJFile));
        ArrayList<Vector3> Points = new ArrayList<>();
        reader.lines().forEach(l -> {
            if (l.startsWith("v ")) {
                String substring = l.substring(2);
                String[] cord = substring.split(" ");
                Vector3 vector3 = new Vector3();
                vector3.x = Float.parseFloat(cord[0]);
                vector3.y = Float.parseFloat(cord[1]);
                vector3.z = Float.parseFloat(cord[2]);
                Points.add(vector3);
            }
        });
        return Points;
    }

    public ArrayList<Face> getFace(File OBJFile) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(OBJFile));
        ArrayList<Face> Faces = new ArrayList<>();
        AtomicReference<HashMap<String, Color>> materials = new AtomicReference<>(new HashMap<>());
        AtomicReference<Color> color = new AtomicReference<>(new Color(0, 0, 0));
        reader.lines().forEach(l -> {
            if (Constant.HasMTL) {
                try {
                    materials.set(ReadMTL.getColors(Constant.MTL));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (Constant.HasMTL) {
                if (l.startsWith("usemtl")) {
                    color.set(materials.get().get(l.split(" ")[1]));
                }
            }
            if (l.startsWith("f")) {
                Face face = new Face();
                String substring = l.substring(2);
                String[] cord = substring.split(" ");
                if (cord.length == 3) {
                    for (int i = 0; i < cord.length; i++) {
                        String id = cord[i].split("/")[0];
                        face.points.add(Integer.valueOf(id));
                    }
                    if (Constant.HasMTL) {
                        face.color = color.get();
                    }
                    Faces.add(face);
                } else if (cord.length == 4) {
                    for (int i = 0; i < cord.length; i++) {
                        String id = cord[i].split("/")[0];
                        face.points.add(Integer.valueOf(id));
                    }
                    face.Has4Point = true;

                    if (Constant.HasMTL) {
                        face.color = color.get();
                    }

                    Faces.add(face);
                } else {
                    System.err.println("A face had more than 4 point, ignoring it");
                }
            }
        });
        return Faces;
    }
}
