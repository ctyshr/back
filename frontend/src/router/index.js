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
import About from '../views/About.vue'
import SellerHome from '../views/SellerHome.vue'
import Edit from '../views/Edit.vue'
import AddItem from '../views/AddItem.vue'
import SellerOrder from '../views/SellerOrder.vue'
import SellerLogin from '../views/SellerLogin.vue'
import UserMessage from '../views/UserMessage.vue'
import SellerRegister from '../views/SellerRegister.vue'
import SellerProfile from '../views/SellerProfile.vue'
import SellerOrderDetail from '../views/SellerOrderDetail.vue'
import SellerMessage from '../views/SellerMessage.vue'
import Rate from '../views/Rate.vue'
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
      path: '/UserMessage/:id',
      name: 'UserMessage',
      component: UserMessage,
      meta: { requiresAuth: true 
        ,keepAlive:true
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
      path: '/Rate/:id',
      name: 'Rate',
      component: Rate,
      meta: { requiresAuth: true 
        ,keepAlive:true
      } 
    },
    {
      path: '/sellerHome',
      name: 'SellerHome',
      component: SellerHome,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/SellerMessage/:customerId',
      name: 'SellerMessage',
      component: SellerMessage,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/SellerOrderDetail/:id',
      name: 'SellerOrderDetail',
      component: SellerOrderDetail,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/SellerProfile',
      name: 'SellerProfile',
      component: SellerProfile,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/SellerRegister',
      name: 'SellerRegister',
      component: SellerRegister,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/sellerLogin',
      name: 'SellerLogin',
      component: SellerLogin,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/SellerOrder',
      name: 'SellerOrder',
      component: SellerOrder,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/edit/:id',
      name: 'Edit',
      component: Edit,
      meta: { 
        keepAlive:false
      } 
    },
    {
      path: '/AddItem',
      name: 'AddItem',
      component: AddItem,
      meta: { 
        keepAlive:false
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
      path: '/About',
      name: 'About',
      component: About,
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
      path: '/OrderDetial/:id',
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
