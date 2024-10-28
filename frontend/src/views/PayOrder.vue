<template>
  <div class="checkout-page">
    <!-- Address Section -->
    <div class="section delivery-address" @click="showAddressModal = true">
      <h2>Select Address</h2>
      <p v-if="selectedAddress">Address: {{ selectedAddress.street }}, {{ selectedAddress.city }}</p>
    </div>

    <!-- Modal for Address Selection -->
    <div class="modal" v-if="showAddressModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Select Delivery Address</h5>
            <button type="button" class="close" @click="showAddressModal = false">&times;</button>
          </div>
          <div class="modal-body">
            <ul>
              <li v-for="address in addresses" :key="address.id" @click="selectAddress(address)">
                {{ address.street }}, {{ address.city }}, {{ address.state }}, {{ address.zip }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- Payment Section -->
    <div class="section payment-method" @click="showPaymentModal = true">
      <h2>Select Payment Method</h2>
      <p v-if="selectedPaymentMethod">Payment: {{ selectedPaymentMethod.holderName }} - {{ selectedPaymentMethod.cardNumber }}</p>
    </div>

    <!-- Modal for Payment Method Selection -->
    <div class="modal" v-if="showPaymentModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Select Payment Method</h5>
            <button type="button" class="close" @click="showPaymentModal = false">&times;</button>
          </div>
          <div class="modal-body">
            <ul>
              <li v-for="method in paymentMethods" :key="method.id" @click="selectPaymentMethod(method)">
                <p>Card Number: {{ method.cardNumber }}</p>
                <p>Card Holder: {{ method.holderName }}</p>
                <p>Expiration Date: {{ method.expiration }}</p>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- Product List -->
    <div class="section product-list">
      <ul>
        <li v-for="item in selectedItems" :key="item.productId">
          {{ item.productName }} - ${{ item.unitPrice }} (Quantity: {{ item.quantity }})
        </li>
      </ul>
    </div>

    <!-- Total Amount -->
    <div class="total-amount">
      <h2>Total Amount: ${{ totalAmount }}</h2>
    </div>

    <!-- Confirm Payment Button -->
    <button class="confirm-payment-btn btn btn-success" @click="confirmPayment">Confirm</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      addresses: [],
      selectedAddress: null,
      showAddressModal: false,
      paymentMethods: [],
      selectedPaymentMethod: null,
      showPaymentModal: false,
      selectedItems: [],
      totalAmount: 0
    };
  },
  created() {
    const selectedItems = JSON.parse(this.$route.query.selectedItems);
    this.selectedItems = selectedItems;
    this.calculateTotalAmount();

    this.fetchAddresses();
    this.fetchPaymentMethods();
  },
  methods: {

    calculateTotalAmount() {
      this.totalAmount = this.selectedItems.reduce((sum, item) => sum + (item.unitPrice * item.quantity), 0);
    },

 
    fetchAddresses() {
      const userId = localStorage.getItem('userToken');
      axios.get(`http://localhost:8081/api/addresses/${userId}`)
        .then(response => {
          this.addresses = response.data;
          this.selectedAddress = this.addresses[0]; 
        })
        .catch(error => {
          console.error('Error fetching addresses:', error);
        });
    },


    selectAddress(address) {
      this.selectedAddress = address;
      this.showAddressModal = false;
    },

    fetchPaymentMethods() {
      const userId = localStorage.getItem('userToken');
      axios.get(`http://localhost:8081/api/payment-methods/${userId}`)
        .then(response => {
          this.paymentMethods = response.data;
          this.selectedPaymentMethod = this.paymentMethods[0]; 
        })
        .catch(error => {
          console.error('Error fetching payment methods:', error);
        });
    },

    selectPaymentMethod(method) {
      this.selectedPaymentMethod = method;
      this.showPaymentModal = false;
    },


  // Confirm payment and send order details
confirmPayment() {
  if (this.selectedAddress && this.selectedPaymentMethod) {
    const userId = localStorage.getItem('userToken');
    const orderDetails = {
      customerName: userId,
      shippingAddress: this.selectedAddress.id,
      paymentMethod: this.selectedPaymentMethod.id,
      orderDate: new Date().toISOString(),
      totalAmount: this.totalAmount,
      status: 'Pending',
      orderItems: this.selectedItems.map(item => ({
        productId: item.productId,
        quantity: item.quantity,
        price: item.unitPrice
      }))
    };

    axios.post('http://localhost:8081/api/orders', orderDetails)
      .then(response => {
        if (response.data && response.data.id) {  // Assuming the API returns the created order object
          // Using JSON.stringify to pass the order object as a query parameter
          this.$router.push({
            name: 'PaymentState',  // Assuming 'PaymentState' is the route name
            query: { order: JSON.stringify(response.data) }
          });
        } else {
          throw new Error('Order creation failed without error message.');
        }
      })
      .catch(error => {
        console.error('Error creating order:', error);
        alert('Failed to create order. Please try again.');
      });
  } else {
    alert('Please select an address and payment method.');
  }
}


  }
};
</script>

<style scoped>
.checkout-page {
  margin: 40px auto;
  width: 80%;
  max-width: 800px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.section {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
}

.product-list ul {
  list-style-type: none;
  padding: 0;
}

.total-amount {
  margin-bottom: 20px;
}

.confirm-payment-btn {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  cursor: pointer;
  position: absolute;
  bottom: 20px;
  right: 20px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-dialog {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  width: 80%;
  max-width: 500px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-body {
  margin-top: 20px;
}
</style>
