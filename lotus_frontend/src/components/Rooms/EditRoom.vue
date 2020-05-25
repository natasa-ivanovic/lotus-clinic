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
                  <v-toolbar-title >Edit room</v-toolbar-title>
                </v-toolbar>
              
                <v-form v-model="valid" ref="form">
                  <v-text-field
                    label="ID"
                    v-model="room.id"
                    readonly
                    filled
                    outlined />
                  <v-text-field
                    label="Name"
                    :rules="[rules.required]"
                    v-model="room.name"
                    outlined />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn v-on:click="editRoom()" color="primary" block>Edit
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
  </div>
</template>

<script>

const apiURL = "/api/rooms";

export default {
    name: "editRoom",
    props: ['id'],
    data() {
        return {
          room: {},
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
            this.room = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch diagnosis!", color: "error", })
        });
    },
    methods: {
        editRoom: function() {
            this.$refs.form.validate();
            if (!this.valid) {
              alert("Error")
              return;
            }
            this.axios({url : apiURL + "/" + this.id, 
                        method: 'PUT',
                        data: this.room
            }).then(response =>   {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully updated room.", color: "success", })
                this.$router.push({name :"rooms"});
            }).catch(error => {
                console.log(error.request.responseText);
                // tip errora
                this.$store.commit('showSnackbar', {text: "Couldn't update room!", color: "error", })
            });
        }
    }
    
}
</script>

<style scoped>

</style>