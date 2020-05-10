<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Browse by doctors</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <template>
                        <v-card>
                            <v-card-title>
                            Doctors
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
                                <template v-slot:item.schedule="{ item }">
                                    <v-icon medium @click="scheduleApp(item)">mdi-calendar-arrow-right</v-icon>
                                </template>
                            </v-data-table>
                        </v-card>
                    </template>
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
            items: this.doctorList,
            search: "",
            headers: [
                {
                    text: "Name",
                    sortable: true,
                    value: 'name'
                },
                {
                    text: "Surname",
                    sortable: true,
                    value: 'surname'
                },
                {
                    text: "Rating",
                    sortable: true,
                    value: 'grade'
                },
                {
                    text: "Appointment type",
                    sortable: true,
                    value: 'type'
                },
                {
                    text: 'Clinic name',
                    sortable: true,
                    value: 'clinic.name'
                },
                {
                    text: 'Clinic address',
                    sortable: true,
                    value: 'clinic.address'
                },
                {
                    text: 'Schedule',
                    sortable: false,
                    value: 'schedule',
                    align: "center"
                }
            ],
        }
    },
    props: {
        doctorList: {
            type: Array,
            default: () => []
        }
    },
    methods: {
        scheduleApp: function(doctor) {
            alert(doctor.name);
        }
    }
}
</script>

<style scoped>

</style>