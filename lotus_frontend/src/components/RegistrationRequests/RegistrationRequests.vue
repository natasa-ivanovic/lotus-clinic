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
                            <v-card-title> <br/> {{req.user.email}}  </v-card-title>
                            <v-btn block v-on:click="accept(req)" color=success dark >Accept</v-btn>
                            <v-btn block v-on:click="details()" color=error dark >Decline</v-btn>    
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
const apiURL = "http://localhost:9001/api/requests";
export default {
    data() {
        return {
            requests: [],
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
                
            })
    },
    methods: {
        accept(req) {
            fetch(apiURL + "/registrations/auth/" + req.id, {
                method: "POST",
                headers: { 'Authorization': this.$authKey }})
            this.requests.splice(this.requests.indexOf(req), 1);
        }
    }
}
</script>
