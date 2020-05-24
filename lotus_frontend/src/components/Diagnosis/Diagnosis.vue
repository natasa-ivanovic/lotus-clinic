<template>
    <div>
        <v-data-table
          :headers="headers"
          :items="diagnoses"
          item-key="id"
          class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>List of diagnoses</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn :to="{ name: 'addDiagnosis' }">Add new diagnosis</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.edit="{ item }">
                <v-icon
                    @click="editDiagnosis(item.id)"
                >
                    mdi-pencil
                </v-icon>
            </template>
            <template v-slot:item.delete="{ item }">
                <v-icon
                    @click="deleteDiagnosis(item)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "/api/diagnoses";
export default {
    name: "diagnoses",
    data() {
        return {
            diagnoses: [],
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
            this.diagnoses = response;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
    },
    methods: {
         deleteDiagnosis: function(diag){
            this.axios({url : apiURL + "/" + diag.id, 
                        method: 'DELETE'
            }).then(response =>   {
                // test this
                console.log(response);
                this.diagnoses.pop(diag);
                this.$store.commit('showSnackbar', {text: "Successfully deleted diagnosis.", color: "success", })
            }).catch(error => {
                console.log(error.request);
                // navesti razlog errora
                this.$store.commit('showSnackbar', {text: "Couldn't delete diagnosis!", color: "error", })
            });
        },
        editDiagnosis: function(editId) {
            this.$router.push({name: "editDiagnosis", params: {id: editId}});
        }
    }
}
</script>

<style scoped>
</style>
