package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import java.util.List;

import com.vartius.worldsim.world.World;
import com.vartius.worldsim.organisms.Organism;

import java.util.Random;

public class Wolf extends Animal {
    private static final Random random = new Random();

    public Wolf(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 5;
        this.strength = 9;
        this.name = "Wolf";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.GRAY);
        return "󰩃";
    }

}
