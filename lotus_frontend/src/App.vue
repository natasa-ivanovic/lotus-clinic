<template>
  <div id="app">
    <Patients />
  </div>
</template>

<script>
import Patients from './components/Patients';

export default {
  name: 'App',
  components: {
    Patients
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
