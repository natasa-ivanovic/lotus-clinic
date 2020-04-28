<template>
    <div>
        <v-data-table
          :headers="headers"
          :items="medicines"
          item-key="id"
          class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>List of medicines</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn :to="{ name: 'addMedicine' }">Add new medicine</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.edit="{ item }">
                <v-icon
                    @click="editRoom(item.id)"
                >
                    mdi-pencil
                </v-icon>
            </template>
            <template v-slot:item.delete="{ item }">
                <v-icon
                    @click="deleteRoom(item.id)"
                >
                    mdi-delete
                </v-icon>
            </template>
            <template v-slot:item.details="{ item }">
                <v-icon
                    @click="getDetails(item.id)"
                >
                    mdi-calendar
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/medicines";
export default {
    name: "medicines",
    data() {
        return {
            medicines: [],
            headers: [
                {text: 'ID', value: 'id'},
                {text: 'Name', value: 'name'},
                {text: 'Details', value: 'details'},
                {text: 'Edit', value: 'edit', sortable: false},
                {text: 'Delete', value: 'delete', sortable: false}
            ]
        }
    },
    mounted() {
        fetch(apiURL)
            .then(response => {
                return response.json();
            })
            .then(c => {
                this.medicines = c;
            })
    },
    methods: {
    }
}
</script>

<style scoped>
</style>
