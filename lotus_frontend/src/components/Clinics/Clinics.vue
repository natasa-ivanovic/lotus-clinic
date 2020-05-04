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
                    @click="deleteClinic(item.id)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/clinics";
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
        fetch(apiURL, {headers: { 'Authorization': this.$authKey}})
            .then(response => {
                return response.json();
            })
            .then(c => {
                this.clinics = c;
            })
    },
    methods: {
        editClinic: function(editId) {
            this.$router.push({name: "editClinic", params: {id: editId}});
        },
        deleteClinic: function(id) {
            fetch(apiURL + "/" + id, {method: 'DELETE',
            headers: {'Authorization': this.$authKey}})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't delete clinic!")
                else
                    return response.json();
            })
            .then(r => {
                for(var i = 0; i < this.clinics.length; i++)
                {
                    if(this.clinics[i].id === r.id)
                    {
                        this.clinics.splice(i,1);
                    }
                }
            })
        }
        
    }
}
</script>

<style scoped>
</style>
