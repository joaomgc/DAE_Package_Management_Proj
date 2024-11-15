<template>
  <div v-if="error">
    Error: {{ error.message }}
  </div>
  <div v-else>
    <h2>Packages</h2>
    <div>{{ packages }}</div>
    <button @click.prevent="refresh">Refresh Data</button>
  </div>
</template>


<script setup>
const config = useRuntimeConfig();
const api = config.public.API_URL;
const { data: packages, error, refresh } = await useFetch(`${api}/packages/all`)
</script>