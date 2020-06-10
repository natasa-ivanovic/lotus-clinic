<template>
    <div>
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
        <v-row>
            <v-col>
                <v-card>
                    <v-toolbar flat color="secondary" dark>
                        <v-toolbar-title>Vacation requests</v-toolbar-title>
                    </v-toolbar>
                    <v-card-text>
                        <v-card-title v-if="vacationRequests.length == 0">
                            No current vacation requests.
                        </v-card-title>
                        <v-list v-else>
                            <v-list-item-group>
                            <v-list-item
                                v-for="(req, i) in vacationRequests"
                                :key="i"
                                >
                                <v-list-item-content>
                                    <v-list-item-title>New request</v-list-item-title>
                                    <v-list-item-content>
                                        Start date: {{req.startDate.substring(0,10)}} <br/>
                                        End date: {{req.endDate.substring(0,10)}} <br/>
                                        From: {{req.name}} {{req.sureName}}
                                    </v-list-item-content>
                                    
                                </v-list-item-content>
                                <v-btn @click="approveVacationRequest(req,i)" color="success">Approve</v-btn>
                            </v-list-item>
                            </v-list-item-group>
                        </v-list>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </div>
</template>

<script>
const apiURL = "/api/requests/rooms";
const vacationURL = "/api/vacation";
export default {
    data() {
        return {
            requests: [],
            vacationRequests: [],
            toRemoveIdx: -1,
        }
    },
    mounted() {
        this.axios({
            url: apiURL,
            method: "GET"
        }).then(response => {
            this.requests = response.data;
        }).catch(error => {
            console.log(error);
            this.$store.commit('showSnackbar', {text: "Error in fetching room requests!", color: "error", })
        });
        this.axios({
            url: vacationURL,
            method: "GET"
        }).then(response => {
            this.vacationRequests = response.data;
        }).catch(error => {
            console.log(error);
            this.$store.commit('showSnackbar', {text: "Error in fetching vacation reqeuests!", color: "error", })
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
        },
        approveVacationRequest(req, idx) {
            this.toRemoveIdx = idx
            this.axios({
            url: vacationURL + "/auth/" + req.id,
            method: "POST"
        }).then(response => {
            console.log(response);
            var i = this.toRemoveIdx;
            console.log(i);
            this.vacationRequests.splice(this.toRemoveIdx,1);

        }).catch(error => {
            console.log(error);
            this.$store.commit('showSnackbar', {text: "Error in approving vacation reqeuests!", color: "error", })
        });
            
        }
    }
}
</script>