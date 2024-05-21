package com.vartius.worldsim;

import com.vartius.worldsim.utils.KeyHandler;
import com.vartius.worldsim.world.World;
// import com.vartius.worldsim.organisms.animals.Human;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int worldWidth = 40;
        int worldHeight = 40;
        int organismCount = 40;
        // while (true) {
        // String input = javax.swing.JOptionPane.showInputDialog("Enter world width:");
        // if (input == null) {
        // System.exit(0);
        // }
        // // check if input is a number
        // if (!input.matches("[0-9]+") || input.isEmpty()) {
        // continue;
        // }
        // worldWidth = Integer.parseInt(input);
        // input = javax.swing.JOptionPane.showInputDialog("Enter world height:");
        // if (input == null) {
        // System.exit(0);
        // }
        // // check if input is a number
        // if (!input.matches("[0-9]+") || input.isEmpty()) {
        // continue;
        // }
        // worldHeight = Integer.parseInt(input);
        // if (worldWidth > 0 && worldHeight > 0) {
        // break;
        // }
        // }
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

        while (true) {
            world.nextTurn(keyHandler);
            System.out.println("Turn: " + world.getTurnCounter());
            frame.repaint();
            ;
        }

    }
}
