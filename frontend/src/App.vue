<template>
  <div id="app">
    <header-nav v-if="$route.meta.keepAlive"></header-nav>
    <router-view/>
  </div>
</template>

<script>
import header from '@/views/Header'
export default {
  name: 'App',

  components: {
    headerNav: header,
  },
  methods: {
    logout() {
      // 注销逻辑，清除 token
      localStorage.removeItem('userToken');
      this.$router.push('/login'); // 重新跳转到登录页面
    }
  }
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
