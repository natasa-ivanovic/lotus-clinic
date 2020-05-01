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
                        <v-img
                            :src="app.src"
                            class="white--text align-end"
                            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                            height="200px"
                        >
                            <v-card-title v-text="app.title"></v-card-title>
                        </v-img>

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
// info koji ima predefinisani pregled:
// datum pocetka
// datum kraja
// doktora
// tip pregleda
// klinika - moze ime + id
// room
// status = premade
// ^- ovo kasnije promenis u scheduled
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
            })
    }
    
}
</script>

<style scoped>

</style>