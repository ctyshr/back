<template>
  <div class="payment-status-page">
    <div class="status-message">
      <h1 v-if="order">Order Successful</h1>
      <p v-if="order">Your order has been successfully placed.</p>
      <ul v-if="order">
        <li v-for="item in order.orderItems" :key="item.productId">
          {{ item.productName }} - ${{ item.price }} x {{ item.quantity }}
        </li>
      </ul>
      <button v-if="order" class="check-order-btn" @click="goToProfile">Check Order</button>
      <p v-else>Order processing failed.</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      order: null
    };
  },
  created() {
    // Parse the order object passed as a query parameter
    const orderParam = this.$route.query.order;
    if (orderParam) {
      try {
        this.order = JSON.parse(orderParam);
      } catch (error) {
        console.error('Failed to parse order data:', error);
      }
    }
  },
  methods: {
    goToProfile() {
      this.$router.push('/profile');
    }
  }
};
</script>

<style scoped>
.payment-status-page {
  margin: 40px auto;
  width: 80%;
  max-width: 600px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.status-message {
  text-align: center;
  margin-bottom: 20px;
}

.check-order-btn {
  background-color: #007BFF;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
}
</style>
