import {Routes} from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./components/layout/layout.component').then(c => c.LayoutComponent),
        children: [
            {
                path: '',
                loadComponent: () => import('./components/game-board/game-board.component').then(c => c.GameBoardComponent)
            }
        ]
    },
    {
        path: '**',
        redirectTo: ''
    }
];