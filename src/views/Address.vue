<template>
  <div class="address-book-container">
    <div class="default-address">
      <h3>Default Address</h3>
      <p v-if="defaultAddress">{{ defaultAddress.street }}, {{ defaultAddress.city }}, {{ defaultAddress.state }}, {{ defaultAddress.postal_code }}, {{ defaultAddress.country }}</p>
      <p v-else>No default address set.</p>
    </div>

    <div class="saved-addresses">
      <h3>Saved Addresses</h3>
      <div v-if="savedAddresses.length > 0" v-for="(address, index) in savedAddresses" :key="index" class="address-item">
        <p>{{ address.street }}, {{ address.city }}, {{ address.state }}, {{ address.postal_code }}, {{ address.country }}</p>
      </div>
      <div v-else>
        <p>No addresses found.</p>
      </div>
    </div>

    <div class="add-address">
      <button @click="showAddAddressForm = true">Add New Address</button>
    </div>

    <div v-if="showAddAddressForm" class="add-address-form">
      <h3>Add New Address</h3>
      <form @submit.prevent="addAddress">
        <div>
          <label for="street">Street:</label>
          <input type="text" v-model="newAddress.street" id="street" required>
        </div>
        <div>
          <label for="city">City:</label>
          <input type="text" v-model="newAddress.city" id="city" required>
        </div>
        <div>
          <label for="state">State:</label>
          <input type="text" v-model="newAddress.state" id="state" required>
        </div>
        <div>
          <label for="postal_code">Postal Code:</label>
          <input type="text" v-model="newAddress.postal_code" id="postal_code" required>
        </div>
        <div>
          <label for="country">Country:</label>
          <input type="text" v-model="newAddress.country" id="country" required>
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: 2,
      defaultAddress: null,
      savedAddresses: [],
      showAddAddressForm: false,
      newAddress: {
        street: '',
        city: '',
        state: '',
        postal_code: '',
        country: ''
      }
    };
  },
  mounted() {
    this.fetchUserAddresses();
  },
  methods: {
    async fetchUserAddresses() {
      try {
       
        const response = await axios.get(`http://localhost:8081/api/addresses/${this.userId}`);
        this.savedAddresses = response.data;

     
        if (this.savedAddresses.length > 0) {
          this.defaultAddress = this.savedAddresses[0];
        }
      } catch (error) {
        console.error('Error fetching addresses:', error);
      }
    },
    async addAddress() {
      try {
      
        const response = await axios.post(`http://localhost:8081/api/addresses/${this.userId}`, this.newAddress);

        this.savedAddresses.push(response.data);

   
        if (this.savedAddresses.length === 1) {
          this.defaultAddress = response.data;
        }

 
        this.newAddress = { street: '', city: '', state: '', postal_code: '', country: '' };
        this.showAddAddressForm = false;
      } catch (error) {
        console.error('Error adding address:', error);
      }
    }
  }
}
</script>

<style scoped>

.address-book-container {
  margin-left: 250px;
  padding: 20px;
}

.default-address, .saved-addresses, .add-address, .add-address-form {
  margin-bottom: 20px;
}

.address-item {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px 0;
}

.add-address button, .add-address-form button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-address button:hover, .add-address-form button:hover {
  background-color: #0056b3;
}

form div {
  margin: 10px 0;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  padding: 8px;
  width: calc(100% - 16px);
  box-sizing: border-box;
}
</style>
