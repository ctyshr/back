<template>
  <div class="payment-cards-container">
    <div v-if="cards.length === 0">
      <p>No payment methods available.</p>
    </div>

    <div v-else>
      <div v-for="(card, index) in cards" :key="index" class="card">
        <p>Card Number: {{ card.cardNumber }}</p>  <!-- 使用 cardNumber -->
        <p>Card Holder: {{ card.cardHolder }}</p>   <!-- 使用 cardHolder -->
        <p>Expiration Date: {{ card.expiration }}</p> <!-- 使用 expiration -->
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
          <input type="text" v-model="newCard.cardNumber" required> <!-- 使用 cardNumber -->
        </div>
        <div>
          <label for="holder">Card Holder:</label>
          <input type="text" v-model="newCard.cardHolder" required> <!-- 使用 cardHolder -->
        </div>
        <div>
          <label for="expiry">Expiration Date:</label>
          <input type="text" v-model="newCard.expiration" required> <!-- 使用 expiration -->
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
      userId: 2,  // 用户ID
      cards: [],  // 存储支付方式
      showAddCardForm: false,
      newCard: {
        cardNumber: '',   // 使用 cardNumber
        cardHolder: '',   // 使用 cardHolder
        expiration: ''    // 使用 expiration
      }
    };
  },
  methods: {
    async loadPaymentMethods() {
      try {
        const response = await axios.get(`http://localhost:8081/api/payment-methods/${this.userId}`);
        this.cards = response.data;  // 将获取的支付方式赋值给 cards
      } catch (error) {
        console.error('Error loading payment methods:', error);
      }
    },
    async addCard() {
      try {
        const response = await axios.post(`http://localhost:8081/api/payment-methods/${this.userId}`, this.newCard);
        this.cards.push(response.data);  // 添加新卡到列表中
        this.newCard = { cardNumber: '', cardHolder: '', expiration: '' };
        this.showAddCardForm = false;
      } catch (error) {
        console.error('Error adding card:', error);
      }
    }
  },
  mounted() {
    this.loadPaymentMethods();  // 页面加载时调用，获取支付方式
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
