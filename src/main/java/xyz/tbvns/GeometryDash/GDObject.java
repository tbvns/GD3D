package xyz.tbvns.GeometryDash;

import xyz.tbvns.Constant;
import xyz.tbvns.WebSocket.WSClient;

import java.net.URI;
import java.util.ArrayList;

public class GDObject {
    public static ArrayList<String> request = new ArrayList<>();

    public static void addBasic(Integer objID, Float posX, Float posY) {
        String JSON =
                "{\n" +
                "  \"action\": \"ADD\",\n" +
                "  \"objects\": \"1," + objID + ",2," + posX + ",3," + posY + "\"\n" +
                "}";
        request.add(JSON);
    }

    public static void addBasic(Integer objID, Float posX, Float posY, Integer groupe) {
        String JSON =
                "{\n" +
                "  \"action\": \"ADD\",\n" +
                "  \"objects\": \"1," + objID + ",2," + posX + ",3," + posY + ",57," + groupe + "\"\n" +
                "}";
        request.add(JSON);
    }

    //public static void addGradient(Float posX, Float posY, Integer groupe) {
    //    String JSON =
    //            "{\n" +
    //                    "  \"action\": \"ADD\",\n" +
    //                    "  \"objects\": \"1," + 2903 + ",2," + posX + ",3," + posY + ",57," + groupe + "\"\n" +
    //                    "}";
    //    new WSClient(URI.create("ws://127.0.0.1:1313"), JSON).run();
    //}

    public static void addMove(Float time, Integer Groupe, Float moveX, Float moveY, Float moveTime) {
        String JSON =
                "{\n" +
                "  \"action\": \"ADD\",\n" +
                "  \"objects\": \"1," + 901 + ",2," + time + ",3," + -1000 + ",10," + moveTime + ",28," + moveX + ",29," + moveY +",51," + Groupe + "\"\n" +
                "}";
        request.add(JSON);
    }

    public static void addKeyframeTrigger() {
        String JSON =
                "{\n" +
                        "  \"action\": \"ADD\",\n" +
                        "  \"objects\": \"1," + 3033 + ",2," + 0 + ",3," + 150 + ",76," + 9999 + ",520," + 1 + ",521," + 1 + ",545," + 1 + ",522," + 1 + ",523," + 1 + ",546," + 1 + "\"\n" +
                        "}";
        request.add(JSON);
    }

    public static void addGradient(Float posX, Float posY, Integer groupe, int p1, int p2 ,int p3, int p4, int id, int zorder, int colorID) {
        String JSON =
                "{\n" +
                        "  \"action\": \"ADD\",\n" +
                        "  \"objects\": \"1,2903,174,0,202,1,456,1,208,0,508,0,25," + zorder + ",209," + id + ",2," + posX + ",3," + posY + ",57," + groupe + ",203, " + p1 + ",204, " + p2 + ",205, " + p4 + ",206," + p3 + ",207,1,21," + colorID + ",22," + colorID + "\"\n" +
                        "}";
        request.add(JSON);
    }

    public static void addColor(Float posX, Float posY, int r, int g, int b, int targetColor) {
        String JSON =
                "{\n" +
                        "  \"action\": \"ADD\",\n" +
                        "  \"objects\": \"1,899,2," + posX + ",3," + posY + ",7," + r + ",8," + g + ",9," + b + ",23," + targetColor + "\"\n" +
                        "}";
        request.add(JSON);
    }

    public static void addKeyframe(Float posX, Float posY, int groupeID, int curve, int order, float duration) {
        String JSON =
                   "{\n" +
                   "  \"action\": \"ADD\",\n" +
                   "  \"objects\": \"1,3032,2," + posX + ",3," + posY + ",51," + groupeID + ",373," + groupeID + ",57," + 9999 + ",378, " + curve + ",374," + order + ",10," + duration + "\"\n" +
                   "}";
        request.add(JSON);
    }

    public static void send() {
        new WSClient(URI.create("ws://127.0.0.1:1313"), request).run();
        try {
            Thread.sleep(Constant.PlaceDelay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
