<template>
    <div>
        <v-toolbar>
            <v-toolbar-title>Lotus Clinic</v-toolbar-title>
            <v-spacer />
            <template v-slot:extension>
                <v-toolbar-items>
                    <v-btn v-on:click="patients()">Patients</v-btn>
                    <v-btn v-on:click="doctors()">Doctors</v-btn>
                    <v-btn v-on:click="rooms()">Rooms</v-btn>
                </v-toolbar-items>
            </template>
        </v-toolbar>
    </div>
</template>

<script>
export default {
  methods: {
    patients: function() {
      this.$router.push({ name: "patients"})
    },
    doctors: function() {
     this.$router.push({ name: "doctors"})
    },
    rooms: function() {
      this.$router.push({ name: "rooms"})
    }
  }
    
}
</script>

<style scoped>

</style>