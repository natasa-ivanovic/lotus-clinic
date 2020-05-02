<template>
    <div>
        <PatientPage v-if="this.$role == 'PATIENT'" />

    </div>
</template>


<script>
import PatientPage from "../components/Patients/PatientHomePage"
export default {
    components: {
        PatientPage
    },
    methods: {
        checkRole: function(role) {
            if (this.$role == role)
                return true;
    }
    },
    created() {
        console.log("User is: " + this.$role)
        if (this.$role == null)
            this.$router.push({ name: "login" })

    }
}
</script>

<style scoped>

</style>