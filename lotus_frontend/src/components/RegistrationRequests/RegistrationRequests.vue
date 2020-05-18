<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Requested registrations</v-toolbar-title>
                </v-toolbar>
                <v-container fluid>
                    <v-row dense>
                    <v-col
                        v-for="req in requests"
                        :key="req.title"
                        cols=6
                    >
                        <v-card>
                            <v-card-text>
                                <div>name: {{req.user.name}}</div>
                                <div>surname: {{req.user.surname}}</div>
                                <div>email: {{req.user.email}}</div>
                            </v-card-text>
                            <v-card-actions>
                                <v-btn v-on:click="details(req)" color=teal dark >Info</v-btn>
                                <v-btn v-on:click="accept(req)" color=success dark >Accept</v-btn>
                                <v-btn v-on:click="declineShow(req)" color=error dark >Decline</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                    </v-row>
                </v-container>
            </v-card>
          </v-col>
        </v-row>
        <v-overlay
            :absolute="true"
            :value="infoOverlay"
        >
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>{{user.name}} {{user.surname}}</v-toolbar-title>
                </v-toolbar>
                    <v-card-text>
                        <div>name: {{user.name}}</div>
                        <div>surname: {{user.surname}}</div>
                        <div>email: {{user.email}}</div>
                        <div>gender: {{user.gender}}</div>
                        <div>birth date: {{user.birthDate.toString().substring(0,10)}}</div>
                        <div>address: {{user.address}}</div>
                        <div>city: {{user.city}}</div>
                        <div>country: {{user.country}}</div>
                        <div>phone number: {{user.phoneNumber}}</div>
                        <div>insurance id: {{user.ssid}}</div>
                    </v-card-text>
                <v-container fluid>
                    <v-btn v-on:click="detailsHide()" color=error dark >Close</v-btn>
                </v-container>
            </v-card>
        </v-overlay>
        <v-overlay
            :absolute="true"
            :value="declineOverlay"
        >
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Decline message</v-toolbar-title>
                </v-toolbar>

                <v-container fluid>
                    <v-textarea 
                        v-model="declineText"
                        label="decline message">
                    </v-textarea>
                    <v-card-actions>
                                <v-btn v-on:click="declineHide()" color=error dark >Close</v-btn>
                                <v-btn v-on:click="decline()" color=success dark >Confirm</v-btn>
                    </v-card-actions>
                    
                </v-container>
            </v-card>
        </v-overlay>
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
            req: {}
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
                    this.requests[i].overlay = false;
                }
            })
    },
    methods: {
        accept(req) {
            fetch(apiURL + "/registrations/auth/" + req.id, {
                method: "POST",
                headers: { 'Authorization': this.$authKey }})
            this.requests.splice(this.requests.indexOf(req), 1);
        },
        declineShow(req) {
            this.req = req;
            this.declineOverlay = true;
        },
        details(req) {
            this.user=req.user;
            this.infoOverlay = true;
        },
        detailsHide() {
            this.infoOverlay = false;
        },
        declineHide() {
            this.declineOverlay = false;
        },
        decline() {
            fetch(apiURL + "/registrations/decline/" + this.req.id, {
                method: "POST",
                headers: {'Authorization': this.$authKey},
                body: this.declineText
            })
            .then(response => {
                if(response.status != 200)
                    alert(response.status)
            })
            this.requests.splice(this.requests.indexOf(this.req), 1);
            this.declineOverlay = false;
        }
    }
}
</script>
