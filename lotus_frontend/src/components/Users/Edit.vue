<template>
  <div>
    <h1>Edit patient</h1>
    <form id="editUser" v-on:submit.prevent="editUser()">
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
              <option value="male">Male</option>
              <option value="female">Female</option>
              <option value="other">Other</option>
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
import {util} from '../../util.js'

const apiURL = "http://localhost:9001/api/";

export default {
  name: "EditUser",
  props: ['id', 'role'],
  data() {
    return {
    }
  },
  mounted() {
    fetch(apiURL + "/" + this.role + "/" + this.id)
      .then(response => {
        return response.json();
      })
      .then(user => {
        var form = document.forms['editUser'];
        form.email.value = user.email;
        form.password.value = user.password;
        form.name.value = user.name;
        form.surname.value = user.surname;
        //alert(dateMixin.dateToString(user.birthDate));
        //form.birthDate.value = Vue.dateToString(user.birthDate);
        form.birthDate.value = util.dateToString(user.birthDate);
        form.gender.value = user.gender;
        form.address.value = user.address;
        form.city.value = user.city;
        form.country.value = user.country;
        form.phoneNumber.value = user.phoneNumber;
        form.id.value = user.id;
      })
  },
  methods: {
      editUser: function() {
      var form = document.forms['editUser'];
      
      var user = {
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
      fetch(apiURL + "/" + this.role + "/" + this.id, {method: 'PUT', 
                      headers: {'Content-Type': 'application/json'}, 
                      body: JSON.stringify(user)})
        .then(response => {
          if (response.status != 200)
            alert("Couldn't update profile!");
          else
            this.$router.push({ name: this.role })
        })
    }
  }
}
</script>

<style scoped>

</style>