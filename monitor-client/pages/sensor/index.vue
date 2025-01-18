<script setup>
import { ref, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const sensors = ref([]);

const fetchSensors = async () => {
  try {
    const endpoint = `http://localhost:8080/monitor/api/sensors`;

    const response = await fetch(endpoint, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Accept': 'application/json'
      }
    });

    const data = await response.json();
    sensors.value = data.sort((a, b) => a.id - b.id);
  } catch (error) {
    console.error('Error fetching sensors:', error);
  }
};

const capitalize = (str) => {
  return str.charAt(0).toUpperCase() + str.slice(1);
};

onMounted(() => {
  fetchSensors();
});
</script>

<template>
  <div class="container">
    <header>
      <h1>Sensors</h1>
      <p>List of all sensors</p>
    </header>
    <main>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Type</th>
            <th>Last value read</th>
            <th>Status</th>
            <th>Volume ID</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="sensor in sensors" :key="sensor.id">
            <td>{{ sensor.id }}</td>
            <td>{{ capitalize(sensor.tipo) }}</td>
            <td>{{ sensor.valor }}</td>
            <td>{{ sensor.status }}</td>
            <td>{{ sensor.volumeId || 'N/A' }}</td>
          </tr>
        </tbody>
      </table>
    </main>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
  background-color: #f4f4f4;
  color: #333;
  font-family: 'Roboto', Helvetica, Arial, sans-serif;
}

header {
  text-align: center;
  margin-bottom: 20px;
}

h1 {
  font-size: 3em;
  color: #333;
  margin-bottom: 10px;
}

header p {
  font-size: 1.5em;
  color: #666;
}

main {
  text-align: center;
  flex-grow: 1;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f4f4f4;
  color: #333;
}

td {
  color: #666;
}
</style>