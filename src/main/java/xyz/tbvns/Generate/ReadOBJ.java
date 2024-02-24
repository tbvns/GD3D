package xyz.tbvns.Generate;

import xyz.tbvns.Object.CompletedFace;
import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
        reader.lines().forEach(l -> {
            if (l.startsWith("f")) {
                Face face = new Face();
                String substring = l.substring(2);
                String[] cord = substring.split(" ");
                if (cord.length == 3) {
                    for (int i = 0; i < cord.length; i++) {
                        String id = cord[i].split("/")[0];
                        face.points.add(Integer.valueOf(id));
                    }
                    Faces.add(face);
                } else if (cord.length == 4) {
                    for (int i = 0; i < cord.length; i++) {
                        String id = cord[i].split("/")[0];
                        face.points.add(Integer.valueOf(id));
                    }
                    face.Has4Point = true;
                    Faces.add(face);
                } else {
                    System.err.println("A face had more than 4 point, ignoring it");
                }
            }
        });
        return Faces;
    }

    public List<CompletedFace> getCompletedFaces(File OBJFile) throws FileNotFoundException {
        List<Vector3> point = getPoints(OBJFile);
        List<Face> faces = getFace(OBJFile);

        List<CompletedFace> completedFaces = new ArrayList<>();

        faces.forEach(f -> {
            CompletedFace face = new CompletedFace();
            face.Has4Point = f.Has4Point;
            f.points.forEach(p -> {
                face.points.add(point.get(p-1));
            });
            completedFaces.add(face);
        });

        return completedFaces;
    }
}
