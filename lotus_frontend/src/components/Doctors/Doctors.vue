<template>
  <div>
    <ViewTable userType="doctors" />
    <button v-on:click="addDoctor()">Add doctor</button>
  </div>
</template>


<script>
import ViewTable from "../Users/ViewTable";

export default {
    name: "Doctors",
    components: {
      ViewTable
    },

    methods: {
        addDoctor: function() {
          this.$router.push({ name: "addUser", params: {userType: "doctor"} })
        }
    }
}
    
</script>


<style scoped>

</style>