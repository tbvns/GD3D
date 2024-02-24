package xyz.tbvns;

import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static boolean HasOBJ = false;
    public static boolean HasMTL = false;
    public static boolean HasGD = false;
    public static boolean isReady = false;
    public static File OBJ = null;
    public static File MTL = null;
    public static boolean FollowPlayer = true;
    public static boolean UseColor = true;
    public static List<Face> faces = new ArrayList<>();
    public static List<Vector3> points = new ArrayList<>();
    public static List<Vector3> points3d = new ArrayList<>();
}
