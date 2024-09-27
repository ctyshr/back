import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/login.vue' 
import Home from '../views/Home.vue' 
import Register from '../views/Register.vue'
import Profile from '../views/Profile.vue'
import ShoppingCart from '../views/ShoppingCart.vue'

import Order from '../views/Order.vue'
import PayOrder from '../views/PayOrder.vue'
import Product from '../views/Product.vue'
import OrderDetial from '../views/OrderDetial.vue'
import PaymentState from '../views/PaymentState.vue'
// import ShoppingCart from '../views/Order.vue'
// import ShoppingCart from '../views/Order.vue'

Vue.use(Router)

function isLoggedIn() {

  return !!localStorage.getItem('userToken');
}

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login ,
      meta: { keepAlive:false
      } 
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      meta: { keepAlive:false
      } 
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/ShoppingCart',
      name: 'ShoppingCart',
      component: ShoppingCart,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/Order',
      name: 'Order',
      component: Order,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/PayOrder',
      name: 'PayOrder',
      component: PayOrder,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/Product/:id',
      name: 'Product',
      component: Product,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/OrderDetial',
      name: 'OrderDetial',
      component: OrderDetial,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/PaymentState',
      name: 'PaymentState',
      component: PaymentState,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
   
    
  ]
})


router.beforeEach((to, from, next) => {
  // if (to.matched.some(record => record.meta.requiresAuth)) {
  //   if (!isLoggedIn()) {
  //     next('/login');
  //   } else {
  //     next();
  //   }
  // } else {
  //   next();
  // }
  next();
});

export default router;
