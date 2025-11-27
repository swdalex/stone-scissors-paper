import {GameResultDetail} from './game-result';
import {SessionInfo} from './session-info';

export interface GameResponse {
    success: boolean;
    message: string;
    gameResult?: GameResultDetail;
    sessionInfo?: SessionInfo;
    timestamp: string;
}