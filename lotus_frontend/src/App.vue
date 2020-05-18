<template>
  <v-app id="app">
    <Menu />
    <v-content>
      <Snackbar />
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import Menu from "./components/Menu"
import Snackbar from "./components/Snackbar"
export default {
  name: 'App',
  components: {
    Menu,
    Snackbar
  },
  data() {
    return {
    }
  },
  methods: {
    /*logout: function() {
      console.log(this.$role);
      console.log(this.$authKey);
      this.$role = null;
      this.$authKey = null;
      console.log(this.$role);
      console.log(this.$authKey);
      localStorage.removeItem('authKey');
      localStorage.removeItem('role');
      this.$router.push({ name: "login" })
    },*/
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>