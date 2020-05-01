<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Predefined appointments</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                Select from one of the options below to schedule an appointment.
                </v-card-text>
                <v-container fluid>
                    <v-row dense>
                    <v-col
                        v-for="app in apps"
                        :key="app.title"
                        cols=6
                    >
                        <v-card>
                            <v-card-title>{{app.time}} {{app.type}} </v-card-title>
                            <v-card-text>
                                Doctor {{app.doctorName}}
                                {{app.doctorSurname}}
                                in room
                                {{app.roomName}}
                            </v-card-text>

                        <v-btn block text v-on:click="details(app)" color=black >More details
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
const apiURL = "http://localhost:9001/api";


export default {
    data() {
        return {
            apps: [],
            overlay: false
        }
    },
    mounted() {
        // fetch zahtev za sve appointmente koji su trenutno predefined
        fetch(apiURL + "/appointments/premade", {headers: { 'Authorization': this.$authKey }})
            .then(response => {
                return response.json();
            })
            .then(appointments => {
                this.apps = appointments;
                this.apps.forEach(app => {
                    app.time = app.startDate.split("T")[0].split().reverse().join() + " " + app.startDate.split("T")[1].split(".")[0] + "-" + app.endDate.split("T")[1].split(".")[0] 
                })
            })
    },
    methods: {
        details: function(app) {
            // TODO: napravi ovo da mozda bude overlay sa vise podataka
            // za sada samo zakazuje
            console.log(app);
            fetch(apiURL + "/appointments/schedule/" + app.id, {
                    method: "POST",
                    headers: {'Authorization': this.$authKey }})
                .then(response => {
                    if (response.status == 200) {
                        alert("Uspesno zakazan pregled!");
                        this.apps.splice(this.apps.indexOf(app), 1);
                    } else
                        alert("Nesto ne valja");
                });
        }
    }
    
}
</script>

<style scoped>

</style>