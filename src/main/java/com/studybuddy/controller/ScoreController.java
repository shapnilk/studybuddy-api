package com.studybuddy.controller;

import com.studybuddy.model.Score;
import com.studybuddy.service.ScoreService;
import com.studybuddy.dto.ScoreRequest;
import com.studybuddy.dto.StatsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    // POST /api/scores — Save a new score
    @PostMapping
    public ResponseEntity<?> saveScore(@RequestBody ScoreRequest request) {
        try {
            if (request.getPlayerName() == null || request.getPlayerName().isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Player name is required"));
            }
            if (request.getSubject() == null || request.getSubject().isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Subject is required"));
            }
            if (request.getScore() < 0 || request.getTotal() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid score or total"));
            }
            Score saved = scoreService.saveScore(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to save score: " + e.getMessage()));
        }
    }

    // GET /api/scores — Get recent scores
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        return ResponseEntity.ok(scoreService.getAllScores());
    }

    // GET /api/scores/player/{name} — Get scores for a player
    @GetMapping("/player/{name}")
    public ResponseEntity<List<Score>> getScoresByPlayer(@PathVariable String name) {
        return ResponseEntity.ok(scoreService.getScoresByPlayer(name));
    }

    // GET /api/scores/leaderboard — Get top 10 scores
    @GetMapping("/leaderboard")
    public ResponseEntity<List<Score>> getLeaderboard() {
        return ResponseEntity.ok(scoreService.getLeaderboard());
    }

    // GET /api/scores/subject/{subject} — Get scores by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Score>> getScoresBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(scoreService.getScoresBySubject(subject));
    }

    // GET /api/scores/stats — Get global stats
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(scoreService.getStats());
    }

    // DELETE /api/scores/{id} — Delete a score
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScore(@PathVariable Long id) {
        boolean deleted = scoreService.deleteScore(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Score deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/scores/health — Health check
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "StudyBuddy API"));
    }
}
