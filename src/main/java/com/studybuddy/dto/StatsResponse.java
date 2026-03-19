package com.studybuddy.dto;

public class StatsResponse {
    private long totalQuizzes;
    private double avgScore;
    private double bestScore;
    private long aiQuizzes;

    public StatsResponse(long totalQuizzes, double avgScore, double bestScore, long aiQuizzes) {
        this.totalQuizzes = totalQuizzes;
        this.avgScore = avgScore;
        this.bestScore = bestScore;
        this.aiQuizzes = aiQuizzes;
    }

    public long getTotalQuizzes() { return totalQuizzes; }
    public void setTotalQuizzes(long totalQuizzes) { this.totalQuizzes = totalQuizzes; }

    public double getAvgScore() { return avgScore; }
    public void setAvgScore(double avgScore) { this.avgScore = avgScore; }

    public double getBestScore() { return bestScore; }
    public void setBestScore(double bestScore) { this.bestScore = bestScore; }

    public long getAiQuizzes() { return aiQuizzes; }
    public void setAiQuizzes(long aiQuizzes) { this.aiQuizzes = aiQuizzes; }
}
