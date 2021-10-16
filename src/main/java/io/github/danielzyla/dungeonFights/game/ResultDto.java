package io.github.danielzyla.dungeonFights.game;

public class ResultDto {

    String playerName;
    long score;

    public ResultDto(String playerName, long score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long getScore() {
        return score;
    }

}
