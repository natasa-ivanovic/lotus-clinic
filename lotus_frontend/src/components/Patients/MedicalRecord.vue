<template>
    <div>
        <v-container>
            <v-row align="center" justify="center">
                <v-col>
                    <v-card class="elevation-3">
                        <v-toolbar flat color="secondary" dark>
                            <v-toolbar-title>Medical record - {{this.record.patientName}} {{this.record.patientSurname}}</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-btn
                                v-if="edit==true"
                                fab
                                small
                                @click="setEditing()"
                            >
                            <v-icon v-if="isEditing" v-on:click="disabled=false">mdi-close</v-icon>
                            <v-icon v-else>mdi-pencil</v-icon>  
                            </v-btn>
                        </v-toolbar>
                        <v-card-text>
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
                                    <v-text-field
                                    label="Blood type"
                                    v-model="record.bloodType" 
                                    :readonly="!isEditing"/>
                                </v-col>
                            </v-row>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn v-if="isEditing==true" @click="saveChanges()" block color="success" height="50">Save changes</v-btn>
                        </v-card-actions>
                    </v-card>
            </v-col>
        </v-row>
    </v-container>
</div>    
</template>

<script>
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
    data() {
        return {
            record: {},
            isEditing: false,
        }
    },
    mounted() {
        this.axios({
            url: apiURL + this.id,
            method: 'GET'
        }).then(response => {
            this.record = response.data;
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
                this.isEditing = !this.isEditing;
                //alert("Successfully updated medical record!");
                //TODO redirekcija ili iskljuci overlay
            }).catch(error => {
                console.log(error);
                alert(error.response.data.text);
                //TODO SREDITI
            })

        }
    }
}
</script>

<style scoped>

</style>
