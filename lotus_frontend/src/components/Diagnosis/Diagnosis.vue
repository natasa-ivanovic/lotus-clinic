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
                    @click="deleteDiagnosis(item.id)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/diagnoses";
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
        fetch(apiURL, {headers: { 'Authorization': this.$authKey}})
            .then(response => {
                return response.json();
            })
            .then(c => {
                this.diagnoses = c;
            })
    },
    methods: {
         deleteDiagnosis: function(id){
            fetch(apiURL + "/" + id, {method: 'DELETE',
            headers: {'Authorization': this.$authKey}})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't delete room!")
                else
                    return response.json();
            })
            .then(r => {
                for(var i = 0; i < this.diagnoses.length; i++)
                {
                    if(this.diagnoses[i].id === r.id)
                    {
                        this.diagnoses.splice(i,1);
                    }
                }
            })
        },
        editDiagnosis: function(editId) {
            this.$router.push({name: "editDiagnosis", params: {id: editId}});
        }
    }
}
</script>

<style scoped>
</style>
