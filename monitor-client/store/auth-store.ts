import { defineStore } from 'pinia';
import { useRouter } from 'vue-router';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: null as string | null,
        user: null as object | null,
    }),
    getters: {
        isAuthenticated: (state) => !!state.token,
    },
    actions: {
        setAuthData(authToken: string, userData: object) {
            this.token = authToken;
            this.user = userData;
            if (typeof window !== 'undefined') {
                localStorage.setItem('token', authToken); // Persist token
                localStorage.setItem('user', JSON.stringify(userData)); // Persist user data
            }
        },
        clearAuthData() {
            this.token = null;
            this.user = null;
            if (typeof window !== 'undefined') {
                localStorage.removeItem('token'); // Clear persisted token
                localStorage.removeItem('user'); // Clear persisted user data
            }
        },
        initializeAuthData() {
            if (typeof window !== 'undefined') {
                const storedToken = localStorage.getItem('token');
                const storedUser = localStorage.getItem('user');
                if (storedToken && storedUser) {
                    this.token = storedToken;
                    this.user = JSON.parse(storedUser);
                }
            }
        },
        logout() {
            this.clearAuthData();
        },
    },
});