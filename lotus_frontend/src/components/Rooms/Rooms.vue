<template>
    <div>
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
                    <v-btn :to="{ name: 'addRoom' }">Add new room</v-btn>
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
                    @click="deleteRoom(item.id)"
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
const apiURL = "http://localhost:9001/api/rooms/";
export default {
    name: "rooms",
  data() {
    return {
      rooms: [],
      headers: [
          {text: 'ID', value: 'id'},
          {text: 'Name', value: 'name'},
          {text: 'Details', value: 'details'},
          {text: 'Edit', value: 'edit', sortable: false},
          {text: 'Delete', value: 'delete', sortable: false}
      ]
    }
  },
  mounted() {
    fetch(apiURL)
      .then(response => {
        return response.json();
      })
      .then(r => {
        this.rooms = r;
      })
  },
  methods: {
    addRoom: function() {
      var form = document.forms['addRoom'];
      
      var room = {
          name : form.name.value,
          id : form.id.value
      };
      fetch(apiURL, {method: 'POST', 
                      headers: {'Content-Type': 'application/json'}, 
                      body: JSON.stringify(room)})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't add room!");
          else
            return response.json();
        })
        .then(r => {
          this.rooms.push(r);
        })
    },
    deleteRoom: function(id) {
      fetch(apiURL + id, {method: 'DELETE'})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't delete room!");
          else
            return response.json();
        })
        .then(r => {
          this.rooms.pop(r);
        })
    },
    editRoom: function(editId) {
      alert(editId);
      this.$router.push({name: "editRoom", params: {id : editId}});
    },
    getDetails: function(id) {
        return id;
    }
  }
    
}
</script>

<style scoped>

</style>