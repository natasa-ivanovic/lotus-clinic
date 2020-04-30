<template>
    <div>
        <v-toolbar color="indigo" dark>
            <v-toolbar-title color="white">Lotus Clinic</v-toolbar-title>
            <v-spacer />
            <template v-slot:extension>
                <v-toolbar-items v-if="checkRole('PATIENT')">
                    <v-btn text :to="{ name: 'doctors'}">Doctors</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('DOCTOR')">
                    <v-btn text :to="{ name: 'patients'}">Patients</v-btn>
                    <v-btn text :to="{ name: 'medicines'}">Medicines</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('NURSE')">
                    <v-btn text :to="{ name: 'patients'}">Patients</v-btn>
                    <v-btn text :to="{ name: 'medicines'}">Medicines</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('ADMIN')">
                    <v-btn text :to="{ name: 'patients'}">Patients</v-btn>
                    <v-btn text :to="{ name: 'doctors'}">Doctors</v-btn>
                    <v-btn text :to="{ name: 'rooms'}">Rooms</v-btn>
                    <v-btn text :to="{ name: 'appointmentTypes'}">Appointment types</v-btn>
                    <v-btn text :to="{ name: 'clinics'}">Clinics</v-btn>
                    <v-btn text :to="{ name: 'medicines'}">Medicines</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('CENTRE_ADMIN')">
                    <v-btn text :to="{ name: 'clinics'}">Clinics</v-btn>
                </v-toolbar-items>
                <v-spacer />
                <v-toolbar-items>
                    <v-btn v-if="!checkRole('')" text v-on:click="logout()">Logout</v-btn>
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
    },
    appointmentTypes: function() {
      this.$router.push({ name: "appointmentTypes"})
    },
    clnics: function() {
      this.$router.push({ name: "clinics"})
    },
    medicines: function() {
      this.$router.push({ name: "medicines"})
    },
    checkRole: function(role) {
      if (this.$role == role)
        return true;
    },
    logout: function() {
      this.$role = "";
      this.$authKey = "";
      this.$router.push({ name: "mainPage" })
    }
  }
    
}
</script>

<style scoped>

</style>