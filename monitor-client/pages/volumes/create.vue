<template>
  <div>
    <h1>Create Volume</h1>
    <form @submit.prevent="submitForm" class="form-create">
      <div class="form-group">
        <label for="volumeId">Volume ID</label>
        <input type="text" id="volumeId" v-model="volume.id" required />
      </div>
      <div class="form-group">
        <label for="volumeName">Volume Name</label>
        <input type="text" id="volumeName" v-model="volume.volumeName" required />
      </div>
      <button type="submit" class="btn">Create Volume</button>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    const volume = ref({
      id: '',
      volumeName: ''
    });

    const submitForm = async () => {
      try {
        const response = await fetch('http://localhost:8080/monitor/api/volumes', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(volume.value)
        });
        if (response.ok) {
          router.push('/volumes');
        } else {
          console.error('Error creating volume:', response.statusText);
        }
      } catch (error) {
        console.error('Error creating volume:', error);
      }
    };

    return {
      volume,
      submitForm
    };
  }
};
</script>

<style scoped>
.form-create {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f4f4f4;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 1em;
}

.btn {
  display: inline-block;
  margin-top: 20px;
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