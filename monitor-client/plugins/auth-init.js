import { useAuthStore } from '~/store/auth-store';

export default defineNuxtPlugin((nuxtApp) => {
    const authStore = useAuthStore();

    if (typeof window !== "undefined") {
        const token = localStorage.getItem("token");
        const user = JSON.parse(localStorage.getItem("user"));

        if (token) {
            authStore.token = token;
        }

        if (user) {
            authStore.user = user;
        }
    }
});