<template>
  <div class="order-detail-container">
    <h2>Order Details</h2>
    <p><strong>Order Item ID:</strong> {{ orderDetails.orderItemId }}</p>
    <p><strong>Product ID:</strong> {{ orderDetails.productId }}</p>
    <p><strong>Quantity:</strong> {{ orderDetails.quantity }}</p>
    <p><strong>Price:</strong> ${{ orderDetails.price.toFixed(2) }}</p>
    <p><strong>Order Date:</strong> {{ formatDate(orderDetails.orderDate) }}</p>
    <p><strong>Buyer Name:</strong> {{ orderDetails.customerName }}</p>

    <button @click="contactBuyer">Contact Buyer</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      orderDetails: {}, 
    };
  },
  methods: {

    fetchOrderDetails() {
      const orderItemId = this.$route.params.id; 
      axios
        .get(`http://localhost:8081/api/orders/${orderItemId}/detail`)
        .then((response) => {
          this.orderDetails = response.data;
        })
        .catch((error) => {
          console.error('Error fetching order details:', error);
        });
    },

   formatDate(date) {
      const options = { 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric', 
        hour: '2-digit', 
        minute: '2-digit' 
      };
      return new Date(date).toLocaleDateString('en-US', options);
    },
 
    contactBuyer() {

      this.$router.push({ name: 'SellerMessage', params: { customerId: this.orderDetails.userId } });
    }
  },
  mounted() {
    this.fetchOrderDetails(); 
  },
};
</script>



<style scoped>
.order-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f7f7f7;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

p {
  font-size: 18px;
  margin: 10px 0;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
