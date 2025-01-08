<template>
  <div>
    <h1>Packages</h1>
    <table>
      <thead>
        <tr>
          <th>Package ID</th>
          <th>Package Type</th>
          <th>Products</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="pkg in packages" :key="pkg.packageId">
          <td>{{ pkg.packageId }}</td>
          <td>{{ pkg.packageType }}</td>
          <td>
            <ul>
              <li v-for="product in pkg.products" :key="product.productId">{{ product.productName }}</li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      packages: []
    };
  },
  created() {
    this.fetchPackages();
  },
  methods: {
    async fetchPackages() {
      try {
        const response = await fetch('http://localhost:8080/monitor/api/packages/all');
        const data = await response.json();
        this.packages = data;
      } catch (error) {
        console.error('Error fetching packages:', error);
      }
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
}
th {
  background-color: #f2f2f2;
}
</style>