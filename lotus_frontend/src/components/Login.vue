<template>
  <v-content>
    <v-container class="fill-height"
     fluid>
     <v-row align="center" justify="center">
       <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>Login</v-toolbar-title>
            </v-toolbar>
            <v-card-text >
              <v-form v-model="valid" ref="form">
                <v-text-field
                  v-model="user.email"
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
              <v-spacer />
              <v-btn color="primary" @click="login()">Login</v-btn>
            </v-card-actions>
          </v-card>
       </v-col>
     </v-row>
    </v-container>
  </v-content>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      user: {
        email: "",
        password: ""
      },
      rules: {
        required: value => !!value || 'Field is required.',
        email: value => {
              const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
              return pattern.test(value) || 'Invalid e-mail.'
        }
      },
      valid: true
    }
  },
  methods: {
    login: function() {
      this.$refs.form.validate();
        if (!this.valid) {
          alert("Error")
          return;
        }
    }
  }
    
}
</script>

<style scoped>

</style>