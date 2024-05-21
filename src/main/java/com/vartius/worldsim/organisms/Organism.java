package com.vartius.worldsim.organisms;

import java.awt.Graphics;

import com.vartius.worldsim.world.World;

public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected int x;
    protected int y;
    protected World world;
    protected boolean alive;
    protected String name;
    protected int age;

    public Organism(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.alive = true;
        this.age = world.getTurnCounter();
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract void action();

    public abstract void collision(Organism other);

    public abstract String draw(Graphics g);
}
