<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="appTypes"
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
          <v-btn @click="addAppType()">Add new appointment type</v-btn>
        </v-toolbar>
      </template>
      <template v-slot:item.operation="{ item} ">
        {{ item.operation ? 'Yes' : 'No' }}
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
const apiURL = "/api/appointmentTypes";
export default {
  name: "appointmentTypes",
  data() {
    return {
      appTypes: [],
      headers: [
        {text: 'ID', value: 'id'},
        {text: 'Name', value: 'name'},
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
              requestUrl = requestUrl + "&sortBy=name&descending=" + sortDesc[0];
          else if (sortBy[0] == "operation")
              requestUrl = requestUrl + "&sortBy=operation&descending=" + sortDesc[0];
      }
      this.axios({url : requestUrl, 
                    method: 'GET'
        }).then(response =>   {
            this.appTypes = response.data.content;
            this.totalItems = response.data.totalElements;
            this.loading = false;
        }).catch(() => {
            this.loading = false;
        });
    },
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