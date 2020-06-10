<template>
    <v-container  fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
              <v-toolbar-title>Vacation request</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form v-model="valid" ref="form">
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="Start date"
                            v-model="vacationTime.startDate"
                            :rules="[rules.required]" />
                      </v-col>
                  </v-row>
                  <v-row>
                      <v-col>
                          <v-text-field
                            label="End date"
                            v-model="vacationTime.endDate" 
                            :rules="[rules.required]" />
                      </v-col>
                  </v-row>
              </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn block v-on:click="sendRequest()" color="primary" height=60>Send</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
const apiURL = "/api/vacation"
export default {
    data() {
        return {
            valid: true,
            rules: {
                required: value => !!value || 'Field is required.' 
            },
            vacationTime: {
                startDate: '',
                endDate: '',
            }
        }
    },
    methods: {
        sendRequest:  function() {
            this.$refs.form.validate();
            if(!this.valid) {
                this.$store.commit('showSnackbar', {text: "Please fill in all the required fields!", color: "error", })
                return;
            }
            this.axios({url : apiURL, 
                        method: 'POST',
                        data: this.vacationTime,
                        headers: {
                            'Content-Type': 'application/json'
                        }
            }).then(response => {
                console.log(response);
                this.$store.commit('showSnackbar', {text: "Successfully sent request.", color: "success", })
                this.$router.push({name: "home"})
            });
        }
    }
}
</script>