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
                </v-card>
              <v-btn color="primary" @click="e1 = 2">Continue</v-btn>
              <v-btn text>Cancel</v-btn>
            </v-stepper-content>

            <v-stepper-step :editable="true" :complete="e1 > 2" step="2">Appointment type</v-stepper-step>
            <v-stepper-content step="2">
              <v-card
                class="mb-12"
                color="white"
                elevation="0">
                  <v-container>
                    <v-autocomplete
                    v-model="appointment.appointmentType"
                    :items="appTypes"
                    dense
                    chips
                    deletable-chips
                    label="Appointment type"
                    ></v-autocomplete>
                  </v-container>
              </v-card>
              <v-btn color="primary" @click="e1 = 3">Continue</v-btn>
              <v-btn text>Cancel</v-btn>
            </v-stepper-content>

            <v-stepper-step :editable="true" :complete="e1 > 3" step="3">Doctor</v-stepper-step>
            <v-stepper-content step="3">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              ></v-card>
              <v-btn color="primary" @click="e1 = 4">Continue</v-btn>
              <v-btn text>Cancel</v-btn>
            </v-stepper-content>

            <v-stepper-step :editable="true" :complete="e1 > 4" step="4">Room</v-stepper-step>
            <v-stepper-content step="4">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              ></v-card>
              <v-btn color="primary" @click="e1 = 5">Continue</v-btn>
              <v-btn text>Cancel</v-btn>
            </v-stepper-content>

            <v-stepper-step step="5">Review</v-stepper-step>
            <v-stepper-content step="5">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              ></v-card>
              <v-btn color="primary">Finish</v-btn>
              <v-btn text>Cancel</v-btn>
            </v-stepper-content>
            </v-stepper>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
  export default {
    data () {
      return {
        e1: 1,
        date: false,
        time: false,
        appointment : {
          startDate: "",
          startTime: "",
          appointmentType: ""
        },
        appTypes: ['1', '2', '3']
        
      }
    },

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