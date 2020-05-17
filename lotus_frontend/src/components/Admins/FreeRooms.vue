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
                                Rooms
                                <v-spacer></v-spacer>
                                <v-text-field
                                    v-model="search"
                                    append-icon="mdi-magnify"
                                    label="Search"
                                    single-line
                                    hide-details></v-text-field>
                                </v-card-title>
                                <v-carousel v-model="selectedDay" hide-delimiters
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
                                </v-carousel>
                                <v-data-table
                                :headers="header"
                                :items="rooms"
                                :search="search">
                                    <template v-slot:item.terms="{item}">
                                        <v-autocomplete
                                            v-model="item.terms"
                                            :items="['1', '2', '3']"
                                            label="Select a term"/>
                                    </template>
                                    <template v-slot:item.schedule>
                                        <v-icon medium>mdi-calendar-arrow-right</v-icon>
                                    </template>
                                    <template v-slot:item.calendar>
                                        <v-icon medium>mdi-calendar-today</v-icon>
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

export default {
    data() {
        return {
            header: [
                { text: "Room name", value: "room"},
                { text: "Terms", value: "terms", sortable: true, align: "center"},
                { text: "Calendar", value: "calendar", sortable: false},
                { text: "Schedule", value: "schedule", sortable: false}
            ],
            rooms: [
                { room: "A1", terms: ["11:00", "11:30"]},
                { room: "A2", terms: ["15:00", "15:30"]},
                
            ],
            selectedDate: ''
        }
    }
    
}
</script>

<style scoped>

</style>