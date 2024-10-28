<template>
  <div class="message-container">
    <div class="header">
      <button @click="goBack" class="back-button">&#x2190;</button>
      <h2>Message with {{ sellerName || 'Seller' }}</h2>
    </div>
    <div class="messages-view">
      <div v-if="messages.length > 0">
        <div v-for="message in messages" :key="message.id"
             class="message-item"
             :class="{'user-message': message.senderType === 'USER', 'seller-message': message.senderType === 'SELLER'}">
          <div class="message-header">
            {{ message.senderType === 'SELLER' ? sellerName : 'You' }}
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
      sellerId: this.$route.params.id,
      userId: localStorage.getItem('userToken'),
      sellerName: '',
      intervalId: null
    };
  },
  methods: {
    fetchMessages() {
      axios.get(`http://localhost:8081/api/messages/${this.sellerId}/${this.userId}`)
        .then((response) => {
          this.messages = response.data;
        })
        .catch((error) => {
          console.error('Error fetching messages:', error);
        });
    },
    sendMessage() {
      if (!this.newMessage.trim()) return;
      const messageRequest = {
        sellerId: this.sellerId,
        userId: this.userId,
        message: this.newMessage,
        senderType: 'USER'
      };
      axios.post('http://localhost:8081/api/messages', messageRequest)
        .then(() => {
          this.newMessage = '';  // Clear input after send
          this.fetchMessages();  // Refresh messages
        })
        .catch((error) => {
          console.error('Error sending message:', error);
        });
    },
    fetchSellerInfo() {
      axios.get(`http://localhost:8081/api/seller/${this.sellerId}`)
        .then((response) => {
          this.sellerName = response.data.storeName;
        })
        .catch((error) => {
          console.error('Error fetching seller info:', error);
        });
    },
    goBack() {
      this.$router.go(-1); // Go back to the previous page
    },
    startPolling() {
      this.intervalId = setInterval(this.fetchMessages, 3000); // Poll every 3 seconds
    }
  },
  mounted() {
    this.fetchMessages();
    this.fetchSellerInfo();
    this.startPolling();
  },
  beforeDestroy() {
    clearInterval(this.intervalId);  // Clear the polling interval
  }
};
</script>

<style scoped>
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
  width: 100%;  /* Ensure full width for alignment */
}
.user-message .message-header, .user-message p {
  align-self: flex-end;
  margin-right: 20px;  /* Add right margin to not stick to the edge */
}
.seller-message .message-header, .seller-message p {
  align-self: flex-start;
}
.message-header {
  font-weight: bold;
  margin-bottom: 4px;
}
.user-message p, .seller-message p {
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
