<script setup lang="ts">
import { ref } from 'vue'
import { useAuthStore } from '~/store/auth-store.js'
import { storeToRefs } from 'pinia'

const authStore = useAuthStore()
const { token, user } = storeToRefs(authStore)
</script>

<template>
  <div>
    Page: auth/login
  </div>
</template>

<style scoped></style>