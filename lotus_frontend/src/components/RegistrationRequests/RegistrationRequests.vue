<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Requested registrations</v-toolbar-title>
                </v-toolbar>
                <v-alert type="info" v-if="this.requests.length == 0">
                    No registration requests are currently pending!
                </v-alert>
                <v-container v-else fluid>
                    <v-row dense>
                    <v-col
                        v-for="req in requests"
                        :key="req.id"
                        cols=6
                    >
                        <v-card>
                            <v-card-text>
                                <div>Name: {{req.user.name}}</div>
                                <div>Surname: {{req.user.surname}}</div>
                                <div>Email: {{req.user.email}}</div>
                            </v-card-text>
                            <v-card-actions>
                                <v-row>
                                    <v-col>
                                        <v-btn v-on:click="details(req)" color=info dark block >Info</v-btn>
                                    </v-col>
                                </v-row>
                            </v-card-actions>
                            <v-card-actions>
                                <v-row>
                                    <v-col>
                                        <v-btn v-on:click="accept(req)" :loading="req.loading" color=success dark block>Accept</v-btn>
                                    </v-col>
                                    <v-col>
                                        <v-btn v-on:click="declineShow(req)" color=error dark block >Decline</v-btn>
                                    </v-col>
                                </v-row>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                    </v-row>
                </v-container>
            </v-card>
          </v-col>
        </v-row>
        <v-dialog
            :absolute="true"
            v-model="infoOverlay"
            max-width="600"
        >
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>{{user.name}} {{user.surname}}</v-toolbar-title>
                </v-toolbar>
                    <v-card-text>
                        <v-row>
                        <v-col>
                            <v-text-field
                            label="Email"
                            v-model="user.email" 
                            readonly />
                        </v-col>
                        </v-row>
                        <v-row>
                        <v-col>
                            <v-text-field
                            label="Name"
                            v-model="user.name" 
                            readonly />
                        </v-col>
                        <v-col>
                            <v-text-field
                            label="Surname"
                            v-model="user.surname" 
                            readonly />                      
                        </v-col>
                        </v-row>
                        <v-row>
                        <v-col>
                            <v-text-field
                            v-model="gender"
                            label="Gender" 
                            readonly />        
                        </v-col>
                        <v-col>
                            <v-text-field
                            v-model="birthDate"
                            label="Birth date"
                            readonly
                                />    
                        </v-col>
                        </v-row>
                        <v-row>
                        <v-col>
                            <v-text-field
                            label="Address"
                            v-model="user.address" 
                            readonly />
                        </v-col>
                        <v-col>     
                            <v-text-field
                            label="City"
                            v-model="user.city" 
                            readonly />           
                        </v-col>
                        <v-col>     
                            <v-text-field
                            label="Country"
                            v-model="user.country" 
                            readonly />         
                        </v-col>
                        </v-row>
                        <v-row>
                        <v-col>
                            <v-text-field
                            label="Phone number"
                            v-model="user.phoneNumber" 
                            readonly />
                        </v-col>
                        <v-col>     
                            <v-text-field
                            label="Insurance id"
                            v-model="user.ssid" 
                            readonly />
                        </v-col>
                        </v-row>
                    </v-card-text>
                <v-container fluid>
                    <v-btn v-on:click="detailsHide()" block color=info dark >Close</v-btn>
                </v-container>
            </v-card>
        </v-dialog>
        <v-dialog
            :absolute="true"
            v-model="declineOverlay"
            max-width="600"
        >
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Decline message</v-toolbar-title>
                </v-toolbar>

                <v-container fluid>
                    <v-textarea 
                        v-model="declineText"
                        outlined
                        label="Decline message">
                    </v-textarea>
                    <v-card-actions>
                        <v-row>
                            <v-col>
                                <v-btn v-on:click="decline()" color=success dark block>Confirm</v-btn>
                            </v-col>
                            <v-col>
                                <v-btn v-on:click="declineHide()" color=error dark block>Close</v-btn>
                            </v-col>
                        </v-row>
                    </v-card-actions>
                    
                </v-container>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
const apiURL = "http://localhost:9001/api/requests";
export default {
    data() {
        return {
            declineText: "",
            declineOverlay: false,
            infoOverlay: false,
            requests: [],
            user: {birthDate: ""},
            req: {},
            gender: "",
            birthDate: "",
        }
    },
    mounted() {
        fetch(apiURL + "/registrations", {headers: { 'Authorization': this.$authKey }})
        .then(response => {
                if (response.status == 200)
                    return response.json();
                else 
                    return false
                })
            .then(requests => {
                if (!requests) {
                    alert("Nesto ne valja");
                    return;
                }
                this.requests = requests
                var i;
                for(i = 0; i < this.requests.length; ++i) {
                    this.$set(this.requests[i], 'loading', false);
                }
            })
    },
    methods: {
        accept(req) {
            if (req.loading) {
                alert("vec se ucitava");
                return;
            }
            fetch(apiURL + "/registrations/auth/" + req.id, {
                method: "POST",
                headers: { 'Authorization': this.$authKey }})
                .then(response => {
                    if (response.status == 200) {
                        this.$store.commit('showSnackbar', {text: "Successfully accepted patient!", color: "success", })
                        this.requests.splice(this.requests.indexOf(req), 1);
                    }
                }) 
            req.loading = true;        
        },
        declineShow(req) {
            this.req = req;
            this.declineOverlay = true;
        },
        details(req) {
            this.user=req.user;
            this.gender = req.user.gender.charAt(0) + req.user.gender.slice(1).toLowerCase();
            this.birthDate = req.user.birthDate.toString().substring(0,10);
            this.infoOverlay = true;
        },
        detailsHide() {
            this.infoOverlay = false;
        },
        declineHide() {
            this.declineOverlay = false;
        },
        decline() {
            if (this.declineText == "") {
                this.$store.commit('showSnackbar', {text: "Please enter a reason for declining the patient!", color: "info", })
                return;
            }
            fetch(apiURL + "/registrations/decline/" + this.req.id, {
                method: "POST",
                headers: {'Authorization': this.$authKey},
                body: this.declineText
            })
            .then(response => {
                if(response.status != 200) {
                    this.$store.commit('showSnackbar', {text: "An error has occurred!", color: "error", })
                }
                else {
                    this.requests.splice(this.requests.indexOf(this.req), 1);
                    this.declineOverlay = false;
                    this.$store.commit('showSnackbar', {text: "Successfully declined patient!", color: "success", })
                }
            })
        },
        checkLoad(req) {
            return req.loading;
        }
    }
}
</script>
