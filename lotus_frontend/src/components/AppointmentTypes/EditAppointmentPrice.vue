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
                    :rules="[rules.required, rules.isNumber, rules.isBetween]"
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
const apiURL = "/api/appointmentPrices"
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
        this.axios({url: apiURL + "/" + this.id})
          .then(response => {
            this.appPrice = response.data;
          }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch appointment type!", color: "error", })
          });
    },
    methods: {
        editAppPrice: function() {
            if (!this.valid) {
              this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
              return;
            }
            this.axios({url : apiURL + "/" + this.id, 
                        method: 'PUT',
                        data: this.appPrice
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully updated appointment price.", color: "success", })
                this.$router.push({name :"appointmentPrices"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update appointment type!", color: "error", })
            });
        }
    }
    
}
</script>

<style scoped>

</style>