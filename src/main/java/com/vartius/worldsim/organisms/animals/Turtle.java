package com.vartius.worldsim.organisms.animals;

import java.util.List;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class Turtle extends Animal {
    private static final Random random = new Random();

    public Turtle(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 1;
        this.strength = 2;
        this.name = "Turtle";
    }

    @Override
    public String draw() {
        return "󰳗";
    }

    @Override
    public void action() {
        if (random.nextInt(4) == 0) {
            super.action();
        }
    }

}
