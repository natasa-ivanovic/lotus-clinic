<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="appPrices"
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
      ],
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
              requestUrl = requestUrl + "&sortBy=type.name&descending=" + sortDesc[0];
          else if (sortBy[0] == "price")
              requestUrl = requestUrl + "&sortBy=price&descending=" + sortDesc[0];
          else if (sortBy[0] == "discount")
              requestUrl = requestUrl + "&sortBy=discount&descending=" + sortDesc[0];
          else if (sortBy[0] == "operation")
              requestUrl = requestUrl + "&sortBy=type.operation&descending=" + sortDesc[0];
      
      
      
      }
      this.axios({url : requestUrl, 
              method: 'GET'
      }).then(response =>   {
          this.appPrices = response.data.content;
          this.totalItems = response.data.totalElements;
          this.loading = false;
      }).catch(() => {
          this.loading = false;
      });
    },
    deleteAppPrice: function(type) {
      this.axios({url : apiURL + "/" + type.id, 
                    method: 'DELETE'
        }).then(() =>   {
           this.appPrices.splice(this.appPrices.indexOf(type), 1);
           this.$store.commit('showSnackbar', {text: "Successfully deleted appointment type.", color: "success", })
        })
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