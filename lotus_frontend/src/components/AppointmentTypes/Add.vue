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
                :rules="[rules.required, rules.isNumber]"
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
const apiURL = "http://localhost:9001/api/appointmentTypes";
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
          required: value => !!value || 'Field is required.',
          isNumber: value => !isNaN(value) || 'Price must be a number.'
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
            fetch(apiURL, {method: 'POST', 
                  headers: {'Content-Type': 'application/json',
                            'Authorization': this.$authKey },
                  body: JSON.stringify(this.appointmentType)})
            .then(response => {
              if (response.status != 200)
                alert("Couldn't add " + this.appointmentType.name + "!");
              else
                this.$router.push({ name: "appointmentTypes" });
          })
        }
    }
}
</script>

<style scoped>

</style>