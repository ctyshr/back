<template>
    <div id="app">
      <nav class="navbar">
        <div class="navbar-links">
          <router-link to="/home" class="nav-link">
            <img src="@/assets/images/logo1_bb.png" alt="Logo" style="width: 100px; height: 100px;" />
          </router-link>
          <router-link to="/profile" class="nav-link">Profile</router-link>
          <router-link to="/ShoppingCart" class="nav-link">shopping cart</router-link>
          <router-link to="/manage-appointments" class="nav-link">About</router-link>
          <a href="/login" class="nav-link" @click="logout">Logout</a>
          <router-link to="/home" class="nav-link">
            <img src="@/assets/images/logo1_bb.png" alt="Logo" style="width: 100px; height: 100px;" />
          </router-link>
        </div>
      </nav>

    </div>
  </template>
  
  
  
  <script>
  export default {
    name: 'Header'
    // methods: {
    //   logout() {
    
    //     localStorage.removeItem('userToken');
  
    //     this.$router.push('/');
    //   }
    // }
  }
  </script>
  
  <style>
  #app {
    font-family: 'Montserrat', sans-serif;
    font-weight: 700;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #0E8387;
  }
  
  .navbar {
    display: flex;
    justify-content: center; 
    align-items: center; 
    height: 100px; 
    background-color: #3BB4B8;
  }
  
  .navbar-links {
    display: flex;
    justify-content: space-around; 
    align-items: center;
    max-width: 1200px; 
    width: 100%; 
    margin: 0 auto; 
  }
  
  .nav-link {
    color: #FFFFFF !important; 
    text-decoration: none;
    padding: 0 25px;
  }
  
  .nav-link:hover {
    text-decoration: underline;
  }
  

  @media (max-width: 1400px) {
    .navbar-links {
      padding: 0 10px;
    }
  }
  </style>
  
  