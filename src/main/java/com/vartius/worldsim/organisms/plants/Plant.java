package com.vartius.worldsim.organisms.plants;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public abstract class Plant extends Organism {
    private static final Random random = new Random();

    public Plant(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 0;
    }

    @Override
    public void action() {
        spread();
    }

    private void spread() {
        List<int[]> positions = world.getFreePositions(x, y, 1);
        if (positions.size() > 0 && random.nextInt(100) < 1) {
            int[] position = positions.get(random.nextInt(positions.size()));
            world.addOrganism(this.getName(), position[0], position[1]);
        }
    }

    @Override
    public void collision(Organism other) {
        if (other.getStrength() > this.strength) {
            this.setAlive(false);
            world.addLog(other.getName() + " eats " + this.getName() + " at " + x + ", " + y);
            world.moveOrganism(other.getX(), other.getY(), x, y);
        } else {
            other.setAlive(false);
            world.addLog(this.getName() + " eats " + other.getName() + " at " + x + ", " + y);
            // world.moveOrganism(x, y, other.getX(), other.getY());
        }
    }

    @Override
    public abstract String draw(Graphics g);
}
