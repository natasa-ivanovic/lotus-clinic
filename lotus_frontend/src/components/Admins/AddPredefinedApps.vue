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
                          :return-value.sync="startDate"
                          persistent
                          width="290px"
                        >
                        <template v-slot:activator="{on}">
                        <v-text-field
                          label="Start date"
                          v-model="startDate"
                          readonly
                          prepend-icon="mdi-calendar"
                          v-on="on"/>
                        </template>
                        <v-date-picker v-model="startDate" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn text color="primary" @click="date = false">Cancel</v-btn>
                          <v-btn text color="primary" @click="$refs.dialogDate.save(startDate)">OK</v-btn>
                        </v-date-picker>
                        </v-dialog>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col>
                        <v-dialog
                          ref="dialogTime"
                          v-model="time"
                          :return-value.sync="startTime"
                          persistent
                          width="290px"
                        >
                        <template v-slot:activator="{on}">
                        <v-text-field
                          label="Start time"
                          v-model="startTime"
                          readonly
                          prepend-icon="mdi-clock"
                          required
                          v-on="on"/>
                        </template>
                        <v-time-picker v-model="startTime" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn text color="primary" @click="time = false">Cancel</v-btn>
                          <v-btn text color="primary" @click="$refs.dialogTime.save(startTime)">OK</v-btn>
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
                  :items="this.getRoomsData()"
                  dense
                  prepend-icon="mdi-hospital-building"
                  label="Room"
                  ></v-autocomplete>
                  </v-row>
                </v-container>
                <v-btn color="primary" @click="convertEndDate()" class="ml-8" width="100">Continue</v-btn>
                <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step step="5">Review</v-stepper-step>
            <v-stepper-content step="5">
              <v-card
                class="mt-n2"
                color="white"
                elevation="0"
              ></v-card>
                <v-list-item>
                  <v-list-item-content>
                  <div class="overline mb-4">YOU CAN CHANGE PREVIOUS STEPS</div>
                  <v-list-item-title class="headline mb-1">Appointment</v-list-item-title>
                  <v-list-item-subtitle>Time: {{this.appointment.startDateString}} - {{this.formattedEndDate}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Appointment type: {{this.aType}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Doctor: {{this.doc}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Room: {{this.room}}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-btn color="primary" class="ml-4" width="100" @click="finishAppointment()">Finish</v-btn>
                <v-btn color="error" class="ml-5" width="100">Cancel</v-btn>
            </v-stepper-content>
            </v-stepper>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
  const apiTypes = "http://localhost:9001/api/appointmentTypes";
  const apiRooms = "http://localhost:9001/api/rooms/free";
  const apiAddApp = "http://localhost:9001/api/appointments";

  export default {
    data () {
      return {
        e1: 1,
        date: false,
        time: false,
        appointment : {
          startDateString: "",  
          endDateLong: "",
          appointmentType: "",
          doctor: "",
          room: ""
        },
        appTypes: [], //name, doctors
        rooms: [],
        doctors: [],
        startDate: "",
        startTime: "",
        formattedEndDate: "",
        aType: "",
        doc: "",
        room: "",
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
          if (el.doctors.length != 0)
            data.push(el.name);
        });
        return data;
      },
      getDoctors: function() {
        var data = [];
        var docs = []
        this.appTypes.forEach(el => {
          if (this.appointment.appointmentType == el.name) {
            el.doctors.forEach(doc => {
              docs.push(doc);
              data.push(doc.name + " " + doc.surname);
            });
          }
        });
        this.doctors = docs;
        return data;
      },
      getRooms: function() {
        this.appointment.startDateString = this.startDate + " " + this.startTime;
        //console.log(date);
        this.e1 = 4;
        fetch(apiRooms, {method: 'POST', headers: {'Content-Type': 'application/json', 
              'Authorization': this.$authKey},
              body: this.appointment.startDateString})
        .then(response => {
          if (response.status != 200)
            return false;
          else
            return response.json();
        })
        .then(rooms => {
          if (rooms == false)
            return; //NAPRAVI TROUGAO
          else {
            this.rooms = rooms.rooms;
            this.appointment.endDateLong = new Date(rooms.endDate);
          }
            
        })
      },
      getRoomsData: function() {
        var data = [];
        this.rooms.forEach(el => {
          data.push(el.name);
        });
        return data;
      },
      convertEndDate: function() {
        var date = this.appointment.endDateLong.toString();
        var elem = date.split(" ")[4];
        this.formattedEndDate = elem.substring(0, 5);
        this.aType = this.appointment.appointmentType;
        this.room = this.appointment.room;
        this.doc = this.appointment.doctor;
        this.e1 = 5;
      },
      finishAppointment: function() {
        console.log(this.appointment);
        this.doctors.forEach(doc => {
            var info = doc.name + " " + doc.surname;
            if (info == this.appointment.doctor) {
              this.appointment.doctor = doc.id;
            }
        });
        this.rooms.forEach(room => {
          if(room.name == this.appointment.room) {
            this.appointment.room = room.id;
          }
        });

        this.appTypes.forEach(at => {
          if (at.name == this.appointment.appointmentType) {
            this.appointment.appointmentType = at.id;
          }
        })

        this.appointment.endDateLong = this.appointment.endDateLong.getTime();
        fetch(apiAddApp, {method: 'POST', 
                  headers: {'Content-Type': 'application/json',
                            'Authorization': this.$authKey },
                  body: JSON.stringify(this.appointment)})
            .then(response => {
              if (response.status != 200)
                alert("Couldn't add this appointment" + "!");
              else {
                alert("Appointment is successfully created!")
                this.$router.push({name : "home"});
              }
          })
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