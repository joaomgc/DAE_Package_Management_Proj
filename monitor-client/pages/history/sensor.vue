<script setup>
import { ref, onMounted } from 'vue';

const sensors = ref([]);


const fetchSensors = async () => {
  try {
    const response = await fetch('http://localhost:8080/monitor/api/sensor');
    const data = await response.json();
    sensors.value = data;
  } catch (error) {
    console.error('Failed to fetch sensors:', error);
  }
};

onMounted(() => {
  fetchSensors();
});
</script>


<template>
    <div class="container">
      <header>
        <h1>Sensor History</h1>
        <p>List of sensor history</p>
      </header>
      <main>
        <table>
          <thead>
            <tr>
              <!-- <th>Id</th> -->
              <th>Sensor ID</th>
              <th>Time</th>
              <th>Value</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="sensor in sensors" :key="sensor.id">
              <!-- <td>{{ sensor.id }}</td> -->
              <td>{{ sensor.sensorId }}</td>
              <td>{{ sensor.timestamp }}</td>
              <td>{{ sensor.valor }}</td>
              <td>{{ sensor.tipo }}</td>
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