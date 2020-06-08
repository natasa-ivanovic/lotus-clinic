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
                    <v-btn :to="{ name: 'addUser', params: {userType: userType, header: 'Add new ' + userType} }" v-if="userType != 'patients'">Add new {{userType}}</v-btn>
                </v-toolbar>
            </template>
            <!--template v-slot:item.edit="{ item }">
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
            </template-->
        </v-data-table>
    </div>
</template>

<script>
const apiURL = "/api";

export default {
    name: "ViewTable",
    data() {
        return {
            users: [],
            headers: [
                {text: 'Email', value: 'username'},
                {text: 'Insurance ID', value: 'ssid'},
                {text: 'Name', value: 'name'},
                {text: 'Surname', value: 'surname'},
                {text: 'Address', value: 'address'},
                {text: 'City', value: 'city'},
                {text: 'Country', value: 'country'},
                {text: 'Phone number', value: 'phoneNumber'},
            ]
        }
    },
    props: {
        userType:  {
            type: String
        }
    },    
    mounted() {
        this.axios({url : apiURL + "/" + this.userType, 
                    method: 'GET'
        }).then(response =>   {
            this.users = response.data;
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "An error has occurred! Please try again later.", color: "error", })
        });
    },
    methods: {
        /*deleteUser: function(id) {
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
            },*/
        addUser: function() {
          this.$router.push({ name: "addUser", params: {userType: this.userType} })
        }
    }
}
</script>