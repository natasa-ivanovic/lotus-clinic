<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>{{this.title}}</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                pick a date
                pick an appointment type
                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        clinic: {
            type: Boolean
        },
        title: {
            type: String,
            default: ""
        }
    },
    data() {
        return {

        }
    },
    mounted() {
        if (this.title == "")   
            this.$router.push( {name: 'appointmentView'});
    }
}
</script>


<style scoped>

</style>