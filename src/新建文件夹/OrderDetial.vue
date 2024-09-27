<template>  
    <div class="order-detail-page">  
      <div class="order-info">  
        <h1>order detial</h1>  
        <p><strong>order number11111</strong>{{ order.id }}</p>  
        <p><strong>order date</strong>{{ order.date }}</p>  
        <p><strong>order amount：</strong>${{ order.totalAmount }}</p>  
      </div>  

    
      <button class="back-btn" @click="goBack">back</button>  
    </div>  
  </template>  
    
  <script>  
  export default {  
    data() {  
      return {  
        order: {  
          id: '123456789',  
          date: '2023-04-01',  
          totalAmount: 999.99,  
        },  
      };  
    },  
    methods: {  
      goBack() {  
 
        this.$router.go(-1);
      },  
    },  
  };  
  </script>  
    
  <style scoped>  
  .order-detail-page {  
    margin: 40px auto;  
    width: 80%;  
    max-width: 600px;  
    padding: 20px;  
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);  
  }  
    
  .order-info {  
    margin-bottom: 20px;  
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