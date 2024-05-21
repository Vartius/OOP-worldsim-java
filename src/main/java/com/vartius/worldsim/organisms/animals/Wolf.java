package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;

import com.vartius.worldsim.world.World;

public class Wolf extends Animal {

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
