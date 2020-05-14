<template>
    <div>
        <v-container>
            <v-row align="center" justify="center">
                <v-col>
                    <v-form>
                    <v-card class="elevation-3">
                        <v-toolbar flat color="secondary" dark>
                            <v-toolbar-title>Medical record - {{this.patient.name}} {{this.patient.surname}}</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-btn
                                v-if="edit==true"
                                fab
                                small
                            >
                            <v-icon v-if="isEditing==true" v-on:click="cancel()">mdi-close</v-icon>
                            <v-icon v-else v-on:click="setEditing()">mdi-pencil</v-icon>  
                            </v-btn>
                        </v-toolbar>
                        <v-card-text>
                            <v-row>
                                <v-col>
                                    <h3>Birth date: {{getBirthDate()}}</h3>
                                </v-col>
                                <v-col>
                                    <h3>Gender: {{this.patient.gender}}</h3>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Weight (kg)"
                                    v-model="record.weight" 
                                    :readonly="!isEditing"/>
                                </v-col>
                                <v-col>
                                    <v-text-field
                                    label="Height (m)"
                                    v-model="record.height" 
                                    :readonly="!isEditing"/>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col>
                                    <v-text-field
                                    label="Allergies"
                                    v-model="record.allergies" 
                                    :readonly="!isEditing"/>
                                </v-col>
                                <v-col>
                                    <v-autocomplete
                                    :items="bloodTypes"
                                    label="Blood type"
                                    v-model="record.bloodType" 
                                    :readonly="!isEditing"/>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col><v-btn block color="primary" @click="showAppointments()">Appointments</v-btn></v-col>
                                <v-col><v-btn block color="primary" @click="showOperations()">Operations</v-btn></v-col>
                                <v-col><v-btn block color="primary" @click="showIlnesses()">Ilnesses</v-btn></v-col>
                            </v-row>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn v-if="isEditing==true" @click="saveChanges()" block color="success" height="50">Save changes</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-form>
            </v-col>
        </v-row>
        <Overlay v-bind:overlay.sync="overlay" :id="this.id"/>
    </v-container>
</div>    
</template>

<script>
import Overlay from "./MROverlay"


const apiURL = "http://localhost:9001/api/medicalRecord/"
const apiFinished = "http://localhost:9001/appointments/finished"


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
            isEditing: false,
            bloodTypes: ['A', 'B', 'AB', 'O'],
            cachedRecord: {},
            allergies: [],
            overlay: false
        }
    },
    mounted() {
        this.axios({
            url: apiURL + this.id,
            method: 'GET'
        }).then(response => {
            this.record = response.data.record;
            this.cachedRecord = Object.assign({}, response.data.record);
            this.patient = response.data.patient;
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
                alert(response.data);
                this.cachedRecord = Object.assign({}, response.data.record);
                this.isEditing = false;
                //alert("Successfully updated medical record!");
                //TODO redirekcija ili iskljuci overlay
            }).catch(error => {
                console.log(error);
                alert(error.response.data.text);
                //TODO SREDITI
            })
        },
        cancel() {
            this.record = Object.assign({}, this.cachedRecord);
            this.isEditing = false;
        },
        getBirthDate() {
            return this.patient.birthDate.split("T")[0];
        },
        showAppointments() {
            this.overlay = true;
        }
        
    }
}
</script>

<style scoped>

</style>
