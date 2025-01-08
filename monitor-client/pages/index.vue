<template>
  <div class="container">
    <header>
      <h1>Welcome to Monitor</h1>
      <p>Your monitoring solution</p>
    </header>
    <main>
      <p>This is the main page of the Monitor. Click the button below to go to the dashboard.</p>
      <router-link to="/dashboard" class="btn">Go to Dashboard</router-link>
    </main>
  </div>
</template>

<script>
export default {
  name: 'HomePage'
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

main p {
  font-size: 1.2em;
  color: #666;
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
  transition: color 0.3s ease, border-bottom 0.3s ease, background-color 0.3s ease;
  padding-bottom: 5px;
}

.btn:hover {
  color: #000;
  background-color: #e0e0e0;
  border-bottom: 2px solid #000;
}

footer {
  text-align: center;
  margin-top: 20px;
  font-size: 0.9em;
  color: #999;
}
</style>