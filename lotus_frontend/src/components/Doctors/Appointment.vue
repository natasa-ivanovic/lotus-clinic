<template>
    <div>
    <h1>Appointment</h1>
    <h2>{{this.appointment}}</h2>
    <MedicalRecord :id="this.appointment.patientId"/>
    </div>
</template>

<script>
import MedicalRecord from "../Patients/MedicalRecord"

export default {
    name: "startAppointment",
    props: {
        appointment: {
            type: Object,
            default: () => {}
        }
    },
    components: {
      MedicalRecord
    },
    data() {
        return {

        }
    },
    mounted() {
        if (this.appointment == {})
            this.$router.push({name: "home"});
    }
    
}
</script>

<style scoped>

</style>