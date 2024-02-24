package xyz.tbvns.Generate;

import xyz.tbvns.Object.Frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCHAN {
    public List<Frame> getFrame(File CHANFile) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(CHANFile));
        ArrayList<Frame> frames = new ArrayList<>();
        reader.lines().forEach(l -> {
            String[] values = l.split("\t");
            Frame frame = new Frame();
            frame.moveX = Float.parseFloat(values[1]);
            frame.moveY = Float.parseFloat(values[2]);
            frame.moveZ = Float.parseFloat(values[3]);
            frame.rotateX = Float.parseFloat(values[4]);
            frame.rotateY = Float.parseFloat(values[5]);
            frame.rotateZ = Float.parseFloat(values[6]);
            frames.add(frame);
        });
        return frames;
    }
}
