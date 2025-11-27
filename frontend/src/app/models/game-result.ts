import {GameMove} from "./game-move";

export enum GameResult {
    WIN = 'WIN',
    LOSE = 'LOSE',
    DRAW = 'DRAW'
}

export interface GameResultDetail {
    playerMove: GameMove;
    computerMove: GameMove;
    result: GameResult;
    message: string;
    timestamp: string;
}