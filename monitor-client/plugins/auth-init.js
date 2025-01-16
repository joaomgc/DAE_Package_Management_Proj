import { useAuthStore } from '~/store/auth-store';

export default defineNuxtPlugin((nuxtApp) => {
    const authStore = useAuthStore();
});
