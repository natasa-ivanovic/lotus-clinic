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
      <v-col v-if="this.editable" cols="10">
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
      <v-col v-else cols="10">
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
        </v-data-table>
      </v-col>
    </v-row>
</template>

<script>
const apiURL = "/api/patients"
export default {
  props: {
    editable: {
      type: Boolean,
    }
  },
  data() {
    if(this.editable)
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
    else
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
      var oldPage = this.options.page;
      this.options.page = 1;
      if(oldPage===this.options.page){
        this.filter()
      }
    },
    filter: function() {
      const { sortBy, sortDesc } = this.options
      var tailoredURL = apiURL + "?name=" + this.name + "&surname=" + this.surname + "&ssid=" + this.ssid + "&pageSize=" + this.options.itemsPerPage + "&pageNo=" + (this.options.page - 1);
      if (sortBy.length != 0) {
          if (sortBy[0] == "username") 
              tailoredURL = tailoredURL + "&sortBy=username&descending=" + sortDesc[0];
          else if (sortBy[0] == "ssid")
              tailoredURL = tailoredURL + "&sortBy=ssid&descending=" + sortDesc[0];
          else if (sortBy[0] == "name")
              tailoredURL = tailoredURL + "&sortBy=name&descending=" + sortDesc[0];
          else if (sortBy[0] == "surname")
              tailoredURL = tailoredURL + "&sortBy=surname&descending=" + sortDesc[0];
          else if (sortBy[0] == "address")
              tailoredURL = tailoredURL + "&sortBy=address&descending=" + sortDesc[0];
          else if (sortBy[0] == "city")
              tailoredURL = tailoredURL + "&sortBy=city&descending=" + sortDesc[0];
          else if (sortBy[0] == "country")
              tailoredURL = tailoredURL + "&sortBy=country&descending=" + sortDesc[0];
          else if (sortBy[0] == "phoneNumber")
              tailoredURL = tailoredURL + "&sortBy=phoneNumber&descending=" + sortDesc[0];
      }
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