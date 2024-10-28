<template>
  <div class="shopping-cart-container">
    <h3>Shopping Cart</h3>
    <div v-if="cartItems.length > 0">
      <div v-for="(item, index) in cartItems" :key="index" class="cart-item">
        <input type="checkbox" v-model="item.selected" @change="updateTotal">
        <!-- Correctly combine base URL with img_url -->
        <img :src="getImageUrl(item)" :alt="item.productName" class="item-image">
        <div class="item-info">
          <p>{{ item.productName }}</p>
          <p>Price: ${{ item.unitPrice.toFixed(2) }}</p>
          <div class="quantity-control">
            <button @click="updateQuantity(item, item.quantity - 1)" :disabled="item.quantity <= 1">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="updateQuantity(item, item.quantity + 1)">+</button>
          </div>
          <button class="remove-btn" @click="removeItemFromCart(item.id)">Remove</button>
        </div>
      </div>
      <div class="cart-summary">
        <p>Total: ${{ totalAmount.toFixed(2) }}</p>
        <button @click="checkout">Checkout</button>
      </div>
      <p v-if="checkoutError" class="error-message">Please select at least one item.</p>
    </div>
    <div v-else>
      <p>Your shopping cart is empty.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      cartItems: [],
      totalAmount: 0,
      checkoutError: false,
    };
  },
  async mounted() {
    await this.fetchCartItems();
  },
  methods: {
    getImageUrl(item) {
   
      return item.imgUrl ? `http://localhost:8081${item.imgUrl}` : 'https://via.placeholder.com/100';
    },
    async fetchCartItems() {
      const userId = localStorage.getItem('userToken');
      if (userId) {
        try {
          const response = await axios.get(`http://localhost:8081/api/carts/${userId}`);
          this.cartItems = response.data;
          await Promise.all(this.cartItems.map(item => this.fetchProductImage(item.productId)));
          this.updateTotal();
        } catch (error) {
          console.error('Error fetching cart items:', error);
        }
      } else {
        console.error('User ID not found in localStorage.');
      }
    },
   async fetchProductImage(productId) {
  try {
    const response = await axios.get(`http://localhost:8081/api/products/${productId}`);
    const index = this.cartItems.findIndex(i => i.productId === productId);
    if (index !== -1) {
      const updatedItem = {
        ...this.cartItems[index],
        imgUrl: response.data.img_url
      };
 
      this.cartItems = [
        ...this.cartItems.slice(0, index),
        updatedItem,
        ...this.cartItems.slice(index + 1)
      ];
    }
  } catch (error) {
    console.error(`Error fetching image for product ${productId}:`, error);
  
    if (index !== -1) {
      const updatedItem = {
        ...this.cartItems[index],
        imgUrl: 'https://via.placeholder.com/100'
      };
      this.cartItems = [
        ...this.cartItems.slice(0, index),
        updatedItem,
        ...this.cartItems.slice(index + 1)
      ];
    }
  }
}
,
    updateTotal() {
      this.totalAmount = this.cartItems
        .filter(item => item.selected)
        .reduce((sum, item) => sum + item.unitPrice * item.quantity, 0);
    },
    updateQuantity(item, newQuantity) {
      if (newQuantity > 0) {
        axios.put(`http://localhost:8081/api/carts/${item.id}/update-quantity`, {
          quantity: newQuantity
        })
        .then(response => {
          item.quantity = response.data.quantity;
          this.updateTotal();
        })
        .catch(error => {
          console.error('Error updating quantity:', error);
        });
      }
    },
    removeItemFromCart(cartId) {
      axios.delete(`http://localhost:8081/api/carts/remove/${cartId}`)
        .then(() => {
          this.cartItems = this.cartItems.filter(item => item.id !== cartId);
          this.updateTotal();
        })
        .catch(error => {
          console.error('Error removing item from cart:', error);
        });
    },
      checkout() {
      const selectedItems = this.cartItems.filter(item => item.selected); 
      if (selectedItems.length === 0) {
        this.checkoutError = true; 
      } else {
        this.checkoutError = false;
    
        this.$router.push({
          path: '/PayOrder',
          query: {
            selectedItems: JSON.stringify(selectedItems)  
          }
        });
      }
    }
   
  }
};
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

.quantity-control {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.quantity-control input {
  width: 40px;
  text-align: center;
}

.quantity-control button {
  padding: 5px 10px;
  margin-left: 5px;
}

.remove-btn {
  margin-top: 10px;
  padding: 5px 10px;
  background-color: red;
  color: white;
  border: none;
  cursor: pointer;
}

.remove-btn:hover {
  background-color: darkred;
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

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
