<template>  
    <div class="product-detail-container">  
      <div class="product-wrapper">  
        <div class="product-image-wrapper">  
          <img :src="product.image" alt="Product Image" class="product-image">  
        </div>  
        <div class="product-info-wrapper">  
          <h1 class="product-name">{{ product.name }}</h1>  
          <p class="price">Price: ${{ product.price }}</p>  
          <p class="description">{{ product.description }}</p>  
          <p class="test-text">test data here</p>  
          <button @click="addToCart">add to cart</button>  
          <button @click="buyNow">buy now</button>  
        </div>  
      </div>  
    

      <div class="reviews-section">  
        <h2>comment</h2>  
        <div class="review" v-for="review in formattedReviews" :key="review.id">  
          <p class="review-author">{{ review.author }}</p>  
          <p class="review-content">{{ review.content }}</p>  
          <p class="review-date">{{ review.formattedDate }}</p>  
        </div>  
      </div>  
    </div>  
  </template>
    
  <script>  
  import { computed } from 'vue';  
    
  export default {  
    name: 'ProductDetail',  
    props: {  
      product: {  
        type: Object,  
        required: true,  
        default: () => ({  
          id: 0,  
          name: '',  
          price: 0,  
          description: '',  
          image: '',  
          reviews: [  
            { id: 1, author: 'userA', content: 'data', date: '2023-04-01' },  
            { id: 2, author: 'userB', content: 'test', date: '2023-04-02' },  
 
          ]  
        })  
      }  
    },  
    setup(props) {  
      const addToCart = () => {  
        
      };  
    
      const buyNow = () => {  
      
      };  
    
      const formattedReviews = computed(() => {  
        return props.product.reviews.map(review => ({  
          ...review,  
          formattedDate: new Date(review.date).toISOString().split('T')[0]  
        }));  
      });  
    
      return {  
        addToCart,  
        buyNow,  
        formattedReviews  
      };  
    }  
  };  
  </script>  
    
  <style scoped>  
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
  height: auto;  
  max-height: 400px;  
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
    
  .description,  
  .test-text {  
    line-height: 1.6;  
    margin-bottom: 20px;  
  }  
    
  button {  
    padding: 10px 20px;  
    font-size: 1em;  
    cursor: pointer;  
  }  
    
  button:not(:last-child) {  
    margin-right: 10px;  
  }  
    
  .reviews-section {  
    margin-top: 40px;  
  }  
    
  .review {  
    border-bottom: 1px solid #ccc;  
    padding-bottom: 10px;  
    margin-bottom: 10px;  
  }  
    
  .review-author {  
    font-weight: bold;  
  }  
    
  .review-content {  
 
  }  
    
  .review-date {  
    color: #666;  
    font-size: 0.9em;  
  }  
  </style>