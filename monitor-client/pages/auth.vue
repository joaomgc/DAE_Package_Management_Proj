<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const config = useRuntimeConfig();
const api = config.public.API_URL;
const loginFormData = reactive({
    username: "",
    password: ""
});
const errorMessage = ref("");

async function login() {
    try {
        errorMessage.value = ""; // Clear any previous error message
        const response = await $fetch(`${api}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            body: loginFormData
        });

        if (response) {
            router.push('/dashboard');
        }
    } catch (e) {
        errorMessage.value = 'Invalid username or password';
        console.error('Login request failed:', e);
    }
}

function reset() {
    loginFormData.username = "";
    loginFormData.password = "";
    errorMessage.value = ""; // Clear the error message on reset
}
</script>

<template>
    <div class="login-container">
        <h1>Login</h1>
        <form @submit.prevent="login">
            <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
            <div class="form-group">
                <label for="username">Username:</label>
                <input id="username" v-model="loginFormData.username" type="text">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input id="password" v-model="loginFormData.password" type="password">
            </div>
            <div class="button-group">
                <button @click="login">LOGIN</button>
                <button @click="reset">RESET</button>
            </div>
        </form>
    </div>
</template>

<style scoped>
.login-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    background-color: #f9f9f9;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
}

.error-message {
    color: red;
    margin-bottom: 15px;
    text-align: center;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
}

.form-group input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}

.button-group {
    display: flex;
    justify-content: space-between;
}

.button-group button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.button-group button:hover {
    background-color: #ddd;
}
</style>
