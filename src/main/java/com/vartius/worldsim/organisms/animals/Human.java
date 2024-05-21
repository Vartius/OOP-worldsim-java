package com.vartius.worldsim.organisms.animals;

import com.vartius.worldsim.world.World;
import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.utils.KeyHandler;

public class Human extends Animal {

    public Human(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 4;
        this.strength = 5;
        this.name = "Human";
    }

    @Override
    public String draw() {
        return "󰙃";
    }

    public void action(KeyHandler keyHandler) throws InterruptedException {
        char key = keyHandler.getKey();
        switch (key) {
            case 'w':
                // move up
                this.world.moveOrganism(this.x, this.y, this.x, this.y - 1);
                break;
            case 'a':
                // move left
                this.world.moveOrganism(this.x, this.y, this.x - 1, this.y);
                break;
            case 's':
                // move down
                this.world.moveOrganism(this.x, this.y, this.x, this.y + 1);
                break;
            case 'd':
                // move right
                this.world.moveOrganism(this.x, this.y, this.x + 1, this.y);
                break;
            case 'e':
                // custom action
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
            System.out.println("GameOver");
            System.exit(0);
        } else {
            other.setAlive(false);
        }
    }
}
