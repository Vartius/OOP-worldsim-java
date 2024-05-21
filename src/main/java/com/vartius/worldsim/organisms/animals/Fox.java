package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class Fox extends Animal {
    private static final Random random = new Random();

    public Fox(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 7;
        this.strength = 3;
        this.name = "Fox";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.ORANGE);
        return "󰄛";
    }

    @Override
    public void action() {
        List<int[]> positions = world.getPossiblePositionsFox(x, y, strength, 1);
        int[] newPosition = positions.get(random.nextInt(positions.size()));
        int newX = newPosition[0];
        int newY = newPosition[1];
        if (world.getOrganism(newX, newY) == null) {
            world.moveOrganism(x, y, newX, newY);
        } else {
            ((Organism) world.getOrganism(newX, newY)).collision(this);
        }
    }

}
