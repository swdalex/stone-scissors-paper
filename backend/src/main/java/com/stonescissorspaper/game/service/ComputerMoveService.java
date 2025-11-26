package com.stonescissorspaper.game.service;

import com.stonescissorspaper.game.model.enums.Move;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ComputerMoveService {

    private final Random random = new Random();
    private final Move[] moves = Move.values();

    /**
     * Generates a random move for the computer player
     */
    public Move generateComputerMove() {
        int randomIndex = random.nextInt(moves.length);
        return moves[randomIndex];
    }
}