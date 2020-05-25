<template>
    <v-container  fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>Add Medicine</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form v-model="valid" ref="form">
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="Name"
                            v-model="medicine.name" 
                            :rules="[rules.required]" />
                      </v-col>
                  </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn block v-on:click="addMedicine()" color="primary" height=60>Add</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
const apiURL = "/api/medicines";
export default {
    name: "AddMedicine",
    data() {
        return {
            medicine: {
                name: "",
            },
            rules: {
                required: value => !!value || 'Field is required.'
            },
            valid: true
        }
    },
    methods: {
        addMedicine: function() {
            this.$refs.form.validate();
            if (!this.valid) {
                this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
                return;
            }
            this.axios({url : apiURL, 
                        method: 'POST',
                        data: this.medicine.name,
                        headers: {
                            'Content-Type': 'text/plain'
                        }
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully added medicine.", color: "success", })
                this.$router.push({name: "medicines"})
            }).catch(error => {
                console.log(error.request);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't add medicine!", color: "error", })
            });
        }
    }
}
</script>

<style scoped>

</style>