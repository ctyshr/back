<template>
  <div class="seller-profile-container">
    <!-- Back to Homepage Button -->
    <div class="top-buttons">
      <button class="back-homepage-button" @click="goToHome">Back to Homepage</button>
    </div>

    <!-- Profile Header with Avatar -->
    <div class="profile-header card">
      <img :src="previewImage || seller.avatar" alt="Store Avatar" class="avatar">
      <input type="file" ref="fileInput" @change="onFileChange" accept="image/*" class="file-input"> 
      <button @click="triggerFileInput" class="change-avatar-button">Change Store Image</button>
    </div>

    <!-- Profile Info in a Card -->
    <div class="profile-info card">
      <h3 class="info-title">Store Information</h3>
      <div class="info-item">
        <label for="storeName">Store Name:</label>
        <input type="text" v-model="seller.storeName" id="storeName">
      </div>
      <div class="info-item">
        <label for="email">Email:</label>
        <input type="email" v-model="seller.email" id="email" disabled>
      </div>
    </div>

    <!-- Save Changes Button -->
    <button @click="saveChanges" class="save-button">Save Changes</button>

    <!-- Submission Status Messages -->
    <p v-if="submissionStatus === 'success'" class="success-message">Profile updated successfully!</p>
    <p v-if="submissionStatus === 'error'" class="error-message">Failed to update profile. Please try again later.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      seller: {
        avatar: 'https://via.placeholder.com/150',
        storeName: '',
        email: ''
      },
      selectedFile: null,
      previewImage: null,
      maxFileSize: 2 * 1024 * 1024, // 2MB file size limit
      submissionStatus: null // Holds the status of the form submission
    };
  },
  methods: {
    // Navigate back to homepage
    goToHome() {
      this.$router.push('/SellerHome'); 
    },

    // Trigger file input to select a new avatar image
    triggerFileInput() {
      this.$refs.fileInput.click();
    },

    // Handle file input change
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        // Validate file size
        if (file.size > this.maxFileSize) {
          this.submissionStatus = 'error';
          return;
        }

        // Validate file type
        const fileType = file.type.split('/')[0];
        if (fileType !== 'image') {
          this.submissionStatus = 'error';
          return;
        }

        this.selectedFile = file;
        this.previewImage = URL.createObjectURL(file); // Preview the selected image
        this.uploadAvatar(); // Upload avatar after selecting it
      }
    },

    // Upload avatar to the backend
    async uploadAvatar() {
      const sellerId = localStorage.getItem('sellerToken');
      if (!sellerId || !this.selectedFile) {
        this.submissionStatus = 'error';
        return;
      }

      const formData = new FormData();
      formData.append('avatar', this.selectedFile);

      try {
        const response = await axios.post(`http://localhost:8081/api/seller/${sellerId}/avatar`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        this.seller.avatar = response.data.avatarUrl;
        this.submissionStatus = 'success';
      } catch (error) {
        console.error('Error uploading avatar:', error);
        this.submissionStatus = 'error';
      }
    },

    // Fetch seller data from the backend using sellerId
    async fetchSellerData() {
      const sellerId = localStorage.getItem('sellerToken');
      if (!sellerId) {
        this.submissionStatus = 'error';
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8081/api/seller/${sellerId}`);
        const avatarUrl = response.data.avatar
          ? `http://localhost:8081${response.data.avatar}`
          : 'https://via.placeholder.com/150';

        this.seller.avatar = avatarUrl;
        this.seller.storeName = response.data.storeName || 'Unknown';
        this.seller.email = response.data.email;
      } catch (error) {
        console.error('Error fetching seller data:', error);
        this.submissionStatus = 'error';
      }
    },

    // Save the changes to the backend
    async saveChanges() {
      const sellerId = localStorage.getItem('sellerToken');
      if (!sellerId) {
        this.submissionStatus = 'error';
        return;
      }

      const updatedData = {
        storeName: this.seller.storeName
      };

      try {
        const response = await axios.put(`http://localhost:8081/api/seller/${sellerId}`, updatedData);
        this.submissionStatus = 'success';
      } catch (error) {
        console.error('Error updating seller data:', error);
        this.submissionStatus = 'error';
      }
    }
  },
  mounted() {
    this.fetchSellerData();
  }
};
</script>

<style scoped>
/* General layout */
.seller-profile-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 30px;
  background-color: #f8f9fa;
  border-radius: 10px;
}

/* Back to homepage button */
.back-homepage-button {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.back-homepage-button:hover {
  background-color: #218838;
}

/* Profile header (avatar section) */
.profile-header {
  display: flex;
  align-items: center;
  padding: 20px;
  background-color: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  margin-bottom: 20px;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  margin-right: 20px;
}

.file-input {
  display: none;
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

/* Profile information (store details section) */
.profile-info {
  background-color: white;
  padding: 20px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.info-title {
  font-size: 1.2rem;
  margin-bottom: 20px;
  color: #343a40;
}

.info-item {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  color: #343a40;
}

input {
  padding: 10px;
  width: 100%;
  border: 1px solid #ced4da;
  border-radius: 5px;
  box-sizing: border-box;
  font-size: 1rem;
}

/* Save button */
.save-button {
  padding: 15px 30px;
  background-color: #17a2b8;
  color: white;
  border: none;
  border-radius: 5px;
  margin-top: 20px;
  cursor: pointer;
  font-size: 1.1rem;
}

.save-button:hover {
  background-color: #138496;
}

/* Success and error messages */
.success-message {
  color: green;
  margin-top: 15px;
  font-weight: bold;
}

.error-message {
  color: red;
  margin-top: 15px;
  font-weight: bold;
}
</style>
