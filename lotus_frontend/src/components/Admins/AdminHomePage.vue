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
                                <v-list-item-content v-if="!req.operation">
                                    <v-list-item-title>New appointment request</v-list-item-title>
                                    <v-list-item-content>
                                        Term: {{formatDate(req.startDate, true)}} <br/>
                                        Appointment type: {{req.type}} <br/>                                       
                                        Doctor: {{req.doctors[0].name}}  {{req.doctors[0].surname}} <br/>
                                        Patient: {{req.patient.name}} {{req.patient.surname}} <br/>
                                    </v-list-item-content>
                                    
                                </v-list-item-content>
                                <v-list-item-content v-else>
                                    <v-list-item-title>New operation request</v-list-item-title>
                                    <v-list-item-content>
                                        Term: {{formatDate(req.startDate, false)}} <br/>
                                        Operation type: {{req.type}} <br/> 
                                        Doctors: {{req.doctors.map(d => d.name + " " + d.surname).join(", ")}} <br/>
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
                                        Start date: {{dateFormatJustDay(req.startDate)}} <br/>
                                        End date: {{dateFormatJustDay(req.endDate)}} <br/>
                                        From: {{req.name}} {{req.sureName}}
                                    </v-list-item-content>
                                    
                                </v-list-item-content>
                                <v-btn @click="processVacationRequest(req,i, true)" color="success">Approve</v-btn>
                                <v-btn @click="processVacationRequest(req,i, false)" color="error">Reject</v-btn>
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
        dateFormatJustDay: function(datestr) {
          var date = new Date(datestr);
          return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
        },
        formatDate(date, includeTime) {
            var d = new Date(date);
            var time = d.toTimeString().split(" ")[0];
            var dateNew = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
            if (includeTime)
                return dateNew + " " + time;
            else
                return dateNew;
        },
        processVacationRequest(req, idx, choice) {
            var targeURL = "";
            if(choice == true) {
                targeURL = vacationURL+"/auth/" + req.id;
            }
            else {
                targeURL = vacationURL+"/decline/" + req.id;
            }
            this.toRemoveIdx = idx
            this.axios({
            url: targeURL,
            method: "POST"
        }).then(response => {
            console.log(response);
            this.vacationRequests.splice(this.toRemoveIdx,1);

        }).catch(error => {
            console.log(error);
            this.$store.commit('showSnackbar', {text: "Error in processing vacation reqeuests!", color: "error", })
        });
            
        }
    }
}
</script>