<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Browse by clinics</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <template v-if="this.items.length != 0">
                        <v-card>
                            <v-card-title>
                            Clinics
                            <v-spacer></v-spacer>
                            <v-text-field
                                v-model="search"
                                append-icon="mdi-magnify"
                                label="Search"
                                single-line
                                hide-details
                            ></v-text-field>
                            </v-card-title>
                            <v-data-table
                            :headers="headers"
                            :items="items"
                            :search="search">
                                <template v-slot:item.rating="{ item} ">
                                    {{ (+item.rating).toFixed(2) }}
                                </template>
                                <template v-slot:item.viewDoctors="{ item }">
                                    <v-icon medium @click="viewDoctors(item)">mdi-doctor</v-icon>
                                </template>
                            </v-data-table>
                        </v-card>
                    </template>
                    <v-alert type="info" v-else>
                        No doctors are available for the requested day and appointment type!
                        Please return to the previous page and choose another day.
                    </v-alert>
                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            items: this.clinicList,
            search: "",
            headers: [
                {
                    text: 'Clinic name',
                    sortable: true,
                    value: 'name'
                },
                {
                    text: 'Rating',
                    sortable: true,
                    value: 'rating'
                },
                {
                    text: 'Clinic address',
                    sortable: true,
                    value: 'address'
                },
                {
                    text: 'View doctors',
                    sortable: false,
                    value: 'viewDoctors',
                    align: "center"
                }
            ],
        }
    },
    props: {
        clinicList: {
            type: Array,
            default: () => []
        }
    },
    methods: {
        viewDoctors: function(clinic) {
            this.$router.push({name: "patientsDoctors", params: {doctorList: clinic.doctors}});
        }
    }
    
}
</script>

<style scoped>

</style>