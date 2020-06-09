<template>
    <v-container fluid>
        <v-row align="center" justify="center">
            <v-col cols="6">
                <v-expansion-panels>
                    <v-expansion-panel>
                        <v-expansion-panel-header style="background:#424242;color:white" class="spacerHeader">Medical record</v-expansion-panel-header>
                        <v-expansion-panel-content eager >
                            <MedicalRecord :id="this.appointment.patientId.toString()" :edit="true" class="mt-4"/>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-expansion-panels>
            </v-col>
        </v-row>
        <v-row align="center" justify="center">
            <v-col cols="6">
                <v-card class="elevation-3">
                    <v-toolbar flat color="secondary" dark>
                        <v-toolbar-title>Appointment type: {{appointment.type}}</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-btn>Report</v-btn>
                    </v-toolbar>
                    <v-card-text>
                        <v-row>
                            <v-col>
                                <v-autocomplete
                                    dense
                                    label="Diagnosis"
                                    item-text="name"
                                    item-value="id"
                                    chips
                                    deletable-chips
                                    selected
                                    multiple
                                    :items="allDiagnosis"
                                    v-model="diagnosis"/>
                            </v-col>
                            <v-col>
                                <v-autocomplete
                                    dense
                                    label="Medicine"
                                    item-text="name"
                                    item-value="id"
                                    chips
                                    deletable-chips
                                    selected
                                    multiple
                                    :items="allMedicines"
                                    v-model="medicine"/>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                            <v-textarea
                                dense
                                color="teal"
                                v-model="info"
                                label="Description">
                            </v-textarea>
                            </v-col>
                        </v-row>
                    </v-card-text>
                    <v-card-actions>
                        <v-row>
                            <v-col>
                                <v-menu
                                    v-model="menuAppointment"
                                    :close-on-content-click="false"
                                    :nudge-width="50"
                                    elevation-10
                                    offset-x
                                    @click:outside="turnOffAppointment()" 
                                    @keydown.esc="turnOffAppointment()"
                                >
                                    <template v-slot:activator="{on, attrs}">
                                        <v-btn color="primary" 
                                                v-bind="attrs" 
                                                v-on="on" 
                                                block>
                                                Schedule appointment
                                        </v-btn>
                                    </template>
                                    <v-card>
                                        <v-card-text>
                                            <v-list>
                                                <v-list-item>
                                                    <v-list-item-content>
                                                        <v-list-item-title>Patient: {{appointment.patientName}} {{appointment.patientSurname}}</v-list-item-title>
                                                        <v-list-item-subtitle>Scheduling a new appointment</v-list-item-subtitle>
                                                    </v-list-item-content>
                                                        <v-icon large>mdi-account-heart-outline</v-icon>
                                                </v-list-item>
                                            </v-list>
                                            <v-divider></v-divider>
                                            <v-form>
                                                <v-row>
                                                    <v-col>
                                                        <v-menu
                                                        @click:outside="turnOffAppointment()" 
                                                        @keydown.esc="turnOffAppointment()"
                                                        max-width="290px"
                                                        min-width="290px"
                                                        ref="dialogAppointment"
                                                        v-model="menuDatePickerApp"
                                                        :return-value.sync="appointmentDate"
                                                        persistent
                                                        :close-on-content-click="false"
                                                        transition="scale-transition"
                                                        nudge-left="308">
                                                            <template v-slot:activator="{on, attrs}">
                                                                <v-text-field
                                                                prepend-icon="mdi-calendar"
                                                                v-model="appointmentDate"
                                                                label="Date"
                                                                v-bind="attrs"
                                                                v-on="on"/>
                                                            </template>
                                                            <v-date-picker
                                                                :min="getToday()"
                                                                v-model="appointmentDate">
                                                                <v-spacer></v-spacer>
                                                                <v-btn text color="primary" @click="menuDatePickerApp = false">Cancel</v-btn>
                                                                <v-btn text color="primary" v-on:click="getFreeTerms()" @click="$refs.dialogAppointment.save(appointmentDate)">OK</v-btn>
                                                            </v-date-picker>
                                                        </v-menu>
                                                    </v-col>
                                                </v-row>
                                                    <v-row>
                                                    <v-col>
                                                        <v-autocomplete 
                                                            prepend-icon="mdi-clock" 
                                                            label="Time"
                                                            :items="formatTerms()"/>
                                                    </v-col>

                                                </v-row>
                                            </v-form>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn color="error" text @click="turnOffAppointment()">Cancel</v-btn>
                                            <v-btn color="primary" text @click="menuAppointment = false">Schedule</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-menu>
                            </v-col>
                            <v-col>
                                <v-menu
                                    v-model="menuOperation"
                                    :close-on-content-click="false"
                                    :nudge-width="50"
                                    nudge-left="827"
                                    elevation-10
                                    offset-x
                                >
                                    <template v-slot:activator="{on, attrs}">
                                        <v-btn color="primary" 
                                                v-bind="attrs" 
                                                v-on="on" 
                                                block>
                                                Schedule operation
                                        </v-btn>
                                    </template>
                                    <v-card>
                                        <v-card-text>
                                            <v-list>
                                                <v-list-item>
                                                    <v-list-item-content>
                                                        <v-list-item-title>Patient: {{appointment.patientName}} {{appointment.patientSurname}}</v-list-item-title>
                                                        <v-list-item-subtitle>Scheduling a new operation</v-list-item-subtitle>
                                                    </v-list-item-content>
                                                        <v-icon large>mdi-account-heart-outline</v-icon>
                                                </v-list-item>
                                            </v-list>
                                            <v-divider></v-divider>
                                            <v-form>
                                                <v-row>
                                                    <v-col><v-text-field label="Date"/></v-col>
                                                    <v-col><v-text-field label="Time"/></v-col>
                                                </v-row>
                                            </v-form>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn color="error" text @click="menuOperation = false">Cancel</v-btn>
                                            <v-btn color="primary" text @click="menuOperation = false">Save</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-menu>
                            </v-col>
                        </v-row>
                    </v-card-actions>
                    <v-card-actions>
                        <v-btn color="success" block @click="endAppointment()">End appointment</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
        <ScheduleAppointment />
    </v-container>
