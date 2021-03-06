<template >
<v-container>
  <v-row class="fill-height">
    <v-col>
      <v-sheet height="64">
        <v-toolbar flat color="white">
          <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
            Today
          </v-btn>
          <v-btn fab text small color="grey darken-2" @click="prev">
            <v-icon small>mdi-chevron-left</v-icon>
          </v-btn>
          <v-btn fab text small color="grey darken-2" @click="next">
            <v-icon small>mdi-chevron-right</v-icon>
          </v-btn>
          <v-toolbar-title>{{ title }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-menu bottom right>
            <template v-slot:activator="{ on }">
              <v-btn
                outlined
                color="grey darken-2"
                v-on="on"
              >
                <span>{{ typeToLabel[type] }}</span>
                <v-icon right>mdi-menu-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="type = 'day'">
                <v-list-item-title>Day</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'week'">
                <v-list-item-title>Week</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'month'">
                <v-list-item-title>Month</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = '4day'">
                <v-list-item-title>4 days</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
        <v-calendar
          :key="componentKey"
          ref="calendar"
          v-model="focus"
          color="primary"
          :events="events"
          :event-color="getEventColor"

          :type="type"
          @click:event="showEvent"
          @click:more="viewDay"
          @click:date="viewDay"
          @change="updateRange"
        ></v-calendar>
        <v-menu
          v-model="selectedOpen"
          :close-on-content-click="false"
          :activator="selectedElement"
          offset-x
        >
          <v-card
            color="grey lighten-4"
            min-width="350px"
            flat
          >
            <v-toolbar
              :color="selectedEvent.color"
              dark
            >
              <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn light icon @click ="selectedOpen = false"><v-icon>mdi-close</v-icon></v-btn>
            </v-toolbar>
            <v-card-text>
              <div v-if="selectedEvent.room">Room: {{selectedEvent.room}}</div>
              <div v-if="selectedEvent.patientName">Patient: {{selectedEvent.patientName}} {{selectedEvent.patientSurname}} </div>
            </v-card-text>
            <v-card-actions>
              <v-btn v-if="selectedEvent.startApp"
                text
                color="success"
                @click="startAppointment(selectedEvent)" 
              >
                Start appointment
              </v-btn>
              <v-btn v-if="selectedEvent.cancel"
                text
                color="error"
                @click="cancelAppointment(selectedEvent)"
              >
                Cancel
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
      </v-sheet>
    </v-col>
  </v-row>
</v-container>
</template>

<script>
const apiURL = "/api/calendarentries"
  export default {
    data() {
        return {
      componentKey: 0,
      focus: '',
      type: 'month',
      appointments: [],
      typeToLabel: {
        month: 'Month',
        week: 'Week',
        day: 'Day',
        '4day': '4 Days',
      },
      start: null,
      end: null,
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      events: [],
      colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
      names: ['Meeting', 'Holiday', 'PTO', 'Travel', 'Event', 'Birthday', 'Conference', 'Party']
        }
    },
    computed: {
      title () {
        const { start, end } = this
        if (!start || !end) {
          return ''
        }

        const startMonth = this.monthFormatter(start)
        const endMonth = this.monthFormatter(end)
        const suffixMonth = startMonth === endMonth ? '' : endMonth

        const startYear = start.year
        const endYear = end.year
        const suffixYear = startYear === endYear ? '' : endYear

        const startDay = start.day + this.nth(start.day)
        const endDay = end.day + this.nth(end.day)

        switch (this.type) {
          case 'month':
            return `${startMonth} ${startYear}`
          case 'week':
          case '4day':
            return `${startMonth} ${startDay} ${startYear} - ${suffixMonth} ${endDay} ${suffixYear}`
          case 'day':
            return `${startMonth} ${startDay} ${startYear}`
        }
        return ''
      },
      monthFormatter () {
        return this.$refs.calendar.getFormatter({
          timeZone: 'UTC', month: 'long',
        })
      },
    },
    mounted () {
      this.$refs.calendar.checkChange()
      this.axios({url : apiURL, 
                    method: 'GET'
        }).then(response => {
            this.appointments = response.data;
            var startApp = false;
            var color = '';
            var name = '';
            var room = '';
            var patientName = '';
            var patientSurname = '';
            var appId = '';
            var cancel = false;
            var appointment = false;
            for(var i = 0; i < this.appointments.length; i++)
            {
              if(this.appointments[i].appointment != null)
              {
                startApp = this.appointments[i].start;
                cancel = this.checkTime(this.appointments[i]);
                color = 'blue';
                if(!startApp) {
                  color = 'grey';
                  cancel = false;
                }
                appointment = true;
                name = 'Appointment';
                room = this.appointments[i].appointment.roomName;
                appId = this.appointments[i].id;
                patientName = this.appointments[i].appointment.patientName;
                patientSurname = this.appointments[i].appointment.patientSurname;
              } else if (this.appointments[i].operation != null) {
                appointment = false;
                color = 'orange';
                cancel = this.checkTime(this.appointments[i]);
                startApp = false;
                if(!this.appointments[i].start) {
                  color = 'grey';
                  cancel = false;
                }
                name = 'Operation';
                room = this.appointments[i].operation.roomName;
                appId = this.appointments[i].id;
                if(this.appointments[i].operation.patient != null) {
                  patientName = this.appointments[i].operation.patient.patient.name;
                  patientSurname = this.appointments[i].operation.patient.patient.surname;
                } else {
                  patientName = 'not available';
                  patientSurname = '';
                }              
              } else if (this.appointments[i].vacation != null) {
                color = 'green';
                name = 'Vacation'
                room = '';
                patientName = '';
                patientSurname = '';
                cancel = false;
                startApp = false;
              }
              this.events.push(Object.assign({}, {
                name: name,
                id: appId,
                appointment: appointment,
                start: this.formatDate(new Date(this.appointments[i].startDate), true),
                end: this.formatDate(new Date(this.appointments[i].endDate), true),
                color: color,
                room: room,
                patientName: patientName,
                patientSurname: patientSurname,
                cancel : cancel,
                startApp : startApp
              }))}
        })
    },
    methods: {
      viewDay ({ date }) {
        this.focus = date
        this.type = 'day'
      },
      getEventColor (event) {
        return event.color
      },
      setToday () {
        this.focus = this.today
      },
      prev () {
        this.$refs.calendar.prev()
      },
      next () {
        this.$refs.calendar.next()
      },
      showEvent ({ nativeEvent, event }) {
        const open = () => {
          this.selectedEvent = event
          this.selectedElement = nativeEvent.target
          setTimeout(() => this.selectedOpen = true, 10)
        }

        if (this.selectedOpen) {
          this.selectedOpen = false
          setTimeout(open, 10)
        } else {
          open()
        }

        nativeEvent.stopPropagation()
      },
      updateRange () {
        // const events = []

        // const min = new Date(`${start.date}T00:00:00`)
        // const max = new Date(`${end.date}T23:59:59`)
        // const days = (max.getTime() - min.getTime()) / 86400000
        // const eventCount = this.rnd(days, days + 20)

        // for (let i = 0; i < eventCount; i++) {
        //   const allDay = this.rnd(0, 3) === 0
        //   const firstTimestamp = this.rnd(min.getTime(), max.getTime())
        //   const first = new Date(firstTimestamp - (firstTimestamp % 900000))
        //   const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
        //   const second = new Date(first.getTime() + secondTimestamp)

        //   events.push({
        //     name: this.names[this.rnd(0, this.names.length - 1)],
        //     start: this.formatDate(first, !allDay),
        //     end: this.formatDate(second, !allDay),
        //     color: this.colors[this.rnd(0, this.colors.length - 1)],
        //   })
        // }

        // this.start = start
        // this.end = end
        // this.events = events
      },
      nth (d) {
        return d > 3 && d < 21
          ? 'th'
          : ['th', 'st', 'nd', 'rd', 'th', 'th', 'th', 'th', 'th', 'th'][d % 10]
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },
      formatDate (a, withTime) {
        return withTime
          ? `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`
          : `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()}`
      },
      checkTime: function(appointment) {
        console.log(appointment);
        var day = new Date(appointment.startDate)
        var appTime = day.getTime();
        var today = new Date();
        var now = today.getTime();

        if (appTime - now < 86400000)
            return false;
        return true;
      },
      cancelAppointment: function(calendarEntry) {
        var url = "";
        if (calendarEntry.appointment) 
          url = "/api/appointments/doctor/cancel/" + calendarEntry.id;
        else
          url = "/api/operations/cancel/" + calendarEntry.id;
        this.axios({
          url:  url,
          method: "GET"
        }).then(() => {
          this.$store.commit('showSnackbar', {text: "Successfully canceled!", color: "success" });
          //izbrisi ga iz liste
          for (var i = 0; i < this.events.length; i++) {
            if (this.events[i].id == calendarEntry.id) {
              this.events.splice(i, 1);
              break;
            } 
          }
        this.selectedOpen = false;
        this.componentKey += 1;
        })
      },
      startAppointment(selectedEvent) {
        this.axios({
            url:"/api/calendarentries/startAppointment/" + selectedEvent.id,
            method: "GET"
        }).then(response => {
            var app = response.data;
            this.$router.push({name: "startAppointment", params: {appointment: app}})
        })
      }
      
    }
  }
</script>