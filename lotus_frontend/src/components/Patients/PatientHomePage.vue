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
            if (response.status != 200)
                return false;
            else
                return response.json();
        })
        .then(apps => {
            if (apps == false ) {
                console.log("TODO: Log me out!");
            } else {
                this.appointments = apps;
                this.appointments.forEach(app => {
                    app.date = app.startDate.split("T")[0]
                    app.time = this.dateFormat(new Date(app.startDate), new Date(app.endDate)); 
                })
            }
        })
    },
    methods: {
        dateFormat: function(date1, date2) {
            var time1 = date1.toTimeString().split(" ")[0];
            var time2 = date2.toTimeString().split(" ")[0];
            return time1 + "-" + time2;
        }
    }
}
</script>



<style scoped>

</style>