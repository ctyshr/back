<template>
  <div class="order-history-container">
    <div class="current-order">
      <h3>Current Order</h3>
      <div v-if="currentOrder" @click="goToOrder(currentOrder.id)" class="clickable-order">
        <p>Order ID: {{ currentOrder.id }}</p>
       
        <p>Status: {{ currentOrder.status }}</p>
        <p>Order Date: {{ currentOrder.orderDate | formatDate }}</p>
        <p>Total: ${{ currentOrder.totalAmount.toFixed(2) }}</p>
      </div>
      <div v-else>
        <p>No current orders.</p>
      </div>
    </div>

    <div class="history-orders">
      <h3>Order History</h3>
      <div v-if="orderHistory.length > 0">
        <div v-for="(order, index) in orderHistory" :key="index" @click="goToOrder(order.id)" class="order-item clickable-order">
          <p>Order ID: {{ order.id }}</p>
         
          <p>Status: {{ order.status }}</p>
          <p>Order Date: {{ order.orderDate | formatDate }}</p>
          <p>Total: ${{ order.totalAmount.toFixed(2) }}</p>
        </div>
      </div>
      <div v-else>
        <p>No past orders found.</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      currentOrder: null,  
      orderHistory: []    
    };
  },
  mounted() {
    this.fetchOrders();
  },
  methods: {
    async fetchOrders() {
      const userId = localStorage.getItem('userToken');  

      try {
        const response = await axios.get(`http://localhost:8081/api/orders/user/${userId}`);
        const orders = response.data.reverse(); 

        if (orders.length > 0) {
          this.currentOrder = orders[0]; 
          this.orderHistory = orders.slice(1);  
        }
      } catch (error) {
        console.error('Error fetching orders:', error);
      }
    },
    goToOrder(orderId) {
      this.$router.push(`/OrderDetial/${orderId}`);
    }
  },
  filters: {
    formatDate(value) {
      if (!value) return '';
      const date = new Date(value);
      return date.toLocaleDateString();
    }
  }
}
</script>

<style scoped>
.order-history-container {
  margin-left: 250px; 
  padding: 20px;
}

.current-order, .history-orders {
  margin-bottom: 20px;
}

.order-item {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px 0;
}

.clickable-order {
  cursor: pointer;
  transition: background-color 0.3s;
}

.clickable-order:hover {
  background-color: #f0f0f0;
}
</style>
