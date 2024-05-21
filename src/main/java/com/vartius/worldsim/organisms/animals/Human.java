package com.vartius.worldsim.organisms.animals;

import com.vartius.worldsim.world.World;

import java.awt.Graphics;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.utils.KeyHandler;

public class Human extends Animal {
    private int specialAction = 0;
    private int specialActionCooldown = 0;

    public Human(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 4;
        this.strength = 5;
        this.name = "Human";
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.BLUE);
        return "󰙃";
    }

    public void action(KeyHandler keyHandler) throws InterruptedException {
        char key = keyHandler.getKey();
        switch (key) {
            case 'w':
                // move up
                if (world.isInBound(this.x, this.y - 1))
                    if (world.getOrganism(this.x, this.y - 1) == null)
                        this.world.moveOrganism(this.x, this.y, this.x, this.y - 1);
                    else {
                        ((Organism) this.world.getOrganism(this.x, this.y - 1)).collision(this);
                    }
                break;
            case 'a':
                // move left
                if (world.isInBound(this.x - 1, this.y))
                    if (world.getOrganism(this.x - 1, this.y) == null)
                        this.world.moveOrganism(this.x, this.y, this.x - 1, this.y);
                    else {
                        ((Organism) this.world.getOrganism(this.x - 1, this.y)).collision(this);
                    }
                break;
            case 's':
                // move down
                if (world.isInBound(this.x, this.y + 1))
                    if (world.getOrganism(this.x, this.y + 1) == null)
                        this.world.moveOrganism(this.x, this.y, this.x, this.y + 1);
                    else {
                        ((Organism) this.world.getOrganism(this.x, this.y + 1)).collision(this);
                    }
                break;
            case 'd':
                // move right
                if (world.isInBound(this.x + 1, this.y))
                    if (world.getOrganism(this.x + 1, this.y) == null)
                        this.world.moveOrganism(this.x, this.y, this.x + 1, this.y);
                    else {
                        ((Organism) this.world.getOrganism(this.x + 1, this.y)).collision(this);
                    }
                break;
            case 'e':
                // Szybkość antylopy
                if (specialAction == 0 && specialActionCooldown == 0) {
                    specialAction = 5;
                    System.out.println("Antelope speed activated");
                }
                break;
            case 'q':
                // quit
                System.exit(0);
                break;
        }

    }

    @Override
    public void collision(Organism other) {
        if (other.getStrength() > this.strength) {
            this.setAlive(false);
            System.out.println(other.getName() + " eats " + this.getName() + " at " + x + ", " + y);
        } else {
            other.setAlive(false);
            System.out.println(this.getName() + " eats " + other.getName() + " at " + x + ", " + y);
        }
    }
}
