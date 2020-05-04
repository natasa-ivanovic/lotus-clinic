<template>
  <v-content>
    <v-container class="fill-height"
     fluid>
     <v-row align="center" justify="center">
       <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-toolbar color="secondary" dark flat>
              <v-toolbar-title>Login</v-toolbar-title>
            </v-toolbar>
            <v-card-text >
              <v-form v-model="valid" ref="form">
                <v-text-field
                  v-model="user.username"
                  :rules="[rules.email, rules.required]"
                  label="Username"
                  prepend-icon="mdi-account"
                  type="text" />
                <v-text-field
                  v-model="user.password"
                  :rules="[rules.required]"
                  label="Password"
                  prepend-icon="mdi-lock"
                  type="password" />
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-row>
                <v-col>
                  <v-btn color="success" @click="login()" block height=55>Login</v-btn>
                </v-col>
              </v-row>
              </v-card-actions>
              <v-card-actions>
              <v-row>
                <v-col>
                  <v-alert v-model="error" type="error" dismissible>Authentification error</v-alert>
                </v-col>
              </v-row>
            </v-card-actions>
          </v-card>
       </v-col>
     </v-row>
    </v-container>
  </v-content>
</template>

<script>
const apiURL = "http://localhost:9001/auth/login";

export default {
  name: "login",
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      rules: {
        required: value => !!value || 'Field is required.',
        email: value => {
              const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
              return pattern.test(value) || 'Invalid e-mail.'
        }
      },
      valid: true,
      error: false
    }
  },
  methods: {
    login: function() {
      this.$refs.form.validate();
      if (!this.valid)
        return;
      fetch(apiURL, {method: 'POST', 
                      headers: {'Content-Type': 'application/json'}, 
                      body: JSON.stringify(this.user)})
        .then(response => {
            if(response.status == 200)
              return response.json();
            else {
              this.error = true;
              return false;
            }
        })
        .then(data => {
          if (data) {

            this.$authKey = "Bearer " + data.accessToken;
            this.$role = data.userRole;
            localStorage.setItem('authKey', "Bearer " + data.accessToken);
            localStorage.setItem('role', data.userRole);
            this.$router.push({ name: "home" })
          }
        })
    }
  }
    
}
</script>

<style scoped>

</style>