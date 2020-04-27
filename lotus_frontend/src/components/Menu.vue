<template>
    <div>
        <v-toolbar color="indigo" dark>
            <v-toolbar-title color="white">Lotus Clinic</v-toolbar-title>
            <v-spacer />
            <template v-slot:extension>
                <v-toolbar-items>
                    <v-btn text :to="{ name: 'patients'}">Patients</v-btn>
                    <v-btn text :to="{ name: 'doctors'}">Doctors</v-btn>
                    <v-btn text :to="{ name: 'rooms'}">Rooms</v-btn>
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