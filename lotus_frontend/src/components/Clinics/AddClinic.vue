<template>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>Add Clinic </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form v-model="valid" ref="form">
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="Name"
                            v-model="clinic.name"
                            :rules="[rules.required]" />
                      </v-col>
                  </v-row>
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="Address"
                            v-model="clinic.address" />
                      </v-col>
                  </v-row>
                  <v-row>
                      <v-col>
                          <v-textarea
                            label="Description"
                            v-model="clinic.description"
                            :rules="[rules.required]" />
                      </v-col>
                  </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn block v-on:click="addClinic()" color="success" height=60>Add</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
const apiURL = "/api/clinics";
export default {
    name: "AddClinic",
    data() {
        return {
            clinic: {
                name: "",
                address: "",
                description: ""
            },
            rules: {
                required: value => !!value || 'Field is required.'
            },
            valid: true
        }
    },
    methods: {
        addClinic: function() {
            this.$refs.form.validate();
            if (!this.valid) {
                this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
                return;
            }
            this.axios({url : apiURL, 
                        method: 'POST',
                        data: this.clinic
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully added clinic.", color: "success", })
                this.$router.push({name: "clinics"})
            }).catch(error => {
                console.log(error.request);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't add clinic!", color: "error", })
            });
        }
    }
}
</script>