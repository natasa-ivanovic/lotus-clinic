import Vue from "vue"
import VueRouter from "vue-router"

Vue.use(VueRouter);

import Patients from "../components/Patients/Patients"
import Doctors from "../components/Doctors/Doctors"
import EditUsers from "../components/Users/Edit"
import Rooms from "../components/Rooms/Rooms"
import EditRooms from "../components/Rooms/Edit"
const routes = [
    {   
        component: Patients,
        name: "patients",
        path: "/patients/"
    },
    {
        component: Doctors,
        name: "doctors",
        path: "/doctors/"
    },
    {   
        component: EditUsers,
        name: "edit",
        path: "users/:role/:id",
        props: true
    },
    {
        component: Rooms,
        name: "rooms",
        path: "/rooms/"
    },
    {
        component: EditRooms,
        name: "editRoom",
        path: "/rooms/:id",
        props: true
    }
    
];

export default new VueRouter({
    mode: 'history',
    routes: routes
});