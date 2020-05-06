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

const apiURL = "http://localhost:9001/api/diagnoses";

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
        fetch(apiURL + "/" + this.id,{headers: { 'Authorization': this.$authKey}})
        .then(response => {
            return response.json();
        })
        .then(diagnosis => {
            this.diagnosis = diagnosis;
        })
    },
    methods: {
        editDiagnosis: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              alert("Error")
              return;
            }
            fetch(apiURL + "/" + this.id, {method: 'PUT',
                  headers: {'Content-Type': 'application/json',
                  'Authorization': this.$authKey},
                  body: JSON.stringify(this.diagnosis)})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't update diagnosis!" + response.status);
                else
                    this.$router.push({name :"diagnoses"});
            })
        }
    }
    
}
</script>

<style scoped>

</style>