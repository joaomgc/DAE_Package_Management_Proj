<template>
  <div>
    <h1>Packages</h1>
    <table>
      <thead>
        <tr>
          <th>Package ID</th>
          <th>Package Type</th>
          <th>Products</th>
          <th>Actions</th>
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
          <td>
            <button @click="addProductToPackage(pkg.packageId)" class="btn">Add Product</button>
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
    },
    async addProductToPackage(packageId) {
      const productId = prompt('Enter the Product ID to add:');
      if (productId) {
        console.log(`Adding product ${productId} to package ${packageId}`);
        try {
          const response = await fetch(`http://localhost:8080/monitor/api/packages/${packageId}/products/${productId}`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (response.ok) {
            alert('Product added successfully');
            console.log('Response:', await response.text()); 
            this.fetchPackages();
          } else {
            console.error('Error adding product:', response.statusText);
            console.log('Response:', await response.text());
          }
        } catch (error) {
          console.error('Error adding product:', error);
        }
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
.btn {
  display: inline-block;
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