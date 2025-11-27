import {Component, Input, OnChanges} from '@angular/core';
import {DatePipe, NgClass} from '@angular/common';
import {GameResult, GameResultDetail} from '../../models/game-result';

@Component({
    selector: 'app-result-display',
    standalone: true,
    imports: [NgClass, DatePipe],
    template: `
        @if (gameResult) {
            <div
                    [ngClass]="getResultCardClasses()"
                    class="card text-center transition-all duration-300"
            >
                <!-- Result Header -->
                <div class="mb-6">
                    <div class="text-6xl mb-4">{{ getResultEmoji() }}</div>
                    <h2 class="text-3xl font-bold mb-2">{{ getResultText() }}</h2>
                    <p class="text-lg text-gray-600">{{ gameResult.message }}</p>
                </div>

                <!-- Moves Comparison -->
                <div class="flex justify-center items-center space-x-8 mb-6">
                    <!-- Player Move -->
                    <div class="text-center">
                        <div class="text-2xl font-semibold text-gray-700 mb-2">You</div>
                        <div class="text-5xl">{{ getMoveEmoji(gameResult.playerMove) }}</div>
                        <div class="text-sm text-gray-600 mt-2">{{ gameResult.playerMove }}</div>
                    </div>

                    <!-- VS -->
                    <div class="text-2xl font-bold text-gray-400">VS</div>

                    <!-- Computer Move -->
                    <div class="text-center">
                        <div class="text-2xl font-semibold text-gray-700 mb-2">Computer</div>
                        <div class="text-5xl">{{ getMoveEmoji(gameResult.computerMove) }}</div>
                        <div class="text-sm text-gray-600 mt-2">{{ gameResult.computerMove }}</div>
                    </div>
                </div>

                <!-- Timestamp -->
                <div class="text-sm text-gray-500">
                    Played at {{ gameResult.timestamp | date:'medium' }}
                </div>
            </div>
        }
    `,
})
export class ResultDisplayComponent implements OnChanges {
    @Input() gameResult: GameResultDetail | null = null;

    ngOnChanges() {
        if (this.gameResult) {
            // Trigger animation when new result comes in
            console.log('New game result:', this.gameResult);
        }
    }

    getResultCardClasses(): string {
        if (!this.gameResult) return '';

        const baseClasses = 'card text-center';
        const resultClasses = {
            [GameResult.WIN]: 'win-glow bg-gradient-to-r from-green-50 to-emerald-50 border-green-200',
            [GameResult.LOSE]: 'lose-glow bg-gradient-to-r from-red-50 to-pink-50 border-red-200',
            [GameResult.DRAW]: 'draw-glow bg-gradient-to-r from-yellow-50 to-amber-50 border-yellow-200'
        }[this.gameResult.result];

        return `${baseClasses} ${resultClasses}`;
    }

    getResultEmoji(): string {
        if (!this.gameResult) return '';

        const emojis = {
            [GameResult.WIN]: '🎉',
            [GameResult.LOSE]: '😞',
            [GameResult.DRAW]: '🤝'
        };
        return emojis[this.gameResult.result];
    }

    getResultText(): string {
        if (!this.gameResult) return '';

        const texts = {
            [GameResult.WIN]: 'You Win!',
            [GameResult.LOSE]: 'You Lose!',
            [GameResult.DRAW]: "It's a Draw!"
        };
        return texts[this.gameResult.result];
    }

    getMoveEmoji(move: string): string {
        const emojis = {
            'STONE': '✊',
            'SCISSORS': '✌️',
            'PAPER': '✋'
        };
        return emojis[move as 'STONE' | 'SCISSORS' | 'PAPER'] || '❓';
    }
}