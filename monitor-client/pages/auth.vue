<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "~/store/auth-store.js";

const loginFormData = reactive({
  username: "",
  password: "",
});

const authStore = useAuthStore();
const router = useRouter();

const errorMessage = ref("");
const loading = ref(false);

async function login() {
  errorMessage.value = "";
  loading.value = true;

  // Frontend validation
  if (!loginFormData.username || !loginFormData.password) {
    errorMessage.value = "Username and password are required.";
    loading.value = false;
    return;
  }

  const success = await authStore.login(loginFormData.username, loginFormData.password);
  loading.value = false;
  if (success) {
    router.push("/dashboard");
  } else {
    errorMessage.value = authStore.errorMessage;
  }
}
</script>

<template>
  <form @submit.prevent="login" class="login-form">
    <div class="form-container">
      <h1>Login</h1>
      <div class="form-group">
        <label for="username">Username:</label>
        <input id="username" v-model="loginFormData.username" />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" type="password" v-model="loginFormData.password" />
      </div>
      <button type="submit" @click="login" class="login-button" :disabled="loading">
        <span v-if="!loading">LOGIN</span>
        <span v-else class="spinner"></span>
      </button>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
    </div>
  </form>
</template>

<style>
.form-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #333;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #333;
  border-radius: 5px;
  box-sizing: border-box;
  background-color: #f9f9f9;
  color: #333;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-button:disabled {
  background-color: #555;
  cursor: not-allowed;
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #333;
  border-radius: 50%;
  width: 16px;
  height: 16px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-message {
  color: red;
  margin-top: 10px;
  font-weight: bold;
  text-align: center;
}
</style>