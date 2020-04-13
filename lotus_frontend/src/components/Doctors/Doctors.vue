<template>
  <div>
    <h1>List of doctors</h1>
      <table v-if="doctors.length" border="2" id="doctors">
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
        <tr v-for="(d, k) in doctors" :key="k">
          <td>{{d.email}}</td>
          <td>{{d.password}}</td>
          <td>{{d.name}}</td>
          <td>{{d.surname}}</td>
          <td>{{d.address}}</td>
          <td>{{d.city}}</td>
          <td>{{d.country}}</td>
          <td>{{d.phoneNumber}}</td>
          <td>{{d.id}}</td>
          <td><button v-on:click="editDoctor(d.id)">Edit</button></td>
          <td><button v-on:click="deleteDoctor(d.id)">Delete</button></td>
        </tr>
      </table>
      <h3 v-else>No doctors in the database!</h3>
      <h1>Add doctor</h1>
        <form id="addDoctor" v-on:submit.prevent="addDoctor()">
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
              <td>Birth date:</td>
              <td><input type="date" name="birthDate" /></td>
            </tr>
            <tr>
              <td>Gender</td>
              <td>
                <select name="gender">
                  <option value="1">Male</option>
                  <option value="0">Female</option>
                  <option value="2">Other</option>
                </select>
              </td>
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
              <td><input type="text" name="phoneNumber" /></td>
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
const apiURL = "http://localhost:9001/api/doctors";
export default {
    name: "Doctors",
  data() {
    return {
      doctors: []
    }
  },
  mounted() {
    fetch(apiURL)
      .then(response => {
        return response.json();
      })
      .then(docs => {
        this.doctors = docs;
      })
  },
  methods: {
    addDoctor: function() {
      var form = document.forms['addDoctor'];
      
      var patient = {
          email : form.email.value,
          password : form.password.value,
          name : form.name.value,
          surname : form.surname.value,
          birthDate : form.birthDate.value,
          gender : form.gender.value,
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
            alert("Couldn't add doctor!");
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
            alert("Couldn't delete doctor!");
          else
            return response.json();
        })
        .then(pats => {
          this.patients = pats;
        })
    },
    editPatient: function(editId) {
      this.$router.push({ name: "editDoctor", params: {id: editId}})
    }
  }
}
    
</script>


<style scoped>

</style>