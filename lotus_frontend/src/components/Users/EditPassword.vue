<template>
  <div>
    <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
              <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Change your password</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form v-model="valid" ref="form">
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="Old password"
                        :append-icon="showOldPass ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required, rules.counter]"
                        :type="showOldPass ? 'text' : 'password'"
                        v-model="oldPass" 
                        @click:append="showOldPass = !showOldPass"
                        required/>
                    </v-col>
                    <v-col></v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        label="New password"
                        :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required, rules.counter]"
                        :type="showPass ? 'text' : 'password'"
                        v-model="newPass" 
                        @click:append="showPass = !showPass" />
                    </v-col>
                    <v-col>
                      <v-text-field
                        label="Confirm password"
                        :append-icon="showConfirmPass ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="showConfirmPass ? 'text' : 'password'"
                        :rules="[rules.required, rules.counter, rules.passwordMatch]"
                        v-model="confirmPass" 
                        @click:append="showConfirmPass = !showConfirmPass" />
                    </v-col>
                  </v-row>
                  
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn block v-on:click="changePass()" color="success" height=60>Change password</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
  </div>
</template>

<script>
const passURL = "/auth/change-password";
const loginURL = "/auth/login";
export default {
  name: "EditUser",
  props: ['id', 'userType'],
  data() {
    return {
      oldPass: "",
      newPass: "",
      confirmPass: "",
      rules: {
          required: value => !!value || 'Field is required.',
          counter: value => value.length > 4 || 'Password must have a minimum of 5 characters',
          passwordMatch: () => this.newPass == this.confirmPass || 'Passwords must match.',
      },
      valid: true,
      showPass: false,
      showConfirmPass: false,
      showOldPass: false
    }
  },
  computed: {
    formIsValid () {
      return (
        this.user.email &&
        this.user.password &&
        this.confirmPass &&
        this.user.name &&
        this.user.surname &&
        this.user.gender &&
        this.user.dateString &&
        this.user.address &&
        this.user.city &&
        this.user.country &&
        this.user.phoneNumber &&
        this.user.ssid
      )
    }
  },
  mounted() {
  },
  methods: {
    changePass: function() {
      this.$refs.form.validate();
      if (!this.valid) {
        return;
      }
      var pwData = {
        oldPassword: this.oldPass,
        newPassword: this.newPass
      };
      this.axios({url : passURL, 
                  method: 'POST',
                  data: pwData
        }).then(response =>   {
          var user = { username: response.data, password: this.newPass};
          this.axios({url : loginURL, 
                  method: 'POST',
                  data: user                  
            }).then(resp => {
              localStorage.setItem('authKey', "Bearer " + resp.data.accessToken);
              this.axios.defaults.headers['Authorization'] = "Bearer " + resp.data.accessToken;
              this.$store.commit('showSnackbar', {text: "Successfully changed password!", color: "success", })
              if (this.$role == null) {
                  // when it's first time logging in and we change role
                  this.$role = resp.data.userRole;
                  localStorage.setItem('role', resp.data.userRole);
                  this.$router.push({ name: "home" });
                  return;
              }
              this.$router.push({ name: "profile" });
            });
          });
    }

  }
}
</script>