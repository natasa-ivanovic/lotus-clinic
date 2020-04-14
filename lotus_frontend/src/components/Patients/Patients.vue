<template>
  <div>
    <ViewTable userType="patients" />
    <button v-on:click="addPatient()">Add patient</button>
  </div>
</template>


<script>
import ViewTable from "../Users/ViewTable";

export default {
    name: "Patients",
    components: {
      ViewTable
    },

    methods: {
        addPatient: function() {
          this.$router.push({ name: "addUser", params: {userType: "patient"} })
        }
    }
}
    
</script>


<style scoped>

</style>