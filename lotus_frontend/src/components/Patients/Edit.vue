<template>
  <div>
    <h1>Edit patient</h1>
    <form id="editPatient" v-on:submit.prevent="editPatient()">
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
const apiURL = "http://localhost:8090/api/patients";

export default {
  name: "EditPatient",
  props: ['id'],
  data() {
    return {
    }
  },
  mounted() {
    fetch(apiURL + "/" + this.id)
      .then(response => {
        return response.json();
      })
      .then(pat => {
        var form = document.forms['editPatient'];
        form.email.value = pat.email;
        form.password.value = pat.password;
        form.name.value = pat.name;
        form.surname.value = pat.surname;
        form.address.value = pat.address;
        form.city.value = pat.city;
        form.country.value = pat.country;
        form.phone.value = pat.phone;
        form.id.value = pat.id;
      })
  },
  methods: {
      editPatient: function() {
      var form = document.forms['editPatient'];
      
      var patient = {
          email : form.email.value,
          password : form.password.value,
          name : form.name.value,
          surname : form.surname.value,
          address : form.address.value,
          city : form.city.value,
          country : form.country.value,
          phone : form.phone.value,
          id : form.id.value
      };
      fetch(apiURL + "/" + this.id, {method: 'PUT', 
                      headers: {'Content-Type': 'application/json'}, 
                      body: JSON.stringify(patient)})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't edit patient!");
          else
            this.$router.push({ name: "patients"})
        })
    },
  }
}
</script>

<style scoped>

</style>