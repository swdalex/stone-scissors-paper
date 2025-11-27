import {Component, inject, OnInit, signal} from '@angular/core';
import {GameService} from '../../services/game.service';
import {PlayerMoveComponent} from '../player-move/player-move.component';
import {ResultDisplayComponent} from '../result-display/result-display.component';
import {GameResponse} from '../../models/game-response';
import {GameResultDetail} from "../../models/game-result";
import {SessionInfo} from "../../models/session-info";
import {GameMove} from '../../models/game-move';

@Component({
    selector: 'app-game-board',
    standalone: true,
    imports: [PlayerMoveComponent, ResultDisplayComponent],
    template: `
        <div class="flex justify-center">
            <div class="w-full lg:w-2/3 space-y-8">
                @if (sessionInfo()) {
                    <div class="card">
                        <h2 class="text-2xl font-bold text-gray-800 mb-4">Session Stats</h2>
                        <div class="grid grid-cols-2 md:grid-cols-4 gap-4 text-center">
                            <div class="p-4 bg-blue-50 rounded-lg">
                                <div class="text-2xl font-bold text-blue-600">{{ sessionInfo()?.gamesPlayed }}</div>
                                <div class="text-sm text-gray-600">Games Played</div>
                            </div>
                            <div class="p-4 bg-green-50 rounded-lg">
                                <div class="text-2xl font-bold text-green-600">{{ sessionInfo()?.playerWins }}</div>
                                <div class="text-sm text-gray-600">Wins</div>
                            </div>
                            <div class="p-4 bg-red-50 rounded-lg">
                                <div class="text-2xl font-bold text-red-600">{{ sessionInfo()?.computerWins }}</div>
                                <div class="text-sm text-gray-600">Losses</div>
                            </div>
                            <div class="p-4 bg-yellow-50 rounded-lg">
                                <div class="text-2xl font-bold text-yellow-600">{{ sessionInfo()?.playerWinPercentage }}
                                    %
                                </div>
                                <div class="text-sm text-gray-600">Win Rate</div>
                            </div>
                        </div>
                    </div>
                }

                @if (lastResult()) {
                    <app-result-display
                            [gameResult]="lastResult()!"
                            class="block"
                    ></app-result-display>
                }

                <app-player-move
                        (moveSelected)="onMoveSelected($event)"
                        [isLoading]="isLoading()"
                        class="block"
                ></app-player-move>
            </div>
        </div>
    `,
})
export class GameBoardComponent implements OnInit {
    private gameService = inject(GameService);

    lastResult = signal<GameResultDetail | null>(null);
    sessionInfo = signal<SessionInfo | null>(null);
    isLoading = signal(false);

    ngOnInit() {
        this.gameService.loadSessionFromStorage();

        if (!this.gameService.currentSessionId()) {
            this.startNewGame();
        } else {
            this.loadSessionInfo();
        }
    }

    startNewGame() {
        this.isLoading.set(true);
        this.gameService.startNewGame().subscribe({
            next: (response: GameResponse) => {
                this.sessionInfo.set(response.sessionInfo || null);
                this.lastResult.set(null);
                this.isLoading.set(false);
            },
            error: (error) => {
                console.error('Failed to start game:', error);
                this.isLoading.set(false);
            }
        });
    }

    onMoveSelected(move: GameMove) {
        this.isLoading.set(true);
        this.gameService.playMove(move).subscribe({
            next: (response: GameResponse) => {
                this.lastResult.set(response.gameResult || null);
                this.sessionInfo.set(response.sessionInfo || null);
                this.isLoading.set(false);
            },
            error: (error) => {
                console.error('Failed to play move:', error);
                this.isLoading.set(false);
            }
        });
    }

    loadSessionInfo() {
        const sessionId = this.gameService.currentSessionId();
        if (sessionId) {
            this.gameService.getSessionInfo(sessionId).subscribe({
                next: (response: GameResponse) => {
                    this.sessionInfo.set(response.sessionInfo || null);
                },
                error: (error) => {
                    console.error('Failed to load session info:', error);
                }
            });
        }
    }
}