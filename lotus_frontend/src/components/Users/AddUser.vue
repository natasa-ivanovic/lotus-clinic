<template>
  <div>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>{{this.header}} </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form v-model="valid" ref="form">
                <v-row>
                  <v-col>
                    <v-text-field
                      label="Email"
                      :rules="[rules.email, rules.required]"
                      v-model="user.username" 
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
                <v-row v-if="this.userType == 'doctors'">
                  <v-col>
                    <v-select
                      
                      label="Speciality"
                      :rules="[rules.required]"
                      :items="appTypes"
                      item-text="name"
                      item-value="id"
                      v-model="speciality" />
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
                <v-row v-if="this.userType=='admins'">
                  <v-col>
                    <v-select
                      :rules="[rules.required]"
                      :items="clinicNames"
                      v-model="selectedClinicName"
                      label="Clinic" />        
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-btn v-if="this.userType != 'patients'" block v-on:click="addUser()" color="success" height=60>Add</v-btn>
              <v-btn v-else block v-on:click="addUser()" color="success" height=60>Register</v-btn>
            </v-card-actions>
            <v-card-actions v-if="this.userType == 'patients'">
                <v-btn block v-on:click="$router.push({name:'login'})" color="info" height=60>Return</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const apiClinics = "/api/clinics";
const apiAppTypes = "/api/appointmentTypes";
export default {
    name: "AddUser",
    data() {
      return {
        user: {
          username: "",
          password: "",
          name: "",
          surname: "",
          gender: "",
          birthDate: "",
          address: "",
          city: "",
          country: "",
          phoneNumber: "",
          ssid: "",
          clinicId: ""
        },
        genders: ['Male', 'Female', 'Other'],
        selectedGender: "",
        dateString: "",
        confirmPass: "",
        clinics: [],
        clinicNames: [],
        selectedClinicName: "",
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
        showConfirmPass: false,
        errorUserName: false,
        errorSSID: false,
        appTypes: [],
        speciality: ""
      }
    },
    props: {
        userType: {
            type: String,
            default: "patients"
        },
        header: {
          type: String,
          default: "Register"
        }
    },
    methods: {
      addUser: function() {
        this.$refs.form.validate();
        if (!this.valid) {
          return;
        }
        // bolja provera, da li se dodaju admini ili cadmini?
        if(this.$role == 'CENTRE_ADMIN') {
          var i;
          for(i = 0; i < this.clinics.length; i++) {
            if(this.selectedClinicName == this.clinics[i].name){
              break;
            }
          }
          this.user.clinicId = this.clinics[i].id;
        }

        this.user.birthDate = this.dateString + "T00:00:00.000+0000";
        this.user.gender = this.selectedGender.toUpperCase();     
        var url = "";
        if (this.userType == "patients")
          url = "/auth/register";
        else {
          url = "/api/" + this.userType; 
          if (this.userType == "doctors")
            url = url + "/" + this.speciality;
        }
        console.log('userType:' + this.userType);
        this.axios({url : url, 
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            data: JSON.stringify(this.user)
          }).then(response =>   {
            console.log(response);
            console.log('userType:' + this.userType);
            if (this.userType == "patients") {
              this.$store.commit('showSnackbar', {text: "Successfully sent registration request! Check your email for details.", color: "success", })
              this.$router.push({name: 'login'})
            }
            else {
              this.$store.commit('showSnackbar', {text: "Successfully added " + this.userType + "!", color: "success", })
              if(this.$role != 'CENTRE_ADMIN')
                this.$router.push({name: this.userType});
              else
                this.$router.push({name: 'home'});
            }
          }).catch(error => {
            console.log(error);
            this.$store.commit('showSnackbar', {text: "An error has ocurred!", color: "error", })
            //TODO SREDITI ERORE ZA SSID I USERNAME
          });
      }
    },
    mounted() {
      if(this.userType=='admins')
        this.axios({url : apiClinics, 
            method: 'GET',
          }).then(response => {
            this.clinics = response.data;
            var i;
            for(i = 0; i < this.clinics.length; i++) {
              this.clinicNames.push(this.clinics[i].name);
            }
          }).catch(error => {
            alert(error);
          })
      if (this.userType == "doctors") {
        this.axios({url: apiAppTypes, method: 'GET'
        }).then(response => {
          this.appTypes = response.data;
        })
      }
    }
    
}
</script>