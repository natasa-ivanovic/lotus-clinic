<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Appointment confirmation</v-toolbar-title>
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

const apiURL = "/api/confirm";

export default {
    props: {
        key: {
            type: String,
            default: ""
        }
    },
    mounted() {
        if (this.key != "") {
            this.axios({url : apiURL + "/" + this.key
                }).then(() =>   {                
                    this.msg = "You have confirmed the appointment. You can now see it on your home page in your scheduled appointments."
                }).catch(() => {
                    this.msg = "An error has occurred. Your appointment may have already been confirmed, or your request is invalid. Check your home page."
                });
        } else
            this.msg = "No key forwarded!"
    },
    data() {
        return {
            msg: ""
        }
    },
}
</script>