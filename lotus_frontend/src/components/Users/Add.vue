<template>
  <div>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>Add {{this.userType.substring(0, this.userType.length -1)}} </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form v-model="valid" ref="form">
                <v-row>
                  <v-col>
                    <v-text-field
                      label="Email"
                      :rules="[rules.email, rules.required]"
                      v-model="user.email" 
                      required/>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field
                      label="Password"
                      :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                      :rules="[rules.required, rules.counter]"
                      :type="showPass ? 'text' : 'password'"
                      v-model="user.password" 
                      minlength="5"
                      @click:append="showPass = !showPass" />
                  </v-col>
                  <v-col>
                    <v-text-field
                      label="Confirm password"
                      :append-icon="showConfirmPass ? 'mdi-eye' : 'mdi-eye-off'"
                      :type="showConfirmPass ? 'text' : 'password'"
                      :rules="[rules.required, rules.counter, rules.passwordMatch]"
                      minlength="5"
                      v-model="confirmPass" 
                      @click:append="showConfirmPass = !showConfirmPass" />
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
                      v-model="user.ssid" />
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-btn block v-on:click="addUser()" color="success" height=60>Add</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/";
export default {
    name: "AddUser",
    data() {
      return {
        user: {
          email: "",
          password: "",
          name: "",
          surname: "",
          gender: "",
          birthDate: "",
          address: "",
          city: "",
          country: "",
          phoneNumber: "",
          ssid: ""
        },
        genders: ['Male', 'Female', 'Other'],
        selectedGender: "",
        dateString: "",
        confirmPass: "",
        menu: false,
        rules: {
            required: value => !!value || 'Field is required.',
            counter: value => value.length > 4 || 'Password must have a minimum of 5 characters',
            email: value => {
              const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
              return pattern.test(value) || 'Invalid e-mail.'
            },
            passwordMatch: () => this.user.password == this.confirmPass || 'Passwords must match.',
            isNumber: value => !isNaN(value) || 'Id must be a number.'
        },
        valid: true,
        showPass: false,
        showConfirmPass: false
      }
    },
    props: {
        userType: {
            type: String
        }
    },
    methods: {
      addUser: function() {
        this.$refs.form.validate();
        if (!this.valid) {
          return;
        }
        this.user.birthDate = this.dateString + "T00:00:00.000+0000";
        this.user.gender = this.selectedGender.toUpperCase();          
        fetch(apiURL + this.userType, {method: 'POST', 
                        headers: {'Content-Type': 'application/json'}, 
                        body: JSON.stringify(this.user)})
          .then(response => {
              if (response.status != 200)
                alert("Couldn't add " + this.userType.substring(0, this.userType.length -1) + "!");
              else
                this.$router.push({ name: this.userType });
          })
      }
    }
    
}
</script>

<style scoped>

</style>