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
                v-model="appointmentType.name"
                required />
                <v-text-field
                label="Price"
                :rules="[rules.required, rules.isNumber]"
                v-model="appointmentType.price"
                required />
                <v-text-field
                label="Discount"
                :rules="[rules.required, rules.isNumber, rules.isBetween]"
                v-model="appointmentType.discount"
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
        appointmentType: {
          name: '',
          price: '',
          discount: '',
        },
        rules: {
          required: value => !!value || value === 0 || 'Field is required.',
          isNumber: value => !isNaN(value) || 'Price must be a number.',
          isBetween: value => (parseInt(value) >= 0 && parseInt(value) <= 100) || 'Discount must be between 0 and 100'
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
                    data: this.appointmentType,
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
