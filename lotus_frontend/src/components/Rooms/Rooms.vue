<template>
    <div>
        <h1>List of rooms</h1>
      <table v-if="rooms.length" border="2" id="rooms">
        <tr>
          <th>Id</th>
          <th>Name</th>
        </tr>
        <tr v-for="(r, k) in rooms" :key="k">
          <td>{{r.id}}</td>
          <td>{{r.name}}</td>
          <td><button v-on:click="editRoom(r.id)">Edit</button></td>
          <td><button v-on:click="deleteRoom(r.id)">Delete</button></td>
        </tr>
      </table>
      <h3 v-else>No rooms in the database!</h3>
      <h1>Add room</h1>
        <form id="addRoom" v-on:submit.prevent="addRoom()">
          <table>
            <tr>
              <td>Id:</td>
              <td><input type="number" name="id" /></td>
            </tr>            
            <tr>
              <td>Name:</td>
              <td><input type="text" name="name" /></td>
            </tr>
            <tr>
              <td></td>
              <td><input type="submit" /></td>
            </tr>		
          </table>
        </form>
    </div>
</template>

<script>
const apiURL = "http://localhost:9001/api/rooms/";
export default {
    name: "rooms",
  data() {
    return {
      rooms: []
    }
  },
  mounted() {
    fetch(apiURL)
      .then(response => {
        return response.json();
      })
      .then(r => {
        this.rooms = r;
      })
  },
  methods: {
    addRoom: function() {
      var form = document.forms['addRoom'];
      
      var room = {
          name : form.name.value,
          id : form.id.value
      };
      fetch(apiURL, {method: 'POST', 
                      headers: {'Content-Type': 'application/json'}, 
                      body: JSON.stringify(room)})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't add room!");
          else
            return response.json();
        })
        .then(r => {
          this.rooms.push(r);
        })
    },
    deleteRoom: function(id) {
      fetch(apiURL + id, {method: 'DELETE'})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't delete room!");
          else
            return response.json();
        })
        .then(r => {
          this.rooms.pop(r);
        })
    },
    editRoom: function(editId) {
      alert(editId);
      this.$router.push({name: "editRoom", params: {id : editId}});
    }
  }
    
}
</script>

<style scoped>

</style>