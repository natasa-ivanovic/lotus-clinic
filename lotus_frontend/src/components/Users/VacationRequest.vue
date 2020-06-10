<template>
  <v-container fluid>
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
                  <v-dialog
                    ref="dialogDate"
                    v-model="date"
                    :return-value.sync="vacationTime.startDate"
                    persistent
                    width="290px"
                  >
                    <template v-slot:activator="{on}">
                      <v-text-field
                        label="Start date"
                        v-model="vacationTime.startDate"
                        readonly
                        prepend-icon="mdi-calendar"
                        v-on="on"
                      />
                    </template>
                    <v-date-picker v-model="vacationTime.startDate" :min="getToday()" scrollable>
                      <v-spacer></v-spacer>
                      <v-btn text color="primary" @click="date = false">Cancel</v-btn>
                      <v-btn text color="primary" @click="$refs.dialogDate.save(vacationTime.startDate)">OK</v-btn>
                    </v-date-picker>
                  </v-dialog>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-dialog
                    ref="dialogDate2"
                    v-model="date2"
                    :return-value.sync="vacationTime.endDate"
                    persistent
                    width="290px"
                  >
                    <template v-slot:activator="{on}">
                      <v-text-field
                        label="Start date"
                        v-model="vacationTime.endDate"
                        readonly
                        prepend-icon="mdi-calendar"
                        v-on="on"
                      />
                    </template>
                    <v-date-picker v-model="vacationTime.endDate" :min="getToday()" scrollable>
                      <v-spacer></v-spacer>
                      <v-btn text color="primary" @click="date2 = false">Cancel</v-btn>
                      <v-btn text color="primary" @click="$refs.dialogDate2.save(vacationTime.endDate)">OK</v-btn>
                    </v-date-picker>
                  </v-dialog>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn block v-on:click="sendRequest()" color="primary" height="60">Send</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
const apiURL = "/api/vacation";
export default {
  data() {
    return {
      valid: true,
      date: false,
      date2: false,
      rules: {
        required: value => !!value || "Field is required."
      },
      vacationTime: {
        startDate: "",
        endDate: ""
      }
    };
  },
  methods: {
    sendRequest: function() {
      this.$refs.form.validate();
      if (!this.valid) {
        this.$store.commit("showSnackbar", {
          text: "Please fill in all the required fields!",
          color: "error"
        });
        return;
      }
      this.axios({
        url: apiURL,
        method: "POST",
        data: this.vacationTime,
        headers: {
          "Content-Type": "application/json"
        }
      }).then(response => {
        console.log(response);
        this.$store.commit("showSnackbar", {
          text: "Successfully sent request.",
          color: "success"
        });
        this.$router.push({ name: "home" });
      });
    },
    getToday: function() {
      var today = new Date();
      return today.toISOString();
    }
  }
};
</script>