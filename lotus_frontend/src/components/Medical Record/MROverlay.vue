<template>
  <div class="text-center">
    <v-dialog v-model="overlay" @click:outside="turnOff()">
        <v-container>
        <v-row align="center" justify="center">
        <v-card>
        <v-card-title>
        {{this.title}}
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
        </v-card-title>
        <v-data-table
        :headers="this.headers"
        :items="this.items"
        :search="search"
        ></v-data-table>
    </v-card>
    </v-row>
    </v-container>
    </v-dialog>
  </div>
</template>

<script>
export default {
    name: "MROverlay",
    props: {
        overlay: {
            type: Boolean
        },
        headers: {
            type: Array
        },
        items: {
            type: Array
        },
        title: {
            type: String
        }
    },
    data() {
        return {
            search: ''
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