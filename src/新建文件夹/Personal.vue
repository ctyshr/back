<template>
  <div class="user-profile-container">
    <div class="profile-header">
      <img :src="user.avatar" alt="User Avatar" class="avatar">
      <button @click="changeAvatar" class="change-avatar-button">Change Avatar</button>
    </div>
    <div class="profile-info">
      <div class="info-item">
        <label for="nickname">Nickname:</label>
        <input type="text" v-model="user.nickname" id="nickname">
      </div>
      <div class="info-item">
        <label for="email">Email:</label>
        <input type="email" v-model="user.email" id="email">
      </div>
      <div class="info-item">
        <label for="phone">Phone:</label>
        <input type="tel" v-model="user.phone" id="phone">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        avatar: 'https://via.placeholder.com/150', // 默认头像
        nickname: 'John Doe',
        email: 'john.doe@example.com',
        phone: '+123456789'
      }
    };
  },
  methods: {
    changeAvatar() {
      // 这里可以添加逻辑以更改头像，比如弹出一个文件选择框
      const newAvatar = prompt('Enter new avatar URL:');
      if (newAvatar) {
        this.user.avatar = newAvatar;
      }
    }
  }
}
</script>

<style scoped>
.user-profile-container {
  margin-left: 250px; /* 离左边的边框250像素 */
  padding: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 20px;
}

.change-avatar-button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.change-avatar-button:hover {
  background-color: #0056b3;
}

.profile-info .info-item {
  margin: 10px 0;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  padding: 8px;
  width: calc(100% - 16px);
  box-sizing: border-box;
}
</style>
