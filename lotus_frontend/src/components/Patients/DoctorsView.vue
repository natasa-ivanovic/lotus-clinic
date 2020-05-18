<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="9">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Browse by doctors</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <template v-if="items.length != 0">
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
                                <template v-slot:item.rating="{ item} ">
                                    {{ (+item.rating).toFixed(2) }}
                                </template>
                                <template v-slot:item.schedule="{ item }">
                                    <v-icon medium @click="scheduleApp(item)">mdi-calendar-arrow-right</v-icon>
                                </template>
                                <template v-slot:item.terms="{ item }">
                                    <v-autocomplete 
                                        v-model="item.selectedDate"
                                        :items="getItems(item.availableTimes)"
                                        label="Select a term"
                                    />
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
const apiURL = "http://localhost:9001/api/patients/request/finish";
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
                    value: 'rating'
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
                    text: 'Free terms',
                    sortable: false,
                    value: 'terms',
                    align: "center"
                },
                {
                    text: 'Schedule',
                    sortable: false,
                    value: 'schedule',
                    align: "center"
                }
            ]
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
            if (doctor.selectedDate == undefined) {
                this.$store.commit('showSnackbar', {text: "Please select a term for your appointment.", color: "info", });
                return;
            }
            var longDate = new Date(doctor.selectedDate).getTime();
            var data = {
                date: longDate,
                doctor: doctor.id
            };
            this.axios({url : apiURL, 
                    method: 'POST',
                    data: data
                }).then(() =>   {
                    this.$store.commit('showSnackbar', {text: "Successfully requested appointment! Check your email for further information.", color: "success", })
                    this.$router.push({name: "home"});
                }).catch(error => {
                    this.$store.commit('showSnackbar', {text: "Something went wrong! Please try again.", color: "error", })
                    console.log(error);
                });
        },
        getItems: function(times) {
            var data = []
            times.forEach(time => {
                data.push({
                    text: this.formatTime(time),
                    value: time
                })
            })
            return data;
        },
        formatTime: function(t) {
            //"2020-05-14T00:00:00.000+0000"
            //Wed May 13 2020 02:00:00
            var time = new Date(t);
            var timeString = time.toTimeString().substring(0, 5) + " " + time.toDateString();
            console.log(time);
            return timeString;
        }
    }
}
</script>

<style scoped>

</style>