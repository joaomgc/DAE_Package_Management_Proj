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
    } catch (e) {
      errorMessage.value = 'Invalid username or password';
      console.error('Login request failed:', e);
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
        console.log("USER INFO IN RESPONSE:", response);
        user.value = response;
      }
    } catch (error) {
      console.error("Failed to fetch user info:", error);
    }
  }

  const userAdmin = computed(() => {
    console.log("User:" + user.value);
    return user.value && user.value.dtype === 'Administrator';
  });

  function logout() {
    token.value = null;
    user.value = null;
  }

  return { token, user, login, userAdmin, logout };
});
