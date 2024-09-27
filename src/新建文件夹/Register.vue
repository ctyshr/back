<template>
  <div class="auth-container">
    <div class="auth-card">
      <div class="profile-image-holder">
        <img src="profile-image-url.jpg" alt="Profile" />
      </div>
      <h2>Create Your Account</h2>
      <form @submit.prevent="register">
        <input v-model="username" type="text" placeholder="Your Username..." required />
        <input v-model="email" type="email" placeholder="Your Email..." required />
        <input v-model="password" type="password" placeholder="Create Password..." required />
        <input v-model="passwordConfirm" type="password" placeholder="Confirm Password..." required />
   
        <button type="submit">Create Account</button>
      </form>
      <p>
        Already have an account? <router-link to="/login">Log In</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      passwordConfirm: ''
    };
  },
  methods: {
    register() {

      if (this.password !== this.passwordConfirm) {
        alert('Passwords do not match!');
        return;
      }

      axios.get('http://localhost:8081/register', {
        params: {
          username: this.username,
          email: this.email,
          password: this.password
        }
      })
      .then(response => {
        alert(`Account created successfully: ${response.data}`);
        this.$router.push('/login'); 
      })
      .catch(error => {
        console.error('Error registering account:', error);
        alert('Registration failed. Please try again.');
      });
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
</style>
