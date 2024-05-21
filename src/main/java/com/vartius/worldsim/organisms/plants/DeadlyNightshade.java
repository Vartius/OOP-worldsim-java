package com.vartius.worldsim.organisms.plants;

import java.awt.Graphics;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class DeadlyNightshade extends Plant {
    private static final Random random = new Random();

    public DeadlyNightshade(int x, int y, World world) {
        super(x, y, world);
        this.strength = 99;
        this.name = "DeadlyNightshade";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.MAGENTA);
        return "";
    }

    @Override
    public void collision(Organism other) {
        super.collision(other);
        other.setAlive(false);
    }

}