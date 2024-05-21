package com.vartius.worldsim.organisms.plants;

import java.awt.Graphics;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class Guarana extends Plant {

    public Guarana(int x, int y, World world) {
        super(x, y, world);
        this.strength = 0;
        this.name = "Guarana";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.GREEN);
        return "󰁳";
    }

    @Override
    public void collision(Organism other) {
        super.collision(other);
        other.setStrength(other.getStrength() + 3);
        world.addLog(other.getName() + " ate " + this.getName() + " and gained 3 strength");
    }

}