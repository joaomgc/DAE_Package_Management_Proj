<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const products = ref([]);
const router = useRouter();

const fetchProducts = async () => {
  try {
    const response = await fetch('http://localhost:8080/monitor/api/products');
    const data = await response.json();
    products.value = data;
  } catch (error) {
    console.error('Error fetching products:', error);
  }
};

const createProduct = () => {
  router.push('/products/create');
};

onMounted(() => {
  fetchProducts();
});
</script>

<template>
  <div>
    <h1>Products</h1>
    <div class="btn-container">
      <button @click="createProduct" class="btn">Create Product</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>Product ID</th>
          <th>Product Name</th>
          <th>Product Type</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.productId">
          <td>{{ product.productId }}</td>
          <td>{{ product.productName }}</td>
          <td>{{ product.productType }}</td>
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
.btn-container {
  text-align: left;
  margin-bottom: 20px;
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
