<template>
  <div>
    <v-container>
      <v-row align="center" justify="center">
        <v-card class="elevation-3">
          <v-toolbar flat color="white" >
            <v-toolbar-title>Add room</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form v-model="valid" ref="form">
              <v-text-field
                label="Name"
                :rules="[rules.required]"
                v-model="name" />
            </v-form>
            <v-card-actions>
              <v-btn block v-on:click="addRoom()" color="primary">Add</v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </v-row>
    </v-container>

  </div>
    
</template>

<script>
const apiURL = "http://localhost:9001/api/rooms"
export default {
  name: "addRoom",
  data() {
    return {
      name : '',
      rules: {
          required: value => !!value || 'Field is required.'
        },
      valid: true
    }
  },
  methods: {
    addRoom: function() {
      this.$refs.form.validate();
        if (!this.valid) {
          alert("Error")
          return;
      }
      fetch(apiURL, {method: 'POST', 
                        headers: {'Content-Type': 'application/json', 'Authorization': this.$authKey}, 
                        body: this.name})
          .then(response => {
              if (response.status != 200)
                alert("Couldn't add " + this.name + "!");
              else
                this.$router.push({ name: "rooms" });
          })
    }
  }
    
}
</script>

<style scoped>

</style>