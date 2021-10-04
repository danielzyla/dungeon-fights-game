package io.github.danielzyla.dungeonFights.view;

import io.github.danielzyla.dungeonFights.component.*;
import io.github.danielzyla.dungeonFights.component.Component;
import io.github.danielzyla.dungeonFights.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class GameBoard {

    private final int cellSize;
    private final Set<Component> componentSet;
    private final GamePanel gamePanel;
    private final static String SOURCE_FILE_NAME = "board.csv";
    private final List<List<String>> componentTypeRows = loadCSVFile();
    private int rowNumber;
    private int colNumber;

    public GameBoard(GamePanel gamePanel) throws IOException {
        this.cellSize = 50;
        this.componentSet = new HashSet<>();
        this.gamePanel = gamePanel;
    }

    private List<List<String>> loadCSVFile() throws IOException {
        BufferedReader input = null;
        List<List<String>> componentTypeRows = new ArrayList<>();
        List<String> types = new ArrayList<>();
        try {
            input = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(GameBoard.class.getResourceAsStream("/csv/" + SOURCE_FILE_NAME))));
            String dataRow;
            while ((dataRow = input.readLine()) != null) {
                types = Arrays.asList(dataRow.split(","));
                componentTypeRows.add(types);
            }
            colNumber = types.size();
            rowNumber = componentTypeRows.size();
        } catch (FileNotFoundException | ExceptionInInitializerError e) {
            e.printStackTrace();
            Thread thread = new Thread(() -> {
                ImageIcon icon = new ImageIcon(String.valueOf(GameBoard.class.getResourceAsStream("/img/emark.png")));
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

    public void drawBoard(Graphics2D g) throws Exception {
        if (componentSet.isEmpty()) {
            int y = -1;
            for (List<String> types : componentTypeRows) {
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
                            case "SL": {
                                Skull skull = new Skull(x * cellSize, y * cellSize, gamePanel);
                                componentSet.add(skull);
                                skull.draw(g);
                            }
                            break;
                            case "GN": {
                                Goblin goblin = new Goblin(x * cellSize, y * cellSize, gamePanel);
                                componentSet.add(goblin);
                                goblin.draw(g);
                            }
                            break;
                            case "GD": {
                                Gold gold = new Gold(x * cellSize, y * cellSize);
                                componentSet.add(gold);
                                gold.draw(g);
                            }
                            break;
                            case "RE": {
                                Rune rune = new Rune(x * cellSize, y * cellSize);
                                componentSet.add(rune);
                                rune.draw(g);
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            componentSet.forEach(component -> {
                try {
                    component.draw(g);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public Set<Component> getComponentSet() {
        return componentSet;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getCellSize() {
        return cellSize;
    }

}
