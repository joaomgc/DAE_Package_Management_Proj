<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '~/store/auth-store';

const products = ref([]);
const router = useRouter();
const authStore = useAuthStore();
const errorMessage = ref("");

const fetchProducts = async () => {
  try {
    const endpoint = `http://localhost:8080/monitor/api/products`;

      const response = await fetch(endpoint, {
            headers: {
                'Authorization': `Bearer ${authStore.token}`,
                'Accept': 'application/json'
            }
        });
    const data = await response.json();
    products.value = data;
  } catch (error) {
    console.error('Error fetching products:', error);
  }
};

const deleteProduct = async (productId) => {
  try {
    errorMessage.value = "";
    const endpoint = `http://localhost:8080/monitor/api/products/${productId}`;

    const response = await fetch(endpoint, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${authStore.token}`,
        'Accept': 'application/json'
      }
    });

    if (response.ok) {
      products.value = products.value.filter(product => product.productId !== productId);
      console.log(`Product ${productId} deleted successfully.`);
    } else {
      console.error('Failed to delete product:', response.statusText);
      if (response.status === 500) {
        errorMessage.value = "Product can't be deleted because it's in use";
      }
    }
  } catch (error) {
    console.error('Error deleting product:', error);
  }
};


const createProduct = () => {
  router.push('/products/create');
};

const downloadCSV = () => {
  const csvContent = "data:text/csv;charset=utf-8," 
    + ["Product ID,Product Name,Product Type"]
    .concat(products.value.map(p => `${p.productId},${p.productName},${p.productType}`))
    .join("\n");

  const encodedUri = encodeURI(csvContent);
  const link = document.createElement("a");
  link.setAttribute("href", encodedUri);
  link.setAttribute("download", "products.csv");
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

onMounted(() => {
  fetchProducts();
});
</script>

<template>
  <div>
    <h1>Products</h1>
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>
    <div class="btn-container">
      <button @click="createProduct" class="btn">Create Product</button>
      <button @click="downloadCSV" class="btn">Download CSV</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>Product ID</th>
          <th>Product Name</th>
          <th>Product Type</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.productId">
          <td>{{ product.productId }}</td>
          <td>{{ product.productName }}</td>
          <td>{{ product.productType }}</td>
          <td>
            <button @click="deleteProduct(product.productId)" class="delete-btn">
              🗑️ Delete
            </button>
          </td>
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