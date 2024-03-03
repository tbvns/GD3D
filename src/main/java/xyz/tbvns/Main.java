package xyz.tbvns;

import xyz.tbvns.Generate.Generate3D;
import xyz.tbvns.Swing.Camera;
import xyz.tbvns.WebSocket.CheckGDConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class Main {

    public static JFrame jFrame;
    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame frame = new JFrame("GD3D");
        jFrame = frame;
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            frame.setSize(540, 290);
        } else {
            frame.setSize(530, 290);
        }

        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-frame.getSize().height/2);

        frame.setResizable(false);
        frame.setIconImage(ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/GD3D.jpg"))));

        JMenuBar bar = new JMenuBar();
        bar.add(HelpMenu());

        frame.setJMenuBar(bar);

        Refresh();

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

        JCheckBox UseAnimation = new JCheckBox("Use animation", Constant.UseAnimation);
        JButton SelectOBJ = new JButton("Select OBJ");
        JButton SelectMTL = new JButton("Select MTL");

        if (Constant.UseAnimation) {
            SelectOBJ.setText("Select OBJ folder");
            SelectMTL.setText("Select MTL folder");
            jPanel.setPreferredSize(new Dimension(145, 120));
        }

        UseAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constant.UseAnimation = !Constant.UseAnimation;
                Refresh();
            }
        });

        SelectOBJ.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constant.UseAnimation) {
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
                } else {
                    JFrame frame = new JFrame("Selecte .obj folder");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new BorderLayout());
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Select .obj folder");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        if (chooser.getSelectedFile() != null) {
                            Constant.OBJ = chooser.getSelectedFile();
                            Constant.HasOBJ = true;
                        }
                    }
                }

                Refresh();
            }
        });

        SelectMTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Constant.UseAnimation) {
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
                } else {
                    JFrame frame = new JFrame("Selecte .mtl folder");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new BorderLayout());
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Select .mtl folder");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        if (chooser.getSelectedFile() != null) {
                            Constant.MTL = chooser.getSelectedFile();
                            Constant.HasMTL = true;
                        }
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
        if (Constant.UseAnimation) {
            jPanel.setPreferredSize(new Dimension(115, 120));
        }

        JCheckBox FollowPlayer = new JCheckBox("Follow player", Constant.FollowPlayer);
        JCheckBox UseColor = new JCheckBox("Use color", Constant.UseColor);
        JCheckBox UseKeyframe = new JCheckBox("Keyframe", Constant.UseKeyframe);
        //TODO: add fps for animation
        //TODO: add way to make frame move instantly (no interpolation)
        //TODO: add speed multiplier for movement change in gd (purple speed, red speed ...)

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

        UseKeyframe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Constant.UseKeyframe = !Constant.UseKeyframe;
            }
        });


        jPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        jPanel.add(FollowPlayer);
        jPanel.add(UseColor);
        jPanel.add(UseKeyframe);

        return jPanel;
    }

    public static JPanel Status() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, 1));
        jPanel.setPreferredSize(new Dimension(120, 120));

        jPanel.setBorder(BorderFactory.createTitledBorder("Status"));

        if (!Constant.UseAnimation) {
            jPanel.add(new JLabel("Has obj file: " + Constant.HasOBJ));
            jPanel.add(new JLabel("Has mtl file: " + Constant.HasMTL));
        } else {
            jPanel.setPreferredSize(new Dimension(125, 120));
            jPanel.add(new JLabel("Has obj folder: " + Constant.HasOBJ));
            jPanel.add(new JLabel("Has mtl folder: " + Constant.HasMTL));
        }
        jPanel.add(new JLabel("Is gd open: " + Constant.HasGD));
        jPanel.add(new JLabel("Is ready: " + Constant.isReady));

        return jPanel;
    }

    public static JPanel Action() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createTitledBorder("Action"));
        jPanel.setPreferredSize(new Dimension(120, 120));

        if (Constant.UseAnimation) {
            jPanel.setPreferredSize(new Dimension(95, 120));
        }

        JButton Proceed = new JButton("Proceed");

        Proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utils.xScreenCenter = Integer.parseInt(Camera.xScreenCenter.getText());
                    Utils.yScreenCenter = Integer.parseInt(Camera.yScreenCenter.getText());

                    Utils.modelScale = Integer.parseInt(Camera.modelScale.getText());

                    Utils.viewAngle.x = Integer.parseInt(Camera.viewAngleX.getText());
                    Utils.viewAngle.y = Integer.parseInt(Camera.viewAngleY.getText());
                    Utils.viewAngle.z = Integer.parseInt(Camera.viewAngleZ.getText());

                    Utils.screenPosition.x = Double.parseDouble(Camera.screenPositionX.getText());
                    Utils.screenPosition.y = Double.parseDouble(Camera.screenPositionY.getText());
                    Utils.screenPosition.z = Double.parseDouble(Camera.screenPositionZ.getText());
                } catch (Exception ex) {
                    JFrame frame = new JFrame("Error:");
                    JOptionPane.showMessageDialog(frame,
                            "Camera numbers are wrong: \n" + ex.getMessage(),
                            "Error !",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Constant.isReady) {
                    try {
                        if (!Constant.UseAnimation) {
                            new Generate3D().Generate();
                        } else {
                            if (Constant.UseKeyframe) {
                                new Generate3D().GenerateAnimKeyframe();
                            } else {
                                new Generate3D().GenerateAnimMove();
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JFrame frame = new JFrame("Error:");
                    JOptionPane.showMessageDialog(frame,
                            "Some element are missing\nRequired element: \n  - OBJ file\n -  WSLiveEditor mod\n  - GD open in the editor",
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
        jFrame.add(Action());
        jFrame.add(Camera.getCamera());

        jFrame.revalidate();
        jFrame.repaint();
    }
}