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
    errorMessage.value = ""; // Reset the error message
    try {
        await authStore.login(loginFormData.username, loginFormData.password);
        if (authStore.token) {
            router.push("/dashboard"); // Redirect to dashboard on successful login
        }
    } catch (error) {
        errorMessage.value = "Login failed. Please check your username and password.";
        console.error("Login error:", error);
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
