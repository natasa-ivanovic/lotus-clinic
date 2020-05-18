<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      max-width="350"
      @click:outside="cancelRating();"
    >
      <v-card>
        <v-card-title class="headline">Rate our service</v-card-title>

        <v-card-text>
            We care about your experience here at Lotus Clinic.
            Please select the number of stars that correlate to how happy you were with your experience with our staff.
            <br/><br/>
            <div class="subtitle">
            How satisfied are you with the doctor that carried out the appointment?
            </div>
            <div class="text-center">
                <v-rating
                v-model="docRating"
                color="yellow darken-2"
                background-color="grey darken-1"
                half-icon="mdi-star-half-full"
                half-increments
                hover
                ></v-rating>
            </div>
            <div class="subtitle">
            How satisfied are you with the overall experience in the clinic?
            </div>
            <div class="text-center">
                <v-rating
                v-model="clinicRating"
                color="yellow darken-2"
                background-color="grey darken-1"
                half-icon="mdi-star-half-full"
                half-increments
                hover
                ></v-rating>
            </div>
        </v-card-text>

        <v-card-actions>
            <v-btn
                color="success"
                dark
                @click="sendRating()"
                width=150
            >
                Send rating
            </v-btn>
            <v-spacer />
            <v-btn
                color="red"
                dark
                @click="cancelRating()"
                width=150
            >
                Cancel
            </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
const apiURL = "http://localhost:9001/api/patients/rate";
export default {
    data() {
        return {
            docRating: 0,
            clinicRating: 0
        }
    },
    props: {
        dialog: {
            type: Boolean,
            default: false
        },
        app: {
            type: Number,
            default: 0
        }
    },
    methods: {
        cancelRating: function() {
            this.$emit('update:dialog', false);
        },
        sendRating: function() {
            if (this.app == 0) {
                alert("Nesto srsly ne valja");
                return;
            }
            if (this.docRating == 0 | this.clinicRating == 0 ) {
                alert("Niste popunili sve!");
                return;
            }
            var queryData = {
                doctorRating: this.docRating,
                clinicRating: this.clinicRating,
                appointmentId: this.app
            };
            this.axios({url : apiURL, 
                    method: 'POST',
                    data: queryData
                }).then(response =>   {
                    console.log(response);
                    this.$store.commit('showSnackbar', {text: "Successfully rated!", color: "success", })
                    this.$emit('rate-app', this.app);
                    this.$emit('update:dialog', false);
                }).catch(error => {
                    console.log(error);
                    this.$store.commit('showSnackbar', {text: "An error has occurred!", color: "error", })
                });
        },
        handleClick: function(event) {
            alert(event);
        }
    }
}
</script>

<style scoped>

</style>