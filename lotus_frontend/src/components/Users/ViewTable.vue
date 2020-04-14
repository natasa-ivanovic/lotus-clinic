<template>
    <div>
    <h1>List of {{this.userType}}</h1>
      <table v-if="users.length" border="2" id="users">
        <tr>
          <th>Email</th>
          <th>Password</th>
          <th>Name</th>
          <th>Surname</th>
          <th>Address</th>
          <th>City</th>
          <th>Country</th>
          <th>Phone</th>
          <th>Id</th>
          <th>Edit</th>
          <th>Delete</th>
        </tr>
        <tr v-for="(u, k) in users" :key="k">
          <td>{{u.email}}</td>
          <td>{{u.password}}</td>
          <td>{{u.name}}</td>
          <td>{{u.surname}}</td>
          <td>{{u.address}}</td>
          <td>{{u.city}}</td>
          <td>{{u.country}}</td>
          <td>{{u.phoneNumber}}</td>
          <td>{{u.id}}</td>
          <td><button v-on:click="editUser(u.id)">Edit</button></td>
          <td><button v-on:click="deleteUser(u.id)">Delete</button></td>
        </tr>
      </table>
      <h3 v-else>No {{this.userType}} in the database!</h3>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/";

export default {
    name: "ViewTable",
    data() {
        return {
            users: []
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
        editUser: function(editId) {
            this.$router.push({ name: "editUser", params: {id: editId, userType: this.userType}})
            }
        }
    }
</script>

<style scoped>

</style>