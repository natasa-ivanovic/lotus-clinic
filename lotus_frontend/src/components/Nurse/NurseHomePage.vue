<template>
    <v-row>
        <v-col>
            <v-card>
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Scheduled appointments</v-toolbar-title>
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
                                    Doctor: {{app.doctorName}} {{app.doctorSurname}} <br />
                                    Room: {{app.roomName}} <br />
                                    Time: {{app.time}} <br />
                                    Diagnosis: {{constructDiagnosis(app)}} <br />
                                    Medicine: {{constructMedicine(app)}}
                                    </v-list-item-content>
                            <v-btn v-on:click="approve(app)" block color="green">Verify</v-btn>
                            </v-list-item-content>
                        </v-list-item>
                        </v-list-item-group>
                    </v-list>
                </v-card-text>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
const apiURL = "/api/appointments/nurse";
export default {
  data() {
    return {
      appointments: [],
    }
  },
  mounted() {
    this.axios({url: apiURL,
            method: 'GET'
        }).then(response => {
            console.log(response);
            this.appointments = response.data;
            this.appointments.forEach(app => {
                app.date = app.startDate.split("T")[0]
                app.time = this.dateFormat(new Date(app.startDate), new Date(app.endDate)); 
            })
        }).catch(error => {
            console.log(error);
        })
  },
  methods: {
        dateFormat: function(date1, date2) {
            var time1 = date1.toTimeString().split(" ")[0];
            var time2 = date2.toTimeString().split(" ")[0];
            return time1 + "-" + time2;
        },
        constructDiagnosis: function(app) {
          console.log(app);
          return app.diagnosis.join(', ');
        },
        constructMedicine: function(app) {
          console.log(app);
          return app.recipes.join(', ');
        },
        approve: function(app){
          this.axios({url: apiURL,
            method: 'POST',
            data: app.id,
            headers: {
              'Content-Type': 'text/plain'
            }
          }).then(response => {
            console.log(response);
            this.appointments.splice(this.appointments.indexOf(app), 1); 
          }).catch(error => {
            console.log(error);
          })
        }
  }
}
</script>
        