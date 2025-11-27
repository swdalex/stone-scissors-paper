/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ['./src/**/*.{html,js,ts,jsx,tsx,css}'],
    theme: {
        extend: {
            animation: {
                'celebrate': 'celebrate 0.5s ease-in-out',
                'pulse-slow': 'pulse 2s infinite',
                'bounce-gentle': 'bounce 1s infinite'
            },
            keyframes: {
                celebrate: {
                    '0%, 100%': {transform: 'scale(1)'},
                    '50%': {transform: 'scale(1.1)'}
                }
            },
            colors: {
                primary: {
                    50: '#eff6ff',
                    200: '#bfdbfe',
                    500: '#3b82f6',
                    600: '#2563eb',
                    700: '#1d4ed8'
                },
                success: '#10b981',
                warning: '#f59e0b',
                error: '#ef4444'
            }
        },
    },
    plugins: [],
}