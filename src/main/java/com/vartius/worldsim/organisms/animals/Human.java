package com.vartius.worldsim.organisms.animals;

import com.vartius.worldsim.world.World;

import java.awt.Graphics;
import java.io.IOException;

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

    public void setSpecialAction(int specialAction) {
        this.specialAction = specialAction;
    }

    public int getSpecialAction() {
        return specialAction;
    }

    public void setSpecialActionCooldown(int specialActionCooldown) {
        this.specialActionCooldown = specialActionCooldown;
    }

    public int getSpecialActionCooldown() {
        return specialActionCooldown;
    }

    @Override
    public String draw(Graphics g) {
        g.setColor(java.awt.Color.BLUE);
        return "󰙃";
    }

    public boolean action(KeyHandler keyHandler) throws InterruptedException {
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
                else
                    return false;
                break;
            case 'a':
                // move left
                if (world.isInBound(this.x - 1, this.y))
                    if (world.getOrganism(this.x - 1, this.y) == null)
                        this.world.moveOrganism(this.x, this.y, this.x - 1, this.y);
                    else {
                        ((Organism) this.world.getOrganism(this.x - 1, this.y)).collision(this);
                    }
                else
                    return false;
                break;
            case 's':
                // move down
                if (world.isInBound(this.x, this.y + 1))
                    if (world.getOrganism(this.x, this.y + 1) == null)
                        this.world.moveOrganism(this.x, this.y, this.x, this.y + 1);
                    else {
                        ((Organism) this.world.getOrganism(this.x, this.y + 1)).collision(this);
                    }
                else
                    return false;
                break;
            case 'd':
                // move right
                if (world.isInBound(this.x + 1, this.y))
                    if (world.getOrganism(this.x + 1, this.y) == null)
                        this.world.moveOrganism(this.x, this.y, this.x + 1, this.y);
                    else {
                        ((Organism) this.world.getOrganism(this.x + 1, this.y)).collision(this);
                    }
                else
                    return false;
                break;
            case 'e':
                // Szybkość antylopy
                if (specialAction == 0 && specialActionCooldown == 0) {
                    specialAction = 5;
                    world.addLog("Antelope speed activated");
                } else {
                    world.addLog(
                            "specialAction: " + specialAction + " specialActionCooldown: " + specialActionCooldown);
                }
                return false;
            case 'q':
                // quit
                System.exit(0);
                break;
            case 'y':
                // save
                try {
                    world.save();
                    world.addLog("Game saved");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            case 'l':
                // load
                try {
                    world.load(keyHandler);
                    world.addLog("Game loaded");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;

    }

    @Override
    public void collision(Organism other) {
        if (other.getStrength() > this.strength) {
            this.setAlive(false);
            world.addLog(other.getName() + " eats " + this.getName() + " at " + x + ", " + y);
        } else {
            other.setAlive(false);
            world.addLog(this.getName() + " eats " + other.getName() + " at " + x + ", " + y);
        }
    }
}
