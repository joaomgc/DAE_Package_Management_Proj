// https://nuxt.com/docs/api/configuration/nuxt-config

import piniaModule from '@pinia/nuxt'

export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  devtools: { enabled: true },

  runtimeConfig: {
    public: {
    API_URL: process.env.API_URL || 'http://localhost:8080/monitor/api'
  }
    },

  plugins: [
    '~/plugins/auth-init.js',
],

  modules: [piniaModule],
})