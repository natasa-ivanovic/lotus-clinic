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
                    @click="editMedicine(item.id)"
                >
                    mdi-pencil
                </v-icon>
            </template>
            <template v-slot:item.delete="{ item }">
                <v-icon
                    @click="deleteMedicine(item)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "/api/medicines";
export default {
    name: "medicines",
    data() {
        return {
            medicines: [],
            headers: [
                {text: 'ID', value: 'id'},
                {text: 'Name', value: 'name'},
                {text: 'Edit', value: 'edit', sortable: false},
                {text: 'Delete', value: 'delete', sortable: false}
            ]
        }
    },
    mounted() {
        this.axios({url : apiURL, 
                    method: 'GET'
        }).then(response =>   {
            this.medicines = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
    },
    methods: {
        deleteMedicine: function(medicine){
            this.axios({url : apiURL + "/" + medicine.id, 
                        method: 'DELETE'
            }).then(response =>   {
                // test this
                console.log(response);
                this.medicines.splice(this.medicines.indexOf(medicine), 1);
                this.$store.commit('showSnackbar', {text: "Successfully deleted medicine.", color: "success", })
            }).catch(error => {
                console.log(error.request);
                // navesti razlog errora
                this.$store.commit('showSnackbar', {text: "Couldn't delete medicine!", color: "error", })
            });
        },
        editMedicine: function(editId) {
            this.$router.push({name: "editMedicine", params: {id: editId}});
        }
    }
}
</script>

<style scoped>
</style>
