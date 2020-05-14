<template>
  <div class="text-center">
    <v-overlay :value="overlay">
        <v-btn
        icon
        @click="turnOff()"
        ><v-icon>mdi-close</v-icon></v-btn>
        <h1> APPOINTMENTS </h1>
    </v-overlay>
  </div>
</template>

<script>
export default {
    name: "MROverlay",
    props: {
        id: {
            type: String,
        },
        overlay: {
            type: Boolean
        }
    },
    data() {
        return {
            appointments: [],
        }
    },
    methods: {
        turnOff() {
            //this.$overlay = false;
            this.$emit('update:overlay', false)
        }
    }
    
}
</script>

<style scoped>

</style>