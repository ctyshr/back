<template>
  <div class="order-history-container">
    <div class="current-order">
      <h3>Current Order</h3>
      <div v-if="currentOrder" @click="goToOrder(currentOrder.id)" class="clickable-order">
        <p>Order ID: {{ currentOrder.id }}</p>
        <p>Items: {{ currentOrder.items.join(', ') }}</p>
        <p>Status: {{ currentOrder.status }}</p>
      </div>
      <div v-else>
        <p>No current orders.</p>
      </div>
    </div>

    <div class="history-orders">
      <h3>Order History</h3>
      <div v-for="(order, index) in orderHistory" :key="index" @click="goToOrder(order.id)" class="order-item clickable-order">
        <p>Order ID: {{ order.id }}</p>
        <p>Items: {{ order.items.join(', ') }}</p>
        <p>Status: {{ order.status }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentOrder: {
        id: '001',
        items: ['Item 1', 'Item 2', 'Item 3'],
        status: 'Processing'
      },
      orderHistory: [
        { id: '002', items: ['Item A', 'Item B'], status: 'Delivered' },
        { id: '003', items: ['Item X', 'Item Y', 'Item Z'], status: 'Delivered' }
      ]
    };
  },
  methods: {
    goToOrder(orderId) {
      // Navigate to the order detail page using the order ID
      this.$router.push(`/OrderDetial`);
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
