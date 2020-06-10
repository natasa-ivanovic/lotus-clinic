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
      <template v-slot:item.operation="{ item} ">
        {{ item.operation ? 'Yes' : 'No' }}
      </template>
      <template v-slot:item.edit="{ item }">
        <v-icon
          @click="editAppPrice(item)"
        >
          mdi-pencil
        </v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon
          @click="deleteAppPrice(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
const apiURL = "/api/appointmentPrices";
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
        {text: 'Operation', value: 'operation'},
        {text: 'Edit', value: 'edit', sortable: false},
        {text: 'Delete', value: 'delete', sortable: false}
      ]
    }
  },
  mounted() {
    this.axios({url : apiURL, 
                    method: 'GET'
        }).then(response =>   {
            this.appPrices = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
  },
  methods: {
    deleteAppPrice: function(type) {
      this.axios({url : apiURL + "/" + type.id, 
                    method: 'DELETE'
        }).then(() =>   {
           // test this
           this.appPrices.splice(this.appPrices.indexOf(type), 1);
           this.$store.commit('showSnackbar', {text: "Successfully deleted appointment type.", color: "success", })
        }).catch(error => {
            console.log(error.request);
            // navesti razlog errora
            this.$store.commit('showSnackbar', {text: "Couldn't delete appointment type!", color: "error", })
        });
    },
    editAppPrice: function(type) {
      this.$router.push({name: "editAppointmentPrice", params: {id : type.id}});
    },
    addAppPrice: function() {
      this.$router.push({name: "addAppointmentPrice"});
    }

  }
    
}
</script>

<style scoped>

</style>