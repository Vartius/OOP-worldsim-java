package com.vartius.worldsim.organisms.animals;

import java.awt.Graphics;
import java.util.Random;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.world.World;

public class Turtle extends Animal {
    private static final Random random = new Random();

    public Turtle(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 1;
        this.strength = 2;
        this.name = "Turtle";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.GREEN);
        return "󰳗";
    }

    @Override
    public void action() {
        if (random.nextInt(4) == 0) {
            super.action();
        }
    }

    @Override
    public void collision(Organism attacker) {
        if (attacker.getStrength() < 5 && !(attacker instanceof Turtle)) {
            System.out.println(attacker.getName() + " attacks " + this.name + " but it's too strong");
            return;
        }
        super.collision(attacker);
    }

}
