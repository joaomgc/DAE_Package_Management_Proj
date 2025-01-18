<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const route = useRoute();
const orders = ref([]);

async function fetchOrders() {
  try {
    const username = route.params.username;
    const endpoint = `http://localhost:8080/monitor/api/clientes/${username}/encomendas`;

    const response = await fetch(endpoint, {
      headers: {
        Authorization: `Bearer ${authStore.token}`,
        Accept: 'application/json',
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

onMounted(fetchOrders);
</script>

<template>
  <div class="container">
    <header>
      <p>My Orders</p>
    </header>
    <main>
      <table v-if="orders.length > 0">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Volumes</th>
            <th>Produtos</th>
            <th>Sensor Value</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.encomendaId">
            <td>{{ order.encomendaId }}</td>
            <td>
              <ul>
                <li v-for="volume in order.volumes" :key="volume.id">
                  {{ volume.volumeName }}
                </li>
              </ul>
            </td>
            <td>
              <ul>
                <li v-for="volume in order.volumes" :key="volume.id">
                  <ul>
                    <li v-for="produto in volume.produtos" :key="produto.productId">
                      {{ produto.productName }} (x{{ produto.quantidade }})
                    </li>
                  </ul>
                </li>
              </ul>
            </td>
            <td>
              <ul>
                <li v-for="sensor in order.volumes" :key="sensor.id">
                  {{ (sensor.sensorId ? sensor.sensorId + ": " : 'N/A: ') + (sensor.valor ?? 'N/A') }}
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

button {
  margin-bottom: 20px;
  padding: 10px 20px;
  font-size: 1em;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
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
