<template>
  <div>
    <ViewTable userType="doctors" />
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
    }
}
    
</script>