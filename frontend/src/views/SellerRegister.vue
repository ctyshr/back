<template>
  <div class="auth-container">
    <div class="auth-card">
      <div class="profile-image-holder">
        <img src="profile-image-url.jpg" alt="Profile" />
      </div>
      <h2>Create Seller Account</h2>
      <form @submit.prevent="register">
        <input v-model="storeName" type="text" placeholder="Store Name..." required />
        <input v-model="email" type="email" placeholder="Your Email..." required />
        <input v-model="password" type="password" placeholder="Create Password..." required />
        <input v-model="passwordConfirm" type="password" placeholder="Confirm Password..." required />
        <button type="submit">Create Account</button>
      </form>
      <p>
        Already have an account? <router-link to="/seller-login">Log In</router-link>
      </p>
    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <p>{{ modalMessage }}</p>
        <button @click="closeModal">OK</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import DOMPurify from 'dompurify';

export default {
  data() {
    return {
      storeName: '',
      email: '',
      password: '',
      passwordConfirm: '',
      showModal: false,
      modalMessage: ''
    };
  },
  methods: {
    register() {
      if (this.password !== this.passwordConfirm) {
        this.modalMessage = 'Passwords do not match!';
        this.showModal = true;
        return;
      }

      const sanitizedStoreName = DOMPurify.sanitize(this.storeName);
      const sanitizedEmail = DOMPurify.sanitize(this.email);

      const registrationData = {
        storeName: sanitizedStoreName,
        email: sanitizedEmail,
        password: this.password
      };

      axios.post('http://localhost:8081/api/seller/register', registrationData)
        .then(response => {
          if (response.data === 'Seller registered successfully!') {
            this.modalMessage = 'Account created successfully!';
            this.showModal = true;

            this.$router.push('/sellerlogin'); 
          } else {
            this.modalMessage = `Registration failed: ${response.data}`;
            this.showModal = true;
          }
        })
        .catch(error => {
          console.error('Error registering account:', error);
          this.modalMessage = 'Registration failed. Please try again.';
          this.showModal = true;
        });
    },
    closeModal() {
      this.showModal = false;
    }
  }
}
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
}

.auth-card {
  background: #f4f4f4;
  padding: 2rem;
  border-radius: 10px;
  text-align: center;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.profile-image-holder {
  margin-bottom: 1rem;
}

.profile-image-holder img {
  border-radius: 50%;
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.auth-card h2 {
  margin: 1rem 0;
  color: #928686;
}

.auth-card form {
  display: flex;
  flex-direction: column;
}

.auth-card input {
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #575050;
  border-radius: 5px;
}

.auth-card button {
  padding: 0.5rem;
  border: none;
  border-radius: 5px;
  background: #313030;
  color: white;
  cursor: pointer;
}

.auth-card p {
  margin-top: 1rem;
  font-size: 0.9rem;
  color: #757373;
}

.auth-card p router-link {
  color: #757373;
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
  max-width: 400px;
  width: 100%;
  text-align: center;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
