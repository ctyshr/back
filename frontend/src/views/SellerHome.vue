<template>
  <div class="store-page-container">
    <div class="top-buttons">
      <button class="homepage-button">Back to Homepage</button>
      <button class="orders-button" @click="goToSellerOrders">Orders</button>
      <button class="login-button" @click="logout">Log in/out</button>
      <button class="profile-button" @click="goToSellerProfile">Seller Profile</button>
    </div>

    <div class="store-name">
      <h1 align="center">Store</h1>
    </div>

    <div class="add-item-section">
      <button class="add-item-button" @click="goToAddNewItem">Add New Item</button>
    </div>

    <label class="section-title">Current Items</label>

    <div v-for="item in products" :key="item.id" class="item-details">
      <div class="item-info card">
        <div class="item-name">
          <label>Item Name:</label>
          <span>{{ item.name }}</span>
        </div>
        <div class="description">
          <label>Description:</label>
          <span>{{ item.description }}</span>
        </div>
        <div class="price">
          <label>Price:</label>
          <span>${{ item.price }}</span>
        </div>
      </div>
      <div class="item-actions">
        <button class="edit-button" @click="editItem(item.id)">Edit</button>
        <button class="remove-button" @click="removeItem(item.id)">Remove</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      products: [] 
    };
  },
  methods: {
    goToAddNewItem() {
      this.$router.push('/AddItem');
    },
    goToSellerProfile() {
      this.$router.push('/SellerProfile');
    },
    goToSellerOrders() {
      this.$router.push('/SellerOrder');
    },
    logout() {
      localStorage.removeItem('sellerToken');
      this.$router.push('/SellerLogin');
    },
    fetchProducts() {
      const sellerId = localStorage.getItem('sellerToken');
      axios.get(`http://localhost:8081/api/products/seller/${sellerId}`)
        .then(response => {
          this.products = response.data;
        })
        .catch(error => {
          console.error('Error fetching products:', error);
        });
    },
    editItem(id) {
      this.$router.push(`/edit/${id}`);
    },
    removeItem(id) {
      axios.delete(`http://localhost:8081/api/products/${id}`)
        .then(() => {
          this.fetchProducts();
        })
        .catch(error => {
          console.error('Error removing item:', error);
        });
    }
  },
  mounted() {
    this.fetchProducts();
  }
};
</script>

<style scoped>
.store-page-container {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #f8f9fa;
}

.section-title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #343a40;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

button:hover {
  transform: scale(1.05);
}

.homepage-button, .login-button, .orders-button, .profile-button {
  background-color: #28a745;
  color: white;
  margin-right: 10px;
}

.homepage-button:hover, .login-button:hover, .orders-button:hover, .profile-button:hover {
  background-color: #218838;
}

.edit-button, .remove-button {
  background-color: #007bff; /* Unified blue color for both buttons */
  color: white;
}

.edit-button:hover, .remove-button:hover {
  background-color: #0056b3; /* Darker blue on hover for both buttons */
}

.remove-button {
  background-color: #dc3545; /* Red color for delete but same style */
}

.remove-button:hover {
  background-color: #c82333; /* Darker red on hover */
}

.add-item-button {
  background-color: #17a2b8;
  color: white;
}

.add-item-button:hover {
  background-color: #138496;
}

.top-buttons {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 30px;
}

.add-item-section {
  text-align: right;
  margin-bottom: 20px;
}

.store-name {
  font-size: 24px;
  color: #343a40;
  margin-bottom: 30px;
}

.item-details {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
}

.item-info {
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  flex: 1;
  margin-right: 20px;
}

.item-actions {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.item-actions button {
  margin-bottom: 10px;
}

.card {
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  transition: transform 0.2s ease;
}

.card:hover {
  transform: translateY(-5px);
}

label {
  font-weight: bold;
  color: #555;
}

span {
  color: #333;
  font-size: 16px;
}
</style>
