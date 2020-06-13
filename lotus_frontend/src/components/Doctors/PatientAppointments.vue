<template>
  <v-container fluid>
    <v-row align="center" justify="center">
      <v-col cols="6">
        <v-expansion-panels>
          <v-expansion-panel>
            <v-expansion-panel-header style="background:#424242;color:white" class="spacerHeader">Medical record</v-expansion-panel-header>
            <v-expansion-panel-content eager >
              <MedicalRecord :id="this.id.toString()" :edit="true" class="mt-4" />
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>
    <v-row align="center" justify="center">
      <v-col cols="6">
        <v-card class="elevation-3">
          <v-toolbar flat color="secondary" dark>
            <v-toolbar-title >Appointments</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-card-title v-if="appointments.length == 0">
              No appointments scheduled!
            </v-card-title>
            <v-list v-else>
              <v-list-item-group>
                <v-list-item
                  v-for="(app, i) in appointments"
                  :key="i"
                  >
                  <v-list-item-content>
                    <v-list-item-title>{{app.type}} on {{app.date}}</v-list-item-title>
                    <v-list-item-content>
                      Patient: {{app.patientName}} {{app.patientSurname}} <br />
                      Room: {{app.roomName}} <br />
                      Time: {{app.time}}
                    </v-list-item-content>
                    <v-row>
                      <v-btn v-on:click="start(app)" block color="success">Start appointment</v-btn>
                    </v-row>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import MedicalRecord from "../MedicalRecord/MedicalRecord"
const apiURL = "api/appointments/doctor/patient"
export default {
  components: {
    MedicalRecord
  },
  props: ['id'],
  data() {
    return {
      appointments: []
    }
  },
  mounted() {
    this.axios({
      url: apiURL + "/" + this.id,
      method: 'GET'
    }).then(response => {
      this.appointments = response.data;
      this.appointments.forEach(app => {
                    app.date = app.startDate.split("T")[0]
                    app.time = this.dateFormat(new Date(app.startDate), new Date(app.endDate)); 
            })
    }).catch(error =>{
      console.log(error);
    });
  },
  methods: {
    start: function(app) {
      console.log(app);
      this.$router.push({name: "startAppointment", params: {appointment: app}})
    },
    dateFormat: function(date1, date2) {
            var time1 = date1.toTimeString().split(" ")[0];
            var time2 = date2.toTimeString().split(" ")[0];
            return time1 + "-" + time2;
    },
  }
}
</script>