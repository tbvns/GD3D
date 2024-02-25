package xyz.tbvns.Swing;

import xyz.tbvns.Constant;

import javax.swing.*;
import java.awt.*;

public class Camera {
    public static JTextField xScreenCenter = new JTextField(Constant.xScreenCenter);
    public static JTextField yScreenCenter = new JTextField(Constant.yScreenCenter);

    public static JTextField screenPositionX = new JTextField(Constant.screenPositionX);
    public static JTextField screenPositionY = new JTextField(Constant.screenPositionY);
    public static JTextField screenPositionZ = new JTextField(Constant.screenPositionZ);

    public static JTextField viewAngleX = new JTextField(Constant.viewAngleX);
    public static JTextField viewAngleY = new JTextField(Constant.viewAngleY);
    public static JTextField viewAngleZ = new JTextField(Constant.viewAngleZ);

    public static JTextField modelScale = new JTextField(Constant.modelScale);
    public static JPanel getCamera() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.setBorder(BorderFactory.createTitledBorder("Camera"));
        jPanel.setPreferredSize(new Dimension(510, 120));

        JPanel ScreenCenter = new JPanel();
        ScreenCenter.setLayout(new BoxLayout(ScreenCenter, BoxLayout.Y_AXIS));
        ScreenCenter.setBorder(BorderFactory.createTitledBorder("Screen center (x/y)"));

        JPanel ScreenPosition = new JPanel();
        ScreenPosition.setLayout(new BoxLayout(ScreenPosition, BoxLayout.Y_AXIS));
        ScreenPosition.setBorder(BorderFactory.createTitledBorder("Screen Position (x/y/z)"));

        JPanel ViewAngle = new JPanel();
        ViewAngle.setLayout(new BoxLayout(ViewAngle, BoxLayout.Y_AXIS));
        ViewAngle.setBorder(BorderFactory.createTitledBorder("View Angle (x/y/z)"));

        ScreenCenter.add(xScreenCenter);
        ScreenCenter.add(yScreenCenter);

        ScreenPosition.add(screenPositionX);
        ScreenPosition.add(screenPositionY);
        ScreenPosition.add(screenPositionZ);

        ViewAngle.add(viewAngleX);
        ViewAngle.add(viewAngleY);
        ViewAngle.add(viewAngleZ);

        ScreenCenter.add(new JLabel("Model scale"));
        ScreenCenter.add(modelScale);

        jPanel.add(ScreenCenter);
        jPanel.add(ScreenPosition);
        jPanel.add(ViewAngle);

        return jPanel;
    }
}
