<template>
    <v-container fluid>
        <v-row align="center" justify="center">
            <v-col cols="7">
                <v-card class="elevation-3">
                    <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Browse free rooms</v-toolbar-title>
                    </v-toolbar>
                    <v-card-text>
                        <template>
                            <v-card>
                                <v-card-title>
                                Term: {{this.formatDate(this.request.startDate)}}
                                <v-spacer></v-spacer>
                                <v-text-field
                                    v-model="search"
                                    append-icon="mdi-magnify"
                                    label="Search"
                                    single-line
                                    hide-details></v-text-field>
                                </v-card-title>
                                <!--v-carousel v-model="selectedDay" hide-delimiters
                                    :continuous="false"
                                    :show-arrows="true"
                                    hide-delimiter-background
                                    height="40"
                                    light
                                >
                                <v-carousel-item
                                    v-for="(slide, i) in ['1', '2', '3']"
                                    :key="i"
                                    class="mb-15"
                                >
                                    <v-sheet
                                    height="100%"
                                    tile
                                    >
                                    <v-row
                                        align="center"
                                        justify="center"
                                    >
                                        <div class="display-1">{{slide}}</div>
                                    </v-row>
                                    </v-sheet>
                                </v-carousel-item>
                                </v-carousel-->
                                <v-data-table
                                :headers="header"
                                :items="rooms"
                                :search="search">
                                    <template v-slot:item.term="{item}">
                                    {{ formatDate(item.term) }}
                                    </template>
                                    <template v-slot:item.schedule="{item}">
                                        <v-icon medium @click="sendNotification(item)">mdi-calendar-arrow-right</v-icon>
                                    </template>
                                    <template v-slot:item.calendar>
                                        <v-icon medium :loading="waiting" >mdi-calendar-today</v-icon>
                                    </template>
                                </v-data-table>
                            </v-card>
                        </template>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
const apiTerms = "/api/rooms/terms";
const apiFinish = "/api/appointments/notification";
export default {
    name: "freeRooms",
    props: ['request'],
    data() {
        return {
            header: [
                { text: "Room name", value: "name"},
                { text: "First free term", value: "term", sortable: true, align: "center"},
                { text: "Calendar", value: "calendar", sortable: false},
                { text: "Schedule", value: "schedule", sortable: false}
            ],
            rooms: [],
            waiting: false
        }
    },
    mounted() {
        var date = new Date(this.request.startDate);
        var milli = date.getTime();
        this.axios({
            headers: {'Content-Type' : 'text/plain'},
            url: apiTerms,
            method: "POST",
            data: milli
        }).then(response => {
            this.rooms = response.data
        }).catch(error => {
            alert(error);
        })
    },
    methods: {
        formatDate(day) {
            var d = new Date(day);
            var time = d.toTimeString().split(" ")[0];
            var date = d.toISOString().split("T")[0];
            return date + " " + time;
        },
        sendNotification(item) {
            //posalji id sobe i id requesta na bekend, napravi app i posalji mejl
            if (this.waiting) {
                this.$store.commit('showSnackbar', {text: "Request alreay sent!", color: "error" })
                return;
            }
            console.log(item);
            var day = new Date(item.term);
            var millis = day.getTime();
            var el = {
                room: item.id,
                request: this.request.id,
                startDate: millis
            }
            this.axios({
                url: apiFinish,
                method: 'POST',
                data: el
                }).then(response => {
                    console.log(response);
                    this.$store.commit('showSnackbar', {text: "Successfully assigned a room to the appointment!", color: "success" })
                    this.$router.push({name: "home"}); 
                }).catch(error => {
                    this.$store.commit('showSnackbar', {text: "An error has occurred!", color: "error" })
                    console.log(error);
                })
            this.waiting = true;
            this.$store.commit('showSnackbar', {text: "Please wait while the emails send!", color: "info" })
        }
    }
    
}
</script>

<style scoped>

</style>