package com.vartius.worldsim;

import com.vartius.worldsim.utils.KeyHandler;
import com.vartius.worldsim.world.World;
// import com.vartius.worldsim.organisms.animals.Human;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Matsvei Kasparovich, 201627");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int worldWidth = 20;
        int worldHeight = 20;
        int organismCount = 20;
        while (true) {
            String input = javax.swing.JOptionPane.showInputDialog("Enter world width:");
            if (input == null) {
                System.exit(0);
            }
            // check if input is a number
            if (!input.matches("[0-9]+") || input.isEmpty()) {
                continue;
            }
            worldWidth = Integer.parseInt(input);
            input = javax.swing.JOptionPane.showInputDialog("Enter world height:");
            if (input == null) {
                System.exit(0);
            }
            // check if input is a number
            if (!input.matches("[0-9]+") || input.isEmpty()) {
                continue;
            }
            worldHeight = Integer.parseInt(input);
            input = javax.swing.JOptionPane.showInputDialog("Enter organism count:");
            if (input == null) {
                System.exit(0);
            }
            // check if input is a number
            if (!input.matches("[0-9]+") || input.isEmpty()) {
                continue;
            }
            organismCount = Integer.parseInt(input);
            if (organismCount > 0 && worldWidth > 0 && worldHeight > 0) {
                break;
            }
        }
        final int windowHeight = screenSize.height - 100;
        final int windowWidth = screenSize.width - 100;
        JFrame frame = new JFrame("WorldSim");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        KeyHandler keyHandler = new KeyHandler();
        frame.addKeyListener(keyHandler);

        frame.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("JetBrainsMono NF", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(windowWidth / 2, windowHeight - 100));
        frame.add(scrollPane, BorderLayout.EAST);
        // frame.add(textArea, BorderLayout.EAST);
        final World world = new World(worldWidth, worldHeight, organismCount, textArea);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                world.draw(g, windowWidth, windowHeight);
            }
        };
        panel.setPreferredSize(new Dimension(windowWidth / 2, windowHeight - 100));
        panel.setLayout(new GridLayout(worldWidth, worldHeight));
        frame.add(panel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                world.drawInfo(g, windowWidth, windowHeight);
            }
        };
        infoPanel.setPreferredSize(new Dimension(windowWidth, 40));
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        final int worldWidthFinal1 = worldWidth;
        final int worldHeightFinal1 = worldHeight;
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSize = 180 * 5 / (worldHeightFinal1 > worldWidthFinal1 ? worldHeightFinal1 : worldWidthFinal1);
                int i = e.getX() / cellSize;
                int j = e.getY() / cellSize;
                if (i < worldWidthFinal1 && j < worldHeightFinal1) {
                    System.out.println("Cell clicked: " + i + "," + j);
                    world.clickCell(i, j);
                }
            }
        });

        while (true) {
            world.nextTurn(keyHandler);
            if (world.getWidth() != worldWidth || world.getHeight() != worldHeight) {
                worldWidth = world.getWidth();
                worldHeight = world.getHeight();
                panel.setPreferredSize(new Dimension(windowWidth / 2, windowHeight - 100));
                final int worldWidthFinal = worldWidth;
                final int worldHeightFinal = worldHeight;
                System.out.println("World size changed to: " + worldWidth + "x" + worldHeight);
                for (MouseListener listener : panel.getMouseListeners()) {
                    panel.removeMouseListener(listener);
                }
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int cellSize = 180 * 5
                                / (worldHeightFinal > worldWidthFinal ? worldHeightFinal : worldWidthFinal);
                        int i = e.getX() / cellSize;
                        int j = e.getY() / cellSize;
                        if (i < worldWidthFinal && j < worldHeightFinal) {
                            System.out.println("Cell clicked: " + i + "," + j);
                            world.clickCell(i, j);
                        }
                    }
                });

                frame.pack();
            }
            System.out.println("Turn: " + world.getTurnCounter());
            frame.repaint();
            ;
        }

    }
}
