<template>
    <v-container  fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>Add Medicine</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form>
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="Name"
                            v-model="medicine.name" />
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
const apiURL = "http://localhost:9001/api/medicines";
export default {
    name: "AddMedicine",
    data() {
        return {
            medicine: {
                name: "",
            }
        }
    },
    methods: {
        addMedicine: function() {
            fetch(apiURL, {method: 'POST',
                    headers: {'Content-Type': 'application/json','Authorization': this.$authKey},
                    body: this.medicine.name})
                .then(response => {
                    if(response.status != 200)
                        alert("Couldn't add " + this.medicine.name + "!" + response.status)
                    else
                        this.$router.push({name: "medicines"})
                })
        }
    }
}
</script>

<style scoped>

</style>