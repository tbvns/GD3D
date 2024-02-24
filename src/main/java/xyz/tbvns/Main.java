package xyz.tbvns;

import xyz.tbvns.Generate.Generate3D;
import xyz.tbvns.WebSocket.CheckGDConnection;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class Main {

    public static JFrame jFrame;
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("GD3D");

        jFrame = frame;

        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        frame.setSize(530, 500);

        JMenuBar bar = new JMenuBar();
        bar.add(HelpMenu());

        frame.setJMenuBar(bar);

        frame.add(FileSelect());
        frame.add(Config());
        frame.add(Status());
        frame.add(Proceed());
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            new Thread(new CheckGDConnection()).start();
            Thread.sleep(200);
        }
    }

    public static JMenu HelpMenu() {
        JMenu menu = new JMenu("Help");

        JMenuItem about = new JMenuItem("About");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem GitHub = new JMenuItem("Github");
        JMenuItem contributor = new JMenuItem("Contributor");

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Error:");
                JOptionPane.showMessageDialog(frame,
                        "Not implemented yet !",
                        "Error !",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(URI.create("https://github.com/tbvns/GD3D/wiki"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GitHub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(URI.create("https://github.com/tbvns/GD3D/"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        contributor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(URI.create("https://github.com/tbvns/GD3D/graphs/contributors"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        menu.add(about);
        menu.add(help);
        menu.add(GitHub);
        menu.add(contributor);

        return menu;
    }

    public static JPanel FileSelect() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 1));
        jPanel.setPreferredSize(new Dimension(120, 120));

        jPanel.setBorder(BorderFactory.createTitledBorder("File"));

        JCheckBox UseAnimation = new JCheckBox("Use animation");
        JButton SelectOBJ = new JButton("Select obj");
        JButton SelectMTL = new JButton("Select mtl");

        SelectOBJ.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Selecte .obj file");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select .obj file");
                FileFilter filter = new FileNameExtensionFilter("wavefront obj file .obj", "obj");
                chooser.setFileFilter(filter);
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    if (chooser.getSelectedFile() != null) {
                        Constant.OBJ = chooser.getSelectedFile();
                        Constant.HasOBJ = true;
                    }
                }

                Refresh();
            }
        });

        SelectMTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Selecte .mtl file");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("material template library file .mtl", "mtl");
                chooser.setFileFilter(filter);
                chooser.setDialogTitle("Select .mtl file");
                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    if (chooser.getSelectedFile() != null) {
                        Constant.MTL = chooser.getSelectedFile();
                        Constant.HasMTL = true;
                    }
                }

                Refresh();
            }
        });


        jPanel.add(UseAnimation);
        jPanel.add(SelectOBJ);
        jPanel.add(new JLabel("Optional:"));
        jPanel.add(SelectMTL);
        return jPanel;
    }

    public static JPanel Config() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 1));
        jPanel.setPreferredSize(new Dimension(120, 120));

        JCheckBox FollowPlayer = new JCheckBox("Follow player", true);
        JCheckBox UseColor = new JCheckBox("Use color", true);

        FollowPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constant.FollowPlayer = !Constant.FollowPlayer;
            }
        });

        UseColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constant.UseColor = !Constant.UseColor;
            }
        });

        jPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        jPanel.add(FollowPlayer);
        jPanel.add(UseColor);

        return jPanel;
    }

    public static JPanel Status() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 1));
        jPanel.setPreferredSize(new Dimension(120, 120));

        jPanel.setBorder(BorderFactory.createTitledBorder("Status"));

        jPanel.add(new JLabel("Has obj file: " + Constant.HasOBJ));
        jPanel.add(new JLabel("Has mtl file: " + Constant.HasMTL));
        jPanel.add(new JLabel("Is gd open: " + Constant.HasGD));
        jPanel.add(new JLabel("Is ready: " + Constant.isReady));

        return jPanel;
    }

    public static JPanel Proceed() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createTitledBorder("Action"));
        jPanel.setPreferredSize(new Dimension(120, 120));

        JButton Proceed = new JButton("Proceed");

        Proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Constant.isReady) {
                    try {
                        new Generate3D().Generate();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JFrame frame = new JFrame("Error:");
                    JOptionPane.showMessageDialog(frame,
                            "Some element are missing\nRequired element: \n  - OBJ file\n  - CHAN file\n  - GD open in the editor",
                            "Error !",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        jPanel.add(Proceed);

        return jPanel;
    }

    public static void Refresh() {
        jFrame.getContentPane().removeAll();

        jFrame.add(FileSelect());
        jFrame.add(Config());
        jFrame.add(Status());
        jFrame.add(Proceed());

        jFrame.revalidate();
        jFrame.repaint();
    }
}