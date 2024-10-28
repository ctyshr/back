<template>
  <div class="product-detail-container" v-if="product">
    <div class="product-wrapper">
      <div class="product-image-wrapper">
        <img :src="`http://localhost:8081${product.img_url}`" alt="Product Image" class="product-image">
      </div>
      <div class="product-info-wrapper">
        <h1 class="product-name">{{ product.name }}</h1>
        <p class="price">Price: ${{ product.price }}</p>
        <p class="description">{{ product.description }}</p>
        <button @click="addToCart">Add to Cart</button>
        <button @click="buyNow">Buy Now</button>
      </div>
    </div>

    <Modal
      :show="isModalVisible"
      title="Add to Cart"
      :message="modalMessage"
      @close="isModalVisible = false"
    />

    <div class="comments-section">
      <h2>Customer Reviews</h2>
      <div v-if="comments.length">
        <div v-for="comment in comments" :key="comment.id" class="comment">
          <div class="comment-header">
            <img
              v-if="!comment.isAnonymous"
              :src="`http://localhost:8081${comment.avatarUrl}`"
              alt="User Avatar"
              class="user-avatar"
            />
            <div class="comment-info">
              <p class="comment-username">
                {{ comment.isAnonymous ? 'Anonymous' : comment.userName }}
              </p>
              <div class="rating">
                <span v-for="star in 5" :key="star">
                  <i :class="star <= comment.rating ? 'fas fa-star' : 'far fa-star'"></i>
                </span>
              </div>
            </div>
          </div>
          <p class="comment-content">{{ comment.content }}</p>
          <p class="comment-time">{{ comment.sendTime }}</p>
          <hr />
        </div>
      </div>
      <div v-else>
        <p>No reviews yet. Be the first to review!</p>
      </div>
    </div>
  </div>
  <div v-else>
    <p>Loading product details...</p>
  </div>
</template>

<script>
import axios from 'axios';
import Modal from './Modal.vue';

export default {
  components: {
    Modal
  },
  data() {
    return {
      product: null,
      isModalVisible: false,
      modalMessage: '',
      comments: []
    };
  },
  methods: {
    async fetchProduct() {
      const productId = this.$route.params.id;
      try {
        const response = await axios.get(`http://localhost:8081/api/products/${productId}`);
        this.product = response.data;
        await this.fetchComments();
      } catch (error) {
        console.error('Error fetching product:', error);
      }
    },
    async fetchComments() {
      const productId = this.$route.params.id;
      try {
        const response = await axios.get(`http://localhost:8081/api/comments/product/${productId}`);
        const comments = response.data;
        this.comments = await Promise.all(
          comments.map(async (comment) => {
            if (!comment.isAnonymous) {
              const userResponse = await axios.get(`http://localhost:8081/user/${comment.senderId}`);
              comment.userName = userResponse.data.username;
              comment.avatarUrl = userResponse.data.avatar;
            }
            return comment;
          })
        );
      } catch (error) {
        console.error('Error fetching comments:', error);
      }
    },
    async addToCart() {
      const userId = localStorage.getItem('userToken');
      if (!userId) {
        this.modalMessage = 'Please log in to add products to cart.';
        this.isModalVisible = true;
        return;
      }

      try {
        await axios.post(`http://localhost:8081/api/carts/${userId}/add`, null, {
          params: { productId: this.product.id }
        });
        this.modalMessage = `Product '${this.product.name}' added to cart!`;
        this.isModalVisible = true;
      } catch (error) {
        console.error('Error adding product to cart:', error);
        this.modalMessage = 'Failed to add product to cart.';
        this.isModalVisible = true;
      }
    },
    buyNow() {
      this.$router.push(`/checkout/${this.product.id}`);
    }
  },
  mounted() {
    this.fetchProduct();
  }
};
</script>

<style scoped>
.comments-section {
  margin-top: 40px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
}

.comments-section h2 {
  font-size: 1.8em;
  margin-bottom: 20px;
}

.comment {
  margin-bottom: 20px;
  padding: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.user-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 15px;
}

.comment-info {
  display: flex;
  flex-direction: column;
}

.comment-username {
  font-weight: bold;
}

.rating {
  display: flex;
  align-items: center;
}

.rating i {
  color: gold;
  margin-right: 2px;
}

.comment-content {
  margin: 10px 0;
}

.comment-time {
  font-size: 0.85em;
  color: #888;
}

.product-detail-container {
  margin: 40px auto;
  width: 80%;
  max-width: 1200px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.product-wrapper {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
}

.product-image-wrapper {
  flex: 1;
  text-align: center;
}

.product-image {
  width: 100%;
  max-width: 400px;
  height: auto;
  object-fit: cover;
}

.product-info-wrapper {
  flex: 2;
  padding-left: 40px;
}

.product-name {
  font-size: 2em;
  margin-bottom: 20px;
}

.price {
  color: #ff5722;
  font-size: 1.5em;
  font-weight: bold;
  margin-bottom: 20px;
}

.description {
  line-height: 1.6;
  margin-bottom: 20px;
}

button {
  padding: 10px 20px;
  font-size: 1em;
  cursor: pointer;
  background-color: #007BFF;
  color: white;
  border-radius: 5px;
  border: none;
}

button:not(:last-child) {
  margin-right: 10px;
}

button:hover {
  background-color: #0056b3;
}
</style>
