package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class Sheep extends Animal {
    private static final Random random = new Random();

    public Sheep(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 4;
        this.strength = 4;
        this.name = "Sheep";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.GRAY);
        return "󰳆";
    }

}
