package com.crackersnacker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("KEY: " + e.getKeyChar() + ", CODE: " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
}
