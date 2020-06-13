<template>
    <v-dialog width="1200" v-model="overlay"  @click:outside="turnOff()" @keydown.esc="turnOff()">
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
            :now="today"
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
    </v-dialog>
</template>


<script>
const apiURL = "/api/calendarentries/"
export default {
    data() {
        return {
            focus: '',
            type: 'month',
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
            events: []
        }
    },
    props: {
        overlay:  {
            type: Boolean,
            default: false 
            },
        id: {
            type: Number,
            default: 0
        }
    },
    watch: {
        overlay: function(newVal) {
            if (newVal == true && this.id != 0) {
                this.events = [];
                this.axios({
                    url: apiURL + this.id
                }).then(response => {
                    response.data.forEach(entry => {
                        var name = (entry.operation != null) ? "Operation" : "Appointment";
                        var color = (entry.operation != null) ? "orange" : "blue";
                        var room = (entry.operation != null) ? entry.operation.roomName : entry.appointment.roomName;
                        var patientName = (entry.operation != null) ? entry.operation.patient.patient.name 
                                    : entry.appointment.patient.patient.name;
                        var patientSurname = (entry.operation != null) ? entry.operation.patient.patient.surname 
                                    : entry.appointment.patient.patient.surname;
                        this.events.push(Object.assign({}, {
                            name: name,
                            start: this.formatDate(new Date(entry.startDate), true),
                            end: this.formatDate(new Date(entry.endDate), true),
                            color: color,
                            room: room,
                            patientName: patientName,
                            patientSurname: patientSurname
                        }))
                    })
                })
            }
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
    methods: {
        turnOff() {
            this.$emit("update:overlay", false);
        },
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
        nth (d) {
            return d > 3 && d < 21
            ? 'th'
            : ['th', 'st', 'nd', 'rd', 'th', 'th', 'th', 'th', 'th', 'th'][d % 10]
        },
        formatDate (a, withTime) {
            return withTime
            ? `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`
            : `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()}`
        },
    }
}
</script>