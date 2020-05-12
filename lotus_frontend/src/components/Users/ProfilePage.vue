<template>
    <div>
        <v-overlay :value="overlay">
            <v-container fluid>
                <v-row align="center" justify="center">
                    <v-col>
                        <v-card class="elevation-3" light>
                                <v-toolbar flat color="secondary" dark>
                                    <v-toolbar-title>Medical record</v-toolbar-title>
                                </v-toolbar>
                                <v-card-text>
                                    <v-row>
                                        <v-col>
                                            <v-text-field
                                            label="Weight"
                                            v-model="record.weight" 
                                            readonly/>
                                        </v-col>
                                        <v-col>
                                            <v-text-field
                                            label="Height"
                                            v-model="record.height" 
                                            readonly/>
                                        </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col>
                                            <v-text-field
                                            label="Allergies"
                                            v-model="record.allergies" 
                                            readonly/>
                                        </v-col>
                                        <v-col>
                                            <v-text-field
                                            label="Blood type"
                                            v-model="record.bloodType" 
                                            readonly/>
                                        </v-col>
                                    </v-row>
                                </v-card-text>
                                <v-card-actions>
                                    <v-row>
                                        <v-col>
                                            <v-btn block @click="overlay=false" color="indigo" dark height=60>Close</v-btn>
                                        </v-col>
                                    </v-row>
                                </v-card-actions>
                            </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </v-overlay>
        <v-container fluid>
            <v-row align="center" justify="center">
                <v-col cols="6">
                    <v-card class="elevation-3" v-if="user != {}">
                            <v-toolbar flat color="secondary" dark>
                                <v-toolbar-title>User details</v-toolbar-title>
                            </v-toolbar>
                            <v-card-text>
                                <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Email"
                                    v-model="user.email" 
                                    readonly/>
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Name"
                                    v-model="user.name" 
                                    readonly/>
                                </v-col>
                                <v-col>
                                    <v-text-field
                                    label="Surname"
                                    v-model="user.surname" 
                                    readonly/>                      
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-select
                                    v-model="selectedGender"
                                    :items="genders"
                                    label="Gender" 
                                    readonly/>        
                                </v-col>
                                <v-col>
                                    <v-text-field
                                    v-model="dateString"
                                    label="Birth date"
                                    readonly
                                        />    
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Address"
                                    v-model="user.address" 
                                    readonly/>
                                </v-col>
                                <v-col>     
                                    <v-text-field
                                    label="City"
                                    v-model="user.city" 
                                    readonly/>           
                                </v-col>
                                <v-col>     
                                    <v-text-field
                                    label="Country"
                                    v-model="user.country" 
                                    readonly/>         
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Phone number"
                                    v-model="user.phoneNumber" 
                                    readonly/>
                                </v-col>
                                <v-col>     
                                    <v-text-field
                                    label="Insurance id"
                                    v-model="user.ssid" 
                                    readonly/>
                                </v-col>
                                </v-row>
                            </v-card-text>
                            <v-card-actions v-if="checkRole('PATIENT')">
                                <v-row>
                                    <v-col>
                                        <v-btn block @click="viewRecord()" color="indigo" dark height=60>View medical record</v-btn>
                                    </v-col>
                                </v-row>
                            </v-card-actions>
                            <v-card-actions>
                                <v-row>
                                    <v-col>
                                        <v-btn block @click="editPatient()" color="indigo" dark height=60>Change information</v-btn>
                                    </v-col>
                                    <v-col>
                                        <v-btn block @click="changePassword()" color="indigo" height=60 disabled>Change password</v-btn>
                                    </v-col>
                                </v-row>
                            </v-card-actions>
                        </v-card>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
import {util} from '../../util.js'
const apiURL = "http://localhost:9001/api";
export default {
    data() {
        return {
            user: {},
            record: {},
            dateString: "",
            genders: ['Male', 'Female', 'Other'],
            selectedGender: 'Other',
            overlay: false
        };
    },
    mounted() {
        var user = ""
        if (this.$role == "PATIENT")
            user = "patients"
        if (this.$role == "DOCTOR")
            user = "doctors"
        /* todo
        if (this.$role == "NURSE")
            user = "nurses" */
        if (this.$role == "ADMIN")
            user = "admins"
        if (this.$role == "CLINIC_ADMIN")
            user = "cadmins"
        fetch(apiURL + "/" + user +"/self", {headers: { 'Authorization': this.$authKey }})
        .then(response => {
            return response.json();
        })
        .then(user => {
            if (this.$role == "PATIENT") {
                this.user = user.patient;
                this.record = user.record;
                this.dateString = util.dateToString(user.patient.birthDate);
                this.selectedGender = user.patient.gender.charAt(0) + user.patient.gender.slice(1).toLowerCase();
            } else {
                this.user= user;
                this.dateString = util.dateToString(user.birthDate);
                this.selectedGender = user.gender.charAt(0) + user.gender.slice(1).toLowerCase();
            }
        })
    },
    methods: {
        editPatient: function() {
            var user = ""
            if (this.$role == "PATIENT")
                user = "patients"
            if (this.$role == "DOCTOR")
                user = "doctors"
            /* todo
            if (this.$role == "NURSE")
                user = "nurses"*/
            if (this.$role == "ADMIN")
                user = "admins"
            /*if (this.$role == "CLINIC_ADMIN")
                user = "cadmins" */
            this.$router.push({ name: "editUser", params: {id: "self", userType: user}})
        },
        checkRole: function(role) {
        if (this.$role == role)
            return true;
        },
        changePassword: function() {
            return;
        },
        viewRecord: function() {
            this.overlay = true;
        }
    }
}
</script>



<style scoped>

</style>