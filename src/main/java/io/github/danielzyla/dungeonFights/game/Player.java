package io.github.danielzyla.dungeonFights.game;

import javax.swing.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends JFrame {

    private final String name;
    private final static Pattern PATTERN = Pattern.compile("\\p{Punct}+");

    public Player(GamePanel gamePanel) {
        String inputName = JOptionPane.showInputDialog(gamePanel, "Enter your name:");
        if (Objects.equals(inputName, "") || inputName == null) {
            this.name = "ANONYMOUS_PLAYER";
        } else {
            while (!validateName(inputName)) {
                inputName = JOptionPane.showInputDialog(gamePanel, "Enter your name:");
            }
            this.name = inputName;
        }
    }

    private boolean validateName(String playerName) {
        Matcher matcher = PATTERN.matcher(playerName);
        if (matcher.find() && matcher.group().equals(playerName)) {
            JOptionPane.showMessageDialog(this,
                    "Illegal characters used!",
                    "Syntax error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    public String getName() {
        return name;
    }

}
