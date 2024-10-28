<template>
  <div class="payment-cards-container">

    <div v-if="cards.length === 0">
      <p>No payment methods available.</p>
    </div>
    <div v-else>
      <div v-for="(card, index) in sanitizedCards" :key="index" class="card">
        <p>Card Number: {{ card.cardNumber }}</p>  
        <p>Card Holder: {{ card.holderName }}</p>  
        <p lang="en">Expiration Date: {{ card.expiration }}</p> 
      </div>
    </div>

   
    <div class="add-card">
      <button @click="showAddCardModal = true">Add New Card</button>
    </div>


    <div v-if="showAddCardModal" class="modal">
      <div class="modal-content">
        <h3>Add New Card</h3>
        <form @submit.prevent="addCard">
          <div>
            <label for="number">Card Number:</label>
            <input 
              type="text" 
              v-model="newCard.cardNumber" 
              required 
              @input="validateCardNumber" 
              :class="{ 'invalid-input': !isCardNumberValid }" 
            />
            <p v-if="!isCardNumberValid" class="error-message">Card number must be 16 digits.</p>
          </div>
          <div>
            <label for="holder">Card Holder:</label>
            <input 
              type="text" 
              v-model="newCard.holderName" 
              required 
              @input="sanitizeInput('holderName')"
            />
          </div>
          <div>
            <label for="expiry" lang="en">Expiration Date:</label> 
            <input 
              type="date" 
              v-model="newCard.expiration" 
              required 
              @input="validateExpirationDate" 
              :class="{ 'invalid-input': !isExpirationDateValid }" 
            />
            <p v-if="!isExpirationDateValid" class="error-message">Expiration date must be in the future.</p>
          </div>
          <div class="modal-actions">
            <button type="submit" :disabled="!isFormValid">Submit</button>
            <button @click="closeAddCardModal">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import DOMPurify from 'dompurify'; // Use DOMPurify for XSS

export default {
  data() {
    return {
      userId: localStorage.getItem('userToken'),
      cards: [],
      showAddCardModal: false,
      newCard: {
        cardNumber: '',
        holderName: '',
        expiration: ''
      },
      isCardNumberValid: true,
      isExpirationDateValid: true
    };
  },
  computed: {
    isFormValid() {
      return this.isCardNumberValid && this.isExpirationDateValid && this.newCard.cardNumber && this.newCard.holderName && this.newCard.expiration;
    },
    sanitizedCards() {
      return this.cards.map(card => ({
        cardNumber: DOMPurify.sanitize(card.cardNumber),
        holderName: DOMPurify.sanitize(card.holderName),
        expiration: DOMPurify.sanitize(card.expiration)
      }));
    }
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
        this.newCard = { cardNumber: '', holderName: '', expiration: '' };
        this.showAddCardModal = false;
      } catch (error) {
        console.error('Error adding card:', error);
      }
    },
    validateCardNumber() {
      this.isCardNumberValid = /^\d{16}$/.test(this.newCard.cardNumber);
    },
    validateExpirationDate() {
      const today = new Date();
      const expirationDate = new Date(this.newCard.expiration);
      this.isExpirationDateValid = expirationDate > today;
    },
    sanitizeInput(field) {
      this.newCard[field] = DOMPurify.sanitize(this.newCard[field]);
    },
    closeAddCardModal() {
      this.showAddCardModal = false;
    }
  },
  mounted() {
    this.loadPaymentMethods();
  }
};
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

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  max-width: 500px;
  width: 100%;
}

.modal-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.invalid-input {
  border-color: red;
}

.error-message {
  color: red;
  font-size: 12px;
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
</style>
