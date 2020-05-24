<template>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>Add diagnosis </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form v-model="valid" ref="form">
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="Name"
                            v-model="diagnosis.name"
                            :rules="[rules.required]" />
                      </v-col>
                  </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn block v-on:click="addDiagnosis()" color="success" height=60>Add</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
const apiURL = "/api/diagnoses/";
export default {
    name: "addDiagnosis",
    data() {
        return {
            diagnosis: {
                name: "",
            },
            rules: {
                required: value => !!value || 'Field is required.'
            },
            valid: true
        }
    },
    methods: {
        addDiagnosis: function() {
            this.$refs.form.validate();
            if (!this.valid) {         
                this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
                return;
            }
            this.axios({url : apiURL, 
                        method: 'POST',
                        data: this.diagnosis.name
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully added diagnosis.", color: "success", })
                this.$router.push({name: "diagnoses"})
            }).catch(error => {
                console.log(error.request);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't add diagnosis!", color: "error", })
            });
        }
    }
}
</script>

<style scoped>

</style>