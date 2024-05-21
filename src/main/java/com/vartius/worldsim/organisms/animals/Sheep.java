package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import com.vartius.worldsim.world.World;

public class Sheep extends Animal {

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
