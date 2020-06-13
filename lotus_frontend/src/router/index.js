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
import AddAppointmentType from "../components/AppointmentTypes/AddAppType"
import EditAppointmentType from "../components/AppointmentTypes/EditAppType"
import EditUser from "../components/Users/EditUser"
import AddUser from "../components/Users/AddUser"
import Rooms from "../components/Rooms/Rooms"
import EditRooms from "../components/Rooms/EditRoom"
import Clinics from "../components/Clinics/Clinics"
import AddClinic from "../components/Clinics/AddClinic"
import EditClinic from "../components/Clinics/EditClinic"
import AddRoom from "../components/Rooms/AddRoom"
import Medicines from "../components/Medicines/Medicines"
import AddMedicine from "../components/Medicines/AddMedicine"
import EditMedicine from "../components/Medicines/EditMedicine"
import HomePage from "../components/HomePage"
import AppointmentView from "../components/Patients/AppointmentView"
import PredefinedApps from "../components/Patients/PredefinedApps"
import AddPredefinedApps from "../components/Admins/AddPredefinedApps"
import ProfilePage from "../components/Users/ProfilePage"
import Calendar from "../components/Doctors/Calendar"
import MyClinic from "../components/Clinics/MyClinic"
import AppointmentQuery from "../components/Patients/AppointmentQuery"
import Diagnosis from "../components/Diagnosis/Diagnosis"
import AddDiagnosis from "../components/Diagnosis/AddDiagnosis"
import EditDiagnosis from "../components/Diagnosis/EditDiagnosis"
import Appointment from "../components/Doctors/Appointment"
import ScheduleAppointment from "../components/Doctors/ScheduleAppointment"
import RegistrationRequestView from "../components/RegistrationRequests/RegistrationRequests"
import SuccesfulRegistration from "../components/Patients/SuccesfulRegistration"
import PatientClinicView from "../components/Patients/ClinicsView"
import PatientDoctorView from "../components/Patients/DoctorsView"
import PreviousAppointments from "../components/Patients/PreviousAppointments"
import FreeRooms from "../components/Admins/FreeRooms"
import EditPassword from "../components/Users/EditPassword"
import AppointmentPrice from "../components/AppointmentTypes/AppointmentPrice"
import AddAppointmentPrice from "../components/AppointmentTypes/AddAppointmentPrice"
import EditAppointmentPrice from "../components/AppointmentTypes/EditAppointmentPrice"
import VacationRequest from "../components/Users/VacationRequest"
import ConfirmAppointment from "../components/Patients/ConfirmAppointment"
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
        path: "/profile/change_info",
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
        path: "/appointment_types"
    },
    {
        component: AddAppointmentType,
        name: "addAppointmentType",
        path: "/appointment_types/add"
    },
    {
        component: EditAppointmentType,
        name: "editAppointmentType",
        path: "/appointment_types/edit/:id",
        props: true
    },
    {
        component: AppointmentPrice,
        name: "appointmentPrices",
        path: "/appointment_prices"
    },
    {
        component: AddAppointmentPrice,
        name: "addAppointmentPrice",
        path: "/appointment_prices/add"
    },
    {
        component: EditAppointmentPrice,
        name: "editAppointmentPrice",
        path: "/appointment_prices/edit/:id",
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
        path: "/registration_requests"
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
        component: EditPassword,
        name: "editPassword",
        path: "/profile/change_password",
        props: true
    },
    {
        component: ScheduleAppointment,
        name: "scheduleAppointment",
        path: "/appointment/new_appointment",
        props: true
    },
    {
        component: VacationRequest,
        name: "vacationRequest",
        path: "/vacation_request"
    },
    {
        component: ConfirmAppointment,
        name: "confirmAppointment",
        path: "/confirm/:key",
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