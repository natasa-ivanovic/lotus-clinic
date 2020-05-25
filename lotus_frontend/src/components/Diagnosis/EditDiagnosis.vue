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
                  <v-toolbar-title >Edit Diagnosis</v-toolbar-title>
                </v-toolbar>
              
                <v-form v-model="valid" ref="form">
                  <v-text-field
                    label="Name"
                    :rules="[rules.required]"
                    v-model="diagnosis.name"
                    outlined />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-on:click="editDiagnosis()" color="primary" block>Edit
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
</template>

<script>

const apiURL = "/api/diagnoses";

export default {
    name: "editDiagnosis",
    props: ['id'],
    data() {
        return {
          diagnosis: {},
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
            this.diagnosis = response;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch diagnosis!", color: "error", })
        });
    },
    methods: {
        editDiagnosis: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
              return;
            }
            this.axios({url : apiURL + "/" + this.id, 
                        method: 'PUT',
                        data: this.diagnosis
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully updated diagnosis.", color: "success", })
                this.$router.push({name :"diagnoses"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update diagnosis!", color: "error", })
            });
        }
    }
    
}
</script>

<style scoped>

</style>