import Vue from "vue"
import VueRouter from "vue-router"

Vue.use(VueRouter);

import Patients from "../components/Patients/Patients"
import Doctors from "../components/Doctors/Doctors"
import AppointmentTypes from "../components/AppointmentTypes/AppointmentTypes"
import AddAppointmentType from "../components/AppointmentTypes/Add"
import EditAppointmentType from "../components/AppointmentTypes/Edit"
import EditUser from "../components/Users/Edit"
import AddUser from "../components/Users/Add"
import Rooms from "../components/Rooms/Rooms"
import EditRooms from "../components/Rooms/Edit"
import Clinics from "../components/Clinics/Clinics"
import AddClinic from "../components/Clinics/Add"
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
    },
    {
        component: Clinics,
        name: "clinics",
        path: "/clinics/"
    },
    {
        component: AddClinic,
        name: "addClinic",
        path: "/clinics/add"
    },
    {
        component: AppointmentTypes,
        name: "appointmentTypes",
        path: "/appointmentTypes/"
    },
    {
        component: AddAppointmentType,
        name: "addAppointmentType",
        path: "/appointmentTypes/add"
    },
    {
        component: EditAppointmentType,
        name: "editAppointmentType",
        path: "/appointmentTypes/edit/:id",
        props: true
    }
    
];

export default new VueRouter({
    mode: 'history',
    routes: routes
});