<template>
  <div id="app">
    <Menu />
    <v-content>
    <router-view></router-view>
    </v-content>
  </div>
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
  color: #2c3e50;
  margin-top: 60px;
}
</style>