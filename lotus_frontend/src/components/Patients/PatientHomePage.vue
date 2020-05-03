<template>
    <v-row>
        <v-col>
            <v-card>
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Scheduled appointments</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <v-card-title v-if="appointments.length == 0">
                        No appointments scheduled!
                    </v-card-title>
                    <v-list v-else>
                        <v-list-item-group>
                        <v-list-item
                            v-for="(app, i) in appointments"
                            :key="i"
                            >
                            <v-list-item-content>
                                <v-list-item-title>{{app.type}} on {{app.date}}</v-list-item-title>
                                <v-list-item-content>
                                    Doctor: {{app.doctorName}} {{app.doctorSurname}} <br />
                                    Room: {{app.roomName}} <br />
                                    Time: {{app.time}}
                                    </v-list-item-content>
                            </v-list-item-content>
                        </v-list-item>
                        </v-list-item-group>
                    </v-list>
                </v-card-text>
            </v-card>
        </v-col>
        <v-col>
            <v-card>
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Scheduled operations</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <v-card-title v-if="operations.length == 0">
                        No operations scheduled!
                    </v-card-title>
                    <template v-else>
                    </template>
                </v-card-text>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
const apiURL = "http://localhost:9001/api";

export default {
    data() {
        return {
            appointments: [],
            operations: []
        };
    },
    mounted() {
        fetch(apiURL + "/appointments/patient", {headers: { 'Authorization': this.$authKey }})
        .then(response => {
            return response.json();
        })
        .then(apps => {
            this.appointments = apps;
            this.appointments.forEach(app => {
                app.date = app.startDate.split("T")[0]
                app.time = app.startDate.split("T")[1].split(".")[0] + "-" + app.endDate.split("T")[1].split(".")[0] 
            })
        })
    },
    methods: {

    }
}
</script>



<style scoped>

</style>