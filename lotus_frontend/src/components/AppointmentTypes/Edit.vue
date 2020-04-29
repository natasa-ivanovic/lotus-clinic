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
              
                <v-form>
                  <v-text-field
                    label="ID"
                    v-model="appType.id"
                    readonly
                    filled
                    outlined />
                  <v-text-field
                    label="Name"
                    :rules="[rules.name, rules.required]"
                    v-model="appType.name"
                    outlined />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-on:click="editAppType()" color="primary" block>Edit
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
    
</template>

<script>
const apiURL = "http://localhost:9001/api/appointmentTypes"
export default {
    name: "editAppointmentType",
    props: ['id'],
    data() {
        return {
            appType: {},
            rules: {
                required: value => !!value || 'Field is required.'
            }
        }
    },
    mounted() {
        fetch(apiURL + "/" + this.id)
        .then(response => {
            return response.json();
        })
        .then(appType => {
            this.appType = appType;
        })
    },
    methods: {
        editAppType: function() {
            fetch(apiURL + "/" + this.id, {method: 'PUT',
                  headers: {'Content-Type': 'application/json'},
                  body: JSON.stringify(this.appType)})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't update appointment type!");
                else
                    this.$router.push({name :"appointmentTypes"});
            })
        }
    }
    
}
</script>

<style scoped>

</style>