package com.studybuddy.service;

import com.studybuddy.model.Score;
import com.studybuddy.repository.ScoreRepository;
import com.studybuddy.dto.ScoreRequest;
import com.studybuddy.dto.StatsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    // Save a new score
    public Score saveScore(ScoreRequest request) {
        Score score = new Score();
        score.setPlayerName(request.getPlayerName());
        score.setSubject(request.getSubject());
        score.setDifficulty(request.getDifficulty());
        score.setScore(request.getScore());
        score.setTotal(request.getTotal());
        score.setSource(request.getSource() != null ? request.getSource() : "fallback");
        return scoreRepository.save(score);
    }

    // Get all scores
    public List<Score> getAllScores() {
        return scoreRepository.findTop20ByOrderByCreatedAtDesc();
    }

    // Get scores for a player
    public List<Score> getScoresByPlayer(String playerName) {
        return scoreRepository.findByPlayerNameOrderByCreatedAtDesc(playerName);
    }

    // Get leaderboard
    public List<Score> getLeaderboard() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }

    // Get scores by subject
    public List<Score> getScoresBySubject(String subject) {
        return scoreRepository.findBySubjectOrderByScoreDesc(subject);
    }

    // Get global stats
    public StatsResponse getStats() {
        List<Score> allScores = scoreRepository.findAll();
        long totalQuizzes = allScores.size();

        double avgScore = allScores.stream()
            .mapToDouble(Score::getPercentage)
            .average()
            .orElse(0.0);

        double bestScore = allScores.stream()
            .mapToDouble(Score::getPercentage)
            .max()
            .orElse(0.0);

        long aiQuizzes = allScores.stream()
            .filter(s -> "ai".equals(s.getSource()))
            .count();

        return new StatsResponse(totalQuizzes, Math.round(avgScore * 10) / 10.0, Math.round(bestScore * 10) / 10.0, aiQuizzes);
    }

    // Delete a score by ID
    public boolean deleteScore(Long id) {
        if (scoreRepository.existsById(id)) {
            scoreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
