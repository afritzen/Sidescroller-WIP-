package main;

import javax.swing.*;

/**
 * Entry point for the game. Here, the JFrame is initialized.
 */
public class Game {

    public static final String GAME_TITLE = "Sidescroller(WIP)";

    public static void main(String[] args) {

        JFrame window = new JFrame(GAME_TITLE);
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

}
