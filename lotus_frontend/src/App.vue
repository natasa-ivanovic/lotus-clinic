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