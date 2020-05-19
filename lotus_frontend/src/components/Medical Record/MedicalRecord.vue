<template>
    <v-form>
        <Overlay v-bind:overlay.sync="overlay" :title="this.title" :headers="this.headerOverlay" :items="this.itemsOverlay"/>
        <v-card class="elevation-3">
            <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>Medical record - {{this.patient.name}} {{this.patient.surname}}</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn
                    v-if="edit==true"
                    fab
                    small
                >
                <v-icon v-if="this.isEditing==true" v-on:click="cancel()">mdi-close</v-icon>
                <v-icon v-else v-on:click="setEditing()">mdi-pencil</v-icon>  
                </v-btn>
            </v-toolbar>
            <v-card-text>
                <v-row>
                    <v-col>
                        <v-text-field
                            label="Birth date"
                            readonly
                            :value="getDate(this.patient.birthDate).date"
                            filled>
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                            label="Gender"
                            readonly
                            :value="formatGender()"
                            filled>
                        </v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <v-text-field
                        label="Weight"
                        suffix="kg"
                        v-model="record.weight" 
                        :readonly="!this.isEditing" />
                    </v-col>
                    <v-col>
                        <v-text-field
                        label="Height"
                        suffix="cm"
                        v-model="record.height" 
                        :readonly="!this.isEditing" />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <v-text-field
                        label="Allergies"
                        v-model="record.allergies" 
                        :readonly="!this.isEditing" />
                    </v-col>
                    <v-col>
                        <v-autocomplete
                        :items="bloodTypes"
                        label="Blood type"
                        v-model="record.bloodType" 
                        :readonly="!this.isEditing" />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col><v-btn block color="primary" @click="showIllnesses()">Illnesses</v-btn></v-col>
                    <v-col><v-btn block color="primary" @click="showAppointments()">Appointments</v-btn></v-col>
                    <v-col><v-btn block color="primary" @click="showOperations()" disabled>Operations</v-btn></v-col>
                </v-row>
            </v-card-text>
            <v-card-actions>
                <v-btn v-if="this.isEditing==true" @click="saveChanges()" block color="success" height="50">Save changes</v-btn>
            </v-card-actions>
        </v-card>
    </v-form>
</template>

<script>
import Overlay from "./MROverlay"


const apiURL = "http://localhost:9001/api/medicalRecord/"

export default {
    name: "MedicalRecord",
     props: {
        id:  {
            type: String
        },
        edit: {
            type: Boolean
        }

    },
    components: {
        Overlay
    },
    data() {
        return {
            record: {},
            patient: {},
            appointments: [],
            isEditing: false,
            bloodTypes: ['A', 'B', 'AB', 'O'],
            cachedRecord: {},
            allergies: [],
            overlay: false,
            headerOverlay: [],
            itemsOverlay: [],
            title: ''
        }
    },
    mounted() {
        this.axios({
            url: apiURL + this.id,
            method: 'GET'
        }).then(response => {
            this.record = response.data.patient.record;
            this.appointments = response.data.appointments;
            this.cachedRecord = Object.assign({}, response.data.patient.record);
            this.patient = response.data.patient.patient;
        }).catch(error => {
            alert(error);
        })
    },
    methods: {
        setEditing() {
            this.isEditing = !this.isEditing;
        },
        saveChanges() {
            this.axios({
                url: apiURL,
                method: 'PUT',
                data: this.record
            }).then(response => {
                this.$store.commit('showSnackbar', {text: "Successfully updated medical record!", color: "success" })
                this.cachedRecord = Object.assign({}, this.record);
                this.isEditing = false;
                console.log(response);
                //alert("Successfully updated medical record!");
                //TODO redirekcija ili iskljuci overlay
            }).catch(error => {
                console.log(error);
                this.$store.commit('showSnackbar', {text: "An error has occurred!", color: "error" })
                //TODO SREDITI
            })
        },
        cancel() {
            this.record = Object.assign({}, this.cachedRecord);
            this.isEditing = false;
        },
        showAppointments() {
            this.title = "Appointments";
            console.log(this.appointments);
            var apps = [];
            this.headerOverlay = [
                { text: 'Date', value: 'date' },
                { text: 'Appointment type', value: 'type' },
                { text: 'Doctor', value: 'doctor' },
                { text: 'Room', value: 'room' },
                { text: 'Clinic', value: 'clinic'}
            ];
            this.appointments.forEach(app => {
                var el = {
                    date: this.getDate(app.startDate).time,
                    type: app.type,
                    doctor: app.doctorName + " " + app.doctorSurname,
                    room: app.roomName,
                    clinic: app.clinic
                }
                apps.push(el);
            });
            this.itemsOverlay = apps;
            this.overlay = true;
        },
        showIllnesses() {
            this.title = "Illnesses";
            this.headerOverlay = [
                { text: 'Date', value: 'date' },
                { text: 'Diagnosis', value: 'diagnosis' },
                { text: 'Description', value: 'description' },
                { text: 'Prescription', value: 'prescription' } //list of medicines
            ];
            var illnesses = [];
            this.appointments.forEach(app => {
                var el = {
                    date: this.getDate(app.startDate).date,
                    diagnosis: app.diagnosis,
                    description: app.description,
                    prescription: app.prescription
                }
                illnesses.push(el);
            })
            this.itemsOverlay = illnesses;
            this.overlay = true;
        },
        formatGender() {
            var g = this.patient.gender;
            if (g == undefined)
                return ""
            return g.charAt(0).toUpperCase() + g.slice(1).toLowerCase();
        },
        getDate(date) {
            if (date == undefined)
                return ""
            var el = date.split("T");
            var d = el[0];
            var t = el[1].substring(0, 5);
            var dateTime = {
                date: d,
                time: d + " " + t
            }
            return dateTime;
        },
        
    }
}
</script>

<style scoped>

</style>
