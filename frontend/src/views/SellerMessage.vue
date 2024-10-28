<template>
  <div class="message-container">
    <div class="header">
      <button @click="goBack" class="back-button">&#x2190;</button>
      <h2>Message with {{ customerName || 'Customer' }}</h2>
    </div>
    <div class="messages-view">
      <div v-if="messages.length > 0">
        <div v-for="message in messages" :key="message.id" 
             class="message-item"
             :class="{'customer-message': message.senderType === 'USER', 'seller-message': message.senderType === 'SELLER'}">
          <div class="message-header">
            {{ message.senderType === 'USER' ? customerName : 'You' }}
          </div>
          <p>{{ message.message }}</p>
        </div>
      </div>
      <div v-else>
        <p>No messages yet.</p>
      </div>
    </div>
    <div class="send-message-form">
      <textarea v-model="newMessage" placeholder="Type your message here..."></textarea>
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      messages: [],
      newMessage: '',
      sellerId: localStorage.getItem('sellerToken'),
      currentCustomerId: this.$route.params.customerId,
      customerName: '',
      intervalId: null
    };
  },
  methods: {
    fetchMessages() {
      axios.get(`http://localhost:8081/api/messages/${this.sellerId}/${this.currentCustomerId}`)
        .then((response) => {
          this.messages = response.data;
        })
        .catch((error) => {
          console.error('Error fetching messages:', error);
        });
    },
    sendMessage() {
      const messageRequest = {
        sellerId: this.sellerId,
        userId: this.currentCustomerId,
        message: this.newMessage,
        senderType: 'SELLER',
      };
      axios.post('http://localhost:8081/api/messages', messageRequest)
        .then(() => {
          this.newMessage = '';
          this.fetchMessages();
        })
        .catch((error) => {
          console.error('Error sending message:', error);
        });
    },
    fetchCustomerInfo() {
      axios.get(`http://localhost:8081/user/${this.currentCustomerId}`)
        .then((response) => {
          this.customerName = response.data.username;
        })
        .catch((error) => {
          console.error('Error fetching customer info:', error);
        });
    },
    goBack() {
      this.$router.go(-1);
    },
    startPolling() {
      this.intervalId = setInterval(this.fetchMessages, 3000); // Poll every 3 seconds
    }
  },
  mounted() {
    this.fetchMessages();
    this.fetchCustomerInfo();
    this.startPolling();
  },
  beforeDestroy() {
    clearInterval(this.intervalId); // Stop polling when component is destroyed
  }
};
</script>

<style scoped>
/* Styles unchanged */
.message-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding: 20px;
}
.header {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}
.back-button {
  position: absolute;
  left: 0;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #707070;
}
.messages-view {
  overflow-y: auto;
  flex-grow: 1;
}
.message-item {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
  width: 100%;
}
.customer-message .message-header, .customer-message p {
  align-self: flex-start;
}
.seller-message .message-header, .seller-message p {
  align-self: flex-end;
  margin-right: 20px;
}
.message-header {
  font-weight: bold;
  margin-bottom: 4px;
}
.customer-message p, .seller-message p {
  background-color: #f9f9f9;
  padding: 8px 12px;
  border-radius: 12px;
  max-width: 80%;
}
.seller-message p {
  background-color: #e9f5ff;
}
.send-message-form {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
}
textarea {
  width: 80%;
  height: 80px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
button {
  width: 18%;
  background-color: #007BFF;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}
button:hover {
  background-color: #0056b3;
}
</style>
