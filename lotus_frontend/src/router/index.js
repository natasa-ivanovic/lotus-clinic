import Vue from "vue"
import VueRouter from "vue-router"

Vue.use(VueRouter);

import Patients from "../components/Patients/Patients"
import Doctors from "../components/Doctors/Doctors"
import EditUser from "../components/Users/Edit"
import AddUser from "../components/Users/Add"
const routes = [
    {   
        component: Patients,
        name: "patients",
        path: "/users/patients/"
    },
    {
        component: Doctors,
        name: "doctors",
        path: "/users/doctors/"
    },
    {   
        component: EditUser,
        name: "editUser",
        path: "/users/:userType/edit/:id",
        props: true
    },
    {
        component: AddUser,
        name: "addUser",
        path: "/users/:userType/add",
        props: true
    }
    
];

export default new VueRouter({
    mode: 'history',
    routes: routes
});