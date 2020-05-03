<template>
    <v-row>
        <v-col>
            <v-card>
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Registration requests</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <v-card-title v-if="requests.length == 0">
                        No current registration requests.
                    </v-card-title>
                    <v-list v-else>
                        <v-list-item-group>
                        <v-list-item
                            v-for="(req, i) in requests"
                            :key="i"
                            >
                            <v-list-item-content>
                                <v-list-item-title>Req</v-list-item-title>
                                <v-list-item-content>
                                    Request info
                                    <v-row>
                                        <v-col>
                                            <v-btn>Accept</v-btn>
                                        </v-col>
                                        <v-col>
                                            <v-btn>Decline</v-btn>
                                        </v-col>
                                    </v-row>
                                </v-list-item-content>
                            </v-list-item-content>
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
        fetch(apiURL + "/patients/requests", {headers: { 'Authorization': this.$authKey }})
        .then(response => {
            return response.json();
        })
        .then(pats => {
            this.requests = pats;
        })
    },
}
</script>

<style scoped>

</style>