</template>

<script>
import MedicalRecord from "../MedicalRecord/MedicalRecord"
import ScheduleAppointment from "./ScheduleAppointment"

const apiURL = "/api/appointments/info"

export default {
    name: "startAppointment",
    props: {
        appointment: {
            type: Object,
            default: () => {}
        }
    },
    components: {
      MedicalRecord,
      ScheduleAppointment
    },
    data() {
        return {
            diagnosis: "",
            medicine: "",
            info: "",
            allMedicines: [],
            allDiagnosis: [],
            freeTerms: [],
            appointmentDate: "",
            appoitnmentTime: "",
            menuAppointment: false,
            menuOperation: false,
            menuDatePickerApp: false
        }
    },
    mounted() {
        console.log("PROVERA");
        console.log(this.appointment);
        if (this.appointment == {})
            this.$router.push({name: "home"});

        this.axios({
            url: apiURL,
            method: 'GET'
        }).then(response => {
            this.allMedicines = response.data.medicines;
            this.allDiagnosis = response.data.diagnosis;
        }).catch(error =>{
            console.log(error);
        })
    },
    methods: {
        endAppointment() {

        },
        getToday() {
            var today = new Date();
            return today.toISOString();
        },
        turnOffAppointment() {
            this.menuDatePickerApp = false;
            this.menuAppointment = false;
            this.appointmentDate = "";
            this.appointmentTime = "";
        },
        getFreeTerms() {
            var date = new Date(this.appointmentDate);
            this.axios({
                url: "/api/doctors/newAppointment",
                method: 'POST',
                data: date.getTime(),
                headers: {
                        'Content-Type': 'text/plain'
                    }
            }).then(response => {
                this.freeTerms = response.data;
            }); 
        },
        formatTime: function(t) {
            var time = new Date(t);
            var timeString = time.toTimeString().substring(0, 5) + " " + time.toDateString();
            console.log(time);
            return timeString;
        },
        formatTerms() {
            var list = [];
            this.freeTerms.forEach(el => {
                var t = this.formatTime(el);
                list.push(t);
            });
            return list;
        }

    }
    
}
</script>

<style scoped>

.spacerHeader {
    font-size: 1.25rem;
    line-height: 1.5;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

</style>