<template>
  <div class="container">
    <header>
      <h1>Orders</h1>
      <p>List of all orders</p>
    </header>
    <main>
      <table>
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer ID</th>
            <th>Status</th>
            <th>Volumes</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.encomendaId">
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
    </main>
  </div>
</template>

<script>
export default {
  name: 'OrdersPage',
  data() {
    return {
      orders: []
    };
  },
  created() {
    fetch('http://localhost:8080/monitor/api/encomendas')
      .then(response => response.json())
      .then(data => {
        this.orders = data;
      });
  }
}
</script>

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

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 5px 0;
}
</style>