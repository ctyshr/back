<template>
  <div id="app">
<div v-if="$route.meta.keepAlive">
  <header-nav></header-nav>
  <router-view/>
</div>
<div v-if="!$route.meta.keepAlive">

  <router-view/>
</div>

  </div>
</template>



<script>
import header from '@/views/Header'
export default {
  name: 'App',

  components:{
    headerNav:header,
  },
  methods: {
    logout() {
  
      localStorage.removeItem('userToken');

      this.$router.push('/');
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
  justify-content: center; /* 保证内容居中 */
  align-items: center; /* 垂直居中 */
  height: 100px; /* 或者设置为您需要的高度 */
  background-color: #3BB4B8; /* 您的导航栏背景色 */
}

.navbar-links {
  display: flex;
  justify-content: space-around; /* 为链接提供均匀空间 */
  align-items: center;
  max-width: 1200px; /* 设置最大宽度 */
  width: 100%; /* 确保它可以扩展到最大宽度 */
  margin: 0 auto; /* 居中 */
}

.nav-link {
  color: #FFFFFF !important; /* 使用 !important 来确保样式的优先级 */
  text-decoration: none;
  padding: 0 25px;
}

.nav-link:hover {
  text-decoration: underline;
}

/* 如果屏幕过窄，减少两边的空间 */
@media (max-width: 1400px) {
  .navbar-links {
    padding: 0 10px;
  }
}
</style>

