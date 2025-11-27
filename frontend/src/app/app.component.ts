import {Component, inject, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {GameService} from './services/game.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet],
    template: `
        <router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {
    private gameService = inject(GameService);

    ngOnInit() {
        this.gameService.loadSessionFromStorage();
    }
}