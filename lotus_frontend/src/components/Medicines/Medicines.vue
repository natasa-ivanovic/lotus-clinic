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
                    @click="deleteMedicine(item.id)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/medicines/";
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
        fetch(apiURL, {headers: { 'Authorization': this.$authKey}})
            .then(response => {
                return response.json();
            })
            .then(c => {
                this.medicines = c;
            })
    },
    methods: {
        deleteMedicine: function(id){
            fetch(apiURL + id, {method: 'DELETE',
            headers: {'Authorization': this.$authKey}})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't delete room!")
                else
                    return response.json();
            })
            .then(r => {
                for(var i = 0; i < this.medicines.length; i++)
                {
                    if(this.medicines[i].id === r.id)
                    {
                        this.medicines.splice(i,1);
                    }
                }
            })
        },
        editMedicine: function(editId) {
            this.$router.push({name: "editMedicine", params: {id: editId}});
        }
    }
}
</script>

<style scoped>
</style>
