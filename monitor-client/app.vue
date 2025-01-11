<template>
  <div id="app">
    <nav>
      <nuxt-link to="/">Home</nuxt-link>
      <nuxt-link to="/dashboard">Dashboard</nuxt-link>
      <nuxt-link to="/orders">Orders</nuxt-link>
      <div class="auth-section">
        <nuxt-link v-if="!isLoggedIn" to="/auth">Login</nuxt-link>
        <img v-else src="/user.png" alt="User Profile" class="user-icon" />
      </div>
    </nav>
    <NuxtPage />
  </div>
</template>

<script>
export default {
  name: 'App'
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

</style>