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
                <v-card-actions>
                <v-row>
                    <v-col>
                    <v-btn color="success" @click="login()" block height=55>Login</v-btn>
                    </v-col>
                </v-row>
                </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>

const apiURL = "/api/requests/registrations";

export default {
    props: ['logkey'],
    mounted() {
        this.axios({url : apiURL + "/" + this.logkey
            }).then(() =>   {                
                this.msg = "Your request has been approved by our administrators. Please log in using the button on the top right."
            }).catch(() => {
                this.msg = "An error has occurred. Your request may have already been approved, or your request is invalid."
            });
    },
    data() {
        return {
            msg: ""
        }
    },
    methods: {    
        login() {
        this.$router.push({ name: "login"});
        }
    }
}
</script>