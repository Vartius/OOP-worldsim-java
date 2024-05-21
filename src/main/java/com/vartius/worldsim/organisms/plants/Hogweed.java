package com.vartius.worldsim.organisms.plants;

import java.awt.Graphics;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.organisms.animals.Animal;
import com.vartius.worldsim.world.World;

public class Hogweed extends Plant {

    public Hogweed(int x, int y, World world) {
        super(x, y, world);
        this.strength = 10;
        this.name = "Hogweed";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.RED);
        return "";
    }

    @Override
    public void action() {
        super.action();
        int x = this.x;
        int y = this.y;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (world.isInBound(x + i, y + j) && i != 0 && j != 0) {
                    if (world.getOrganism(x + i, y + j) != null) {
                        if (world.getOrganism(x + i, y + j) instanceof Animal)
                            ((Organism) world.getOrganism(x + i, y + j)).setAlive(false);
                    }
                }
            }
        }
    }

    @Override
    public void collision(Organism other) {
        super.collision(other);
        other.setAlive(false);
    }

}