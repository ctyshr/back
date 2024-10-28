<template>
  <div class="about-container">
    <h1>About Us</h1>
    <p>Welcome to my website! this is my work for Software Capstone COMP-10202</p>

    <section class="team-section">

      <div class="team-member" v-for="member in team" :key="member.id">
   
        <div class="team-member-info">
          <h3>{{ member.name }}</h3>
          <p>{{ member.school }}</p>
  
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      team: [
        {
          id: 1,
          name: 'Tianyang Chen',
          school: 'Mohawk College',

        }
      ]
    };
  }
}
</script>

<style scoped>
.about-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.team-section {
  margin-top: 30px;
}

.team-member {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.team-member-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 20px;
}

.team-member-info {
  flex: 1;
}

.team-member-info h3 {
  margin: 0 0 5px;
}

.team-member-info p {
  margin: 0;
}
</style>
