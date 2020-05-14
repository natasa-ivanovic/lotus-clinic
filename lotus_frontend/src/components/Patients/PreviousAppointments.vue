<template>
    <v-container fluid>
        <RatingDialog v-bind:dialog.sync="rating" v-bind:app.sync="ratedId" v-on:rate-app="onAppointmentRated"/>
        <v-row align="center" justify="center">
          <v-col cols="9">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>My previous appointments</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <template>
                        <v-card>
                            <v-card-title>
                            Appointments
                            <v-spacer></v-spacer>
                            <v-text-field
                                v-model="search"
                                append-icon="mdi-magnify"
                                label="Search"
                                single-line
                                hide-details
                            ></v-text-field>
                            </v-card-title>
                            <v-data-table
                            :headers="headers"
                            :items="items"
                            :search="search">
                                <template v-slot:item.diagnosis="{ item }">
                                    <v-icon medium @click="showDiagnosis(item)">mdi-file-chart</v-icon>
                                </template>
                                <template v-slot:item.rate="{ item }">
                                    <v-icon medium @click="showRating(item)" :disabled="item.rated">mdi-star</v-icon>
                                </template>
                            </v-data-table>
                        </v-card>
                    </template>
                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
const apiURL = "http://localhost:9001/api/appointments/patient/past";
import RatingDialog from "./RatingDialog"
export default {
    components: {
        RatingDialog
    },
    data() {
        return {
            items: [],
            search: "",
            headers: [
                {
                    text: 'Date',
                    sortable: true,
                    value: 'date'
                },
                {
                    text: 'Doctor',
                    sortable: true,
                    value: 'doctor'
                },
                {
                    text: 'Appointment type',
                    sortable: true,
                    value: 'type'
                },
                {
                    text: 'Room',
                    sortable: true,
                    value: 'roomName'
                },
                {
                    text: 'Clinic name',
                    sortable: true,
                    value: 'clinic'
                },
                {
                    text: 'Diagnosis',
                    sortable: false,
                    value: 'diagnosis',
                    align: 'center'
                },

                {
                    text: 'Rate',
                    sortable: false,
                    value: 'rate',
                    align: "center"
                }
            ],
            rating: false,
            ratedId: 0
        }
    },
    mounted() {
        this.axios({url : apiURL, 
            method: 'GET'
          }).then(response => {
            this.items = response.data;
            this.items.forEach(item => {
                item.doctor = item.doctorName + " " + item.doctorSurname;
                item.date = this.getDateString(item.startDate, item.endDate);
            })
          }).catch(error => {
            alert(error.response.data.text);
          });
    },
    methods: {
        showDiagnosis: function(app) {
            alert("Todo x " + app.id);
        },
        showRating: function(app) {
            if (app.rated == false) {
                this.ratedId = app.id;
                this.rating = true;
            }
        },
        getDateString(startDateString, endDateString) {
            var startDate = new Date(startDateString);
            var endDate = new Date(endDateString);
            var result = startDate.toTimeString().split(':')[0] + ":" + startDate.toTimeString().split(':')[1] + "-" 
                        + endDate.toTimeString().split(':')[0] + ":" + endDate.toTimeString().split(':')[1] + " "
                        + startDate.toISOString().split('T')[0];
            return result;
        },
        onAppointmentRated: function(app) {
            this.items.forEach(item => {
                if (item.id == app) {
                    item.rated = true;
                }
            })
        }
    }
}
</script>

<style scoped>

</style>