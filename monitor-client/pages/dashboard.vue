<script>
    export default {
      name: 'DashboardPage',
    }
</script>
    
<template>
  <div class="dashboard-container">
    <h1>Dashboard</h1>
    <div class="links">
      <nuxt-link to="/packages" class="btn">Go to Packages</nuxt-link>
      <nuxt-link to="/products" class="btn">Go to Products</nuxt-link>
      <nuxt-link to="/volumes" class="btn">Go to Volumes</nuxt-link>
      <nuxt-link to="/sensors" class="btn">Go to Sensors</nuxt-link>
      <nuxt-link to="/sensor" class="btn">Go to Sensor History</nuxt-link>
    </div>
  </div>
</template>


<style scoped>
.dashboard-container {
  text-align: center;
  margin-top: 50px;
  font-family: 'Roboto', Helvetica, Arial, sans-serif;
  color: #333;
}

h1 {
  font-size: 2.5em;
  color: #333;
  margin-bottom: 20px;
}

.links {
  display: flex;
  justify-content: center;
  gap: 20px;
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
  transition: color 0.3s ease, border-bottom 0.3s ease, background-color 0.3s ease;
  padding-bottom: 5px;
}

.btn:hover {
  color: #000;
  background-color: #e0e0e0;
  border-bottom: 2px solid #000;
}
</style>