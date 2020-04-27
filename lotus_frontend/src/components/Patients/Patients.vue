<template>
  <div>
    <ViewTable userType="patients" />
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
    }
}
    
</script>


<style scoped>

</style>