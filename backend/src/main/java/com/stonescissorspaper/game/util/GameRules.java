package com.stonescissorspaper.game.util;

import com.stonescissorspaper.game.model.enums.Move;
import com.stonescissorspaper.game.model.enums.Result;

public class GameRules {

    public static Result determineWinner(Move playerMove, Move computerMove) {
        if (playerMove == computerMove) {
            return Result.DRAW;
        }

        return switch (playerMove) {
            case STONE -> computerMove == Move.SCISSORS ? Result.WIN : Result.LOSE;
            case SCISSORS -> computerMove == Move.PAPER ? Result.WIN : Result.LOSE;
            case PAPER -> computerMove == Move.STONE ? Result.WIN : Result.LOSE;
            default -> throw new IllegalArgumentException("Unknown move: " + playerMove);
        };
    }

    public static boolean isValidMove(String move) {
        if (move == null) return false;

        try {
            Move.valueOf(move.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
