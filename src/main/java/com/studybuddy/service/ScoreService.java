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

    public List<Score> getAllScores() {
        return scoreRepository.findTop20ByOrderByCreatedAtDesc();
    }

    public List<Score> getScoresByPlayer(String playerName) {
        return scoreRepository.findByPlayerNameOrderByCreatedAtDesc(playerName);
    }

    public List<Score> getLeaderboard() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }

    public List<Score> getScoresBySubject(String subject) {
        return scoreRepository.findBySubjectOrderByScoreDesc(subject);
    }

    public StatsResponse getStats() {
        List<Score> allScores = scoreRepository.findAll();
        long totalQuizzes = allScores.size();
        double avgScore = 0.0;
        double bestScore = 0.0;
        long aiQuizzes = 0;

        if (!allScores.isEmpty()) {
            double total = 0.0;
            double best = 0.0;
            for (Score s : allScores) {
                double pct = s.getTotal() > 0 ? (double) s.getScore() / s.getTotal() * 100 : 0;
                total += pct;
                if (pct > best) best = pct;
                if ("ai".equals(s.getSource())) aiQuizzes++;
            }
            avgScore = Math.round((total / allScores.size()) * 10.0) / 10.0;
            bestScore = Math.round(best * 10.0) / 10.0;
        }

        return new StatsResponse(totalQuizzes, avgScore, bestScore, aiQuizzes);
    }

    public boolean deleteScore(Long id) {
        if (scoreRepository.existsById(id)) {
            scoreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
