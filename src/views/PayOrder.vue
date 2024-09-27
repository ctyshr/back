<template>  
  <div class="checkout-page">  
    <div class="section delivery-address" @click="showAddressModal = true">  
      <h2>select address</h2>  

      <div class="modal" tabindex="-1" role="dialog" v-if="showAddressModal">  
        <div class="modal-dialog" role="document">  
          <div class="modal-content">  
            <div class="modal-header">  
              <h5 class="modal-title">选择配送地址</h5>  
              <button type="button" class="close" @click="showAddressModal = false" aria-label="Close">  
                <span aria-hidden="true">&times;</span>  
              </button>  
            </div>  
            <div class="modal-body">  
              <ul class="list-group">  
                <li class="list-group-item" v-for="address in addresses" :key="address.id" @click="selectAddress(address)">  
                  {{ address.street }}, {{ address.city }}, {{ address.state }}, {{ address.zip }}  
                </li>  
              </ul>  
            </div>  
          </div>  
        </div>  
      </div>  

      <p v-if="selectedAddress">Address: {{ selectedAddress.street }}, {{ selectedAddress.city }}</p>  
    </div>  
  
    <div class="section payment-method" @click="showPaymentModal = true">  
 
      <div class="modal" tabindex="-1" role="dialog" v-if="showPaymentModal">  
        <div class="modal-dialog" role="document">  
          <div class="modal-content">  
            <div class="modal-header">  
              <h5 class="modal-title">how would you like to pay</h5>  
              <button type="button" class="close" @click="showPaymentModal = false" aria-label="Close">  
                <span aria-hidden="true">&times;</span>  
              </button>  
            </div>  
            <div class="modal-body">  
              <ul class="list-group">  
                <li class="list-group-item" v-for="method in paymentMethods" :key="method.id" @click="selectPaymentMethod(method)">  
                  {{ method.name }}  
                </li>  
              </ul>  
            </div>  
          </div>  
        </div>  
      </div>  
  

      <p v-if="selectedPaymentMethod">payment: {{ selectedPaymentMethod.name }}</p>  
    </div>  
  
    <div class="section product-list">  

      <ul>  
        <li>productA - $10</li>  
        <li>productB - $20</li>  
      </ul>  
    </div>  
  
    <div class="total-amount">  
      <h2>total amount: $30</h2>  
    </div>  
  
    <button class="confirm-payment-btn btn btn-success" @click="confirmPayment">confirm</button>  
  </div>  
</template>  
  
<script>  
export default {  
  data() {  
    return {  
      addresses: [  

        { id: 1, street: 'default', city: 'default', state: 'default', zip: 'postcode' }  
      ],  
      selectedAddress: null, 
      showAddressModal: false,  
      paymentMethods: [  
 
        { id: 1, name: 'default payment' }  
      ],  
      selectedPaymentMethod: null, 
      showPaymentModal: false  
    };  
  },  
  created() {  

    this.selectedAddress = this.addresses[0];  
    this.selectedPaymentMethod = this.paymentMethods[0];  
  },  
  methods: {  
    selectAddress(address) {  
      this.selectedAddress = address;  
      this.showAddressModal = false;  
    },  
    selectPaymentMethod(method) {  
      this.selectedPaymentMethod = method;  
      this.showPaymentModal = false;  
    },  
    confirmPayment() {  
  
  this.$router.push('/PaymentState'); 
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
</style>