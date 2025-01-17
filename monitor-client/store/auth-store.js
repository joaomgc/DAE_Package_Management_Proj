import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore("authStore", () => {
  const token = ref(null);
  const user = ref(null);
  const errorMessage = ref(null);

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
        token.value = response; // Assuming response contains the token
        await fetchUserInfo();
        return true; // Login successful
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
  })

  function logout() {
    token.value = null;
    user.value = null;
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
