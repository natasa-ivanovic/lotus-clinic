<template>
  <div>
    <h1>List of patients</h1>
      <table v-if="patients.length" border="2" id="patients">
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
        <tr v-for="(p, k) in patients" :key="k">
          <td>{{p.email}}</td>
          <td>{{p.password}}</td>
          <td>{{p.name}}</td>
          <td>{{p.surname}}</td>
          <td>{{p.address}}</td>
          <td>{{p.city}}</td>
          <td>{{p.country}}</td>
          <td>{{p.phoneNumber}}</td>
          <td>{{p.id}}</td>
          <td><button v-on:click="editPatient(p.id)">Edit</button></td>
          <td><button v-on:click="deletePatient(p.id)">Delete</button></td>
        </tr>
      </table>
      <h3 v-else>No patients in the database!</h3>
      <h1>Add patient</h1>
        <form id="addPatient" v-on:submit.prevent="addPatient()">
          <table>
            <tr>
              <td>Email:</td>
              <td><input type="text" name="email" /></td>
            </tr>            
            <tr>
              <td>Password:</td>
              <td><input type="password" name="password" /></td>
            </tr>
            <tr>
              <td>Name:</td>
              <td><input type="text" name="name" /></td>
            </tr>
            <tr>
              <td>Surname:</td>
              <td><input type="text" name="surname" /></td>
            </tr>
            <tr>
              <td>Address:</td>
              <td><input type="text" name="address" /></td>
            </tr>
            <tr>
              <td>City:</td>
              <td><input type="text" name="city" /></td>
            </tr>
            <tr>
              <td>Country:</td>
              <td><input type="text" name="country" /></td>
            </tr>
            <tr>
              <td>Phone:</td>
              <td><input type="text" name="phone" /></td>
            </tr>
            <tr>
              <td>Id:</td>
              <td><input type="number" name="id" /></td>
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
const apiURL = "http://localhost:9001/api/patients";

export default {
  name: "Patients",
  data() {
    return {
      patients: []
    }
  },
  mounted() {
    fetch(apiURL)
      .then(response => {
        return response.json();
      })
      .then(pats => {
        this.patients = pats;
      })
  },
  methods: {
    addPatient: function() {
      var form = document.forms['addPatient'];
      
      var patient = {
          email : form.email.value,
          password : form.password.value,
          name : form.name.value,
          surname : form.surname.value,
          birthDate : form.birthDate,
          address : form.address.value,
          city : form.city.value,
          country : form.country.value,
          phoneNumber : form.phoneNumber.value,
          id : form.id.value
      };
      fetch(apiURL, {method: 'POST', 
                      headers: {'Content-Type': 'application/json'}, 
                      body: JSON.stringify(patient)})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't add patient!");
          else
            return response.json();
        })
        .then(pats => {
          this.patients = pats;
        })
    },
    deletePatient: function(id) {
      fetch(apiURL + '/' + id, {method: 'DELETE'})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't delete patient!");
          else
            return response.json();
        })
        .then(pats => {
          this.patients = pats;
        })
    },
    editPatient: function(editId) {
      //alert(editId);
      this.$router.push({ name: "edit", params: {id: editId, role: "patients"}})
    }
  }
}
</script>

<style scoped>

</style>