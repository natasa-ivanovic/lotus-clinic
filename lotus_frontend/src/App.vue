<template>
  <v-app id="app">
    <Menu />
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import Menu from "./components/Menu"

export default {
  name: 'App',
  components: {
    Menu
  },
  data() {
    return {
    }
  },
  methods: {
    logout: function() {
      console.log(this.$role);
      console.log(this.$authKey);
      this.$role = null;
      this.$authKey = null;
      console.log(this.$role);
      console.log(this.$authKey);
      localStorage.removeItem('authKey');
      localStorage.removeItem('role');
      this.$router.push({ name: "login" })
    }
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