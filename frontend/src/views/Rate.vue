<template>
  <div class="rate-page">
    <h1>Rate Product</h1>
    <div class="rating-container">
      <span v-for="star in 5" :key="star" class="star" @click="setRating(star)">
        <i v-if="star <= rating" class="fas fa-star"></i>
        <i v-else class="far fa-star"></i>
      </span>
    </div>
    <textarea v-model="comment" placeholder="Leave your comment here..."></textarea>
    <div class="options">
      <input type="checkbox" id="anonymous" v-model="isAnonymous">
      <label for="anonymous">Submit as Anonymous</label>
    </div>
    <button @click="submitRating">Submit</button>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      rating: 0,
      comment: '',
      isAnonymous: false,
      orderItemId: null,  
      productId: null     
    };
  },
  mounted() {
    this.initializeData();
    console.log('User Token:', localStorage.getItem('userToken'));
  },
  methods: {
    initializeData() {
      this.orderItemId = this.$route.params.orderItemId || this.$route.query.orderItemId;
      this.productId = this.$route.query.productId;
    },
    setRating(star) {
      this.rating = star;
    },
    async submitRating() {
      const userId = localStorage.getItem('userToken');
      try {
        const response = await axios.post(`http://localhost:8081/api/comments`, {
          orderItemId: this.orderItemId,
          productId: this.productId,
          rating: this.rating,
          content: this.comment,
          senderId: userId,
          isAnonymous: this.isAnonymous ? '1' : '0', 
        });
   
        console.log("Submitted isAnonymous value:", this.isAnonymous ? '1' : '0');
        alert('Thank you for your feedback!');
        this.$router.push('/Home');
      } catch (error) {
        console.error('Failed to submit rating:', error);
        alert('Failed to submit your rating. Please try again later.');
      }
    }
  }
}
</script>

<style scoped>
.rate-page {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  text-align: center;
}

.rating-container {
  margin: 20px 0;
}

.star {
  cursor: pointer;
  color: gold;
  font-size: 24px;
}

textarea {
  width: 100%;
  margin-top: 10px;
  padding: 10px;
  height: 100px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
}

.options {
  margin: 10px 0;
}

button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
