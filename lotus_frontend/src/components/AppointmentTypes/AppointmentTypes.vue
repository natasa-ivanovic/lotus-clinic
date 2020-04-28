<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="apTypes"
      item-key="id"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>List of rooms</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="addAppointmentType()">Add new appointment type</v-btn>
        </v-toolbar>
      </template>
      <template v-slot:item.edit="{ item }">
        <v-icon
          @click="editAppointmentType(item.id)"
        >
          mdi-pencil
        </v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon
          @click="deleteAppointmentType(item.id)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
//const apiURL = "http://localhost:9001/api/appointmentTypes/";
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
  }
    
}
</script>

<style scoped>

</style>