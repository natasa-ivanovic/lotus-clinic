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
          @click="deleteAppType(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
const apiURL = "api/appointmentTypes";
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
    this.axios({url : apiURL, 
                    method: 'GET'
        }).then(response =>   {
            this.appTypes = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
  },
  methods: {
    deleteAppType: function(type) {
      this.axios({url : apiURL + "/" + type.id, 
                    method: 'DELETE'
        }).then(() =>   {
           // test this
           this.appTypes.splice(this.appTypes.indexOf(type), 1);
           this.$store.commit('showSnackbar', {text: "Successfully deleted appointment type.", color: "success", })
        }).catch(error => {
            console.log(error.request);
            // navesti razlog errora
            this.$store.commit('showSnackbar', {text: "Couldn't delete appointment type!", color: "error", })
        });
    },
    editAppType: function(editId) {
      this.$router.push({name: "editAppointmentType", params: {id : editId}});
    },
    addAppType: function() {
      this.$router.push({name: "addAppointmentType"});
    }

  }
    
}
</script>

<style scoped>

</style>