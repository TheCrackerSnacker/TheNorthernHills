package com.crackersnacker;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Game extends JPanel implements IUpdatable{
    int x = 0;
    int y = 0;
    int width;
    int height;

    public Game(int width, int height) {
        super();
        this.width = width;
        this.height = height;
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
