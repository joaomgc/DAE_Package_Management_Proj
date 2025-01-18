<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/store/auth-store';

const errorMessage = ref("");
const router = useRouter();
const authStore = useAuthStore();
const newPackage = ref({
  packageId: '',
  packageType: ''
});

const submitForm = async () => {
  try {
    errorMessage.value = "";
    const response = await fetch('http://localhost:8080/monitor/api/embalagens', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newPackage.value)
    });
    if (response.ok) {
      router.push('/packages');
    } else {
      console.error('Error creating package:', response.statusText);
    }

    if (response.status === 409) {
      errorMessage.value = "Package already exists";
    }

  } catch (error) {
    console.error('Error creating package:', error);
  }
};
</script>

<template>
  <div>
    <h1>Create Package</h1>
    <form @submit.prevent="submitForm" class="form-create">
      <div class="form-group">
        <label for="packageId">Package ID</label>
        <input type="text" id="packageId" v-model="newPackage.packageId" required />
      </div>
      <div class="form-group">
        <label for="packageType">Package Type</label>
        <input type="text" id="packageType" v-model="newPackage.packageType" required />
      </div>
      <button type="submit" class="btn">Create Package</button>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
    </form>
  </div>
</template>

<style scoped>
.form-create {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f4f4f4;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 1em;
}

.btn {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 1em;
  color: #555;
  background-color: #f4f4f4;
  border: 1px solid #ddd;
  border-radius: 5px;
  text-decoration: none;
  cursor: pointer;
  transition: color 0.3s ease, border-bottom 0.3s ease, background-color 0.3s ease;
  padding-bottom: 5px;
}

.btn:hover {
  color: #000;
  background-color: #e0e0e0;
  border-bottom: 2px solid #000;
}
</style>