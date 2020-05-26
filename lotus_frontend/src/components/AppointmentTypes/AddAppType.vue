<template>
  <div>
    <v-container>
      <v-row align="center" justify="center">
        <v-card class="elevation-3">
          <v-toolbar flat color="white" >
            <v-toolbar-title>Add appointment type</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form v-model="valid" ref="form">
              <v-text-field
                label="Name"
                :rules="[rules.required]"
                v-model="name"
                required />
            </v-form>
            <v-card-actions>
              <v-btn block v-on:click="addAppType()" color="primary">Add</v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const apiURL = "/api/appointmentTypes";
export default {
    name: "addAppointmentType",
    data() {
      return {
        name: '',
        rules: {
          required: value => !!value || 'Field is required.'
        },
        valid: true
      }
    },
    methods: {
      addAppType: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              alert("Error")
              return;
            }
            this.axios({url : apiURL, 
                    method: 'POST',
                    data: this.name,
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                }).then(response =>   {
                    console.log(response);
                    this.$store.commit('showSnackbar', {text: "Successfully added appointment type.", color: "success", })
                    this.$router.push({ name: "appointmentTypes" });
                }).catch(error => {
                    console.log(error.request.responseText);
                    this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
                });
        }
    }
}
</script>
