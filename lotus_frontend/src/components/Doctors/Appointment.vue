<template>
    <v-container fluid>
        <v-row align="center" justify="center">
            <v-col cols="6">
                <v-expansion-panels>
                    <v-expansion-panel>
                        <v-expansion-panel-header style="background:#424242;color:white" class="spacerHeader">Medical record</v-expansion-panel-header>
                        <v-expansion-panel-content eager >
                            <MedicalRecord :id="this.appointment.patientId.toString()" :edit="true" class="mt-4"/>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-expansion-panels>
            </v-col>
        </v-row>
        <v-row align="center" justify="center">
            <v-col cols="6">
                <v-card class="elevation-3">
                    <v-toolbar flat color="secondary" dark>
                        <v-toolbar-title>Appointment type: {{appointment.type}}</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-btn>Report</v-btn>
                    </v-toolbar>
                    <v-card-text>
                        <v-row>
                            <v-col>
                                <v-autocomplete
                                    label="Diagnosis"
                                    item-text="name"
                                    item-value="id"
                                    chips
                                    deletable-chips
                                    selected
                                    multiple
                                    :items="allDiagnosis"
                                    v-model="diagnosis"/>
                            </v-col>
                            <v-col>
                                <v-autocomplete
                                    label="Medicine"
                                    item-text="name"
                                    item-value="id"
                                    chips
                                    deletable-chips
                                    selected
                                    multiple
                                    :items="allMedicines"
                                    v-model="medicine"/>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                            <v-textarea
                                color="teal"
                                v-model="info"
                                label="Description">
                            </v-textarea>
                            </v-col>
                        </v-row>
                    </v-card-text>
                    <v-card-actions>
                        <v-row>
                            <v-col><v-btn color="primary" block>New appointment</v-btn></v-col>
                            <v-col><v-btn color="primary" block>New operation</v-btn></v-col>
                        </v-row>
                    </v-card-actions>
                    <v-card-actions>
                        <v-btn color="success" block @click="endAppointment()">End appointment</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import MedicalRecord from "../Medical Record/MedicalRecord"
const apiURL = "http://localhost:9001/api"

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
            allMedicines: [],
            allDiagnosis: []

        }
    },
    mounted() {
        if (this.appointment == {})
            this.$router.push({name: "home"});

        this.axios({
            url: apiURL + "/appointments/info",
            method: 'GET'
        }).then(response => {
            this.allMedicines = response.data.medicines;
            this.allDiagnosis = response.data.diagnosis;
        }).catch(error =>{
            console.log(error);
        })
    },
    methods: {
        endAppointment() {

        }
    }
    
}
</script>

<style scoped>

.spacerHeader {
    font-size: 1.25rem;
    line-height: 1.5;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

</style>