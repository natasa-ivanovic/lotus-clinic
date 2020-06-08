<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Predefined appointments</v-toolbar-title>
                </v-toolbar>
                <v-card-text v-if="this.apps.length != 0">
                Select from one of the options below to schedule an appointment.
                </v-card-text>
                <v-alert type="info" v-if="this.apps.length == 0">
                    No premade appointments are currently available!
                    Please request an appointment using one of the options on the previous page.
                </v-alert>
                <v-container v-else fluid>
                    <v-row dense>
                    <v-col
                        v-for="app in apps"
                        :key="app.title"
                        cols=6
                    >
                        <v-card>
                            <v-card-title>{{app.type}} <br/> {{app.time}}  </v-card-title>
                            <v-card-text>
                                Doctor: {{app.doctorName}}
                                {{app.doctorSurname}} <br/>
                                Clinic: {{app.clinic}} <br/>
                                Room: {{app.roomName}} <br/>
                                Price: {{app.price}} <br/>
                                Price with discount({{app.discount}}%): {{app.price * (100-app.discount)/100}}
                            </v-card-text>

                        <v-btn block v-on:click="details(app)" color=success dark >Schedule
                        </v-btn>

                        </v-card>
                    </v-col>
                    </v-row>
                </v-container>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
const apiRequest = "/api/appointments/premade";
const apiFinish = "/api/appointments/schedule";

export default {
    data() {
        return {
            apps: [],
            overlay: false
        }
    },
    mounted() {
        this.axios({url: apiRequest,
            method: 'GET'
        }).then(response => {
            console.log(response);
            this.apps = response.data;
            this.apps.forEach(app => {
                var start = new Date(app.startDate);
                var end = new Date(app.endDate);
                app.time = this.dateFormat(start,end);
            })
        }).catch(error => {
            console.log(error);
        })
    },
    methods: {
        details: function(app) {
            this.axios({url : apiFinish + "/" + app.id, 
                        method: 'POST'
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully scheduled appointment!", color: "success", })
                this.apps.splice(this.apps.indexOf(app), 1);
            }).catch(error => {
                console.log(error.request);          
                this.$store.commit('showSnackbar', {text: "Something went wrong! Please try again.", color: "error", })
            });
        },
        dateFormat: function(date1, date2) {
            var date = date1.toISOString().split("T")[0];
            var time1 = date1.toTimeString().split(" ")[0];
            var time2 = date2.toTimeString().split(" ")[0];
            return date + " " + time1 + "-" + time2;
        }
    }
    
}
</script>