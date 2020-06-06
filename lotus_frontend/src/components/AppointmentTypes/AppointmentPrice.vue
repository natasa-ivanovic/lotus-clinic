<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="appPrices"
      item-key="id"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>List of appointment types</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn @click="addAppPrice()">Add new appointment type</v-btn>
        </v-toolbar>
      </template>
      <template v-slot:item.edit="{ item }">
        <v-icon
          @click="editAppPrice(item.id)"
        >
          mdi-pencil
        </v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon
          @click="deleteAppPrice(item.id)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/appointmentPrices/";
export default {
  name: "appointmentPrices",
  data() {
    return {
      appPrices: [],
      headers: [
        {text: 'ID', value: 'id'},
        {text: 'Name', value: 'name'},
        {text: 'Price', value: 'price'},
        {text: 'Discount', value: 'discount'},
        {text: 'Edit', value: 'edit', sortable: false},
        {text: 'Delete', value: 'delete', sortable: false}
      ]
    }
  },
  mounted() {
    fetch(apiURL, {headers: { 'Authorization': this.$authKey}})
    .then(response => {
      return response.json(); //name, doctors
    })
    .then(r => {
      this.appPrices = r;
    })
  },
  methods: {
    deleteAppPrice: function(id) {
      fetch(apiURL + id, {headers: { 'Authorization': this.$authKey}, method: 'DELETE'})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't delete appointment Price!");
          else
            return response.json();
        })
        .then(r => {
          this.appPrices.pop(r);
        })
    },
    editAppPrice: function(editId) {
      this.$router.push({name: "editAppointmentPrice", params: {id : editId}});
    },
    addAppPrice: function() {
      this.$router.push({name: "addAppointmentPrice"});
    }

  }
    
}
</script>

<style scoped>

</style>