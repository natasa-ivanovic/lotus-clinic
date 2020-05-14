<template>
    <div>
    <MedicalRecord :id="this.appointment.patientId" :edit="true"/>
    <v-container>
    <v-row align="center" justify="center">
    <v-col>
    <v-card class="elevation-3">
        <v-toolbar flat color="secondary" dark>
            <v-toolbar-title>Appointment type: {{appointment.type}}</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn>Report</v-btn>
        </v-toolbar>
    <v-card-text>
        <v-row>
            <v-col>
                <v-text-field
                    label="Diagnosis"
                    v-model="diagnosis"/>
            </v-col>
            <v-col>
                <v-text-field
                    label="Medicine"
                    v-model="medicine"/>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
            <v-text-field
                label="Information"
                v-model="info"/>
            </v-col>
        </v-row>
    </v-card-text>
    <v-card-actions>

        <v-btn color="success" block>End appointment</v-btn>
    </v-card-actions>
    </v-card>
    </v-col>
    </v-row>
    </v-container>
    </div>
</template>

<script>
import MedicalRecord from "../Medical Record/MedicalRecord"

export default {
    name: "startAppointment",
    props: {
        appointment: {
            type: Object,
            default: () => {}
        }
    },
    components: {
      MedicalRecord,
    },
    data() {
        return {
            diagnosis: "",
            medicine: "",
            info: "",

        }
    },
    mounted() {
        if (this.appointment == {})
            this.$router.push({name: "home"});
    },
    methods: {
        
    }
    
}
</script>

<style scoped>

</style>