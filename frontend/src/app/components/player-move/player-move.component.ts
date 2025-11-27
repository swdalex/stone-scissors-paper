import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass} from '@angular/common';
import {ALL_MOVES, GameMove} from '../../models/game-move';

@Component({
    selector: 'app-player-move',
    standalone: true,
    imports: [NgClass],
    template: `
        <div class="card text-center">
            <h2 class="text-2xl font-bold text-gray-800 mb-6">Choose Your Move</h2>

            <div class="flex justify-center space-x-6 mb-6">
                @for (move of availableMoves; track move) {
                    <button
                            (click)="selectMove(move)"
                            [disabled]="isLoading"
                            [ngClass]="getMoveButtonClass(move)"
                    >
                        <span class="text-4xl mb-2 block">{{ getMoveEmoji(move) }}</span>
                        <span class="font-semibold">{{ move }}</span>
                    </button>
                }
            </div>

            @if (isLoading) {
                <div class="flex justify-center items-center space-x-2 text-gray-600">
                    <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-primary-500"></div>
                    <span>Processing your move...</span>
                </div>
            }

            <div class="text-sm text-gray-500 mt-4">
                Click on a symbol to make your move!
            </div>
        </div>
    `,
})
export class PlayerMoveComponent {
    @Output() moveSelected = new EventEmitter<GameMove>();
    @Input() isLoading = false;

    availableMoves: GameMove[] = ALL_MOVES;

    selectMove(move: GameMove) {
        if (!this.isLoading) {
            this.moveSelected.emit(move);
        }
    }

    getMoveButtonClass(move: GameMove): string {
        const baseClasses = 'move-button flex flex-col items-center justify-center';
        const stateClasses = this.isLoading
            ? 'opacity-50 cursor-not-allowed'
            : 'cursor-pointer hover:shadow-xl';

        const colorClasses = {
            [GameMove.STONE]: 'bg-red-100 hover:bg-red-200 text-red-700 border-red-300',
            [GameMove.SCISSORS]: 'bg-green-100 hover:bg-green-200 text-green-700 border-green-300',
            [GameMove.PAPER]: 'bg-blue-100 hover:bg-blue-200 text-blue-700 border-blue-300'
        }[move];

        return `${baseClasses} ${stateClasses} ${colorClasses}`;
    }

    getMoveEmoji(move: GameMove): string {
        const emojis = {
            [GameMove.STONE]: '✊',
            [GameMove.SCISSORS]: '✌️',
            [GameMove.PAPER]: '✋'
        };
        return emojis[move];
    }
}