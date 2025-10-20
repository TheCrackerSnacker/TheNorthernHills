package com.crackersnacker;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Game extends JPanel implements IUpdatable {
    int x = 0;
    int y = 0;

    public Game(KeyboardInput input) { this(input, 1200, 800); }

    public Game(KeyboardInput input, int width, int height) {
        super();
        this.addKeyListener(input);
        this.setSize(width, height);
    }

    @Override
    public void updateObject(float deltaTime) {
        x += 1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        g.fillRect(x, y, 200, 200);
    }

    @Override
    public void cleanUpObject() {
        System.err.println("Cleaning Up!");
    }
}
