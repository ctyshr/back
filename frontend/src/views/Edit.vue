<template>
  <div class="item-edit-container">
    <!-- Header buttons -->
    <div class="top-buttons">
      <button @click="goBackToHome" class="back-homepage-button">Back to Homepage</button>
      <button class="login-button">Log in/out</button>
    </div>

    <!-- Image Upload Section -->
    <div class="profile-header">
      <img :src="previewImage || fullImageUrl" alt="Product Image" class="avatar">
      <input type="file" ref="fileInput" @change="onFileChange" accept="image/*" class="file-input">
      <button @click="triggerFileInput" class="change-avatar-button">Change Image</button>
      <button @click="uploadImage" class="upload-button">Upload Image</button>
    </div>

    <!-- Item details form -->
    <div class="item-details-form">
      <div class="form-group">
        <label for="item-name">Item Name:</label>
        <input type="text" id="item-name" v-model="item.name" placeholder="Enter item name">
      </div>

      <div class="form-group">
        <label for="item-description">Item Description:</label>
        <textarea id="item-description" v-model="item.description" placeholder="Enter item description"></textarea>
      </div>

      <div class="form-group form-inline">
        <div>
          <label for="item-price">Price:</label>
          <input type="number" id="item-price" v-model="item.price" placeholder="Price in $">
        </div>
      </div>

      <div class="form-group form-inline">
        <div>
          <label for="promotional">Promotional:</label>
          <input type="checkbox" id="promotional" v-model="item.promotional">
        </div>

        <div v-if="item.promotional">
          <label for="discount">Discount:</label>
          <input type="number" id="discount" v-model="item.discount" placeholder="xx% off">
        </div>
      </div>

      <!-- Save button -->
      <button @click="saveItem" class="save-button">Save Item</button>
    </div>

    <!-- Modal -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <p>{{ modalMessage }}</p>
        <button @click="goBackToHome" class="back-home-button">Back to Home</button>
        <button @click="closeModal" class="continue-button">Continue</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      item: {
        name: '',
        description: '',
        price: null,
        promotional: false,
        discount: null,
        img_url: ''
      },
      selectedFile: null,
      previewImage: null,
      showModal: false, // Controls the visibility of the modal
      modalMessage: ''  // Stores the modal message
    };
  },
  computed: {
   
    fullImageUrl() {
      return this.item.img_url ? `http://localhost:8081${this.item.img_url}` : '';
    }
  },
  methods: {
    // Fetch product details based on ID from route
    fetchProductDetails() {
      const productId = this.$route.params.id;
      axios.get(`http://localhost:8081/api/products/${productId}`)
        .then(response => {
          this.item = response.data;
        
        })
        .catch(error => {
          console.error('Error fetching product:', error);
        });
    },

    triggerFileInput() {
      this.$refs.fileInput.click();
    },

    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.selectedFile = file;
        this.previewImage = URL.createObjectURL(file); // Preview the image before upload
      }
    },

    uploadImage() {
      if (!this.selectedFile) {
        this.modalMessage = 'Please select an image to upload.';
        this.showModal = true;
        return;
      }

      const formData = new FormData();
      formData.append('image', this.selectedFile);

      const productId = this.$route.params.id;
      axios.post(`http://localhost:8081/api/products/${productId}/upload-image`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then(response => {
          this.item.img_url = response.data.imageUrl;
          this.modalMessage = 'Image uploaded successfully!';
          this.showModal = true;
        })
        .catch(error => {
          console.error('Error uploading image:', error);
          this.modalMessage = 'Error uploading image.';
          this.showModal = true;
        });
    },

    // Save item details
    saveItem() {
      const productId = this.$route.params.id;
      axios.put(`http://localhost:8081/api/products/${productId}`, this.item)
        .then(() => {
          this.modalMessage = 'Item details updated successfully!';
          this.showModal = true;
        })
        .catch(error => {
          console.error('Error saving item:', error);
          this.modalMessage = 'Error saving item details.';
          this.showModal = true;
        });
    },

    // Close modal
    closeModal() {
      this.showModal = false;
    },

    // Go back to SellerHome
    goBackToHome() {
      this.$router.push('/SellerHome');
    }
  },
  mounted() {
    this.fetchProductDetails();
  }
}
</script>

<style scoped>
/* General layout */
.item-edit-container {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  background-color: #f9f9f9;
}

/* Header buttons */
.top-buttons {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

button {
  padding: 10px 20px;
  border: 1px solid #000;
  cursor: pointer;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

/* Image upload */
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

.change-avatar-button, .upload-button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.change-avatar-button:hover, .upload-button:hover {
  background-color: #0056b3;
}

/* Form layout */
.item-details-form {
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-weight: bold;
  margin-bottom: 5px;
  display: block;
}

input[type="text"], input[type="number"], textarea {
  width: calc(100% - 20px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

textarea {
  resize: none;
  height: 100px;
}

.save-button {
  width: 100%;
  padding: 15px;
  font-size: 16px;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
}

.modal-content button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.modal-content button:hover {
  background-color: #0056b3;
}

.back-home-button {
  background-color: #dc3545;
}

.continue-button {
  background-color: #28a745;
}
</style>
