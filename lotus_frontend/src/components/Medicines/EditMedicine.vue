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
                  <v-toolbar-title >Edit Medicine</v-toolbar-title>
                </v-toolbar>
              
                <v-form v-model="valid" ref="form">
                  <v-text-field
                    label="Name"
                    :rules="[rules.required]"
                    v-model="medicine.name"
                    outlined />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-on:click="editMedicine()" color="primary" block>Edit
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
</template>

<script>

const apiURL = "/api/medicines";

export default {
    name: "editMedicine",
    props: ['id'],
    data() {
        return {
          medicine: {},
          rules: {
            required: value => !!value || 'Field is required.'
          },
          valid: true
        }
    },
    mounted() {
        this.axios({url : apiURL + "/" + this.id, 
                    method: 'GET'
        }).then(response =>   {
            this.medicine = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch clinic!", color: "error", })
        });
    },
    methods: {
        editMedicine: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              alert("Error")
              return;
            }
            this.axios({url : apiURL + "/" + this.id, 
                        method: 'PUT',
                        data: this.medicine
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully updated medicine.", color: "success", })
                this.$router.push({name :"medicines"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update medicine!", color: "error", })
            });
        }
    }
    
}
</script>