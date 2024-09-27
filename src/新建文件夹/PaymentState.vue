<template>  
    <div class="payment-status-page">  
      <div class="status-message">  
        <h1>{{ paymentStatus }}</h1>  
        <p v-if="paymentStatus === 'success'">payment successful</p>  
        <p v-else>something wrong with the payment</p>  
      </div>  
    
      <div class="order-content" v-if="order">  
        <h2>order detail</h2>  
        <ul>  
          <li v-for="item in order.items" :key="item.id">  
            {{ item.name }} - ${{ item.price }} x {{ item.quantity }}  
          </li>  
        </ul>  
        <p><strong>total amount</strong>¥{{ order.totalAmount }}</p>  
      </div>  
    
      <button class="back-btn" @click="goBack">back</button>  
    </div>  
  </template>  
    
  <script>  
  export default {  
    data() {  
      return {  
        paymentStatus: 'success', 
        order: {  
          id: '123456789',  
          items: [  
            { id: 1, name: 'productA', price: 100, quantity: 2 },  
            { id: 2, name: 'productB', price: 200, quantity: 1 },  
          ],  
          totalAmount: 400,  
        },  
      };  
    },  
    methods: {  
      goBack() {  
this.$router.push('/home'); 
      },  
    },  
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
    
  .order-content ul {  
    list-style-type: none;  
    padding: 0;  
  }  
    
  .back-btn {  
    background-color: #007BFF;  
    color: white;  
    padding: 10px 20px;  
    border: none;  
    border-radius: 5px;  
    cursor: pointer;  
    width: 100%;  
  }  
  </style>