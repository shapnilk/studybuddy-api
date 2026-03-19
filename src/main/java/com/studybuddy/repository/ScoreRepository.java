package com.studybuddy.repository;

import com.studybuddy.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    // Get all scores for a player
    List<Score> findByPlayerNameOrderByCreatedAtDesc(String playerName);

    // Get all scores for a subject
    List<Score> findBySubjectOrderByScoreDesc(String subject);

    // Get top 10 scores globally
    List<Score> findTop10ByOrderByScoreDesc();

    // Get scores by difficulty
    List<Score> findByDifficultyOrderByCreatedAtDesc(String difficulty);

    // Get highest score per player
    @Query("SELECT s FROM Score s WHERE s.score = (SELECT MAX(s2.score) FROM Score s2 WHERE s2.playerName = s.playerName)")
    List<Score> findBestScorePerPlayer();

    // Count total quizzes completed
    long count();

    // Get recent scores
    List<Score> findTop20ByOrderByCreatedAtDesc();
}
