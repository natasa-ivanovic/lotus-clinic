<template>
    <div>
        <v-data-table
            :headers="headers"
            :items="users"
            item-key="email"
            class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>List of {{userType}}</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn :to="{ name: 'addUser', params: {userType: userType} }">Add new {{userType}}</v-btn>
                </v-toolbar>
            </template>
            <template v-slot:item.edit="{ item }">
                <v-icon
                    @click="editUser(item.id)"
                >
                    mdi-pencil
                </v-icon>
            </template>
            <template v-slot:item.delete="{ item }">
                <v-icon
                    @click="deleteUser(item.id)"
                >
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/";

export default {
    name: "ViewTable",
    data() {
        return {
            users: [],
            headers: [
                {text: 'Email', value: 'email'},
                {text: 'Password', value: 'password'},
                {text: 'Name', value: 'name'},
                {text: 'Surname', value: 'surname'},
                {text: 'Address', value: 'address'},
                {text: 'City', value: 'city'},
                {text: 'Country', value: 'country'},
                {text: 'Phone number', value: 'phoneNumber'},
                {text: 'Insurance ID', value: 'id'},
                {text: 'Edit', value: 'edit', sortable: false },
                {text: 'Delete', value: 'delete', sortable: false },
            ]
        }
    },
    props: {
        userType:  {
            type: String
        }
    },    
    mounted() {
        fetch(apiURL + this.userType)
        .then(response => {
            return response.json();
        })
        .then(users => {
            this.users = users;
        })
    },
    methods: {
        deleteUser: function(id) {
            fetch(apiURL + this.userType + '/' + id, {method: 'DELETE'})
                .then(response => {
                    if (response.status != 200)
                        alert("Couldn't delete " + this.userType.substring(0, this.userType.length -1) + "!");
                    else
                        return response.json();
                })
                .then(user => {
                    this.users.pop(user);
                })
        },
        editUser: function(id) {
            this.$router.push({ name: "editUser", params: {id: id, userType: this.userType}})
            },
        addUser: function() {
          this.$router.push({ name: "addUser", params: {userType: this.userType} })
        }
    }
}
</script>

<style scoped>

</style>