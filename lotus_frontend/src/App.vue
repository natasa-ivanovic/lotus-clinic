<template>
  <div id="app">
    <button v-on:click="patients()">Patients</button>
    <button v-on:click="doctors()">Doctors</button>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {

  },
  data() {
    return {
    }
  },
  methods: {
    patients: function() {
      this.$router.push({ name: "patients"})
    },
    doctors: function() {
     this.$router.push({ name: "doctors"})
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
