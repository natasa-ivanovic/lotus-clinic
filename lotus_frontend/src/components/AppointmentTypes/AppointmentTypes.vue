<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="appTypes"
      item-key="id"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>List of rooms</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="addAppType()">Add new appointment type</v-btn>
        </v-toolbar>
      </template>
      <template v-slot:item.edit="{ item }">
        <v-icon
          @click="editAppType(item.id)"
        >
          mdi-pencil
        </v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon
          @click="deleteAppType(item.id)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/appointmentTypes/";
export default {
  name: "appointmentTypes",
  data() {
    return {
      appTypes: [],
      headers: [
        {text: 'ID', value: 'id'},
        {text: 'Name', value: 'name'},
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
      this.appTypes = r;
    })
  },
  methods: {
    deleteAppType: function(id) {
      fetch(apiURL + id, {method: 'DELETE'})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't delete appointment type!");
          else
            return response.json();
        })
        .then(r => {
          this.appTypes.pop(r);
        })
    },
    editAppType: function(editId) {
      alert(editId);
      this.$router.push({name: "editAppointmentType"});
    },
    addAppType: function() {
      this.$router.push({name: "addAppointmentType"});
    }

  }
    
}
</script>

<style scoped>

</style>