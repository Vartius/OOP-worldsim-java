package com.vartius.worldsim.organisms.plants;

import java.awt.Graphics;

import com.vartius.worldsim.world.World;

public class Grass extends Plant {

    public Grass(int x, int y, World world) {
        super(x, y, world);
        this.strength = 0;
        this.name = "Grass";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.GREEN);
        return "󱔐";
    }

}