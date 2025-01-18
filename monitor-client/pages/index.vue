<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const redirectToLogin = () => {
  router.push('/login')
}
</script>

<template>
  <div class="index-container">
    <header>
      <h1>Welcome to Our Application</h1>
    </header>

    <main class="main-content">
      <p>Please login to continue</p>
      <!-- <button @click="redirectToLogin" class="login-button"> -->
      <nuxt-link to="/auth" class="login-button">
        Go to Login
      </nuxt-link>
    </main>
  </div>
</template>

<style scoped>
.index-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  text-align: center;
}

.main-content {
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-button {
  background-color: #000000;
  border: none;
  color: white;
  padding: 10px 20px; 
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #616161;
}
</style>