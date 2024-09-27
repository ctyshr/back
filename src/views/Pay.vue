<template>
  <div class="payment-cards-container">
    <div v-if="cards.length === 0">
      <p>No payment methods available.</p>
    </div>

    <div v-else>
      <div v-for="(card, index) in cards" :key="index" class="card">
        <p>Card Number: {{ card.cardNumber }}</p>  
        <p>Card Holder: {{ card.cardHolder }}</p>  
        <p>Expiration Date: {{ card.expiration }}</p> 
      </div>
    </div>

    <div class="add-card">
      <button @click="showAddCardForm = true">Add New Card</button>
    </div>

    <div v-if="showAddCardForm" class="add-card-form">
      <h3>Add New Card</h3>
      <form @submit.prevent="addCard">
        <div>
          <label for="number">Card Number:</label>
          <input type="text" v-model="newCard.cardNumber" required> 
        </div>
        <div>
          <label for="holder">Card Holder:</label>
          <input type="text" v-model="newCard.cardHolder" required> 
        </div>
        <div>
          <label for="expiry">Expiration Date:</label>
          <input type="text" v-model="newCard.expiration" required> 
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: 2,  
      cards: [],  
      showAddCardForm: false,
      newCard: {
        cardNumber: '',   
        cardHolder: '',   
        expiration: ''    
      }
    };
  },
  methods: {
    async loadPaymentMethods() {
      try {
        const response = await axios.get(`http://localhost:8081/api/payment-methods/${this.userId}`);
        this.cards = response.data;  
      } catch (error) {
        console.error('Error loading payment methods:', error);
      }
    },
    async addCard() {
      try {
        const response = await axios.post(`http://localhost:8081/api/payment-methods/${this.userId}`, this.newCard);
        this.cards.push(response.data);  
        this.newCard = { cardNumber: '', cardHolder: '', expiration: '' };
        this.showAddCardForm = false;
      } catch (error) {
        console.error('Error adding card:', error);
      }
    }
  },
  mounted() {
    this.loadPaymentMethods();  
  }
}
</script>

<style scoped>
.payment-cards-container {
  margin-left: 250px;
  padding: 20px;
}

.card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px 0;
}

.add-card {
  margin-top: 20px;
}

.add-card-form {
  margin-top: 20px;
}

button {
  cursor: pointer;
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
}

button:hover {
  background-color: #0056b3;
}

form div {
  margin: 10px 0;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  padding: 8px;
  width: calc(100% - 16px);
  box-sizing: border-box;
}
</style>
