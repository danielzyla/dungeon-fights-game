package io.github.danielzyla.dungeonFights.view;

import io.github.danielzyla.dungeonFights.component.Component;
import io.github.danielzyla.dungeonFights.component.Floor;
import io.github.danielzyla.dungeonFights.component.Wall;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {

    public GameBoard(int cellSize, Graphics2D g) throws IOException {
        drawBoard(cellSize, g);
    }

    private List<List<String>> loadCSVFile() throws IOException {
        BufferedReader input = null;
        List<List<String>> componentTypeRows = new ArrayList<>();
        try {
            input = new BufferedReader(new FileReader("src/main/resources/csv/board.csv"));
            String dataRow;
            while ((dataRow = input.readLine()) != null) {
                List<String> types = Arrays.asList(dataRow.split(";"));
                componentTypeRows.add(types);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (input != null) input.close();
        }
        return componentTypeRows;
    }

    private void drawBoard(int cellSize, Graphics2D g) throws IOException {
        List<List<String>> componentTypeRows = loadCSVFile();
        int y = -1;
        for (List<String> types : componentTypeRows) {
            y++;
            int x = -1;
            for (String type : types) {
                x++;
                if (type != null) {
                    switch (type) {
                        case "W": {
                            Wall wall = new Wall(new Point((x * cellSize), (y * cellSize)));
                            this.drawComponent(wall, g);
                        }
                        break;
                        case "F": {
                            Floor floor = new Floor(new Point((x * cellSize), (y * cellSize)));
                            this.drawComponent(floor, g);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void drawComponent(Component component, Graphics2D g) {
        g.drawImage(component.getImage(), (int) component.getPosition().getX(), (int) component.getPosition().getY(), null);
    }
}
