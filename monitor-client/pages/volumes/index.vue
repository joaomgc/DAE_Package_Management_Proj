<template>
  <div>
    <h1>Volumes</h1>
    <table>
      <thead>
        <tr>
          <th>Volume ID</th>
          <th>Volume Name</th>
          <th>Package ID</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="volume in volumes" :key="volume.id">
          <td>{{ volume.id }}</td>
          <td>{{ volume.volumeName }}</td>
          <td>
            <span v-if="volume.packageId">{{ volume.packageId }}</span>
            <span v-else>
              <input v-model="volume.newPackageId" placeholder="Enter Package ID" />
              <button @click="associatePackage(volume.id, volume.newPackageId)">Associate</button>
            </span>
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
      volumes: []
    };
  },
  created() {
    this.fetchVolumes();
  },
  methods: {
    async fetchVolumes() {
      try {
        const response = await fetch('http://localhost:8080/monitor/api/volumes');
        const data = await response.json();
        this.volumes = data.map(volume => ({ ...volume, newPackageId: '' }));
      } catch (error) {
        console.error('Error fetching volumes:', error);
      }
    },
    async associatePackage(volumeId, packageId) {
      try {
        const response = await fetch(`http://localhost:8080/monitor/api/volumes/${volumeId}/packages/${packageId}`, {
          method: 'POST'
        });
        if (response.ok) {
          this.fetchVolumes();
        } else {
          console.error('Error associating package:', response.statusText);
        }
      } catch (error) {
        console.error('Error associating package:', error);
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