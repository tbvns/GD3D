package xyz.tbvns;

import xyz.tbvns.Object.Face;
import xyz.tbvns.Object.Vector3;

import javax.vecmath.Vector3d;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static boolean UseAnimation = false;
    public static boolean HasOBJ = false;
    public static boolean HasMTL = false;
    public static boolean HasGD = false;
    public static boolean isReady = false;
    public static File OBJ = null;
    public static File MTL = null;
    public static boolean FollowPlayer = true;
    public static boolean UseColor = true;
    public static boolean UseKeyframe = true;
    public static boolean UseCameraPath = true;
    public static List<Face> faces = new ArrayList<>();
    public static List<Vector3> points = new ArrayList<>();
    public static List<Vector3> points3d = new ArrayList<>();

    public static String xScreenCenter = "160";
    public static String yScreenCenter = "120";
    public static String screenPositionX = "0";
    public static String screenPositionY = "0";
    public static String screenPositionZ = "20";
    public static String viewAngleX = "180";
    public static String viewAngleY = "0";
    public static String viewAngleZ = "90";
    public static String modelScale = "20";
}
