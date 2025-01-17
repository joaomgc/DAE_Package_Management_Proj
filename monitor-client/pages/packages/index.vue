<script>
import { useRouter } from 'vue-router';

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
        const response = await fetch('http://localhost:8080/monitor/api/embalagens');
        const data = await response.json();
        this.packages = data;
      } catch (error) {
        console.error('Error fetching packages:', error);
      }
    },
    redirectToCreate() {
      const router = useRouter();
      this.$router.push('/packages/create');
    }
  }
};
</script>

<template>
  <div>
    <h1>Packages</h1>
    <button @click="redirectToCreate" class="btn" style="float: left;">Create Package</button>
    <table>
      <thead>
        <tr>
          <th>Package ID</th>
          <th>Package Type</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="pkg in packages" :key="pkg.packageId">
          <td>{{ pkg.packageId }}</td>
          <td>{{ pkg.packageType }}</td>
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
th, td {
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
