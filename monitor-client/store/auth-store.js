import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore("authStore", () => {
  const token = ref(null);
  const user = ref(null);

  const config = useRuntimeConfig();
  const api = config.public.API_URL;

  async function login(username, password) {
    try {
      const response = await $fetch(`${api}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: { username, password },
      });
      if (response) {
        token.value = response;
        await fetchUserInfo();
      }
    } catch (error) {
      console.error("Login failed:", error);
    }
  }

  async function fetchUserInfo() {
    try {
      const response = await $fetch(`${api}/auth/user`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token.value}`,
          Accept: "application/json",
        },
      });
      if (response) {
        user.value = response;
      }
    } catch (error) {
      console.error("Failed to fetch user info:", error);
    }
  }

  function logout() {
    token.value = null;
    user.value = null;
  }

  return { token, user, login, logout };
});
