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
                  <v-toolbar-title >Edit Clinic</v-toolbar-title>
                </v-toolbar>
              
                <v-form v-model="valid" ref="form">
                  <v-text-field
                    label="Name"
                    :rules="[rules.required]"
                    v-model="clinic.name"
                    outlined />
                  <v-text-field
                    label="Address"
                    :rules="[rules.required]"
                    v-model="clinic.address"
                    outlined />
                    <v-textarea
                      outlined
                      label="Description"
                      :rules="[rules.required]"
                      v-model="clinic.description"/>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-on:click="editClinic()" color="primary" block>Edit
                </v-btn>
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
    props: ['id'],
    data() {
        return {
          clinic: {},
          rules: {
            required: value => !!value || 'Field is required.'
          },
          valid: true
        }
    },
    mounted() {
        this.axios({url : apiURL + "/" + this.id, 
                    method: 'GET'
        }).then(response =>   {
            this.clinic = response;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch clinic!", color: "error", })
        });
    },
    methods: {
        editClinic: function() {
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
                this.$router.push({name :"clinics"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update clinic!", color: "error", })
            });
        }
    }
    
}
</script>

<style scoped>

</style>