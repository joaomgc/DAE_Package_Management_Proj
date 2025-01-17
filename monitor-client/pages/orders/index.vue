<script setup>
import { ref, computed, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const orders = ref([]);


const filteredOrders = computed(() => {
  const user = authStore.user;
  if (user) {
    return orders.value.filter(order => order.customerId === user.username);
  }
  return [];
});

async function fetchOrders() {
  try {
    let response = null;
    
    if (!authStore.userAdmin) {
      const username = authStore.getUsername;
      
      const endpoint = `http://localhost:8080/monitor/api/clientes/${username}/encomendas`;
      
      response = await fetch(endpoint, {
        headers: {
          'Authorization': `Bearer ${authStore.token}`,
          'Accept': 'application/json'
        }
      });
    } else {
      response = await fetch('http://localhost:8080/monitor/api/encomendas');
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
      <table v-if="filteredOrders.length > 0">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer ID</th>
            <th>Status</th>
            <th>Volumes</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in filteredOrders" :key="order.encomendaId">
            <td>{{ order.encomendaId }}</td>
            <td>{{ order.customerId }}</td>
            <td>{{ order.estado }}</td>
            <td>
              <ul>
                <li v-for="volume in order.volumes" :key="volume.id">
                  {{ volume.volumeName }}
                </li>
              </ul>
            </td>
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