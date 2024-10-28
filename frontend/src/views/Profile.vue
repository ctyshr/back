    <template><div>
    <div class="sidebar">
      <ul>
        <li v-for="(item, index) in menuItems" :key="index">
         <button @click="changePage(item.text)">{{ item.text }}</button> 
        </li>
      </ul>
    </div>
    <div v-if="page==='Personal'">
      <Personal></Personal>
    </div>
    <div v-if="page==='Address'">
      <Address></Address>
    </div>
    <div v-if="page==='Pay'">
      <Pay></Pay>
    </div>
    <div v-if="page==='Order'">
      <Order></Order>
    </div>
    </div>

  </template>
   
  <script>
  import Personal from '../views/Personal.vue'
  import Address from '../views/Address.vue'
  import Pay from '../views/Pay.vue'
  import Order from '../views/Order.vue'
  export default {
    name: 'left',
    components: {
    Personal,
    Address,
    Pay,
    Order,   
  },
    data() {
      return {
        page:'Personal',
        menuItems: [
          { text: 'Personal'},
          { text: 'Address' },
          { text: 'Pay'},
          { text: 'Order'}
        ]
      };
    },
    methods: {
      changePage(text){
        this.page=text;

      }

    }
  };
  </script>
   
  <style scoped>
  .sidebar {
    position:fixed ;
    background-color: #f2f2f2;
    padding: 20px;
    width: 200px;
    height: 1000px;
  }
   
  .sidebar ul {
    list-style-type: none;
    padding: 0;
  }
   
  .sidebar li a {
    color: #333;
    text-decoration: none;
    display: block;
    padding: 10px;
  }
   
  .sidebar li a:hover {
    background-color: #555;
    color: white;
  }
  </style>