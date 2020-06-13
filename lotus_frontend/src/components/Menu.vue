<template>
    <div>
        <v-toolbar color="indigo" dark>
            <v-toolbar-title color="white">Lotus Clinic</v-toolbar-title>
            <v-spacer />
            <template v-slot:extension>
                <v-toolbar-items v-if="checkRole('PATIENT')">
                    <v-btn text :to="{ name: 'home'}">Home</v-btn>
                    <v-btn text :to="{ name: 'appointmentView'}">New appointment</v-btn>
                    <v-btn text :to="{ name: 'pastAppointmentsView'}">Previous appointments</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('DOCTOR')">
                    <v-btn text :to="{ name: 'home'}">Home</v-btn>
                    <v-btn text :to="{ name: 'patientSearch', params: {editable: true}}">Patients</v-btn>
                    <v-btn text :to="{ name: 'calendar'}">Calendar</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('NURSE')">
                    <v-btn text :to="{ name: 'home'}">Home</v-btn>
                    <v-btn text :to="{ name: 'patientSearch', params: { editable: false }}">Patients</v-btn>
                    <v-btn text :to="{ name: 'calendar'}">Calendar</v-btn>
                    <v-btn text :to="{ name: 'vacationRequest'}">Vacation</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('ADMIN')">
                    <v-btn text :to="{ name: 'home'}">Home</v-btn>
                    <v-btn text :to="{ name: 'doctors'}">Doctors</v-btn>
                    <v-btn text :to="{ name: 'rooms'}">Rooms</v-btn>
                    <v-btn text :to="{ name: 'appointments'}">Add appointment</v-btn>
                    <v-btn text :to="{ name: 'appointmentPrices'}">Appointment types</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole('CENTRE_ADMIN')">
                    <v-btn text :to="{ name: 'home'}">Home</v-btn>
                    <v-btn text :to="{ name: 'clinics'}">Clinics</v-btn>
                    <v-btn text :to="{ name: 'diagnoses'}">Diagnoses</v-btn>
                    <v-btn text :to="{ name: 'medicines'}">Medicines</v-btn>
                    <v-btn text :to="{ name: 'registrationRequestView'}">Registration Requests</v-btn>
                    <v-btn text :to="{ name: 'addUser', params: { userType: 'admins', header: 'Add clinic admin'}}">Add clinic admin</v-btn>
                    <v-btn text :to="{ name: 'appointmentTypes'}">Appointment types</v-btn>
                </v-toolbar-items>
                <v-spacer />
                <v-toolbar-items v-if="!checkRole(null)">
                    <v-btn text :to="{ name: 'my_clinic'}" v-if="checkRole('ADMIN')">My clinic</v-btn>
                    <v-btn text :to="{ name: 'profile'}">Profile</v-btn>
                    <v-btn text v-on:click="logout()">Logout</v-btn>
                </v-toolbar-items>
                <v-toolbar-items v-if="checkRole(null)">
                    <v-btn text :to="{ name: 'addUser', params: { userType: 'patients', header: 'Register'}}">Register</v-btn>
                    <v-btn text :to="{ name: 'login'}">Login</v-btn>
                </v-toolbar-items>
                
            </template>
        </v-toolbar>
    </div>
</template>

<script>
export default {
  methods: {
    checkRole: function(role) {
      if (this.$role == role)
        return true;
    },
    logout: function() {
        this.$role = null;
        localStorage.removeItem('authKey');
        this.axios.defaults.headers['Authorization'] = "";
        localStorage.removeItem('role');
        this.$router.push({ name: "login" })
    }
  }
}
</script>