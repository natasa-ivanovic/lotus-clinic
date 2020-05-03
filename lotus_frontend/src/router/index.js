import Vue from "vue"
import VueRouter from "vue-router"

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

Vue.use(VueRouter);

import Login from "../components/Login"
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
import EditClinic from "../components/Clinics/Edit"
import AddRoom from "../components/Rooms/Add"
import Medicines from "../components/Medicines/Medicines"
import AddMedicine from "../components/Medicines/Add"
import EditMedicine from "../components/Medicines/Edit"
import HomePage from "../components/HomePage"
import AppointmentView from "../components/Patients/AppointmentView"
import PredefinedApps from "../components/Patients/PredefinedApps"
import AddPredefinedApps from "../components/Admins/AddPredefinedApps"
import ProfilePage from "../components/Users/ProfilePage"
import Calendar from "../components/Doctors/Calendar"
import Register from "../components/Patients/Register"

const routes = [
    {
        component: Login,
        name: "login",
        path:"/login"
    },
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
        component: EditClinic,
        name: "editClinic",
        path: "/clinics/edit/:id",
        props: true
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
    },
    {
        component: Medicines,
        name: "medicines",
        path: "/medicines"
    },
    {
        component: AddMedicine,
        name: "addMedicine",
        path: "/medicines/add"
    },
    {
        component: EditMedicine,
        name: "editMedicine",
        path: "/medicines/edit/:id",
        props: true
    },
    {
        component: HomePage,
        name: "home",
        path: "/home"
    },
    {
        component: AppointmentView,
        name: "appointmentView",
        path: "/new_appointment"
    },
    {
        component: PredefinedApps,
        name: "predefinedApps",
        path: "/new_appointment/predefined"
    },
    {
        component: AddPredefinedApps,
        name: "appointments",
        path: "/appointments/add"
    },
    {
        component: ProfilePage,
        name: "profile",
        path: "/profile"
    },
    {
        component: Calendar,
        name: "calendar",
        path: "/users/doctors/calendar"
    },
    {
        component: Register,
        name: "register",
        path: "/register"
    },

    {
        path: "/*",
        redirect: "/home"
    }
];

export default new VueRouter({
    mode: 'history',
    routes: routes
});