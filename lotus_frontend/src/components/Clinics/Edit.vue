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

const apiURL = "http://localhost:9001/api/clinics";

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
        fetch(apiURL + "/" + this.id,{headers: { 'Authorization': this.$authKey}})
        .then(response => {
            return response.json();
        })
        .then(clinic => {
            this.clinic = clinic;
        })
    },
    methods: {
        editClinic: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              alert("Error")
              return;
            }
            fetch(apiURL + "/" + this.id, {method: 'PUT',
                  headers: {'Content-Type': 'application/json',
                  'Authorization': this.$authKey},
                  body: JSON.stringify(this.clinic)})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't update clinic!");
                else
                    this.$router.push({name :"clinics"});
            })
        }
    }
    
}
</script>

<style scoped>

</style>