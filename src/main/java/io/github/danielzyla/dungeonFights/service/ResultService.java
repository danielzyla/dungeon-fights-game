package io.github.danielzyla.dungeonFights.service;

import io.github.danielzyla.dungeonFights.game.ResultDto;
import io.github.danielzyla.dungeonFights.repository.ResultRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ResultService extends JFrame {

    private final ResultRepository resultRepository;

    public ResultService() {
        this.resultRepository = new ResultRepository();
    }

    public void showBestScores() throws SQLException {

        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setTitle("Top results");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("TOP RESULTS");
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        jPanel.add(title);

        List<ResultDto> resultDtos = resultRepository.readBestScoresFromDatabase();
        int i = 1;
        for (ResultDto resultDto : resultDtos) {
            JLabel jLabel = new JLabel(i + ": " + resultDto.getPlayerName() + ": " + resultDto.getScore());
            jPanel.add(jLabel);
            i++;
        }

        add(jPanel);
        setVisible(true);
    }

    public void savePlayerResult(String name, long score) throws SQLException {
        resultRepository.savePlayerResultIntoDatabase(name, score);
    }

}
