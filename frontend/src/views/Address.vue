<template>
  <div class="address-book-container">
    <!-- Default Address Section -->
    <div class="default-address">
      <h3>Default Address</h3>
      <p v-if="defaultAddress">{{ defaultAddress.street }}, {{ defaultAddress.city }}, {{ defaultAddress.state }}, {{ defaultAddress.postal_code }}, {{ defaultAddress.country }}</p>
      <p v-else>No default address set.</p>
    </div>

    <!-- Saved Addresses Section -->
    <div class="saved-addresses">
      <h3>Saved Addresses</h3>
      <div v-if="sanitizedAddresses.length > 0" v-for="(address, index) in sanitizedAddresses" :key="index" class="address-item">
        <p>{{ address.street }}, {{ address.city }}, {{ address.state }}, {{ address.postal_code }}, {{ address.country }}</p>
      </div>
      <div v-else>
        <p>No addresses found.</p>
      </div>
    </div>

    <!-- Add New Address Button -->
    <div class="add-address">
      <button @click="showAddAddressForm = true">Add New Address</button>
    </div>

    <!-- Add Address Modal -->
    <div v-if="showAddAddressForm" class="modal">
      <div class="modal-content">
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
          <div class="modal-actions">
            <button type="submit">Submit</button>
            <button type="button" @click="closeModal">Cancel</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Success Modal -->
    <div v-if="showSuccessModal" class="modal">
      <div class="modal-content">
        <p>{{ successMessage }}</p>
        <button @click="closeSuccessModal">OK</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import DOMPurify from 'dompurify';  // Import DOMPurify for XSS protection

export default {
  data() {
    return {
       userId: localStorage.getItem('userToken'),
      defaultAddress: null,
      savedAddresses: [],
      showAddAddressForm: false,
      showSuccessModal: false,
      successMessage: '',
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
  computed: {
    // Output XSS protection - sanitizes data before rendering
    sanitizedAddresses() {
      return this.savedAddresses.map(address => ({
        street: DOMPurify.sanitize(address.street),
        city: DOMPurify.sanitize(address.city),
        state: DOMPurify.sanitize(address.state),
        postal_code: DOMPurify.sanitize(address.postal_code),
        country: DOMPurify.sanitize(address.country)
      }));
    }
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
        // Input XSS protection - sanitize user input before sending to the server
        const sanitizedAddress = {
          street: DOMPurify.sanitize(this.newAddress.street),
          city: DOMPurify.sanitize(this.newAddress.city),
          state: DOMPurify.sanitize(this.newAddress.state),
          postal_code: DOMPurify.sanitize(this.newAddress.postal_code),
          country: DOMPurify.sanitize(this.newAddress.country)
        };

        const response = await axios.post(`http://localhost:8081/api/addresses/${this.userId}`, sanitizedAddress);
        this.savedAddresses.push(response.data);

        if (this.savedAddresses.length === 1) {
          this.defaultAddress = response.data;
        }

        // Reset form and close modal
        this.newAddress = { street: '', city: '', state: '', postal_code: '', country: '' };
        this.showAddAddressForm = false;

        // Show success message
        this.successMessage = 'Address added successfully!';
        this.showSuccessModal = true;
      } catch (error) {
        console.error('Error adding address:', error);
      }
    },
    closeModal() {
      this.showAddAddressForm = false;
    },
    closeSuccessModal() {
      this.showSuccessModal = false;
    }
  }
};
</script>

<style scoped>

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  max-width: 400px;
  width: 100%;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
}

button:hover {
  background-color: #0056b3;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

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
