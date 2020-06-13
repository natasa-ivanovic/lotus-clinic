<template>
    <v-row>
      <v-col cols="2">
        <v-container>
          <v-text-field
            label="Name"
            v-model="name" />
          <v-text-field
            label="Surname"
            v-model="surname" />
          <v-text-field
            label="Insurance ID"
            v-model="ssid" />
          <v-btn color="primary" @click="searchClikc()">Search</v-btn>
        </v-container>
      </v-col>
      <v-col cols="10">
        <v-data-table
            :headers="headers"
            :items="users"
            item-key="email"
            class="elevation-1"
            :options.sync="options"
            :server-items-length="totalItems"
            :footer-props="{
              itemsPerPageOptions: [1, 5, 10]
            }"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>List of Patients</v-toolbar-title>
                    <v-spacer></v-spacer>
                </v-toolbar>
            </template>
            <template v-slot:item.edit="{ item }">
                <v-icon
                    @click="editPatient(item)"
                >
                    mdi-pencil
                </v-icon>
            </template>
        </v-data-table>
        </v-col>
    </v-row>
</template>

<script>
const apiURL = "/api/patients"
export default {
  data() {
    return {
      users: [],
      headers: [
        {text: 'Email', value: 'username'},
        {text: 'Insurance ID', value: 'ssid'},
        {text: 'Name', value: 'name'},
        {text: 'Surname', value: 'surname'},
        {text: 'Address', value: 'address'},
        {text: 'City', value: 'city'},
        {text: 'Country', value: 'country'},
        {text: 'Phone number', value: 'phoneNumber'},
        {text: 'Edit', value: 'edit', sortable: false},
      ],
      name: "",
      surname: "",
      ssid: "",
      totalItems: 0,
      options: {}
    }
  },
  watch: {
        options: {
            handler () {    
                this.filter();
            },
            deep: true,
        },
    },
  methods: {
    searchClikc: function() {
      this.options.page = 1;
      this.filter();
    },
    filter: function() {
      console.log(this.options.page)
      var tailoredURL = apiURL + "?name=" + this.name + "&surname=" + this.surname + "&ssid=" + this.ssid + "&pageSize=" + this.options.itemsPerPage + "&pageNo=" + (this.options.page - 1);
        this.axios({url : tailoredURL, 
                  method: 'GET'
      }).then(response => {
        this.users = response.data.content;
        this.totalItems = response.data.totalElements;
      }).catch(error => {
          console.log(error.request);
          this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
      });
    },
    editPatient: function(item) {
      this.$router.push({ name: "patientAppointments", params: {id: item.id} });
    }
  }
}
</script>