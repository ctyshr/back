<template>
  <div class="auth-container">
    <div class="auth-card">

      <h2>Login to Your Account</h2>
      <form @submit.prevent="login">
        <input v-model="email" type="text" placeholder="Account..." required />
        <input v-model="password" type="password" placeholder="Password..." required />
        <button type="submit">Log In</button>
      </form>
      <p v-if="loginError" style="color: red;">Login info incorrect.</p>
      <p>
        Don't have account? <router-link to="/register">Create One</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      password: '',
      loginError: false,
      forceRerender: false,
    };
  },
    computed: {
    isLoggedIn() {
      return !!localStorage.getItem('userToken') && this.forceRerender;
    },
  },
  methods: {
    checkLogin() {
      this.forceRerender = !this.forceRerender;
    },
    async login() {
   const params = new URLSearchParams({
    email: this.email, 
    password: this.password
   }).toString();
   try {
    const response = await axios.get(`http://localhost:8081/login?${params}`);
  console.log(response.data);
     if (response.data >0) {

      localStorage.setItem('userToken', response.data);
       this.$router.push('/Home'); 
    } else {
       this.loginError = true; 
    }
   } catch (error) {
     console.error('Login error:', error);
     this.loginError = true; 
  }

    }
  },
   created() {
    this.$on('check-login', this.checkLogin);
    localStorage.removeItem('userToken');
  },
  beforeDestroy() {
    this.$off('check-login', this.checkLogin);
  },
  
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
