<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="6">
        <v-stepper v-model="e1" vertical>
          
          <v-stepper-step :editable="true" :rules="[() => this.step1]" :complete="e1 > 1" step="1">Date
            <small v-if="!this.step1">Please enter the date.</small></v-stepper-step>
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
                        <v-date-picker v-model="startDate" :min="getToday()" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn text color="primary" @click="date = false">Cancel</v-btn>
                          <v-btn text color="primary" @click="$refs.dialogDate.save(startDate)">OK</v-btn>
                        </v-date-picker>
                        </v-dialog>
                      </v-col>
                    </v-row>
                  </v-container>
                  <v-btn color="primary" @click="getAppTypes()" class="ml-10" width="100">Continue</v-btn>
                </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true" :rules="[() => this.step2]" :complete="e1 > 2" step="2">Appointment type
            <small v-if="!this.step2">Please select an appointment type.</small></v-stepper-step>
            <v-stepper-content step="2">
              <v-card
                class="mb-12"
                color="white"
                elevation="0">
                  <v-container>
                    <v-row>
                    <v-autocomplete
                    class="mt-0"
                    v-model="appointment.type"
                    :items="this.types"
                    dense
                    prepend-icon="mdi-pill"
                    label="Appointment type"
                    ></v-autocomplete>
                    </v-row>
                  </v-container>
                  <v-btn color="primary" @click="getDoctors()" class="ml-8" width="100">Continue</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true" :rules="[() => this.step3]" :complete="e1 > 3" step="3">Doctor
              <small v-if="!this.step3">Please select a doctor.</small></v-stepper-step>
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
                  v-model="appointment.doctorId"
                  :items="this.doctors"
                  dense
                  prepend-icon="mdi-doctor"
                  label="Doctor"
                  ></v-autocomplete>
                  </v-row>
                </v-container>
                <v-btn color="primary" @click="getTerms()" class="ml-8" width="100">Continue</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true" :rules="[() => this.step4]" :complete="e1 > 4" step="4">Time
              <small v-if="!this.step4">Please select a term.</small></v-stepper-step>
            <v-stepper-content step="4">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              >
                <v-container>
                    <v-row>
                      <v-col>
                        <v-autocomplete v-model="startTime" :items="allTerms" label="Term"/> 
                      </v-col>
                    </v-row>
                </v-container>
                <v-btn color="primary" @click="getRooms()" class="ml-8" width="100">Continue</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true"  :rules="[() => this.step5]" :complete="e1 > 5" step="5">Room
            <small v-if="!this.step5">Please select a room.</small></v-stepper-step>
            <v-stepper-content step="5">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              >
                <v-container>
                  <v-row>
                  <v-autocomplete
                  class="mt-0"
                  v-model="appointment.roomId"
                  :items="this.rooms"
                  dense
                  prepend-icon="mdi-hospital-building"
                  label="Room"
                  ></v-autocomplete>
                  </v-row>
                </v-container>
                <v-btn color="primary" @click="convertEndDate()" class="ml-8" width="100">Continue</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step :editable="true"  :rules="[() => this.step6]" :complete="e1 > 6" step="6">Discount
            <small v-if="!this.step6">Please enter a discount.</small></v-stepper-step>
            <v-stepper-content step="6">
              <v-card
                class="mb-12"
                color="white"
                elevation="0"
              >
              <v-container>
                <v-row>
                </v-row>
                  <v-text-field
                    label="Discount"
                    suffix="%"
                    :rules="[rules.required, rules.isNumber, rules.isBetween]"
                    v-model="appointment.discount"
                    required />
              </v-container>
               <v-btn color="primary" @click="checkDiscount()" class="ml-8" width="100">Continue</v-btn>
              </v-card>
            </v-stepper-content>

            <v-stepper-step step="7" >Review</v-stepper-step>
            <v-stepper-content step="7">
              <v-card
                class="mt-n2"
                color="white"
                elevation="0"
              ></v-card>
                <v-list-item>
                  <v-list-item-content>
                  <div class="overline mb-4">YOU CAN CHANGE PREVIOUS STEPS</div>
                  <v-list-item-title class="headline mb-1">Appointment</v-list-item-title>
                  <v-list-item-subtitle>Time: {{ this.formattedDate}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Appointment type: {{this.aType}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Doctor: {{this.doc}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Room: {{this.room}}</v-list-item-subtitle>
                  <v-list-item-subtitle>Discount: {{this.appointment.discount}} %</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-btn color="primary" class="ml-4" width="100" @click="finishAppointment()">Finish</v-btn>
                <v-btn color="error" class="ml-5" width="100" @click="redirect()">Cancel</v-btn>
            </v-stepper-content>
            </v-stepper>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
  const apiTypes = "/api/appointmentPrices";
  const apiTerms = "/api/appointments/free_doctors"
  const apiRooms = "/api/rooms/free";
  const apiAddApp = "/api/appointments";  

  export default {
    data () {
      return {
        e1: 1,
        date: false,
        appointment : {
          startDate: new Date(),
          type: "",
          doctorId: "",
          roomId: "",
          discount: "0"
        },
        appTypes: [],
        types: [],
        freeRooms: [],
        rooms: [],
        doctors: [],
        startDate: "",
        allTerms: [],
        startTime: "",
        formattedDate: "",
        aType: "",
        doc: "",
        room: "",
        rules: {
          step1: () => this.step1, //date and time
          step2: () => this.step2, //appointment type
          step3: () => this.step3, //doctor
          step4: () => this.step4, //time
          step5: () => this.step5,  //room
          step6: () => this.step6,  //price
          required: value => !!value || value === 0 || 'Field is required.',
          isNumber: value => !isNaN(value) || 'Price must be a number.',
          isBetween: value => (parseInt(value) >= 0 && parseInt(value) <= 100) || 'Discount must be between 0 and 100'
        },
        step1: true,
        step2: true,
        step3: true,
        step4: true,
        step5: true,
        step6: true,
      }
    },
    mounted() {
      this.axios({url : apiTypes, 
                    method: 'GET'
        }).then(response =>   {
            this.appTypes = response.data;
        });
    },
    methods: {
      getToday: function() {
        var today = new Date();
        return today.toISOString();
      },
      getAppTypes: function() {
        if (this.startDate == "") {
          this.step1 = false;
          return;
        }
        this.step1 = true;
        var data = [];
        this.appTypes.forEach(el => {
          var type = {}
          if (el.doctors.length != 0) {
            type = {
              text: el.name,
              value: el.id
            }
            data.push(type);
          }
        });
        this.types = data;
        this.e1 = 2;
      },
      getDoctors: function() {
        if (this.appointment.type == "") {
          this.step2 = false;
          return;
        }
        this.step2 = true;
        var docs = [];
        console.log(this.appointment.type);
        this.appTypes.forEach(el => {
          console.log(el.id);
          if (this.appointment.type == el.id) {
            this.appointment.discount = el.discount;
            el.doctors.forEach(doc => {
              var doctor = {
                text: doc.name + " " + doc.surname,
                value: doc.id
              }
              docs.push(doctor);
            });
          }
        });
        this.doctors = docs;
        this.e1 = 3;
      },
      getTerms: function() {
        if (this.appointment.doctorId == "") {
          this.step3 = false;
          return;
        }
        this.step3 = true;
        this.e1 = 4;
        var queryData = {
            requestDate: new Date(this.startDate).getTime(),
            appointmentType: this.appointment.type,
            doctorId: this.appointment.doctorId
        };
        this.axios({url : apiTerms, 
                    method: 'POST',
                    data:  queryData
        }).then(response =>   {
            this.allTerms = [];
            response.data.forEach(d => {
              var date = new Date(d);
              this.allTerms.push({
                text: this.$util.dateToTerm(date),
                value: date.getTime()
              })
            })
        })
      },

      getRooms: function() {
        if (this.startTime == "") {
          this.step4 = false;
          return;
        }
        this.step4 = true;
        this.appointment.startDate = new Date(this.startTime);
        this.e1 = 5;
        this.axios({url : apiRooms + "/" + this.appointment.startDate.getTime(), 
                    method: 'POST',
        }).then(response =>   {
            var rooms = response.data;
            var r = [];
            rooms.rooms.forEach(el => {
              var room = {
                text: el.name,
                value: el.id
              };
              r.push(room);
            })
            this.freeRooms = rooms.rooms;
            this.rooms = r;
        })
      },
      convertEndDate: function() {
        if (this.appointment.roomId == "") {
          this.step5 = false;
          return;
        }
        this.step5 = true;
        this.getAppInfo();
        this.e1 = 6;
      },
      checkDiscount: function() {
        if (this.appointment.discount === "") {
          this.step6 = false;
          return;
        }
        this.step6 = true;
        this.e1 = 7;
        this.formattedDate = this.$util.dateToFullTerm(this.appointment.startDate);
      },
      getAppInfo: function() {
        this.appTypes.forEach(el => {
          if (el.id == this.appointment.type) {
            this.aType = el.name;
            el.doctors.forEach(doc => {
              if (doc.id == this.appointment.doctorId) {
                this.doc = doc.name + " " + doc.surname;
              }
            });
          } 
        });

        this.freeRooms.forEach(el => {
          if (el.id == this.appointment.roomId) {
            this.room = el.name;
          }
        });
      },
      finishAppointment: function() {
        this.axios({url : apiAddApp, 
                    method: 'POST',
                    data:  this.appointment
        }).then(() =>   {
            this.$store.commit('showSnackbar', {text: "Appointment is successfully created!", color: "success", })
            this.$router.push({name : "home"}); 
        });
      },
      redirect: function() {
        this.$router.push({name : "home"});
      }
    }

  }
</script>

<style scoped>

.my-datepicker {
  width: 100%;
  height: 30px;
}

</style>