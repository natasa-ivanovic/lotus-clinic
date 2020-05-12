<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Make a new appointment</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                Select from one of the options below to schedule an appointment.
                </v-card-text>
                <v-card-actions>
                    <v-row>
                        <v-col>
                            <v-btn block @click="predefined()" color="indigo" dark height=60>Pre-defined appointments</v-btn>
                        </v-col>
                    </v-row>
                </v-card-actions>
                <v-card-actions>
                    <v-row>
                        <v-col>
                            <v-btn block @click="doctors()" color="indigo" height=60 dark>Browse doctors</v-btn>
                        </v-col>
                    </v-row>
                </v-card-actions>
                <v-card-actions>
                    <v-row>
                        <v-col>
                            <v-btn block @click="clinics()" color="indigo"  height=60 dark>Browse clinics</v-btn>
                        </v-col>
                    </v-row>
                </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    methods: {
        predefined: function() {
            this.$router.push({ name: "predefinedApps" })
        },
        doctors: function() {
            this.$router.push({ name: "apppointmentQuery", params: {clinic: false, title: "Search by doctors"}})
        },
        clinics: function() {
            this.$router.push({ name: "apppointmentQuery", params: {clinic: true, title: "Search by clinics"} })
        }
    }
}
</script>

<style scoped>

</style>