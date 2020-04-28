import Vue from "vue"
import VueRouter from "vue-router"

Vue.use(VueRouter);

import Patients from "../components/Patients/Patients"
import Doctors from "../components/Doctors/Doctors"
import AppointmentTypes from "../components/AppointmentTypes/AppointmentTypes"
import EditUser from "../components/Users/Edit"
import AddUser from "../components/Users/Add"
import Rooms from "../components/Rooms/Rooms"
import EditRooms from "../components/Rooms/Edit"
import AddRoom from "../components/Rooms/Add"

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
        component: Rooms,
        name: "rooms",
        path: "/rooms/"
    },
    {
        component: AppointmentTypes,
        name: "appointmentTypes",
        path: "/appointmentTypes/"
    },
    {
        component: EditRooms,
        name: "editRoom",
        path: "/rooms/edit/:id",
        props: true
    },
    {
        component: AddRoom,
        name: "addRoom",
        path: "/rooms/add"
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