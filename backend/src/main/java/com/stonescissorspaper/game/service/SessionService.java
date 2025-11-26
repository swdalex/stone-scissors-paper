package com.stonescissorspaper.game.service;

import com.stonescissorspaper.game.model.dto.GameResult;
import com.stonescissorspaper.game.model.dto.SessionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SessionService {

    private final Map<String, SessionInfo> activeSessions = new ConcurrentHashMap<>();

    public SessionInfo createSession(String sessionId) {
        SessionInfo session = SessionInfo.builder()
                .sessionId(sessionId)
                .gamesPlayed(0)
                .playerWins(0)
                .computerWins(0)
                .draws(0)
                .createdAt(Instant.now())
                .lastActivity(Instant.now())
                .build();

        activeSessions.put(sessionId, session);
        log.debug("Created new session: {}", sessionId);

        return session;
    }

    public Optional<SessionInfo> getSession(String sessionId) {
        SessionInfo session = activeSessions.get(sessionId);
        if (session != null) {
            session.setLastActivity(Instant.now());
        }
        return Optional.ofNullable(session);
    }

    public boolean sessionExists(String sessionId) {
        return activeSessions.containsKey(sessionId);
    }

    public SessionInfo recordGameResult(String sessionId, GameResult gameResult) {
        SessionInfo session = getSession(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found: " + sessionId));

        session.setGamesPlayed(session.getGamesPlayed() + 1);
        session.setLastActivity(Instant.now());

        switch (gameResult.getResult()) {
            case WIN:
                session.setPlayerWins(session.getPlayerWins() + 1);
                break;
            case LOSE:
                session.setComputerWins(session.getComputerWins() + 1);
                break;
            case DRAW:
                session.setDraws(session.getDraws() + 1);
                break;
        }

        // Calculate win percentage
        if (session.getGamesPlayed() > 0) {
            double winPercentage = (double) session.getPlayerWins() / session.getGamesPlayed() * 100;
            session.setPlayerWinPercentage(Math.round(winPercentage * 100.0) / 100.0);
        }

        log.debug("Updated session statistics: {}", sessionId);
        return session;
    }
}