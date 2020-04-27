<template>
  <div>
    <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
              <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Edit {{this.userType.substring(0, this.userType.length -1)}} </v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Email"
                        v-model="user.email" />
                    </v-col>
                    <v-col>
                      <v-text-field
                        label="Password"
                        v-model="user.password" />
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Name"
                        v-model="user.name" />
                    </v-col>
                    <v-col>
                      <v-text-field
                        label="Surname"
                        v-model="user.surname" />                      
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-select
                        v-model="selectedGender"
                        :items="genders"
                        label="Gender" />        
                    </v-col>
                    <v-col>
                      <v-menu
                        v-model="menu"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        min-width="290px">
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="dateString"
                            label="Birth date"
                            readonly
                            v-on="on"
                             />                      
                        </template>
                        <v-date-picker v-model="dateString" @input="menu = false"></v-date-picker>
                      </v-menu>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Address"
                        v-model="user.address" />
                    </v-col>
                    <v-col>     
                      <v-text-field
                        label="City"
                        v-model="user.city" />           
                    </v-col>
                    <v-col>     
                      <v-text-field
                        label="Country"
                        v-model="user.country" />         
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Phone number"
                        v-model="user.phoneNumber" />
                    </v-col>
                    <v-col>     
                      <v-text-field
                        label="Insurance id"
                        v-model="user.id" />
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn block v-on:click="editUser()" color="success" height=60>Edit</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
  </div>
</template>

<script>
import {util} from '../../util.js'

const apiURL = "http://localhost:9001/api";

export default {
  name: "EditUser",
  props: ['id', 'userType'],
  data() {
    return {
      user: {},
      menu: false,
      dateString: String,
      genders: ['Male', 'Female', 'Other'],
      selectedGender: 'Other'
    }
  },
  mounted() {
    fetch(apiURL + "/" + this.userType + "/" + this.id)
      .then(response => {
        return response.json();
      })
      .then(user => {
        this.user= user;
        this.dateString = util.dateToString(user.birthDate);
        this.selectedGender = user.gender.charAt(0).toUpperCase() + user.gender.slice(1);
      })
  },
  methods: {
      editUser: function() {
        var oldDate = this.user.birthDate.split('T')[1]; 
        this.user.birthDate = this.dateString + 'T' + oldDate;
        this.user.gender = this.selectedGender.charAt(0).toLowerCase() + this.selectedGender.slice(1);
        fetch(apiURL + "/" + this.userType + "/" + this.id, {method: 'PUT', 
                        headers: {'Content-Type': 'application/json'}, 
                        body: JSON.stringify(this.user)})
          .then(response => {
            if (response.status != 200)
              alert("Couldn't update profile!");
            else
              this.$router.push({ name: this.userType })
          })
    }
  }
}
</script>

<style scoped>

</style>