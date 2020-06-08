<template>
  <div>
    <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3" v-if="user != undefined">
              <v-toolbar flat color="secondary" dark>
                <v-toolbar-title v-if="userType">Edit {{userType.substring(0, userType.length -1)}} </v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form v-model="valid" ref="form">
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Email"
                        :rules="[rules.email, rules.required]"
                        v-model="user.username" 
                        required
                        readonly/>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Name"
                        :rules="[rules.required]"
                        v-model="user.name" />
                    </v-col>
                    <v-col>
                      <v-text-field
                        label="Surname"
                        :rules="[rules.required]"
                        v-model="user.surname" />                      
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-select
                        v-model="selectedGender"
                        :rules="[rules.required]"
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
                            :rules="[rules.required]"
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
                        :rules="[rules.required]"
                        v-model="user.address" />
                    </v-col>
                    <v-col>     
                      <v-text-field
                        label="City"
                        :rules="[rules.required]"
                        v-model="user.city" />           
                    </v-col>
                    <v-col>     
                      <v-text-field
                        label="Country"
                        :rules="[rules.required]"
                        v-model="user.country" />         
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Phone number"
                        :rules="[rules.required]"
                        v-model="user.phoneNumber" />
                    </v-col>
                    <v-col>     
                      <v-text-field
                        label="Insurance id"
                        :rules="[rules.required, rules.isNumber]"
                        v-model="user.ssid" 
                        readonly/>
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

const apiURL = "/api";

export default {
  name: "EditUser",
  props: ['id', 'userType'],
  data() {
    return {
      user: undefined,
      menu: false,
      dateString: "",
      genders: ['Male', 'Female', 'Other'],
      selectedGender: 'Other',
      confirmPass: "",
      rules: {
          required: value => !!value || 'Field is required.',
          counter: value => value.length > 4 || 'Password must have a minimum of 5 characters',
          email: value => {
            const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            return pattern.test(value) || 'Invalid e-mail.'
          },
          isNumber: value => !isNaN(value) || 'Id must be a number.'
      },
      valid: true,
      showPass: false,
      showConfirmPass: false
    }
  },
  computed: {
    formIsValid () {
      return (
        this.user.username &&
        this.user.name &&
        this.user.surname &&
        this.user.gender &&
        this.user.dateString &&
        this.user.address &&
        this.user.city &&
        this.user.country &&
        this.user.phoneNumber &&
        this.user.ssid
      )
    }
  },
  mounted() {
    this.axios({url : apiURL + "/" + this.userType + "/" + this.id, 
                    method: 'GET'
        }).then(res =>   {
          var user = res.data;
          if (this.$role == "PATIENT") {
                this.user = user.patient;
                this.record = user.record;
                this.dateString = util.dateToString(user.patient.birthDate);
                this.selectedGender = user.patient.gender.charAt(0) + user.patient.gender.slice(1).toLowerCase();
          } else {
              this.user = user;
              this.dateString = util.dateToString(user.birthDate);
              this.selectedGender = user.gender.charAt(0) + user.gender.slice(1).toLowerCase();
          }
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch user!", color: "error", })
        });
  },
  methods: {
      editUser: function() {
        this.$refs.form.validate();
        if (!this.valid) {
          return;
        }
        var oldDate = this.user.birthDate.split('T')[1]; 
        this.user.birthDate = this.dateString + 'T' + oldDate;
        this.user.gender = this.selectedGender.toUpperCase();
        this.axios({url : apiURL + "/" + this.userType + "/" + this.id, 
                        method: 'PUT',
                        data: this.user
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully updated user.", color: "success", })
                if (this.id == "self") {
                  this.$router.push({ name: "profile" })
                }
                else {
                  this.$router.push({ name: this.userType })
                }
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update user!", color: "error", })
            });
    }
  }
}
</script>