package com.vartius.worldsim.organisms.plants;

import java.awt.Graphics;

import com.vartius.worldsim.world.World;

public class Dandelion extends Plant {

    public Dandelion(int x, int y, World world) {
        super(x, y, world);
        this.strength = 0;
        this.name = "Dandelion";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.YELLOW);
        return "󰧱";
    }

    @Override
    public void action() {
        for (int i = 0; i < 3; i++) {
            super.action();
        }
    }
}