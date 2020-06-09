<template>
    <v-container fluid>       
        <RatingDialog v-bind:dialog.sync="rating" v-bind:app.sync="ratedId" v-on:rate-app="onAppointmentRated"/>
        <DiagnosisDialog v-bind:dialog.sync="diagnosis" v-bind:app="viewApp" />
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
                            </v-card-title>
                            <v-data-table
                            :headers="headers"
                            :items="items"
                            :server-items-length="totalItems"
                            :loading="loading"
                            :options.sync="options"
                            :footer-props="{
                                itemsPerPageOptions: [1, 5, 10]
                            }">
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
const apiURL = "/api/appointments/patient/past";
import RatingDialog from "./RatingDialog"
import DiagnosisDialog from "./DiagnosisDialog"
export default {
    components: {
        RatingDialog,
        DiagnosisDialog
    },
    data() {
        return {
            items: [],
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
            diagnosis: false,
            ratedId: 0,
            viewApp: {},
            totalItems: 0,
            loading: true,
            options: {}
        }
    },
    watch: {
        options: {
            handler () {    
                this.getDataFromApi();
            },
            deep: true,
        },
    },
    mounted() {
        // this.getDataFromApi();
    },
    methods: {
        getDataFromApi: function() {
            this.loading = true;
            const { sortBy, sortDesc, page, itemsPerPage } = this.options
            var requestUrl = apiURL + "?pageNo=" + (page - 1) + "&pageSize=" + itemsPerPage;
            if (sortBy.length != 0) {
                if (sortBy[0] == "date") 
                    requestUrl = requestUrl + "&sortBy=startDate&descending=" + sortDesc[0];
                else if (sortBy[0] == "doctor")
                    requestUrl = requestUrl + "&sortBy=doctor.name&descending=" + sortDesc[0];
                else if (sortBy[0] == "type")
                    requestUrl = requestUrl + "&sortBy=appointmentType.name&descending=" + sortDesc[0];
                else if (sortBy[0] == "roomName")
                    requestUrl = requestUrl + "&sortBy=room.name&descending=" + sortDesc[0];
                else if (sortBy[0] == "clinic")
                    requestUrl = requestUrl + "&sortBy=clinic.name&descending=" + sortDesc[0];
                else
                    requestUrl = requestUrl + "&sortBy=" + sortBy[0] + "&descending=" + sortDesc[0];
            }
            console.log(requestUrl);
            this.axios({url : requestUrl, 
                method: 'GET'
            }).then(response => {
                this.loading = false;
                this.items = response.data.content;
                this.totalItems = response.data.totalElements;
                this.items.forEach(item => {
                    item.doctor = item.doctorName + " " + item.doctorSurname;
                    item.date = this.getDateString(item.startDate, item.endDate);
                    if (item.recipes == null)
                        item.recipes = ['No recipes prescribed.'];
                })
            }).catch(() => {
                this.loading = false;
            });  
        },
        showDiagnosis: function(app) {
            this.viewApp = app;
            this.diagnosis = true;
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