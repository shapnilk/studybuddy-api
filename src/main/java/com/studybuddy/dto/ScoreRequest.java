package com.studybuddy.dto;

public class ScoreRequest {
    private String playerName;
    private String subject;
    private String difficulty;
    private int score;
    private int total;
    private String source;

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
