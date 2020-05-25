<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                <v-toolbar-title>{{this.title}}</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <v-row>
                        <v-col>
                            <v-dialog
                                ref="dialog"
                                v-model="dialog"
                                :return-value.sync="requestDate"
                                persistent
                                width="290px"
                                >
                            <template v-slot:activator="{on}">
                                <v-text-field
                                label="Select a date for your appointment"
                                v-model="requestDate"
                                readonly
                                prepend-icon="mdi-calendar"
                                v-on="on"/>
                                </template>
                            <v-date-picker v-model="requestDate" :min="getToday()" scrollable>
                                <v-spacer></v-spacer>
                                <v-btn text color="primary" @click="dialog = false">Cancel</v-btn>
                                <v-btn text color="primary" @click="$refs.dialog.save(requestDate)">OK</v-btn>
                                </v-date-picker>
                            </v-dialog>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <v-autocomplete
                                class="mt-0"
                                v-model="appointmentType"
                                :items="this.types"
                                item-value="id"
                                item-text="name"
                                dense
                                prepend-icon="mdi-pill"
                                label="Select an appointment type"
                                />
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <v-btn block v-on:click="search()" color=indigo dark height=60 >Search</v-btn>
                        </v-col>
                    </v-row>
                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
const apiAppTypes = "/api/appointmentTypes";
const apiRequest = "/api/patients/request";

export default {
    data() {
        return {
            requestDate: "",
            dialog: false,
            types: [],
            appointmentType: ""

        }
    },
    props: {
        clinic: {
            type: Boolean
        },
        title: {
            type: String,
            default: ""
        }
    },
    mounted() {
        if (this.title == "")   
            this.$router.push( {name: 'appointmentView'});
        this.axios({url : apiAppTypes, 
            method: 'GET'
          }).then(response =>   {
            this.types = response.data;
          }).catch(error => {
            alert(error.response.data.text);
          });
    },
    methods: {
        getToday: function() {
            var today = new Date();
            return today.toISOString();
        },
        search: function() {
            console.log(this.appointmentType);
            var newDate = new Date(this.requestDate);
            var queryData = {
                requestDate: newDate.getTime(),
                appointmentType: this.appointmentType,
                clinics: this.clinic
            };
            this.axios({url : apiRequest, 
                    method: 'POST',
                    data: queryData
                }).then(response =>   {
                    console.log(response);
                    if (this.clinic) {
                        this.$router.push({name: "patientsClinics", params: {clinicList: response.data}});
                    } else {
                        this.$router.push({name: "patientsDoctors", params: {doctorList: response.data}});
                    }
                }).catch(error => {
                    console.log(error);
                    alert(error.response.data);
                    //TODO SREDITI ERORE ZA SSID I USERNAME
                });
        }
    }
}
</script>


<style scoped>

</style>