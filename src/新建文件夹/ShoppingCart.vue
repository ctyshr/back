<template>
  <div class="shopping-cart-container">
    <h3>Shopping Cart</h3>
    <div v-for="(item, index) in cartItems" :key="index" class="cart-item">
      <input type="checkbox" v-model="item.selected" @change="updateTotal">
      <img :src="item.image" :alt="item.name" class="item-image">
      <div class="item-info">
        <p>{{ item.name }}</p>
        <p>Price: ${{ item.price.toFixed(2) }}</p>
      </div>
    </div>
    
    <div class="cart-summary">
      <p>Total: ${{ totalAmount.toFixed(2) }}</p>
      <button @click="checkout">Checkout</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cartItems: [
        {
          image: 'https://via.placeholder.com/100',
          name: 'Product 1',
          price: 29.99,
          selected: false
        },
        {
          image: 'https://via.placeholder.com/100',
          name: 'Product 2',
          price: 49.99,
          selected: false
        },
        {
          image: 'https://via.placeholder.com/100',
          name: 'Product 3',
          price: 19.99,
          selected: false
        }
      ],
      totalAmount: 0
    };
  },
  methods: {
    updateTotal() {
      this.totalAmount = this.cartItems
        .filter(item => item.selected)
        .reduce((sum, item) => sum + item.price, 0);
    },
    checkout() {
      this.$router.push('/PayOrder'); 
    }
  }
}
</script>

<style scoped>
.shopping-cart-container {
  padding: 20px;
}

.cart-item {
  display: flex;
  align-items: center;
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px 0;
}

.cart-item input {
  margin-right: 10px;
}

.item-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 20px;
}

.item-info {
  flex: 1;
}

.cart-summary {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #ccc;
  padding-top: 10px;
}

button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
