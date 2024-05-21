package com.vartius.worldsim.organisms.animals;

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
    public String draw() {
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

}
