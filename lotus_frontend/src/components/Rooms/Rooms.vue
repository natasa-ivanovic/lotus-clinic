<template>
    <div>
        <RoomCalendar v-bind:overlay.sync="overlay" v-bind:id.sync="selectedId" />
        <v-data-table
          :headers="headers"
          :items="rooms"
          item-key="id"
          class="elevation-1"
        >
          <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>List of rooms</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn @click="addRoom()">Add new room</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.edit="{ item }">
                <v-icon
                    @click="editRoom(item.id)"
                >
                    mdi-pencil
                </v-icon>
            </template>
            <template v-slot:item.delete="{ item }">
                <v-icon
                    @click="deleteRoom(item)"
                >
                    mdi-delete
                </v-icon>
            </template>
            <template v-slot:item.details="{ item }">
                <v-icon
                    @click="getDetails(item.id)"
                >
                    mdi-calendar
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
import RoomCalendar from "../Rooms/RoomCalendar"
const apiURL = "/api/rooms";
export default {
    name: "rooms",
    components: {
        RoomCalendar
    },
    data() {
            return {
                rooms: [],
                headers: [
                    {text: 'ID', value: 'id'},
                    {text: 'Name', value: 'name'},
                    {text: 'Details', value: 'details'},
                    {text: 'Edit', value: 'edit', sortable: false},
                    {text: 'Delete', value: 'delete', sortable: false}
                ],
                selectedId: 0,
                overlay: false
            }
    },
    mounted() {
        this.axios({url : apiURL, 
                        method: 'GET'
            }).then(response =>   {
                this.rooms = response.data;
            }).catch(error => {
                console.log(error.request);
                this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
            });
    },
    methods: {
        deleteRoom: function(room) {
        this.axios({url : apiURL + "/" + room.id, 
                    method: 'DELETE'
        }).then(response =>   {
                console.log(response);
                this.rooms.splice(this.rooms.indexOf(room), 1);
                this.$store.commit('showSnackbar', {text: "Successfully deleted room.", color: "success", })
        }).catch(error => {
            console.log(error.request);
            // navesti razlog errora
            this.$store.commit('showSnackbar', {text: "Couldn't delete room!", color: "error", })
        });
        },
        editRoom: function(editId) {
        this.$router.push({name: "editRoom", params: {id : editId}});
        },
        getDetails: function(id) {
            this.selectedId = id; 
            this.overlay = true;
        },
        addRoom: function() {
        this.$router.push({name: "addRoom"})
        }
    }
    
}
</script>