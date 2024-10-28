<template>
  <div class="seller-orders-container">

    <h2>Seller Orders</h2>

    <div class="search-bar">
      <button class="home-button" @click="goToSellerHome">Back to Home</button>
      <input v-model="searchQuery" type="text" placeholder="Search orders by product name..." />
      <button @click="filterOrders">Search</button>
    </div>

    <div>

      <div v-if="filteredOrders.length > 0" class="orders-list">
        <div v-for="(order, index) in paginatedOrders" :key="index" class="order-card" @click="goToOrderDetail(order.orderItemId)">
          <h3>Order ID: {{ order.orderId }}</h3>
          <p><strong>Product Name:</strong> {{ order.productName }}</p>
          <p><strong>Quantity:</strong> {{ order.quantity }}</p>
          <p><strong>Price:</strong> ${{ order.price.toFixed(2) }}</p>
          <p><strong>Order Status:</strong> {{ order.status || 'Pending' }}</p>
        </div>
      </div>
      <div v-else>
        <p>No orders found for this seller.</p>
      </div>
    </div>

    <div class="pagination" v-if="totalPages > 1">
      <button @click="prevPage" :disabled="currentPage === 1">Previous</button>
      <span>Page {{ currentPage }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Next</button>
    </div>
  </div>
</template>



<script>
import axios from 'axios';

export default {
  data() {
    return {
      sellerId: localStorage.getItem('sellerToken'),
      orders: [],
      filteredOrders: [],
      searchQuery: '',
      currentPage: 1,
      pageSize: 5,
    };
  },
  computed: {
    paginatedOrders() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredOrders.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredOrders.length / this.pageSize);
    },
  },
  methods: {
    fetchOrders() {
      axios.get(`http://localhost:8081/api/orders/seller/${this.sellerId}`)
        .then(response => {
          this.orders = response.data;
          this.filteredOrders = this.orders;
        })
        .catch(error => {
          console.error('Error fetching orders:', error);
        });
    },
    filterOrders() {
      this.filteredOrders = this.orders.filter(order =>
        order.productName.toLowerCase().includes(this.searchQuery.trim().toLowerCase())
      );
      this.currentPage = 1;
    },
    prevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    },
    goToOrderDetail(orderItemId) {
      this.$router.push(`/SellerOrderDetail/${orderItemId}`);
    },
    goToSellerHome() {
      this.$router.push('/SellerHome');  // Redirect to the SellerHome page
    }
  },
  mounted() {
    this.fetchOrders();
  }
};
</script>


<style scoped>
.home-button {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  margin-right: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.home-button:hover {
  background-color: #218838;
}
.seller-orders-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f7f7f7;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 28px;
  color: #333;
}

.search-bar {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.search-bar input {
  padding: 10px;
  font-size: 16px;
  width: 300px;
  margin-right: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.search-bar button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-bar button:hover {
  background-color: #0056b3;
}

.orders-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.order-card {
  background-color: white;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  cursor: pointer;
}

.order-card h3 {
  margin-bottom: 10px;
  color: #333;
}

.order-card p {
  margin: 5px 0;
  color: #555;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
}

.pagination button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  margin: 0 10px;
  cursor: pointer;
}

.pagination button:disabled {
  background-color: #ccc;
}

.pagination span {
  font-size: 16px;
}
</style>
