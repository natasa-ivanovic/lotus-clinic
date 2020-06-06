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
              <v-autocomplete 
              class="mt-0"
              v-model="typeElem"
              :items="this.typesNames"
              dense
              prepend-icon="mdi-pill"
              label="Appointment type">
              </v-autocomplete>
                <v-text-field
                label="Price"
                :rules="[rules.required, rules.isNumber]"
                v-model="appointmentPrice.price"
                required />
                <v-text-field
                label="Discount"
                :rules="[rules.required, rules.isNumber, rules.isBetween]"
                v-model="appointmentPrice.discount"
                required />
            </v-form>
            <v-card-actions>
              <v-btn block v-on:click="addAppPrice()" color="primary">Add</v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const apiURL = "/api/appointmentPrices";
const apiTypes = "/api/appointmentTypes";
export default {
    name: "addAppointmentPrice",
    data() {
      return {
        typeElem: {},
        types: [],
        typesNames: [],
        appointmentPrice: {
          name: '',
          price: '',
          discount: '',
          type_id: '',
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
      addAppPrice: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              return;
            }
            this.appointmentPrice.type_id = this.typeElem;
            this.axios({url : apiURL, 
                    method: 'POST',
                    data: this.appointmentPrice,
                }).then(response =>   {
                    console.log(response);
                    this.$store.commit('showSnackbar', {text: "Successfully added appointment type price.", color: "success", })
                    this.$router.push({ name: "appointmentPrices" });
                }).catch(error => {
                    console.log(error.request.responseText);
                    this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
                });
        }
    },
    mounted() {
        this.axios({url : apiTypes})
          .then(response => {
            var data = [];
            this.types = response.data;
            this.types.forEach(el => {
                var type = {
                    text: el.name,
                    value: el.id
                }
                data.push(type);
            });
            this.typesNames = data;
          }).catch(error => {
              console.log(error.request.responseText);
              this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
             
          });
    }
}
</script>

<style scoped>

</style>