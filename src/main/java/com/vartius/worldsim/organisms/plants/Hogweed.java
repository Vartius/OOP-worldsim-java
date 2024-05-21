package com.vartius.worldsim.organisms.plants;

import java.util.Random;

import com.vartius.worldsim.world.World;

public class Hogweed extends Plant {
    private static final Random random = new Random();

    public Hogweed(int x, int y, World world) {
        super(x, y, world);
        this.strength = 10;
        this.name = "Hogweed";
    }

    @Override
    public String draw() {
        return "";
    }

}