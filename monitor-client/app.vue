<template>
  <div id="app">
    <nav>
      <nuxt-link to="/">Home</nuxt-link>
      <nuxt-link to="/dashboard">Dashboard</nuxt-link>
      <nuxt-link v-if="isLoggedIn" to="/orders">Orders</nuxt-link>
      <div class="auth-section">
        <nuxt-link v-if="!isLoggedIn" to="/auth">Login</nuxt-link>
        <div v-else class="user-info">
          <img src="/user.png" alt="User Profile" class="user-icon" />
          <span class="username">{{ user?.username }}</span>
          <button @click="logout" class="logout-btn">Logout</button>
        </div>
      </div>
    </nav>
    <NuxtPage />
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "~/store/auth-store";

const authStore = useAuthStore();
const router = useRouter();

const isLoggedIn = computed(() => !!authStore.token);
const user = computed(() => authStore.user);

function logout() {
  authStore.logout();
  router.push("/");
}
</script>

<style>
#app {
  font-family: 'Roboto', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #333;
  margin-top: 0;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f4f4f4;
  padding: 15px 20px;
  border-bottom: 1px solid #ddd;
}

nav a {
  color: #555;
  text-decoration: none;
  margin: 0 20px;
  font-size: 1.1em;
  font-weight: 500;
  transition: color 0.3s ease, border-bottom 0.3s ease;
  padding-bottom: 5px;
}

nav a:hover {
  color: #000;
  border-bottom: 2px solid #000;
}

.auth-section {
  display: flex;
  align-items: center;
  margin-left: auto;
}

.user-info {
  display: flex;
  align-items: center;
}

.auth-section .user-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.auth-section .user-icon:hover {
  transform: scale(1.1);
}

.username {
  margin-left: 10px;
  font-size: 1.1em;
  font-weight: 500;
  color: #555;
}

.logout-btn {
  margin-left: 10px;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #f44336;
  color: white;
  font-size: 1.1em;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.logout-btn:hover {
  background-color: #d32f2f;
}
</style>