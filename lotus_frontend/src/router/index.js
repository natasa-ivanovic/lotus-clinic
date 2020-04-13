import Vue from "vue"
import VueRouter from "vue-router"

Vue.use(VueRouter);

import Patients from "../components/Patients/Patients"
import Doctors from "../components/Doctors/Doctors"
import EditPatient from "../components/Patients/Edit"
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
        component: EditPatient,
        name: "editPatient",
        path: "/patients/:id",
        props: true
    }
    
];

export default new VueRouter({
    mode: 'history',
    routes: routes
});