package com.vartius.worldsim.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.vartius.worldsim.organisms.Organism;
import com.vartius.worldsim.organisms.animals.*;
import com.vartius.worldsim.organisms.plants.*;
import com.vartius.worldsim.utils.KeyHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;

import java.util.ArrayList;
import static java.lang.Math.abs;

public class World {
    Random random = new Random();
    private int width;
    private int height;
    private Organism[][] grid;
    private List<Organism> organisms = new ArrayList<>();
    private int turnCounter;
    private JTextArea logs = new JTextArea();
    private boolean isPlayerTurn = false;

    public World(int width, int height, int organismCount, JTextArea logs) {
        this.width = width;
        this.height = height;
        this.grid = new Organism[width][height];
        this.turnCounter = 0;
        this.logs = logs;
        initializeWorld(organismCount);
    }

    private void initializeWorld(int organismCount) {
        String[] organismsNames = { "Wolf", "Sheep", "Turtle", "Fox", "Antelope", "Grass", "Dandelion", "Guarana",
                "Hogweed", "DeadlyNightshade" };
        int[] position = getRandomFreePosition();
        addOrganism("Human", position[0], position[1]);
        // for (String name : organismsNames) {
        // position = getRandomFreePosition();
        // addOrganism(name, position[0], position[1]);
        // }

        // random organisms
        for (int i = 0; i < organismCount; i++) {
            position = getRandomFreePosition();
            addOrganism(organismsNames[random.nextInt(organismsNames.length)], position[0], position[1]);
        }

        // position = getRandomFreePosition();
        // addOrganism("Turtle", position[0], position[1]);
        // position = getRandomFreePosition();
        // addOrganism("Turtle", position[0], position[1]);

        // position = getRandomFreePosition();
        // addOrganism("Turtle", position[0], position[1]);

        // position = getRandomFreePosition();
        // addOrganism("Turtle", position[0], position[1]);

        // position = getRandomFreePosition();
        // addOrganism("Turtle", position[0], position[1]);

    }

    public List<Organism> sortOrganisms() {
        List<Organism> sortedOrganisms = new ArrayList<>(organisms);
        sortedOrganisms.sort((o1, o2) -> {
            if (o1.getInitiative() == o2.getInitiative()) {
                return o1.getAge() - o2.getAge();
            }
            return o2.getInitiative() - o1.getInitiative();
        });
        return sortedOrganisms;
    }

