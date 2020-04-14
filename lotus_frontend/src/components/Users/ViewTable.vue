// <template>
//     <div>
//     <h1>List of {{type}}</h1>
//       <table v-if="doctors.length" border="2" id="doctors">
//         <tr>
//           <th>Email</th>
//           <th>Password</th>
//           <th>Name</th>
//           <th>Surname</th>
//           <th>Address</th>
//           <th>City</th>
//           <th>Country</th>
//           <th>Phone</th>
//           <th>Id</th>
//           <th>Edit</th>
//           <th>Delete</th>
//         </tr>
//         <tr v-for="(d, k) in doctors" :key="k">
//           <td>{{d.email}}</td>
//           <td>{{d.password}}</td>
//           <td>{{d.name}}</td>
//           <td>{{d.surname}}</td>
//           <td>{{d.address}}</td>
//           <td>{{d.city}}</td>
//           <td>{{d.country}}</td>
//           <td>{{d.phoneNumber}}</td>
//           <td>{{d.id}}</td>
//           <td><button v-on:click="editDoctor(d.id)">Edit</button></td>
//           <td><button v-on:click="deleteDoctor(d.id)">Delete</button></td>
//         </tr>
//       </table>
//       <h3 v-else>No {{type}} in the database!</h3>
// </template>

// <script>
// export default {
    
// }
// </script>

// <style scoped>

// </style>