package io.github.danielzyla.dungeonFights.view;

import io.github.danielzyla.dungeonFights.component.Component;
import io.github.danielzyla.dungeonFights.component.Ghost;
import io.github.danielzyla.dungeonFights.component.Gold;
import io.github.danielzyla.dungeonFights.component.Wall;
import io.github.danielzyla.dungeonFights.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class GameBoard {

    private final int cellSize;
    private final Set<Component> componentSet;
    private final GamePanel gamePanel;
    private final static String SOURCE_FILE_NAME = "board.csv";
    private static List<List<String>> COMPONENT_TYPE_ROWS = null;

    static {
        try {
            COMPONENT_TYPE_ROWS = loadCSVFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameBoard(int cellSize, GamePanel gamePanel) {
        this.cellSize = cellSize;
        this.componentSet = new HashSet<>();
        this.gamePanel = gamePanel;
    }

    private static List<List<String>> loadCSVFile() throws IOException {
        BufferedReader input = null;
        List<List<String>> componentTypeRows = new ArrayList<>();
        try {
            input = new BufferedReader(new FileReader("src/main/resources/csv/" + SOURCE_FILE_NAME));
            String dataRow;
            while ((dataRow = input.readLine()) != null) {
                List<String> types = Arrays.asList(dataRow.split(","));
                componentTypeRows.add(types);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Thread thread = new Thread(() -> {
                ImageIcon icon = new ImageIcon("src/main/resources/img/emark.png");
                JOptionPane.showMessageDialog(
                        null,
                        "Nie znaleziono pliku " + SOURCE_FILE_NAME,
                        "Game alert",
                        JOptionPane.ERROR_MESSAGE,
                        icon
                );
            });
            thread.start();
        } finally {
            if (input != null) input.close();
        }
        return componentTypeRows;
    }

    public void drawBoard(Graphics2D g) throws IOException {
        if (componentSet.isEmpty()) {
            int y = -1;
            for (List<String> types : COMPONENT_TYPE_ROWS) {
                y++;
                int x = -1;
                for (String type : types) {
                    x++;
                    if (type != null) {
                        switch (type) {
                            case "W": {
                                Wall wall = new Wall(x * cellSize, y * cellSize);
                                componentSet.add(wall);
                                wall.draw(g);
                            }
                            break;
                            case "GT": {
                                Ghost ghost = new Ghost(x * cellSize, y * cellSize, gamePanel);
                                componentSet.add(ghost);
                                ghost.draw(g);
                            }
                            break;
                            case "GD": {
                                Gold gold = new Gold(x * cellSize, y * cellSize);
                                componentSet.add(gold);
                                gold.draw(g);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            componentSet.forEach(component -> component.draw(g));
        }
    }

    public Set<Component> getComponentSet() {
        return componentSet;
    }
}
