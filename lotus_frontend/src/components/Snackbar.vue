<template>
    <v-snackbar
        top
        v-model="snackbar.visible"
        :multi-line="snackbar.multiline === true" 
        :color="snackbar.color" >
        {{ snackbar.text }}

        <v-btn
        dark
        text
        @click="closeSnackbar"
        >
            Close
        </v-btn>
    </v-snackbar>
</template>

<script>
import { mapMutations } from "vuex"
export default {
    computed: {
      snackbar() {
        return this.$store.state.snackbar
      }
    },
    methods: {
      ...mapMutations(["closeSnackbar"])
    }
    
}
</script>