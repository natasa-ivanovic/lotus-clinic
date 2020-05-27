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
                  <v-toolbar-title >Edit appointment type</v-toolbar-title>
                </v-toolbar>
              
                <v-form v-model="valid" ref="form">
                  <v-text-field
                    label="ID"
                    v-model="appPrice.id"
                    readonly
                    filled
                    outlined />
                  <v-text-field
                    label="Name"
                    :rules="[rules.required]"
                    v-model="appPrice.name"
                    readonly
                    filled
                    outlined />
                  <v-text-field
                    label="Price"
                    :rules="[rules.required, rules.isNumber]"
                    v-model="appPrice.price"
                    outlined />
                  <v-text-field
                    label="Discount"
                    :rules="[rules.required, rules.isNumber, rules/isBetween]"
                    v-model="appPrice.discount"
                    outlined />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-on:click="editAppPrice()" color="primary" block>Edit
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
    
</template>

<script>
const apiURL = "http://localhost:9001/api/appointmentPrices"
export default {
    name: "editAppointmentPrice",
    props: ['id'],
    data() {
        return {
            appPrice: {},
            rules: {
                required: value => !!value || value === 0 || 'Field is required.',
                isNumber: value => !isNaN(value) || 'Price must be a number.',
                isBetween: value => (parseInt(value) >= 0 && parseInt(value) <= 100) || 'Discount must be between 0 and 100'
            },
            valid: true
        }
    },
    mounted() {
        this.$refs.form.validate();
        if (!this.valid) {
          alert("Error")
          return;
        }
        fetch(apiURL + "/" + this.id, {headers: { 'Authorization': this.$authKey}})
        .then(response => {
            return response.json();
        })
        .then(appPrice => {
            this.appPrice = appPrice;
        })
    },
    methods: {
        editAppPrice: function() {
            fetch(apiURL + "/" + this.id, {method: 'PUT',
                  headers: {'Content-Type': 'application/json', 'Authorization': this.$authKey},
                  body: JSON.stringify(this.appPrice)})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't update appointment type!");
                else
                    this.$router.push({name :"appointmentPrices"});
            })
        }
    }
    
}
</script>

<style scoped>

</style>