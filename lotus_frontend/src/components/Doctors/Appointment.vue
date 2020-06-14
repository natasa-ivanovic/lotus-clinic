<template>
    <v-container fluid>
        <v-row align="center" justify="center">
            <v-col cols="6">
                <v-expansion-panels>
                    <v-expansion-panel>
                        <v-expansion-panel-header style="background:#424242;color:white" class="spacerHeader">Medical record</v-expansion-panel-header>
                        <v-expansion-panel-content eager >
                            <MedicalRecord :id="this.appointment.patientId.toString()" :edit="true" class="mt-4" />
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
                                    :close-on-click="false"
                                    :nudge-width="50"
                                    elevation-10
                                    offset-x
                                    @click:outside="turnOffAppointment()" 
                                    @keydown.esc="turnOffAppointment()"
                                >
                                    <template v-slot:activator="{on, attrs}">
                                        <v-btn v-if="edit" color="primary" 
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
                                                        :close-on-click="false"
                                                        transition="scale-transition"
                                                        nudge-left="308">
                                                            <template v-slot:activator="{on, attrs}">
                                                                <v-text-field
                                                                prepend-icon="mdi-calendar"
                                                                v-model="appointmentDate"
                                                                label="Select date"
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
                                                            label="Select term"
                                                            v-model="appointmentTerm"
                                                            :items="formatTerms()"/>
                                                    </v-col>

                                                </v-row>
                                            </v-form>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn color="error" text @click="turnOffAppointment()">Cancel</v-btn>
                                            <v-btn color="primary" text @click="scheduleAppointment()">Schedule</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-menu>
                            </v-col>
                            <v-col>
                                <v-menu
                                    @click:outside="turnOffOperation()" 
                                    @keydown.esc="turnOffOperation()"
                                    v-model="menuOperation"
                                    :close-on-content-click="false"
                                    :close-on-click="false"
                                    :nudge-width="50"
                                    nudge-left="830"
                                    elevation-10
                                    offset-x
                                >
                                    <template v-slot:activator="{on, attrs}">
                                        <v-btn v-if="edit" color="primary" 
                                                v-bind="attrs" 
                                                v-on="on"
                                                @click="getDoctors()"
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
                                                    <v-col>
                                                        <v-menu
                                                        @click:outside="turnOffOperation()" 
                                                        @keydown.esc="turnOffOperation()"
                                                        max-width="290px"
                                                        min-width="290px"
                                                        ref="dialogOperation"
                                                        v-model="menuDatePickerOperation"
                                                        :return-value.sync="operationDate"
                                                        persistent
                                                        :close-on-content-click="false"
                                                        transition="scale-transition"
                                                        nudge-right="525">
                                                            <template v-slot:activator="{on, attrs}">
                                                                <v-text-field
                                                                prepend-icon="mdi-calendar"
                                                                v-model="operationDate"
                                                                label="Select date"
                                                                v-bind="attrs"
                                                                v-on="on"/>
                                                            </template>
                                                            <v-date-picker
                                                                :min="getToday()"
                                                                v-model="operationDate">
                                                                <v-spacer></v-spacer>
                                                                <v-btn text color="primary" @click="menuDatePickerOperation = false">Cancel</v-btn>
                                                                <v-btn text color="primary" @click="$refs.dialogOperation.save(operationDate)">OK</v-btn>
                                                            </v-date-picker>
                                                        </v-menu>
                                                    </v-col>
                                                    <v-col>
                                                        <v-autocomplete 
                                                            prepend-icon="mdi-needle" 
                                                            label="Select operation type"
                                                            v-model="operationType"
                                                            allow-overflow="false"
                                                            :items="formatOperations()"/>
                                                    </v-col>
                                                </v-row>
                                                <v-row>
                                                    <v-col>
                                                        <div id="stop">
                                                        <v-autocomplete
                                                            prepend-icon="mdi-doctor" 
                                                            label="Select doctors"
                                                            v-model="operationDoctors"
                                                            chips
                                                            deletable-chips
                                                            multiple
                                                            allow-overflow="false"
                                                            :items="formatDoctors()"/>
                                                        </div>
                                                    </v-col>
                                                </v-row>
                                            </v-form>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn color="error" text @click="turnOffOperation()">Cancel</v-btn>
                                            <v-btn color="primary" text @click="scheduleOperation()">Schedule</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-menu>
                            </v-col>
                        </v-row>
                    </v-card-actions>
                    <v-card-actions>
                        <v-btn v-if="edit" color="success" block @click="endAppointment()">Finish appointment</v-btn>
                        <v-btn v-else color="success" block @click="endAppointment()">Save</v-btn>
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
        },
        edit: {
          type: Boolean,
          default: false
        }
    },
    components: {
      MedicalRecord,
      ScheduleAppointment
    },
    data() {
        return {
            diagnosis: [],
            medicine: [],
            info: "",
            allMedicines: [],
            allDiagnosis: [],
            freeTerms: [],
            doctors: [],
            operations: [],
            appointmentDate: "",
            appointmentTerm: "",
            operationDate: "",
            operationType: "",
            operationDoctors: [],
            menuAppointment: false,
            menuOperation: false,
            menuDatePickerApp: false,
            menuDatePickerOperation: false
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
            this.appointment.diagnosis.forEach(diag => {
              this.allDiagnosis.forEach(d => {
                if(d.name === diag){
                  this.diagnosis.push(d.id);
                }
              })
            });
            this.appointment.recipes.forEach(recipe => {
              this.allMedicines.forEach(r => {
                console.log(r.name)
                if(r.name === recipe){
                  this.medicine.push(r.id);
                }
              })
            });   
        }).catch(error =>{
            console.log(error);
        });
        this.info = this.appointment.description;

        this.axios({
            url: "/api/appointmentPrices",
            method: "GET"
        }).then(response => {
            this.operations = response.data;
        })
    },
    methods: {
        endAppointment() {
            var app = {
                id : this.appointment.id,
                diagnosis: this.diagnosis,
                recipes: this.medicine,
                description: this.info
            }
            console.log(app)
            this.axios({
                url: "/api/appointments/finish",
                method: "POST",
                data: app
            }).then(() => {
                if(this.edit){
                    this.$store.commit('showSnackbar', {text: "Successfully finished appointment!", color: "success"});
                }
                else {
                    this.$store.commit('showSnackbar', {text: "Successfully edited appointment!", color: "success"});
                }
                this.$router.push({name: "home"});
                this.medicine = "";
                this.diagnosis = "";
                this.description = "";
            })
        },
        getToday() {
            var today = new Date()
            var tomorrow = new Date(today)
            tomorrow.setDate(tomorrow.getDate() + 1)
            return tomorrow.toISOString();
        },
        turnOffAppointment() {
            this.menuDatePickerApp = false;
            this.menuAppointment = false;
            this.appointmentDate = "";
            this.appointmentTerm = "";
        },
        turnOffOperation() {
            this.menuDatePickerOperation = false;
            this.menuOperation = false;
            this.operationDate = "";
            this.operationDoctors = [];
        
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
                var t = {
                    text: this.formatTime(el),
                    value: el
                };
                list.push(t);
            });
            return list;
        },
        scheduleAppointment() {
            //id doktora, id pacijenta, datum pocetka
            var d = new Date(this.appointmentTerm);
            var roomRequest = {
                patient : {
                    id: this.appointment.patientId
                },
                doctors: [{
                    id : this.appointment.doctorId
                }],
                startDate: d.getTime()
            };
            console.log(roomRequest);
            this.axios({
                url: "/api/requests/appointment",
                method: 'POST',
                data: roomRequest
            }).then(() => {
                this.$store.commit('showSnackbar', {text: "Successfully scheduled appointment!", color: "success"})
            });
            this.menuAppointment = false;
            this.appointmentDate = "";
            this.appointmentTerm = "";
            this.freeTerms = [];
        },
        scheduleOperation() {
            // lista id doktora, id pacijenta, datum pocetka
            var list = [];
            var d = new Date(this.operationDate);
            console.log(this.operationDoctors)
            this.operationDoctors.forEach(el => {
                var doc = { id: el };
                list.push(doc);
            });
            var roomRequest = {
                doctors: list,
                patient: {
                    id : this.appointment.patientId
                },
                startDate: d.getTime(),
                type: this.operationType
            };
            this.axios({
                url: "/api/requests/operation",
                method: 'POST',
                data: roomRequest
            }).then(() => {
                this.$store.commit('showSnackbar', {text: "Successfully scheduled operation!", color: "success"})
            });
            this.turnOffOperation();
        },
        getDoctors() {
            this.axios({
                url: "/api/doctors",
                method: "GET"
            }).then(response => {
                this.doctors = response.data
            });
        },
        formatDoctors() {
            var list = [];
            this.doctors.forEach(doc => {
                var d = {
                    text: doc.name + " " + doc.surname,
                    value: doc.id
                };
                list.push(d);
            })
            return list;
        },
        formatOperations() {
            var list = [];
            this.operations.forEach(el => {
                if(el.operation == true) {
                    var item = {
                    text: el.name,
                    value: el.id
                    }
                    list.push(item);
                }
            });
            return list;
        }

    }
    
}
</script>

<style scoped>

#stop {
    width: 500px;
}

.spacerHeader {
    font-size: 1.25rem;
    line-height: 1.5;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

</style>