    public void nextTurn(KeyHandler keyHandler) throws InterruptedException {
        turnCounter++;
        this.organisms = sortOrganisms();
        for (int i = 0; i < organisms.size(); i++) {
            if (!(organisms.get(i) instanceof Human) && isPlayerTurn) {
                continue;
            } else
                isPlayerTurn = false;
            // check if human
            if (organisms.get(i) instanceof Human) {
                Human human = (Human) organisms.get(i);
                if (human.getSpecialActionCooldown() > 0) {
                    human.setSpecialActionCooldown(human.getSpecialActionCooldown() - 1);
                }

                while (!human.action(keyHandler))
                    ;
                if (human.getSpecialAction() > 3) {
                    human.setSpecialAction(human.getSpecialAction() - 1);
                    while (!human.action(keyHandler))
                        ;
                    human.setSpecialActionCooldown(5);
                } else if (human.getSpecialAction() > 0) {
                    human.setSpecialAction(human.getSpecialAction() - 1);
                    if (random.nextInt(100) < 50) {
                        while (!human.action(keyHandler))
                            ;
                    }
                    human.setSpecialActionCooldown(5);
                }
                if (isPlayerTurn) {
                    turnCounter--;
                    return;
                }
            } else if (organisms.get(i).isAlive() && organisms.get(i).getAge() != turnCounter) {
                organisms.get(i).action();
            }
        }

        // remove dead organisms
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] != null && !grid[i][j].isAlive()) {
                    if (grid[i][j] instanceof Human) {
                        addLog("Game over!");
                        System.exit(0);
                    }
                    removeOrganism(grid[i][j]);
                }
            }
        }
        for (int i = 0; i < organisms.size(); i++) {
            if (!organisms.get(i).isAlive()) {
                if (organisms.get(i) instanceof Human) {
                    addLog("Game over!");
                    System.exit(0);
                }
                organisms.remove(i);
                i--;
            }
        }
    }

    public void addOrganism(String name, int x, int y) {
        // grid[organism.getX()][organism.getY()] = organism;
        // organisms.add(organism);
        System.err.println("Adding " + name + " at " + x + ", " + y);
        Organism org = null;
        switch (name) {
            case "Human":
                org = new Human(x, y, this);
                break;
            case "Wolf":
                org = new Wolf(x, y, this);
                break;
            case "Sheep":
                org = new Sheep(x, y, this);
                break;
            case "Turtle":
                org = new Turtle(x, y, this);
                break;
            case "Fox":
                org = new Fox(x, y, this);
                break;
            case "Antelope":
                org = new Antelope(x, y, this);
                break;
            case "Grass":
                org = new Grass(x, y, this);
                break;
            case "Dandelion":
                org = new Dandelion(x, y, this);
                break;
            case "Guarana":
                org = new Guarana(x, y, this);
                break;
            case "Hogweed":
                org = new Hogweed(x, y, this);
                break;
            case "DeadlyNightshade":
                org = new DeadlyNightshade(x, y, this);
                break;
            default:
                break;
        }
        grid[x][y] = org;
        organisms.add(org);
    }

    public void removeOrganism(Organism organism) {
        grid[organism.getX()][organism.getY()] = null;
        organisms.remove(organism);
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        final int fontSize = 180 * 5 / (height > width ? height : width);
        g.setFont(new Font("JetBrainsMono NF", Font.PLAIN, fontSize));
        int cellSize = fontSize;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] != null) {
                    g.drawString(String.valueOf(grid[i][j].draw(g)), i * cellSize + 3,
                            j * cellSize + cellSize - 3);
                }
            }
        }

        // Draw the grid
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= width; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, height * cellSize);
        }
        for (int i = 0; i <= height; i++) {
            g.drawLine(0, i * cellSize, width * cellSize, i * cellSize);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void moveOrganism(int x, int y, int newX, int newY) {
        // if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
        // return;
        // }
        if (!isInBound(newX, newY)) {
            return;
        }
        Organism organism = grid[x][y];
        grid[x][y] = null;
        grid[newX][newY] = organism;
        organism.setPosition(newX, newY);
    }

    public boolean isInBound(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Human getHuman() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] instanceof Human) {
                    return (Human) grid[i][j];
                }
            }
        }
        return null;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public List<int[]> getPossiblePositions(int x, int y, int size) {
        List<int[]> positions = new ArrayList<>();
        for (int i = x - size; i <= x + size; i++) {
            for (int j = y - size; j <= y + size; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height && (i != x || j != y)
                        && abs(i - x) + abs(j - y) <= size) {
                    positions.add(new int[] { i, j });
                }
            }
        }
        return positions;
    }

    public int[] getRandomFreePosition() {
        List<int[]> freePositions = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] == null) {
                    freePositions.add(new int[] { i, j });
                }
            }
        }
        if (freePositions.size() == 0) {
            return null;
        }
        return freePositions.get((int) (Math.random() * freePositions.size()));
    }

    public List<int[]> getPossiblePositionsFox(int x, int y, int strength, int size) {
        List<int[]> positions = getPossiblePositions(x, y, size);
        for (int i = 0; i < positions.size(); i++) {
            int[] position = positions.get(i);
            if (grid[position[0]][position[1]] == null) {
                continue;
            }
            if (grid[position[0]][position[1]].getStrength() > strength) {
                positions.remove(i);
                i--;
            }

        }
        return positions;
    }

    public List<int[]> getFreePositions(int x, int y, int size) {
        List<int[]> positions = getPossiblePositions(x, y, size);
        for (int i = 0; i < positions.size(); i++) {
            int[] position = positions.get(i);
            if (grid[position[0]][position[1]] != null) {
                positions.remove(i);
                i--;
            }
        }
        return positions;
    }

    public Object getOrganism(int newX, int newY) {
        return grid[newX][newY];
    }

    public void drawInfo(Graphics g, int windowWidth, int windowHeight) {
        final int fontSize = 25;
        g.setFont(new Font("JetBrainsMono NF", Font.PLAIN, fontSize));
        g.drawString("Turn: " + turnCounter + " E: specialActivty Q: Quit", 10, 30);
    }

    public void addLog(String log) {
        logs.append(log + "\n");
    }

    public void save() throws IOException {
        // world width, world height, turnCounter
        // specialActionCooldown, specialAction
        // organism count
        // for each organism: name, x, y, age, strength, initiative, alive

        // save to file save.json
        System.out.println("Saving...");
        JSONObject world = new JSONObject();
        world.put("width", width);
        world.put("height", height);
        world.put("turnCounter", turnCounter);
        // world.put("specialActionCooldown", getHuman().getSpecialActionCooldown());
        // world.put("specialAction", getHuman().getSpecialAction());

        JSONArray organismsArray = new JSONArray();
        for (Organism organism : organisms) {
            JSONObject org = new JSONObject();
            if (organism instanceof Human) {
                org.put("specialActionCooldown", ((Human) organism).getSpecialActionCooldown());
                org.put("specialAction", ((Human) organism).getSpecialAction());
            }
            org.put("name", organism.getName());
            org.put("x", organism.getX());
            org.put("y", organism.getY());
            org.put("age", organism.getAge());
            org.put("strength", organism.getStrength());
            org.put("initiative", organism.getInitiative());
            org.put("alive", organism.isAlive());
            organismsArray.put(org);
        }
        world.put("organisms", organismsArray);

        FileWriter file = new FileWriter("save.json");
        file.write(world.toString());
        file.flush();
        file.close();
    }

    public void load(KeyHandler keyHandler) throws IOException, InterruptedException {
        JSONObject world = new JSONObject();
        FileReader file = new FileReader("save.json");
        int c;
        StringBuilder sb = new StringBuilder();
        while ((c = file.read()) != -1) {
            sb.append((char) c);
        }
        file.close();
        world = new JSONObject(sb.toString());

        // delete all current organisms
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = null;
            }
        }
        organisms.clear();

        width = world.getInt("width");
        height = world.getInt("height");
        turnCounter = world.getInt("turnCounter");

        JSONArray organismsArray = world.getJSONArray("organisms");
        for (int i = 0; i < organismsArray.length(); i++) {
            JSONObject org = organismsArray.getJSONObject(i);
            addOrganism(org.getString("name"), org.getInt("x"), org.getInt("y"));
            if (org.has("specialActionCooldown")) {
                Human human = (Human) grid[org.getInt("x")][org.getInt("y")];
                human.setSpecialActionCooldown(org.getInt("specialActionCooldown"));
                human.setSpecialAction(org.getInt("specialAction"));
            }
            Organism organism = grid[org.getInt("x")][org.getInt("y")];
            organism.setAge(org.getInt("age"));
            organism.setStrength(org.getInt("strength"));
            organism.setInitiative(org.getInt("initiative"));
            organism.setAlive(org.getBoolean("alive"));
        }
        isPlayerTurn = true;
    }

}