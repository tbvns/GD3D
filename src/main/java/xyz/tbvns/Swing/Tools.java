package xyz.tbvns.Swing;

import xyz.tbvns.Main;
import xyz.tbvns.Maths.BPM;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Tools {
    public JMenu menu() {
        JMenu menu = new JMenu("Tools");

        JMenuItem BPMtoFPS = new JMenuItem("BPM to FPS");
        BPMtoFPS.addActionListener(e -> {
            try {
                BPMtoFPS();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menu.add(BPMtoFPS);
        return menu;
    }

    public JFrame BPMtoFPS() throws IOException {
        JFrame jFrame = new JFrame("BPM to FPS calculator");
        JDialog frame = new JDialog(jFrame, "BPM to FPS calculator");
        frame.setLayout(null);
        frame.setSize(120, 180);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-frame.getSize().height/2);
        frame.setResizable(false);
        frame.setIconImage(ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/GD3D.png"))));

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            frame.setSize(120, 220);
        }

        JLabel BPMlabel = new JLabel("BPM:");
        BPMlabel.setBounds(10, 10, 40, 20);

        JTextField BPMtextField = new JTextField("80");
        BPMtextField.setBounds(10, 30, 100, 20);

        JLabel BARlabel = new JLabel("Beats per bar:");
        BARlabel.setBounds(10, 50, 200, 20);

        JTextField BARtextField = new JTextField("4");
        BARtextField.setBounds(10, 70, 100, 20);

        JButton cal = new JButton("Calculate");
        cal.setBounds(10, 100, 100, 30);
        cal.addActionListener(e -> {
            try {
                float BPM = Float.parseFloat(BPMtextField.getText());
                float BAR = Float.parseFloat(BARtextField.getText());

                xyz.tbvns.Maths.BPM bpm = new BPM(BPM, BAR);

                JFrame newFrame = new JFrame("Error:");
                JOptionPane.showMessageDialog(newFrame,
                        "Best fps: " + bpm.toFPS() + "\nMinimal FPS: " + bpm.toMinimalFPS(),
                        "BPM to FPS converter",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception err) {
                JFrame errf = new JFrame("Error:");
                JOptionPane.showMessageDialog(errf,
                        "Numbers are wrong: \n" + err.getMessage(),
                        "Error !",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton help = new JButton("Help");
        help.setBounds(10, 140, 100, 30);

        help.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/tbvns/GD3D/wiki/Tools#bpm-to-fps-calculator"));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        frame.add(BPMlabel);
        frame.add(BPMtextField);
        frame.add(BARlabel);
        frame.add(BARtextField);
        frame.add(cal);
        frame.add(help);

        frame.setVisible(true);
        return jFrame;
    }
}
