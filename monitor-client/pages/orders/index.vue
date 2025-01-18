<script setup>
import { ref, computed, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const orders = ref([]);

const filteredOrders = computed(() => {
  const user = authStore.user;
  if (user && authStore.userClient) {
    return orders.value.filter(order => {
      console.log('ORDER.CLIENTUSERNAME: ', order.clientUsername);
      return order.clientUsername === user.username;
    });
  } else if(authStore.userAdmin) {
    return orders.value;
  }
  return [];
});

async function fetchOrders() {
  try {
    let response = null;

    if (authStore.userClient) {
      const username = authStore.getUsername;

      const endpoint = `http://localhost:8080/monitor/api/clientes/${username}/encomendas`;

      response = await fetch(endpoint, {
        headers: {
          'Authorization': `Bearer ${authStore.token}`,
          'Accept': 'application/json'
        }
      });
    } else if (authStore.userAdmin) {
      response = await fetch('http://localhost:8080/monitor/api/encomendas');
    }
    else {
      return;
    }

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    orders.value = data;
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
}

function downloadCSV() {
  const headers = ['Order ID', 'Username', 'Volumes', 'Status'];
  const rows = filteredOrders.value.map(order => [
    order.encomendaId,
    order.clientUsername,
    order.volumes.map(volume => volume.volumeName).join(', '),
    order.estado
  ]);

  let csvContent = 'data:text/csv;charset=utf-8,';
  csvContent += headers.join(',') + '\n';
  rows.forEach(row => {
    csvContent += row.join(',') + '\n';
  });

  const encodedUri = encodeURI(csvContent);
  const link = document.createElement('a');
  link.setAttribute('href', encodedUri);
  link.setAttribute('download', 'orders.csv');
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

onMounted(async () => {
  fetchOrders();
});
</script>

<template>
  <div class="container">
    <header>
      <p>{{ authStore.userAdmin ? 'List of all orders' : 'My Orders' }}</p>
    </header>
    <main>
      <button @click="downloadCSV">Download CSV</button>
      <table v-if="filteredOrders.length > 0">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Username</th>
            <th>Volumes</th>
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