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

async function login() {
  errorMessage.value = "";

  // Frontend validation
  if (!loginFormData.username || !loginFormData.password) {
    errorMessage.value = "Username and password are required.";
    return;
  }

  const success = await authStore.login(loginFormData.username, loginFormData.password);
  if (success) {
    router.push("/dashboard");
  } else {
    errorMessage.value = authStore.errorMessage;
  }
}


</script>

<template>
    <form @submit.prevent="login">
        
  <div>
    <h1>Login</h1>
    <div>
      <label for="username">Username:</label>
      <input id="username" v-model="loginFormData.username" />
    </div>
    <div>
      <label for="password">Password:</label>
      <input id="password" type="password" v-model="loginFormData.password" />
    </div>
    <button type="submit" @click="login">LOGIN</button>

    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
  </div>
</form>
</template>

<style>
.error-message {
  color: red;
  margin-top: 10px;
  font-weight: bold;
}
</style>
