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
import EditUser from "../components/Users/EditUser"
import AddUser from "../components/Users/AddUser"
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
import MyClinic from "../components/Clinics/MyClinic"
import AppointmentQuery from "../components/Patients/AppointmentQuery"
import Diagnosis from "../components/Diagnosis/Diagnosis"
import AddDiagnosis from "../components/Diagnosis/Add"
import EditDiagnosis from "../components/Diagnosis/Edit"
import Appointment from "../components/Doctors/Appointment"
import RegistrationRequestView from "../components/RegistrationRequests/RegistrationRequests"
import SuccesfulRegistration from "../components/Patients/SuccesfulRegistration"
import PatientClinicView from "../components/Patients/ClinicsView"
import PatientDoctorView from "../components/Patients/DoctorsView"
import PreviousAppointments from "../components/Patients/PreviousAppointments"
import FreeRooms from "../components/Admins/FreeRooms"


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
        component: AppointmentQuery,
        name: "apppointmentQuery",
        path: "/new_appointment/request",
        props: true
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
        component: MyClinic,
        name: "my_clinic",
        path: "/my_clinic"
    },
    {
        component: Diagnosis,
        name: "diagnoses",
        path: "/diagnoses/"
    },
    {
        component: AddDiagnosis,
        name: "addDiagnosis",
        path: "/diagnoses/add"
    },
    {
        component: EditDiagnosis,
        name: "editDiagnosis",
        path: "/diagnoses/edit/:id",
        props: true
    },
    {
        component: Appointment,
        name: "startAppointment",
        path: "/doctors/start_appointment",
        props: true
    },
    {
        component: RegistrationRequestView,
        name: "registrationRequestView",
        path: "/registrationRequests"
    },
    {
        component: SuccesfulRegistration,
        name: "succesfulRegistration",
        path: "/registrations/:logkey",
        props: true
    },
    {
        component: PatientClinicView,
        name: "patientsClinics",
        path: "/new_appointment/clinics",
        props: true
    },
    {
        component: PatientDoctorView,
        name: "patientsDoctors",
        path: "/new_appointment/doctors",
        props: true
    },
    {
        component: PreviousAppointments,
        name: "pastAppointmentsView",
        path: "/my_appointments",
        props: true
    },
    {
        component: FreeRooms,
        name: "freeRooms",
        path: "/free_rooms",
        props: true
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