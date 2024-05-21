package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class Antelope extends Animal {
    private static final Random random = new Random();

    public Antelope(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 4;
        this.strength = 4;
        this.name = "Antelope";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.ORANGE);
        return "󱌃";
    }

    @Override
    public void action() {
        List<int[]> positions = world.getPossiblePositions(x, y, 2);
        int[] newPosition = positions.get(random.nextInt(positions.size()));
        int newX = newPosition[0];
        int newY = newPosition[1];
        if (world.getOrganism(newX, newY) == null) {
            world.moveOrganism(x, y, newX, newY);
        } else {
            ((Organism) world.getOrganism(newX, newY)).collision(this);
        }
    }

    @Override
    public void collision(Organism attacker) {
        if (random.nextInt(2) == 0) {
            List<int[]> positions = world.getFreePositions(x, y, 1);
            if (positions.size() > 0) {
                int[] position = positions.get(random.nextInt(positions.size()));
                world.moveOrganism(x, y, position[0], position[1]);
                world.addLog(this.name + " escapes from " + attacker.getName() + " at " + x + ", " + y);
            } else {
                super.collision(attacker);
            }
        } else {
            super.collision(attacker);
        }
    }

}
