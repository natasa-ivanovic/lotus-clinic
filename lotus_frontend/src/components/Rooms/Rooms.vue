<template>
    <div>
        <RoomCalendar v-bind:overlay.sync="overlay" v-bind:id.sync="selectedId" />
        <v-data-table
          :headers="headers"
          :items="rooms"
          :server-items-length="totalItems"
          :loading="loading"
          :options.sync="options"
          :footer-props="{
              itemsPerPageOptions : [1, 5, 10]
          }"
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
                    {text: 'Details', value: 'details', sortable: false},
                    {text: 'Edit', value: 'edit', sortable: false},
                    {text: 'Delete', value: 'delete', sortable: false}
                ],
                selectedId: 0,
                overlay: false,
                loading: true,
                totalItems: 0,
                options: {}

            }
    },
    watch: {
        options: {
            handler () {    
                this.getDataFromApi();
            },
            deep: true,
        }
    },
    methods: {
        getDataFromApi() {
            this.loading = true;
            const { sortBy, sortDesc, page, itemsPerPage } = this.options
            var requestUrl = apiURL + "/pages" + "?pageNo=" + (page - 1) + "&pageSize=" + itemsPerPage;
            if (sortBy.length != 0) {
                if (sortBy[0] == "id") 
                    requestUrl = requestUrl + "&sortBy=id&descending=" + sortDesc[0];
                else if (sortBy[0] == "name")
                    requestUrl = requestUrl + "&sortBy=name&descending=" + sortDesc[0];
            }
            this.axios({url :requestUrl, 
                        method: 'GET'
            }).then(response =>   {
                this.rooms = response.data.content;
                this.totalItems = response.data.totalElements;
                this.loading = false;
            }).catch(() => {
                this.loading = false;
            });
        },
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