<template>
  <div>
  <v-container
      class="fill-height"
      fluid
    >
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
                  <v-text-field
                    label="Name"
                    :rules="[rules.required]"
                    v-model="clinic.name"
                    :readonly="!isEditing"
                    outlined />
                  <v-text-field
                    label="Address"
                    :rules="[rules.required]"
                    v-model="clinic.address"
                    :readonly="!isEditing"
                    outlined />
                    <v-textarea
                      outlined
                      label="Description"
                      :rules="[rules.required]"
                      :readonly="!isEditing"
                      v-model="clinic.description"/>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-if="!isEditing" v-on:click="editClinic()" color="primary" block>Edit</v-btn>
                <v-btn v-if="isEditing" v-on:click="saveClinic()" color="success" block>Save changes</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
</template>

<script>

const apiURL = "/api/clinics";

export default {
    name: "editClinic",
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
          isEditing: false
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
          });
        } else { //admin klinickog centra
          this.axios({url : apiURL + "/" + this.id, 
                      method: 'GET'
          }).then(response =>   {
              this.clinic = response.data;
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
                else
                  this.$router.push({name: "home"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update clinic!", color: "error", })
            });
            this.isEditing = false;
        }
    }
    
}
</script>