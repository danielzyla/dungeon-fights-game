package io.github.danielzyla.dungeonFights.game;

import javax.swing.*;
import java.util.Objects;

public class Player extends JFrame {

    private final String name;

    public Player(GamePanel gamePanel) {
        String optionalInput = JOptionPane.showInputDialog(gamePanel, "Enter your name:");
        if (Objects.equals(optionalInput, "") || optionalInput == null) {
            this.name = "ANONYMOUS_PLAYER";
        } else this.name = optionalInput;
    }

    public String getName() {
        return name;
    }

}
