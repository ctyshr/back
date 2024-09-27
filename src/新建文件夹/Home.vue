<template>
  <div class="product-recommendations-container">
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="Search products...">
      <button @click="searchProducts">Search</button>
    </div>

    <div class="recommended-products">
      <h3>Recommended Products</h3>
      <div
        v-for="(product, index) in recommendedProducts"
        :key="index"
        class="product-item clickable-product"
        @click="goToProduct(product)"
      >
        <img :src="product.image" :alt="product.name" class="product-image">
        <div class="product-info">
          <p>{{ product.name }}</p>
          <p>{{ product.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      recommendedProducts: [
        {
          id: 1,
          image: 'https://via.placeholder.com/150',
          name: 'Product 1',
          description: 'Description for Product 1'
        },
        {
          id: 2,
          image: 'https://via.placeholder.com/150',
          name: 'Product 2',
          description: 'Description for Product 2'
        },
        {
          id: 3,
          image: 'https://via.placeholder.com/150',
          name: 'Product 3',
          description: 'Description for Product 3'
        }
      ]
    };
  },
  methods: {
    searchProducts() {
      
      console.log(`Searching for: ${this.searchQuery}`);
    },
    goToProduct(product) {
      
      this.$router.push(`/product/${product.id}`);
    }
  }
}
</script>

<style scoped>
.product-recommendations-container {
  padding: 20px;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.search-bar input {
  flex: 1;
  padding: 10px;
  margin-right: 10px;
}

.search-bar button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-bar button:hover {
  background-color: #0056b3;
}

.recommended-products {
  margin-top: 20px;
}

.product-item {
  display: flex;
  align-items: center;
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px 0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.product-item:hover {
  background-color: #f0f0f0;
}

.product-image {
  width: 150px;
  height: 150px;
  object-fit: cover;
  margin-right: 20px;
}

.product-info {
  flex: 1;
}
</style>
