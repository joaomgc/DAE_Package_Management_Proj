<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const router = useRouter();
const packages = ref([]);
const errorMessage = ref("");

const fetchPackages = async () => {
  try {
    const endpoint = `http://localhost:8080/monitor/api/embalagens`;

    const response = await fetch(endpoint, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Accept': 'application/json'
      }
    });

    const data = await response.json();
    packages.value = data;
  } catch (error) {
    console.error('Error fetching packages:', error);
  }
};

const deletePackage = async (packageId) => {
  try {
    errorMessage.value = "";
    const endpoint = `http://localhost:8080/monitor/api/embalagens/${packageId}`;

    const response = await fetch(endpoint, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      packages.value = packages.value.filter(pkg => pkg.packageId !== packageId);
      console.log(`Package ${packageId} deleted successfully.`);
    } else {
      console.error('Failed to delete package:', response.statusText);
      if (response.status === 500) {
        errorMessage.value = "Package can't be deleted because it's in use";
      }
    }
  } catch (error) {
    console.error('Error deleting package:', error);
  }
};

const redirectToCreate = () => {
  router.push('/packages/create');
};

onMounted(() => {
  fetchPackages();
});
</script>

<template>
  <div>
    <h1>Packages</h1>
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    <button @click="redirectToCreate" class="btn" style="float: left">Create Package</button>
    <table>
      <thead>
        <tr>
          <th>Package ID</th>
          <th>Package Type</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="pkg in packages" :key="pkg.packageId">
          <td>{{ pkg.packageId }}</td>
          <td>{{ pkg.packageType }}</td>
          <td>
            <button @click="deletePackage(pkg.packageId)" class="delete-btn">
              🗑️ Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
}

.btn {
  display: inline-block;
  margin-bottom: 20px;
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
