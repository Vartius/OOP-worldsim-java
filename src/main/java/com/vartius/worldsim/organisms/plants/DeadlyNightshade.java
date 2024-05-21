package com.vartius.worldsim.organisms.plants;

import java.util.Random;

import com.vartius.worldsim.world.World;

public class DeadlyNightshade extends Plant {
    private static final Random random = new Random();

    public DeadlyNightshade(int x, int y, World world) {
        super(x, y, world);
        this.strength = 99;
        this.name = "DeadlyNightshade";
    }

    @Override
    public String draw() {
        return "";
    }

}