package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public abstract class Animal extends Organism {
    private static final Random random = new Random();

    public Animal(int x, int y, World world) {
        super(x, y, world);
    }

    @Override
    public void action() {
        List<int[]> positions = world.getPossiblePositions(x, y, 1);
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
    public void collision(Organism other) {
        if (other.getName() == this.name) {
            // reproduction
            List<int[]> positions = world.getFreePositions(x, y, 1);
            if (positions.size() > 0 && random.nextInt(100) < 10) {
                int[] position = positions.get(random.nextInt(positions.size()));
                world.addOrganism(this.getName(), position[0], position[1]);
            }
        } else {
            if (other.getStrength() > this.strength) {
                world.addLog(other.getName() + " eats " + this.getName() + " at " + x + ", " + y);
                this.setAlive(false);
                world.moveOrganism(other.getX(), other.getY(), x, y);
            } else {
                world.addLog(this.getName() + " eats " + other.getName() + " at " + x + ", " + y);
                other.setAlive(false);
                world.moveOrganism(x, y, other.getX(), other.getY());
            }
        }
    }

    @Override
    public abstract String draw(Graphics g);
}
