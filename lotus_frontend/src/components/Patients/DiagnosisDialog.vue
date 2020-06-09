<template>
    <v-row justify="center">
    <v-dialog
      v-model="dialog"
      max-width="350"
      @click:outside="closeOverlay();"
    >
      <v-card>
        <v-card-title class="headline">Your diagnosis</v-card-title>
        <v-card-text>
            <v-text-field outlined label="Disease name" :value="getDiagnosis(app)" readonly />
            <v-textarea outlined label="Information about the appointment" :value="app.description" readonly />
            <v-autocomplete outlined label="Recipes from this appointment" :items="app.recipes" />
        </v-card-text>

        <v-card-actions>
            <v-btn
                color="success"
                dark
                block
                @click="closeOverlay()"
                width=150
            >
                Return
            </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>

</template>

<script>
export default {
    methods: {
        closeOverlay: function() {
            this.$emit('update:dialog', false);
        },
        getDiagnosis: function(app) {
            var diagnosis = "";
            if (app.diagnosis != undefined) {
                    app.diagnosis.forEach(d => {
                        diagnosis = diagnosis + d + ", ";
                })
            }
            return diagnosis.substring(0, diagnosis.length - 2);
        }
    },
    props: {
        dialog: {
            type: Boolean,
            default: false
        },
        app: {
            type: Object,
            default: () => {}
        }
    }
}
</script>