<template>
  <div class="user-profile-container">
    <div class="profile-header">
      <img :src="previewImage || user.avatar" alt="User Avatar" class="avatar">
      <input type="file" ref="fileInput" @change="onFileChange" accept="image/*" class="file-input">
      <button @click="triggerFileInput" class="change-avatar-button">Change Avatar</button>
    </div>
    <div class="profile-info">
      <div class="info-item">
        <label for="username">Username:</label>
        <input type="text" v-model="user.username" id="username">
      </div>
      <div class="info-item">
        <label for="email">Email:</label>
        <input type="email" v-model="user.email" id="email" disabled>
      </div>
    </div>
    <button @click="saveChanges" class="save-button">Save Changes</button>
    
    <!-- Show submission status -->
    <p v-if="submissionStatus === 'success'" class="success-message">Profile updated successfully!</p>
    <p v-if="submissionStatus === 'error'" class="error-message">Failed to update profile. Please try again later.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {
        avatar: 'https://via.placeholder.com/150',
        username: '',
        email: ''
      },
      selectedFile: null,
      previewImage: null,
      maxFileSize: 2 * 1024 * 1024, // Maximum file size is 2MB
      submissionStatus: null // Holds the status of the form submission
    };
  },
  methods: {
    // Trigger file input when button is clicked
    triggerFileInput() {
      this.$refs.fileInput.click(); // Triggers the hidden file input element
    },

    // Method to handle file input change
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        // Validate file size
        if (file.size > this.maxFileSize) {
          this.submissionStatus = 'error';
          return;
        }

        // Validate file type (ensure it's an image)
        const fileType = file.type.split('/')[0];
        if (fileType !== 'image') {
          this.submissionStatus = 'error';
          return;
        }

        this.selectedFile = file;
        this.previewImage = URL.createObjectURL(file);

        // After file is selected, immediately call the upload method
        this.uploadAvatar();
      }
    },

    // Method to upload avatar to backend
    async uploadAvatar() {
      const userId = localStorage.getItem('userToken');
      if (!userId) {
        this.submissionStatus = 'error';
        return;
      }

      if (!this.selectedFile) {
        this.submissionStatus = 'error';
        return;
      }

      const formData = new FormData();
      formData.append('avatar', this.selectedFile);

      try {
        const response = await axios.post(`http://localhost:8081/${userId}/avatar`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        this.user.avatar = response.data.avatarUrl;
        this.submissionStatus = 'success'; // Set success message
      } catch (error) {
        console.error('Error uploading avatar:', error);
        this.submissionStatus = 'error'; // Set error message
      }
    },

    // Method to fetch user data from backend using userId
    async fetchUserData() {
      const userId = localStorage.getItem('userToken');
      if (!userId) {
        this.submissionStatus = 'error';
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8081/user/${userId}`);
        const avatarUrl = response.data.avatar 
          ? `http://localhost:8081${response.data.avatar}`  
          : 'https://via.placeholder.com/150';
          
        this.user.avatar = avatarUrl;
        this.user.username = response.data.username || 'Unknown';
        this.user.email = response.data.email;
      } catch (error) {
        console.error('Error fetching user data:', error);
        this.submissionStatus = 'error';
      }
    },

    // Method to save changes to backend
    async saveChanges() {
      const userId = localStorage.getItem('userToken');
      if (!userId) {
        this.submissionStatus = 'error';
        return;
      }

      const updatedData = {
        username: this.user.username
      };

      try {
        const response = await axios.put(`http://localhost:8081/user/${userId}`, updatedData);
        this.submissionStatus = 'success'; // Set success message
      } catch (error) {
        console.error('Error updating user data:', error);
        this.submissionStatus = 'error'; // Set error message
      }
    }
  },
  mounted() {
    this.fetchUserData();
  }
};
</script>

<style scoped>
.user-profile-container {
  margin-left: 250px;
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

.file-input {
  display: none; /* Hide the file input */
}

.change-avatar-button, .save-button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.change-avatar-button:hover, .save-button:hover {
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

.save-button {
  margin-top: 20px;
}

.success-message {
  color: green;
  margin-top: 10px;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
