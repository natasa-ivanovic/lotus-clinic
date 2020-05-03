<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="6">
        <v-stepper v-model="e1" vertical>
          <v-stepper-step :editable="true" :complete="e1 > 1" step="1">Date and time</v-stepper-step>
            <v-stepper-content step="1">
              <v-card
                class="mb-12"
                color="white"
                elevation="0">
                  <v-container>
                    <v-row>
                      <v-col>
                        <v-dialog
                          ref="dialogDate"
                          v-model="date"
                          :return-value.sync="appointment.startDate"
                          persistent
                          width="290px"
                        >
                        <template v-slot:activator="{on}">
                        <v-text-field
                          label="Start date"
                          v-model="appointment.startDate"
                          readonly
                          prepend-icon="mdi-calendar"
                          v-on="on"/>
                        </template>
                        <v-date-picker v-model="appointment.startDate" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn text color="primary" @click="date = false">Cancel</v-btn>
                          <v-btn text color="primary" @click="$refs.dialogDate.save(appointment.startDate)">OK</v-btn>
                        </v-date-picker>
                        </v-dialog>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col>
                        <v-dialog
                          ref="dialogTime"
                          v-model="time"
                          :return-value.sync="appointment.startTime"
                          persistent
                          width="290px"
                        >
                        <template v-slot:activator="{on}">
                        <v-text-field
                          label="Start time"
                          v-model="appointment.startTime"
                          readonly
                          prepend-icon="mdi-clock"
                          required
                          v-on="on"/>
                        </template>
                        <v-time-picker v-model="appointment.startTime" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn text color="primary" @click="time = false">Cancel</v-btn>
                          <v-btn text color="primary" @click="$refs.dialogTime.save(appointment.startTime)">OK</v-btn>
                        </v-time-picker>
                        </v-dialog>
                      </v-col>
                    </v-row>
                  </v-container>
                  <v-btn color="primary" @click="e1 = 2" class="ml-10" width="100">Continue</v-btn>
                  <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
                </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true" :complete="e1 > 2" step="2">Appointment type</v-stepper-step>
            <v-stepper-content step="2">
              <v-card
                class="mb-12"
                color="white"
                elevation="0">
                  <v-container>
                    <v-row>
                    <v-autocomplete
                    class="mt-0"
                    v-model="appointment.appointmentType"
                    :items="this.getAppTypes()"
                    dense
                    chips
                    deletable-chips
                    prepend-icon="mdi-pill"
                    label="Appointment type"
                    ></v-autocomplete>
                    </v-row>
                  </v-container>
                  <v-btn color="primary" @click="e1 = 3" class="ml-8" width="100">Continue</v-btn>
                  <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true" :complete="e1 > 3" step="3">Doctor</v-stepper-step>
            <v-stepper-content step="3">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              >
                <v-container>
                  <v-row>
                  <v-autocomplete
                  class="mt-0"
                  v-model="appointment.doctor"
                  :items="this.getDoctors()"
                  dense
                  chips
                  deletable-chips
                  prepend-icon="mdi-doctor"
                  label="Doctor"
                  ></v-autocomplete>
                  </v-row>
                </v-container>
                <v-btn color="primary" @click="getRooms()" class="ml-8" width="100">Continue</v-btn>
                <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true" :complete="e1 > 4" step="4">Room</v-stepper-step>
            <v-stepper-content step="4">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              >
                <v-container>
                  <v-row>
                  <v-autocomplete
                  class="mt-0"
                  v-model="appointment.room"
                  :items="this.rooms"
                  dense
                  chips
                  deletable-chips
                  prepend-icon="mdi-hospital-building"
                  label="Room"
                  ></v-autocomplete>
                  </v-row>
                </v-container>
                <v-btn color="primary" @click="e1 = 5" class="ml-8" width="100">Continue</v-btn>
                <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step step="5">Review</v-stepper-step>
            <v-stepper-content step="5">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              ></v-card>
              <v-btn color="primary" class="ml-10" width="100">Finish</v-btn>
              <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
            </v-stepper-content>
            </v-stepper>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
  const apiTypes = "http://localhost:9001/api/appointmentTypes";

  export default {
    data () {
      return {
        e1: 1,
        date: false,
        time: false,
        appointment : {
          startDate: "",
          startTime: "",
          appointmentType: "",
          doctor: "",
          room: ""
        },
        appTypes: [], //name, doctors
        room: "",
        doctor: "",
        
        
        
      }
    },
    mounted() {
      fetch(apiTypes, {headers: { 'Authorization': this.$authKey }})
        .then(response => {
            return response.json();
        })
        .then(response => {
            this.appTypes = response;
        })

    },
    methods: {
      getAppTypes: function() {
        var data = [];
        this.appTypes.forEach(el => {
          data.push(el.name);
        });
        return data;
      },
      getDoctors: function() {
        var data = [];
        this.appTypes.forEach(el => {
          if (this.appointment.appointmentType == el.name) {
            el.doctors.forEach(doc => {
              data.push(doc.name + " " + doc.surname);
            });
          }
        });
        return data;
      },
      getRooms: function() {
        //poslati datum i vreme
        //var data = [];
      }
    }

  }
</script>

<style scoped>

.my-datepicker {
  width: 100%;
  height: 30px;
}

.my-timepicker {
  width: 100%;
  height: 30px;
}

</style>