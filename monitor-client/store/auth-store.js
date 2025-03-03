import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useAuthStore = defineStore("authStore", () => {
  const token = ref(null);
  const user = ref(null);
  const errorMessage = ref(null);

  if (typeof window !== "undefined") {
    token.value = localStorage.getItem("token");
    user.value = JSON.parse(localStorage.getItem("user"));
  }

  const config = useRuntimeConfig();
  const api = config.public.API_URL;

  async function login(username, password) {
    errorMessage.value = ""; // Reset the error message
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
        if (typeof window !== "undefined") {
          localStorage.setItem("token", token.value);
        }
        await fetchUserInfo();
        return true;
      }
    } catch (e) {
      const status = e.response?.status;

      if (status === 500) {
        errorMessage.value = "A server error occurred. Please try again later.";
      } else if (status === 401) {
        errorMessage.value = "Invalid password. Please try again.";
      } else if (status === 400) {
        errorMessage.value = "Please fill in all required fields.";
      } else {
        errorMessage.value = "An unknown error occurred. Please try again.";
      }

      console.error("Login request failed:", {
        status,
        message: e.message,
        response: e.response?._data || null,
      });
      return false; // Login failed
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
        if (typeof window !== "undefined") {
          localStorage.setItem("user", JSON.stringify(user.value));
        }
      }
    } catch (error) {
      console.error("Failed to fetch user info:", error);
    }
  }

  const userAdmin = computed(() => {
    return user.value && user.value.role === 'A';
  });

  const userClient = computed(() => {
    return user.value && user.value.role === 'C';
  });

  const getUsername = computed(() => {
    return user.value ? user.value.username : '';
  });

  function logout() {
    token.value = null;
    user.value = null;
    if (typeof window !== "undefined") {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
    }
  }

  return {
    token,
    user,
    login,
    userAdmin,
    userClient,
    getUsername,
    logout,
    errorMessage
  };
});