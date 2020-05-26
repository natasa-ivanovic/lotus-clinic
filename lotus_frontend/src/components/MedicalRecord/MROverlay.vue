<template>
    <v-dialog max-width="1200" v-model="overlay" @click:outside="turnOff()">
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
            <v-card-text>
                <v-data-table
                :headers="this.headers"
                :items="this.items"
                :search="search"
                ></v-data-table>
            </v-card-text>
        </v-card>
    </v-dialog>
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
            this.$emit('update:overlay', false)
        }
    }
    
}
</script>