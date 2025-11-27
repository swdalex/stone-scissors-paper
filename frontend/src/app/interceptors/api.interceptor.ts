import {HttpInterceptorFn} from '@angular/common/http';
import {catchError, throwError} from 'rxjs';

const showError = (message: string) => {
    console.error('API Error:', message);

    if (typeof window !== 'undefined' && window.alert) {
        window.alert(`Error: ${message}`);
    }
};

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
    if (!req.headers.has('Content-Type') && !(req.body instanceof FormData)) {
        req = req.clone({
            setHeaders: {
                'Content-Type': 'application/json'
            }
        });
    }

    return next(req).pipe(
        catchError((error) => {
            let errorMessage;

            if (error.error instanceof ErrorEvent) {
                // Client-side error
                errorMessage = `Error: ${error.error.message}`;
            } else {
                // Server-side error
                if (error.error && error.error.message) {
                    errorMessage = error.error.message;
                } else {
                    errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
                }
            }

            showError(errorMessage);

            return throwError(() => error);
        })
    );
};