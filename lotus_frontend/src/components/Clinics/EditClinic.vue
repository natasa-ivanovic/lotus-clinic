<template>
  <div>
    <v-container
      class="fill-height"
      fluid
    >
      <Map v-bind:overlay.sync="overlay" v-bind:address.sync="address" />
      <v-row
        align="center"
        justify="center"
      >
          <v-col
              cols="6"
          >
            <v-card class="elevation-3">
              <v-card-text>
                <v-toolbar elevation="0">
                  <v-toolbar-title >Clinic information</v-toolbar-title>
                </v-toolbar>
              
                <v-form v-model="valid" ref="form">
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Name"
                        :rules="[rules.required]"
                        v-model="clinic.name"
                        :readonly="!isEditing"
                        dense
                        />
                    </v-col>
                    </v-row>
                    <v-row>
                      <v-col>
                        <v-text-field
                          label="Address"
                          :rules="[rules.required]"
                          v-model="clinic.address"
                          :readonly="!isEditing">
                      <template v-slot:append>
                          <v-btn v-on:click="showMap(clinic.address)" icon><v-icon large>mdi-map-marker</v-icon></v-btn>
                      </template>
                      </v-text-field>
                      </v-col>
                     </v-row>
                     <v-row>
                       <v-col>
                          <v-textarea
                            label="Description"
                            :rules="[rules.required]"
                            :readonly="!isEditing"
                            v-model="clinic.description"/>
                       </v-col>
                     </v-row>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-if="!isEditing" v-on:click="editClinic()" color="primary" block>Edit</v-btn>
                <v-col>
                <v-btn v-if="isEditing" v-on:click="saveClinic()" color="success" block>Save changes</v-btn>
                </v-col>
                <v-col>
                <v-btn v-if="isEditing" v-on:click="cancel()" color="error" block>Cancel</v-btn>
                </v-col>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
</template>

<script>
import Map from "../Clinics/Map";
const apiURL = "/api/clinics";

export default {
    name: "editClinic",
    components: {
        Map
    },
    props: {
      id: {
            type: Number,
            default: () => 0
    }},
    data() {
        return {
          clinic: {},
          rules: {
            required: value => !!value || 'Field is required.'
          },
          valid: true,
          isEditing: false,
          overlay: false,
          address: "",
          cachedClinic: {}
        }
    },
    mounted() {
        // ako je ulogovan admin, getuj njegovu kliniku
        if (this.id == 0) {
          this.axios({
            url: "/api/clinics/myClinic",
            method: "GET"
          }).then(response => {
            this.clinic = response.data;
            this.cachedClinic = Object.assign({}, this.clinic);
          });
        } else { //admin klinickog centra
          this.axios({url : apiURL + "/" + this.id, 
                      method: 'GET'
          }).then(response =>   {
              this.clinic = response.data;
              this.cachedClinic = Object.assign({}, this.clinic);
          }).catch(error => {
              console.log(error.request);
              this.$store.commit('showSnackbar', {text: "Couldn't fetch clinic!", color: "error", })
          });
        }
    },
    methods: {
        editClinic: function() {
          this.isEditing = true;
        },
        saveClinic: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
              return;
            }
            this.axios({url : apiURL + "/" + this.id, 
                        method: 'PUT',
                        data: this.clinic
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully updated clinic.", color: "success", })
                if(this.$role == "CENTRE_ADMIN")
                  this.$router.push({name :"clinics"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update clinic!", color: "error", })
            });
            this.isEditing = false;
        },
        showMap(addr) {
            this.address = addr;
            this.overlay = true;
        },
        cancel() {
            this.clinic = Object.assign({}, this.cachedClinic);
            this.isEditing = false;
        }
    }
    
}
</script>