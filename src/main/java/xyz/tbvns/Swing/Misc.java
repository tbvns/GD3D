package xyz.tbvns.Swing;

import xyz.tbvns.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Misc {
    public static JTextField FrameSkip = new JTextField(String.valueOf(Constant.SkipFrame));
    public static JTextField FPS = new JTextField(String.valueOf(Constant.FPS));
    public static JComboBox Speed = new JComboBox<>(new Object[]{"Half speed", "Normal speed", "Double speed", "Triple speed", "Quadruple speed"});
    public static JTextField StartingGroup = new JTextField(String.valueOf(Constant.StartingGroup));
    public static JCheckBox OptimizeColor = new JCheckBox("Optimize Color");
    public static JCheckBox OptimizeGradient = new JCheckBox("Optimize Gradient");
    public static JTextField Delay = new JTextField(String.valueOf(Constant.PlaceDelay));

    public static JPanel getMisc() {
        // Panel action management
        Speed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constant.Speed = Speed.getSelectedIndex();
                if (Speed.getSelectedIndex() == 0) {
                    Constant.GameSpeed = 8.4F;
                } else if (Speed.getSelectedIndex() == 1) {
                    Constant.GameSpeed = 10.41667F;
                } else if (Speed.getSelectedIndex() == 2) {
                    Constant.GameSpeed = 12.91667F;
                } else if (Speed.getSelectedIndex() == 3) {
                    Constant.GameSpeed = 15.667F;
                } else if (Speed.getSelectedIndex() == 4) {
                    Constant.GameSpeed = 19.2F;
                }
            }
        });


        // Panel construction
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.setBorder(BorderFactory.createTitledBorder("Misc"));
        jPanel.setPreferredSize(new Dimension(510, 120));

        JPanel Frames = new JPanel();
        Frames.setLayout(new BoxLayout(Frames, BoxLayout.Y_AXIS));
        Frames.setBorder(BorderFactory.createTitledBorder("Frames"));

        JPanel Game = new JPanel();
        Game.setLayout(new BoxLayout(Game, BoxLayout.Y_AXIS));
        Game.setBorder(BorderFactory.createTitledBorder("Game"));

        JPanel Optimisation = new JPanel();
        Optimisation.setLayout(new BoxLayout(Optimisation, BoxLayout.Y_AXIS));
        Optimisation.setBorder(BorderFactory.createTitledBorder("Optimisation"));

        JPanel Other = new JPanel();
        Other.setLayout(new BoxLayout(Other, BoxLayout.Y_AXIS));
        Other.setBorder(BorderFactory.createTitledBorder("Other"));

        Frames.add(new JLabel("Skip frame"));
        Frames.add(FrameSkip);
        Frames.add(new JLabel("FPS"));

        Frames.add(FPS);
        Game.add(new JLabel("Game speed"));
        Speed.setSelectedIndex(Constant.Speed);
        Game.add(Speed);
        Game.add(new JLabel("Starting group"));
        Game.add(StartingGroup);

        Optimisation.add(new JLabel("Triggers"));
        Optimisation.add(OptimizeColor);
        Optimisation.add(OptimizeGradient);
        Optimisation.add(new JLabel(" "));

        Other.add(new JLabel("Place delay (ns)"));

        Delay.setPreferredSize(new Dimension(30, 20));
        Other.add(Delay);
        Other.add(new JLabel(" "));
        Other.add(new JLabel(" "));
        Other.add(new JLabel(" "));

        jPanel.add(Frames);
        jPanel.add(Game);
        jPanel.add(Optimisation);
        jPanel.add(Other);

        return jPanel;
    }
}
