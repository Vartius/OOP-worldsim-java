package com.vartius.worldsim;

import com.vartius.worldsim.organisms.animals.Human;
import com.vartius.worldsim.utils.KeyHandler;
import com.vartius.worldsim.world.World;
// import com.vartius.worldsim.organisms.animals.Human;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int worldWidth = 20;
        int worldHeight = 20;
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

        final World world = new World(worldWidth, worldHeight);
        final int windowHeight = screenSize.height - 100;
        final int windowWidth = screenSize.width - 100;
        JFrame frame = new JFrame("WorldSim");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        KeyHandler keyHandler = new KeyHandler();
        frame.addKeyListener(keyHandler);
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                world.draw(g, windowWidth, windowHeight);
            }
        };
        frame.add(panel);
        frame.setVisible(true);

        while (true) {
            world.nextTurn(keyHandler);
            System.out.println("Turn: " + world.getTurnCounter());
            frame.repaint();
            ;
        }

    }
}
