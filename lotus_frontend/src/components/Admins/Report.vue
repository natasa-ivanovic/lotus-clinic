<template>
    <v-container fluid>
        <RoomCalendar v-bind:overlay.sync="overlay" v-bind:entries.sync="calendarEntries" />
        <v-row align="center" justify="center">
            <v-col cols="7">
                <v-card class="elevation-3">
                    <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Business report</v-toolbar-title>
                    </v-toolbar>
                    <v-card-text>
                        <template>
                            <v-row>
                                <v-col cols=6>
                                    <v-card>
                                        <v-toolbar flat color="secondary" dark>
                                            <v-toolbar-title>My Clinic</v-toolbar-title>
                                        </v-toolbar>
                                        <v-card>
                                            <v-card-text>
                                            <v-alert color="purple darken-5" dark icon="mdi-star">Average clinic rating: {{averageRating.toFixed(2)}}</v-alert>
                                            <v-alert color="purple darken-5" dark icon="mdi-star">Average doctor rating: {{averageDoctorRating.toFixed(2)}}</v-alert>
                                            <v-alert color="purple lighten-3" icon="mdi-doctor" 
                                                v-for="doc in doctorList" :key="doc.id">{{doc.name}} {{doc.surname}}: {{doc.rating.toFixed(2)}}</v-alert>
                                            <v-btn block color="primary" v-on:click="overlay = true">View all appointments</v-btn>
                                            </v-card-text>
                                        </v-card>
                                    </v-card>
                                </v-col>
                                <v-col cols=6>
                                    <v-card>
                                    <v-toolbar flat color="secondary" dark>
                                    <v-toolbar-title>Get profit report</v-toolbar-title>
                                    </v-toolbar>
                                    <v-form v-model="valid" ref="form">
                                    <v-card-text>
                                        <v-row>
                                            <v-col cols=6>
                                                <v-menu
                                                    v-model="menu1"
                                                    :close-on-content-click="false"
                                                    :nudge-right="40"
                                                    transition="scale-transition"
                                                    offset-y
                                                    min-width="290px"
                                                >
                                                    <template v-slot:activator="{ on, attrs }">
                                                    <v-text-field
                                                        v-model="startDate"
                                                        label="Start date"
                                                        prepend-icon="mdi-calendar"
                                                        readonly
                                                        v-bind="attrs"
                                                        v-on="on"
                                                        :rules="[rules.required]"
                                                    ></v-text-field>
                                                    </template>
                                                    <v-date-picker v-model="startDate" @input="menu1 = false"></v-date-picker>
                                                </v-menu>
                                            </v-col>
                                            <v-col cols=6>
                                                <v-menu
                                                    v-model="menu2"
                                                    :close-on-content-click="false"
                                                    :nudge-right="40"
                                                    transition="scale-transition"
                                                    offset-y
                                                    min-width="290px"
                                                >
                                                    <template v-slot:activator="{ on, attrs }">
                                                    <v-text-field
                                                        v-model="endDate"
                                                        label="End date"
                                                        prepend-icon="mdi-calendar"
                                                        readonly
                                                        v-bind="attrs"
                                                        v-on="on"
                                                        :rules="[rules.required]"
                                                    ></v-text-field>
                                                    </template>
                                                    <v-date-picker v-model="endDate" @input="menu2 = false"></v-date-picker>
                                                </v-menu>
                                            </v-col>
                                        </v-row>
                                        <v-row>
                                            <v-col>
                                                <v-btn block color="primary"  @click="getProfits()">Get profit report</v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-card-text>
                                    </v-form>
                                    <v-card-text v-if="this.showResults">
                                        <v-alert color="purple darken-5" dark icon="mdi-cash-multiple" >Total profit: {{profitResults.totalProfit}} RSD</v-alert>
                                        <v-alert color="purple lighten-3" icon="mdi-format-list-numbered">Total billed appointments: {{profitResults.totalCount}}</v-alert>
                                        <v-alert color="purple lighten-3" icon="mdi-cash">Average appointment price: {{ profitResults.averagePrice}} RSD</v-alert>                                        
                                    </v-card-text>
                                    </v-card>
                                </v-col>
                            </v-row>
                        </template>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
const apiReport = "/api/report";
const apiProfit = "/api/profit";
import RoomCalendar from "../Rooms/RoomCalendar"
export default {
    components: {
        RoomCalendar
    },
    data() {
        return {
            averageRating: 0,
            averageDoctorRating: 0,
            doctorList: [],
            calendarEntries: [],
            startDate: "",
            endDate: "",
            overlay: false,
            showResults: false,
            profitResults: {
                averagePrice: 0,
                totalProfit: 0,
                totalCount: 0
            },
            menu1: false,
            menu2: false,
            rules: {
            required: value => !!value || 'Field is required.'
          },
          valid: true,
        }
    },
    mounted() {
        this.axios({url: apiReport
        }).then(response => {
            var data = response.data;
            this.averageRating = data.averageRating;
            this.averageDoctorRating = data.averageDoctorRating;
            this.doctorList = data.doctorList;
            this.calendarEntries = data.calendarEntries;
        })
    },
    methods: {
        getProfits() {
            this.$refs.form.validate();
            if (!this.valid) {
              this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
              return;
            }
            var datesData = {
                startDate: new Date(this.startDate),
                endDate: new Date(this.endDate)
            };
            this.axios({url: apiProfit,
                data: datesData,
                method: "POST"
                }).then(response => {
                    this.profitResults = response.data;
                    this.showResults = true;
                })
        }
    }
}
</script>