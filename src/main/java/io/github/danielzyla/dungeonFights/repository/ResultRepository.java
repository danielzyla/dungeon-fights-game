package io.github.danielzyla.dungeonFights.repository;

import io.github.danielzyla.dungeonFights.game.ResultDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultRepository {

    public static final int LIMIT_OF_RESULTS_SHOWN = 20;

    public void savePlayerResultIntoDatabase(String playerName, long score) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/score_db", "sa", "");
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS results (player VARCHAR(50) NOT NULL, score BIGINT NOT NULL);";
            statement.executeUpdate(sql);
            sql = "INSERT INTO results VALUES ('" + playerName + "', " + score + ");";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public List<ResultDto> readBestScoresFromDatabase() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<ResultDto> resultDtoList = new ArrayList<>();
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/score_db;IFEXISTS=TRUE", "sa", "");
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS results (player VARCHAR(50) NOT NULL, score BIGINT NOT NULL);";
            statement.executeUpdate(sql);
            sql = "SELECT DISTINCT * FROM results ORDER BY score DESC LIMIT " + LIMIT_OF_RESULTS_SHOWN + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String player = resultSet.getString("player");
                int score = resultSet.getInt("score");
                ResultDto resultDto = new ResultDto(player, score);
                resultDtoList.add(resultDto);
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return resultDtoList;
    }

}
