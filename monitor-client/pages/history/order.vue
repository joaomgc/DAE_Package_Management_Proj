<script setup>
import { ref, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();

const orders = ref([]);
const selectedOrder = ref('');

const filteredOrders = computed(() => {
  const user = authStore.user;
  if (user && authStore.userAdmin) {
    if (selectedOrder.value) {
      return orders.value.filter(order => order.encomendaId === selectedOrder.value);
    }
    return orders.value;
  }
  return [];
});


const fetchOrders = async () => {
  try {
    const response = await fetch('http://localhost:8080/monitor/api/encomendas/historico');
    const data = await response.json();
    orders.value = data;
  } catch (error) {
    console.error('Failed to fetch orders:', error);
  }
};


onMounted(() => {
  fetchOrders();
});
</script>


<template>
    <div class="container">
      <header>
        <h1>Order History</h1>
        <p>List of order history</p>
      </header>
      <main>
        <p>Filter by order id</p>
      <select v-if="authStore.userAdmin" v-model="selectedOrder">
        <option value="">All Orders</option>
        <option v-for="order in orders" :key="order.encomendaId" :value="order.encomendaId">
          {{ order.encomendaId }}
        </option>
      </select>
        <table>
          <thead>
            <tr>
              <th>Order Id</th>
              <th>Status</th>
              <th>Time</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in filteredOrders" :key="order.encomendaId">
              <td>{{ order.encomendaId }}</td>
              <td>{{ order.estado }}</td>
              <td>{{ order.timestamp }}</td>
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