export interface SessionInfo {
    sessionId: string;
    gamesPlayed: number;
    playerWins: number;
    computerWins: number;
    draws: number;
    playerWinPercentage: number;
    createdAt: string;
    lastActivity: string;
}