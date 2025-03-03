<script setup>
import { ref, computed, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const orders = ref([]);
const clients = ref([]);
const selectedClient = ref('');

const filteredOrders = computed(() => {
  if (selectedClient.value) {
    return orders.value.filter(order => order.clientUsername === selectedClient.value);
  }
  return orders.value;
});

async function fetchClients() {
  try {
    const endpoint = `http://localhost:8080/monitor/api/clientes/`;

    const response = await fetch(endpoint, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Accept': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    clients.value = await response.json();
  } catch (error) {
    console.error('Error fetching clients:', error);
  }
}

async function fetchOrders() {
  try {
    const endpoint = `http://localhost:8080/monitor/api/encomendas`;
    const response = await fetch(endpoint, {
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Accept': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    orders.value = await response.json();
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
}

onMounted(() => {
  fetchOrders();
  fetchClients();
});
</script>

<template>
  <div class="container">
    <header>
      <p>List of all orders</p>
    </header>
    <main>
      <p>Filter by client</p>
      <select v-model="selectedClient">
        <option value="">All Clients</option>
        <option v-for="client in clients" :key="client.username" :value="client.username">
          {{ client.username }}
        </option>
      </select>
      <table v-if="filteredOrders.length > 0">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Username</th>
            <th>Volumes</th>
            <th>Sensor</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in filteredOrders" :key="order.encomendaId">
            <td>{{ order.encomendaId }}</td>
            <td>{{ order.clientUsername }}</td>
            <td>
              <ul>
                <li v-for="volume in order.volumes" :key="volume.id">
                  {{ volume.volumeName }}
                </li>
              </ul>
            </td>
            <td>
              <ul>
                <li v-for="sensor in order.volumes" :key="sensor.id">
                  {{ sensor.sensorId ?? 'N/A' }}
                </li>
              </ul>
            </td>
            <td>{{ order.estado }}</td>
          </tr>
        </tbody>
      </table>
      <p v-else>No orders found.</p>
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

th,
td {
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

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 5px 0;
}
</style>
