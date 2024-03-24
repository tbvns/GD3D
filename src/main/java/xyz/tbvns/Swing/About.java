package xyz.tbvns.Swing;

import xyz.tbvns.Constant;
import xyz.tbvns.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.TextUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class About {
    public static JFrame about() throws IOException, BadLocationException {
        JFrame jFrame = new JFrame("About GD3D");
        jFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        BufferedImage image = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/GD3Dbanner.png")));
        Image img = image.getScaledInstance(image.getWidth() / 10, image.getHeight() / 10, 1);

        InputStream inputStream = About.class.getResourceAsStream("/About.html");
        JEditorPane editorPane = new JEditorPane("text/html", new String(inputStream.readAllBytes()));
        editorPane.setPreferredSize(new Dimension(350, 200));
        editorPane.setEditable(false);
        editorPane.setFocusable(false);

        JButton button = new JButton("Close");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
            }
        });

        jFrame.add(new JLabel(new ImageIcon(img)));
        jFrame.add(editorPane);
        jFrame.add(button);

        return jFrame;
    }
}
