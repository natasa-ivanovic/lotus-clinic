<template>
    <div>
        <v-data-table
          :headers="headers"
          :items="clinics"
          item-key="id"
          class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>List of clinics</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn :to="{ name: 'addClinic' }">Add new clinic</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.edit="{ item }">
                <v-icon
                    @click="editClinic(item.id)"
                >
                    mdi-pencil
                </v-icon>
            </template>
            <template v-slot:item.delete="{ item }">
                <v-icon
                    @click="deleteClinic(item)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "/api/clinics";
export default {
    name: "clinics",
    data() {
        return {
            clinics: [],
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
            this.clinics = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
    },
    methods: {
        editClinic: function(editId) {
            this.$router.push({name: "editClinic", params: {id: editId}});
        },
        deleteClinic: function(clinic) {
            this.axios({url : apiURL + "/" + clinic.id, 
                        method: 'DELETE'
            }).then(response =>   {
                // test this
                console.log(response);
                this.clinics.splice(this.clinics.indexOf(clinic), 1);
                this.$store.commit('showSnackbar', {text: "Successfully deleted clinic.", color: "success", })
            }).catch(error => {
                console.log(error.request);
                // navesti razlog errora
                this.$store.commit('showSnackbar', {text: "Couldn't delete clinic!", color: "error", })
            });
        }
        
    }
}
</script>
