<script setup>
import { reactive } from "vue";
import { useAuthStore } from "~/store/auth-store.js";

const loginFormData = reactive({
  username: "",
  password: "",
});

const authStore = useAuthStore();

async function login() {
  await authStore.login(loginFormData.username, loginFormData.password);
}
</script>

<template>
  <h1>Login Form</h1>
  <div>Username: <input v-model="loginFormData.username" /></div>
  <div>Password: <input type="password" v-model="loginFormData.password" /></div>
  <button @click="login">LOGIN</button>
</template>
