<template>
    <v-row>
      <v-col cols="2">
        <v-container>
        <v-text-field
          label="Name"
          @input="filter()"
          v-model="name" />
        <v-text-field
          label="Surname"
          @input="filter()"
          v-model="surname" />
        <v-text-field
          label="Insurance ID"
          @input="filter()"
          v-model="ssid" />
        </v-container>
      </v-col>
      <v-col cols="10">
        <v-data-table
            :headers="headers"
            :items="users"
            item-key="email"
            class="elevation-1"
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
      unfilteredUserd: [],
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
      ssid: ""
    }
  },
  mounted() {
    this.axios({url : apiURL, 
                    method: 'GET'
        }).then(response => {
          this.unfilteredUserd = response.data;
          this.users = this.unfilteredUserd;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
  },
  methods: {
    filter: function() {
      this.users = [];
      this.unfilteredUserd.forEach(user => {
        if(user.name.toLowerCase().includes(this.name.toLowerCase()) &&
          user.surname.toLowerCase().includes(this.surname.toLowerCase()) &&
          user.ssid.toString().includes(this.ssid))
          this.users.push(user)
      });
    },
    editPatient: function() {
      console.log("e")
    }
  }
}
</script>