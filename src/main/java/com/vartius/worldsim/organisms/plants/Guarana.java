package com.vartius.worldsim.organisms.plants;

import java.util.Random;

import com.vartius.worldsim.world.World;

public class Guarana extends Plant {
    private static final Random random = new Random();

    public Guarana(int x, int y, World world) {
        super(x, y, world);
        this.strength = 0;
        this.name = "Guarana";
    }

    @Override
    public String draw() {
        return "󰁳";
    }

}