<template>
    <div>
        <v-container fluid>
            <v-row align="center" justify="center">
                <v-col>
                    <v-card class="elevation-3" light>
                        <v-toolbar flat color="secondary" dark>
                                <v-toolbar-title>Medical record - {{this.record.patientName}} {{this.record.patientSurname}}</v-toolbar-title>
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
        }
    },
    data() {
        return {
            record: {}
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
    }
}
</script>

<style scoped>

</style>