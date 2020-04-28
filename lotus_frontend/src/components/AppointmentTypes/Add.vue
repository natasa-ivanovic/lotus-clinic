<template>
  <div>
    <v-container>
      <v-row align="center" justify="center">
        <v-card class="elevation-3">
          <v-toolbar flat color="white" >
            <v-toolbar-title>Add appointment type</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                label="Name"
                :rules="[rules.name, rules.required]"
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
const apiURL = "http://localhost:9001/api/appointmentTypes";
export default {
    name: "addAppointmentType",
    data() {
      return {
        name: '',
        rules: {
          required: value => !!value || 'Field is required.'
        }
      }
    },
    methods: {
      addAppType: function() {
            fetch(apiURL, {method: 'POST', 
                  headers: {'Content-Type': 'application/json'}, 
                  body: this.name})
            .then(response => {
              if (response.status != 200)
                alert("Couldn't add " + this.name + "!");
              else
                this.$router.push({ name: "appointmentTypes" });
          })
        }
    }
}
</script>

<style scoped>

</style>