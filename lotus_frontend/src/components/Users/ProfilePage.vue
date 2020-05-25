<template>
    <div>
        <v-container fluid>
            <v-dialog max-width="1200" v-model="recordOverlay" @click:outside="closeRecord()">
                <MedicalRecord :id="this.id.toString()" :edit="false"/>
            </v-dialog>
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
                                    readonly />
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Name"
                                    v-model="user.name" 
                                    readonly />
                                </v-col>
                                <v-col>
                                    <v-text-field
                                    label="Surname"
                                    v-model="user.surname" 
                                    readonly />                      
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-select
                                    v-model="selectedGender"
                                    :items="genders"
                                    label="Gender" 
                                    readonly />        
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
                                    readonly />
                                </v-col>
                                <v-col>     
                                    <v-text-field
                                    label="City"
                                    v-model="user.city" 
                                    readonly />           
                                </v-col>
                                <v-col>     
                                    <v-text-field
                                    label="Country"
                                    v-model="user.country" 
                                    readonly />         
                                </v-col>
                                </v-row>
                                <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Phone number"
                                    v-model="user.phoneNumber" 
                                    readonly />
                                </v-col>
                                <v-col>     
                                    <v-text-field
                                    label="Insurance id"
                                    v-model="user.ssid" 
                                    readonly />
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
                                        <v-btn block @click="editUser()" color="indigo" dark height=60>Change information</v-btn>
                                    </v-col>
                                    <v-col>
                                        <v-btn block @click="changePassword()" color="indigo" dark height=60 >Change password</v-btn>
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
import MedicalRecord from '../MedicalRecord/MedicalRecord'

const apiURL = "/api";
export default {
    data() {
        return {
            user: {},
            record: {},
            dateString: "",
            genders: ['Male', 'Female', 'Other'],
            selectedGender: 'Other',
            recordOverlay: false,
            id: ''
        };
    },
    components: {
        MedicalRecord
    },
    mounted() {
        var user = ""
        if (this.$role == "PATIENT")
            user = "patients"
        if (this.$role == "DOCTOR")
            user = "doctors"
        if (this.$role == "NURSE")
            user = "nurses" 
        if (this.$role == "ADMIN")
            user = "admins"
        if (this.$role == "CLINIC_ADMIN")
            user = "cadmins"
        this.axios({url : apiURL + "/" + user +"/self", 
                    method: 'GET'
        }).then(res =>   {
            var user = res.data;
            if (this.$role == "PATIENT") {
                this.user = user.patient;
                this.id = user.patient.id;
                this.record = user.record;
                this.dateString = util.dateToString(user.patient.birthDate);
                this.selectedGender = user.patient.gender.charAt(0) + user.patient.gender.slice(1).toLowerCase();
            } else {
                this.user= user;
                this.dateString = util.dateToString(user.birthDate);
                this.selectedGender = user.gender.charAt(0) + user.gender.slice(1).toLowerCase();
            }
        }).catch(error => {
            console.log(error.request);
            this.$store.commit('showSnackbar', {text: "Couldn't fetch user!", color: "error", })
        });
    },
    methods: {
        editUser: function() {
            var user = ""
            if (this.$role == "PATIENT")
                user = "patients"
            if (this.$role == "DOCTOR")
                user = "doctors"
            if (this.$role == "NURSE")
                user = "nurses"
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
            this.$router.push({name: "editPassword"});
        },
        viewRecord: function() {
            this.recordOverlay = true;
        },
        closeRecord: function() {
            this.recordOverlay = false;
        }
    }
}
</script>



<style scoped>

</style>