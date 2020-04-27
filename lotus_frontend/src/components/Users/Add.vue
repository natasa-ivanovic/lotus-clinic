<template>
    <div>
        <h1>Add {{this.userType.substring(0, this.userType.length -1)}}</h1>
          <form id="addUser" v-on:submit.prevent="addUser()">
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
const apiURL = "http://localhost:9001/api/";
export default {
    name: "AddUser",
    props: {
        userType: {
            type: String
        }
    },
    methods: {
        addUser: function() {
            var form = document.forms['addUser'];
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
            
            fetch(apiURL + this.userTypes, {method: 'POST', 
                            headers: {'Content-Type': 'application/json'}, 
                            body: JSON.stringify(user)})
                .then(response => {
                    if (response.status != 200)
                    alert("Couldn't add " + this.userType.substring(0, this.userType.length -1) + "!");
                    else
                    return response.json();
                })
                .then(() => {
                    this.$router.push({ name: this.userType })
            })
      }
    }
    
}
</script>

<style scoped>

</style>