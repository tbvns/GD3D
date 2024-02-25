package xyz.tbvns.Generate;

import xyz.tbvns.Object.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ReadMTL {
    public static HashMap<String, Color> getColors(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String, Color> materials = new HashMap<>();
        AtomicReference<String> name = new AtomicReference<>("");

        reader.lines().forEach(l -> {
            if (l.startsWith("newmtl")) {
                String[] s = l.split(" ");
                name.set(s[1]);
            } else if (l.startsWith("Kd")) {
                String[] s = l.split(" ");
                float r = Float.parseFloat(s[1]);
                float g = Float.parseFloat(s[2]);
                float b = Float.parseFloat(s[3]);

                materials.put(name.get(), new Color(r, g, b));
            }
        });

        return materials;
    }
}
