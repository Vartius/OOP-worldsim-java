package com.vartius.worldsim.organisms.animals;

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
        if (other.getStrength() > this.strength) {
            this.setAlive(false);
        } else {
            other.setAlive(false);
        }
    }

    @Override
    public abstract String draw();
}
