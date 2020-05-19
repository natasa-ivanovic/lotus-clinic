<template>
    <v-row>
        <v-col>
            <v-card>
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Room requests</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <v-card-title v-if="requests.length == 0">
                        No current appointment requests.
                    </v-card-title>
                    <v-list v-else>
                        <v-list-item-group>
                        <v-list-item
                            v-for="(req, i) in requests"
                            :key="i"
                            >
                            <v-list-item-content>
                                <v-list-item-title>New request</v-list-item-title>
                                <v-list-item-content>
                                    Term: {{formatDate(req.startDate)}} <br/>
                                    Appointment type: {{req.type}} <br/>                                       
                                    Doctor: {{req.doctor.name}}  {{req.doctor.surname}} <br/>
                                    Patient: {{req.patient.name}} {{req.patient.surname}} <br/>
                                </v-list-item-content>
                                
                            </v-list-item-content>
                            <v-btn @click="approveRequest(req)" color="success">Approve</v-btn>
                        </v-list-item>
                        </v-list-item-group>
                    </v-list>
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
            requests: []
        }
    },
    mounted() {
        /*fetch(apiURL + "/patients/requests", {headers: { 'Authorization': this.$authKey }})
        .then(response => {
            return response.json();
        })
        .then(pats => {
            this.requests = pats;
        })*/
        this.axios({
            url: apiURL + "/requests/rooms",
            method: "GET"
        }).then(response => {
            this.requests = response.data;
        }).catch(error => {
            alert(error);
        });
    },
    methods: {
        approveRequest(req) {
            console.log(req);
            //da se prosledi datum komponenti (lista datuma, od zatrazenog + 7 dana?)
            this.$router.push({name: "freeRooms", params: {request: req}}); 
                    
        },
        formatDate(date) {
            var d = new Date(date);
            var time = d.toTimeString().split(" ")[0];
            var dateNew = d.toISOString().split("T")[0];
            return dateNew + " " + time;
        }
    }
}
</script>

<style scoped>

</style>