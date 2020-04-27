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
              
                <v-form>
                  <v-text-field
                    label="ID"
                    v-model="room.id"
                    readonly
                    filled
                    outlined />
                  <v-text-field
                    label="Name"
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

const apiURL = "http://localhost:9001/api/rooms";

export default {
    name: "editRoom",
    props: ['id'],
    data() {
        return {
          room: {}
        }
    },
    mounted() {
        fetch(apiURL + "/" + this.id)
        .then(response => {
            return response.json();
        })
        .then(room => {
            this.room = room;
        })
    },
    methods: {
        editRoom: function() {
            fetch(apiURL + "/" + this.id, {method: 'PUT',
                  headers: {'Content-Type': 'application/json'},
                  body: JSON.stringify(this.room)})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't update room!");
                else
                    this.$router.push({name :"rooms"});
            })
        }
    }
    
}
</script>

<style scoped>

</style>