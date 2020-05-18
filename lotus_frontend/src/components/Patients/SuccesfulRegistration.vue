<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Registration status</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                {{this.msg}}
                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>

const apiURL = "http://localhost:9001/api/requests/registrations";

export default {
    props: ['logkey'],
    mounted() {
        fetch(apiURL + "/" + this.logkey)
        .then(response => {
            if (response.status != 200){
                alert(response.status)
                this.msg = "An error has occurred on our servers. Please try again later."
            }
            else
                this.msg = "Your request has been approved by our administrators. Please log in using the button on the top right."
        })
    },
    data() {
        return {
            msg: ""
        }
    },
}
</script>