<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const router = useRouter();

const isLoggedIn = computed(() => !!authStore.token);
const user = computed(() => authStore.user);

function logout() {
  authStore.logout();
  router.push("/");
}
</script>

<template>
  <div id="app">
    <nav>
      <nuxt-link v-if="!isLoggedIn" to="/">Home</nuxt-link>
      <nuxt-link v-if="authStore.userAdmin" to="/dashboard">Dashboard</nuxt-link>
      <nuxt-link v-if="authStore.userAdmin" to="/orders">Orders</nuxt-link>
      <nuxt-link v-if="authStore.userClient" :to="`/orders/${user?.username}`">Orders</nuxt-link>
      <nuxt-link v-if="authStore.userAdmin" to="/clients">Clients</nuxt-link>
      <div v-if="authStore.userAdmin" class="dropdown">
        <button class="dropdown-btn">History</button>
        <div class="dropdown-content">
          <nuxt-link to="/history/order">Order's History</nuxt-link>
          <nuxt-link to="/history/sensor">Sensor's History</nuxt-link>
        </div>
      </div>
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

/* Dropdown styling */
.dropdown {
  position: relative;
  display: inline-block;
  margin-left: 20px;
}

.dropdown-btn {
  background-color: transparent;
  color: #555;
  font-size: 1.1em;
  font-weight: 500;
  border: none;
  cursor: pointer;
  padding: 5px 0;
  transition: color 0.3s ease;
}

.dropdown-btn:hover {
  color: #000;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  min-width: 200px;
  z-index: 1;
}

.dropdown-content a {
  color: #555;
  text-decoration: none;
  display: block;
  padding: 10px 15px;
  transition: background-color 0.3s ease;
}

.dropdown-content a:hover {
  background-color: #f1f1f1;
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>
