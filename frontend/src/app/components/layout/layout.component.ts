import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';

@Component({
    selector: 'app-layout',
    standalone: true,
    imports: [RouterOutlet],
    template: `
        <div class="min-h-screen bg-gradient-to-br from-blue-500 via-purple-600 to-purple-800">
            <header class="bg-white/10 backdrop-blur-md border-b border-white/20">
                <div class="container mx-auto px-4 py-4">
                    <div class="flex justify-between items-center">
                        <h1 class="text-3xl font-bold text-white">
                            🎮 Stone-Scissors-Paper
                        </h1>
                        <div class="flex items-center space-x-4">
                            <button
                                    (click)="newGame()"
                                    class="px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded-lg transition-colors"
                            >
                                🚀 New Game
                            </button>
                        </div>
                    </div>
                </div>
            </header>

            <main class="container mx-auto px-4 py-8">
                <router-outlet></router-outlet>
            </main>

            <footer class="bg-black/20 text-white/70 text-center py-4 mt-8">
                <div class="container mx-auto px-4">
                    <p>Stone Scissors Paper Game - Built with Angular 21 & Spring Boot</p>
                </div>
            </footer>
        </div>
    `
})
export class LayoutComponent {
    newGame() {
        location.reload();
    }
}