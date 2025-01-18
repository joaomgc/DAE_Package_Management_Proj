<script setup>
import { ref, computed, onMounted } from 'vue';
import { useAuthStore } from '~/store/auth-store';

const authStore = useAuthStore();
const clients = ref([]);

const filteredClients = computed(() => {
    const user = authStore.user;
    if (user && authStore.userAdmin) {
        return clients.value;
    }
    return [];
});

async function fetchCLients() {
    try {
        let response = null;

        const endpoint = `http://localhost:8080/monitor/api/clientes/`;

        response = await fetch(endpoint, {
            headers: {
                'Authorization': `Bearer ${authStore.token}`,
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        clients.value = data;
    } catch (error) {
        console.error('Error fetching clients:', error);
    }

}

function downloadCSV() {
    const headers = ['Username', 'Name', 'Email'];
    const rows = filteredClients.value.map(client => [client.username, client.name, client.email]);

    let csvContent = 'data:text/csv;charset=utf-8,';
    csvContent += headers.join(',') + '\n';
    rows.forEach(row => {
        csvContent += row.join(',') + '\n';
    });

    const encodedUri = encodeURI(csvContent);
    const link = document.createElement('a');
    link.setAttribute('href', encodedUri);
    link.setAttribute('download', 'clients.csv');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

onMounted(async () => {
    fetchCLients();
});
</script>

<template>
    <div class="container">
        <header>
            <p>List of all clients</p>
        </header>
        <main>
            <table v-if="filteredClients.length > 0">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="client in filteredClients" :key="client.username">
                        <td>{{ client.username }}</td>
                        <td>{{ client.name }}</td>
                        <td>{{ client.email }}</td>
                    </tr>
                </tbody>
            </table>
            <button v-if="filteredClients.length > 0" @click="downloadCSV">Download CSV</button>
            <p v-else>No clients found.</p>
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

button {
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #45a049;
}
</style>