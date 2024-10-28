<template>
  <div class="add-item-container">
    <!-- Header with Back to Homepage button -->
    <div class="top-buttons">
      <button @click="goBackToHome" class="back-homepage-button">Back to Homepage</button>
    </div>

    <h2 class="page-title">Add New Item</h2>

    <!-- Form for adding new item -->
    <div class="item-form card">
      <div class="form-field">
        <label for="item-name">Item Name:</label>
        <input type="text" id="item-name" v-model="newItem.name" placeholder="Enter item name">
      </div>
      <div class="form-field">
        <label for="item-description">Item Description:</label>
        <textarea id="item-description" v-model="newItem.description" placeholder="Enter item description"></textarea>
      </div>
      <div class="form-field">
        <label for="item-price">Price ($):</label>
        <input type="number" id="item-price" v-model="newItem.price" placeholder="Enter item price">
      </div>
      <div class="form-field">
        <label for="item-stock">Stock:</label>
        <input type="number" id="item-stock" v-model="newItem.stock" placeholder="Enter item stock">
      </div>
      <div class="form-field">
        <label for="promotion">Promotion:</label>
        <input type="text" id="promotion" v-model="newItem.promotion" placeholder="Enter promotion details">
      </div>
      <div class="form-field">
        <label for="discount">Discount (%):</label>
        <input type="number" id="discount" v-model="newItem.discount" placeholder="Enter discount percentage">
      </div>
      <div class="form-actions">
        <button @click="addItem" class="submit-button">Add Item</button>
        <button @click="cancel" class="cancel-button">Cancel</button>
      </div>
    </div>
     <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
      <div class="modal-content">
        <div class="modal-header">
          <h5>{{ modalTitle }}</h5>
          <button @click="showModal = false" class="close-button">&times;</button>
        </div>
        <div class="modal-body">
          <p>{{ modalMessage }}</p>
        </div>
        <div class="modal-footer">
          <button @click="showModal = false" class="close-button">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      newItem: {
        name: '',
        description: '',
        price: '',
        stock: '',
        promotion: '',
        discount: ''
      },
      showModal: false,
      modalTitle: '',
      modalMessage: ''
    };
  },
  methods: {
    async addItem() {
      const sellerId = localStorage.getItem('sellerToken');
      if (!sellerId) {
        return;
      }

      const productData = {
        name: this.newItem.name,
        description: this.newItem.description,
        price: parseFloat(this.newItem.price),
        stock: parseInt(this.newItem.stock, 10),
        sellerId: sellerId
      };

      try {
        const response = await axios.post('http://localhost:8081/api/products', productData);
        this.modalTitle = 'Success';
        this.modalMessage = 'Item added successfully!';
        this.showModal = true;
        setTimeout(() => {
          this.showModal = false;
          this.$router.push('/SellerHome');
        }, 2000); // Automatically close the modal and redirect after 2 seconds
      } catch (error) {
        this.modalTitle = 'Error';
        this.modalMessage = 'Failed to add item, please try again.';
        this.showModal = true;
      }
    },
    cancel() {
      this.$router.push('/store');
    },
    goBackToHome() {
      this.$router.push('/SellerHome');
    }
  }
};
</script>

<style scoped>
.add-item-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.page-title {
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
}

.top-buttons {
  text-align: left;
  margin-bottom: 20px;
}

.back-homepage-button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.back-homepage-button:hover {
  background-color: #0056b3;
}

.card {
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.form-field {
  margin-bottom: 15px;
}

label {
  font-weight: bold;
  color: #333;
}

input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

textarea {
  resize: vertical;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.submit-button, .cancel-button {
  padding: 10px 20px;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.submit-button {
  background-color: #28a745;
}

.submit-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #dc3545;
}

.cancel-button:hover {
  background-color: #c82333;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.6);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
}

.modal-header, .modal-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.close-button {
  border: none;
  background: none;
  cursor: pointer;
  font-size: 16px;
}

.modal-body {
  margin: 20px 0;
}
</style>