<template>
  <div class="order-detail-page">
    <div v-if="order">
      <div class="order-info">
        <h1>Order Detail</h1>
        <p><strong>Order Number:</strong> {{ order.id }}</p>
        <p><strong>Order Date:</strong> {{ new Date(order.orderDate).toLocaleDateString() }}</p>
        <p><strong>Total Amount:</strong> ${{ order.totalAmount.toFixed(2) }}</p>
      </div>
      <div class="order-items">
        <h2>Order Items</h2>
        <ul>
          <li v-for="(item, index) in order.orderItems" :key="index" class="item-card">
            <div class="item-image-container">
              <img :src="`http://localhost:8081${item.productImage}`" alt="Product Image" class="product-image">
            </div>
            <div class="item-info">
              <h3>{{ item.productName }}</h3>
              <p><strong>Quantity:</strong> {{ item.quantity }}</p>
              <p><strong>Price:</strong> ${{ item.price }}</p>
            </div>
            <div class="item-actions">
              <button class="contact-seller-btn" @click="contactSeller(item.productId)">Contact Seller</button>
             <button v-if="!item.hasComment" class="rate-btn" @click="rateProduct(item)">Rate</button>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div v-else>
      <p>Loading order details...</p>
    </div>
    <button class="back-btn" @click="goBack">Back</button>
  </div>
</template>




  <script>
  import axios from 'axios';

  export default {
    data() {
      return {
        order: null
      };
    },
    mounted() {
      this.fetchOrderDetails();
    },
    methods: {
          async fetchOrderDetails() {
      const orderId = this.$route.params.id;
      try {
        const response = await axios.get(`http://localhost:8081/api/orders/${orderId}`);
        const orderData = response.data;
        await Promise.all(orderData.orderItems.map(async (item) => {
          const productResponse = await axios.get(`http://localhost:8081/api/products/${item.productId}`);
          item.productName = productResponse.data.name;
          item.productImage = productResponse.data.img_url;
          const commentResponse = await axios.get(`http://localhost:8081/api/comments/check/${item.id}`);
          item.hasComment = commentResponse.data === 1;
        }));
        this.order = orderData;
      } catch (error) {
        console.error('Error fetching order details:', error);
        alert('Failed to load order details.');
      }
    },
   rateProduct(item) {
    this.$router.push({
      path: `/Rate/${item.id}`,
      query: {
        orderItemId: item.id,
        productId: item.productId
      }
    });
  },

      async contactSeller(productId) {
        try {
          const response = await axios.get(`http://localhost:8081/api/products/${productId}/seller`);
          const sellerId = response.data.sellerId;
          this.$router.push({ name: 'UserMessage', params: { id: sellerId } });
        } catch (error) {
          console.error('Error fetching seller info:', error);
          alert('Failed to contact seller.');
        }
      },

      goBack() {
        this.$router.go(-1);
      }
    }
  };
  </script>

  <style scoped>
  .rate-btn {
  background-color: #ffc107;
  color: black;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.rate-btn:hover {
  background-color: #e0a800;
}
  .order-detail-page {
    margin: 40px auto;
    width: 80%;
    max-width: 800px;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  .order-info {
    margin-bottom: 20px;
  }

  .order-items ul {
    list-style-type: none;
    padding: 0;
  }

  .item-card {
    display: flex;
    align-items: center;
    border-bottom: 1px solid #ccc;
    padding: 15px 0;
    gap: 15px;
  }

  .item-image-container {
    flex: 0 0 50px; /* fixed width */
  }

  .product-image {
    width: 100%;
    height: auto;
    object-fit: cover;
  }

  .item-info {
    flex-grow: 1;
  }

  .item-actions {
    text-align: right;
  }

  .contact-seller-btn {
    background-color: #28a745;
    color: white;
    padding: 5px 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .contact-seller-btn:hover {
    background-color: #218838;
  }

  .back-btn {
    background-color: #007BFF;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    width: 100%;
    margin-top: 20px;
  }

  .back-btn:hover {
    background-color: #0056b3;
  }
  </style>
