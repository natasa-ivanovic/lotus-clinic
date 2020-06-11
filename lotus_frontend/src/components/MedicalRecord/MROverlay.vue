<template>
    <v-dialog max-width="1200" v-model="overlay" @click:outside="turnOff()">
        <v-card>
            <v-card-title>
                {{this.title}}
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-data-table
                v-if="overlay"
                :headers="this.headers"
                :items="this.items"
                :server-items-length="totalItems"
                :loading="loading"
                :options.sync="options"
                :footer-props="{
                    itemsPerPageOptions: [1, 5, 10]
                }">
                <template v-slot:item.doctors="{ item }">
                    <v-select :items="item.doctors" :value="item.doctors[0]"/>
                </template>
                </v-data-table>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script>
const apiURL = "/api/appointments/patient/";
const apiOperations = "/api/operations/patient/"
export default {
    name: "MROverlay",
    props: {
        overlay: {
            type: Boolean
        },
        headers: {
            type: Array
        },
        title: {
            type: String
        },
        id: {
            type: String
        }
    },
    data() {
        return {
            search: '',
            items: [],
            totalItems: 0,
            loading: true,
            options: {},
            selectedDoctor: ""            
        }
    },
    watch: {
        options: {
            handler () {    
                this.getDataFromApi();
            },
            deep: true,
        },
    },
    methods: {
        turnOff() {
            this.$emit('update:overlay', false)
        },
        getDataFromApi: function() {
            this.loading = true;
            const { sortBy, sortDesc, page, itemsPerPage } = this.options
            var requestUrl;
            if (this.title != "Operations")
                requestUrl = apiURL + this.id + "/past" + "?pageNo=" + (page - 1) + "&pageSize=" + itemsPerPage;
            else
                requestUrl = apiOperations + this.id + "/past" + "?pageNo=" + (page - 1) + "&pageSize=" + itemsPerPage;
            if (this.title == "Illnesses") {
                if (sortBy.length != 0) {
                    if (sortBy[0] == "date") 
                        requestUrl = requestUrl + "&sortBy=startDate&descending=" + sortDesc[0];
                    else if (sortBy[0] == "diagnosis")
                        requestUrl = requestUrl + "&sortBy=diagnosis.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "prescription")
                        requestUrl = requestUrl + "&sortBy=prescriptions.medicine.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "description")
                        requestUrl = requestUrl + "&sortBy=information&descending=" + sortDesc[0];
                }
            }
            else if (this.title == "Appointments") {
                if (sortBy.length != 0) {
                    if (sortBy[0] == "date") 
                        requestUrl = requestUrl + "&sortBy=startDate&descending=" + sortDesc[0];
                    else if (sortBy[0] == "doctor")
                        requestUrl = requestUrl + "&sortBy=doctor.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "type")
                        requestUrl = requestUrl + "&sortBy=appointmentType.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "room")
                        requestUrl = requestUrl + "&sortBy=room.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "clinic")
                        requestUrl = requestUrl + "&sortBy=clinic.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "price")
                        requestUrl = requestUrl + "&sortBy=doctor.specialty.price&descending=" + sortDesc[0];
                }
            }
            else {
                if (sortBy.length != 0) {
                    if (sortBy[0] == "date") 
                        requestUrl = requestUrl + "&sortBy=startDate&descending=" + sortDesc[0];
                    else if (sortBy[0] == "operationType")
                        requestUrl = requestUrl + "&sortBy=type.type.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "roomName")
                        requestUrl = requestUrl + "&sortBy=room.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "clinicName")
                        requestUrl = requestUrl + "&sortBy=clinic.name&descending=" + sortDesc[0];
                    else if (sortBy[0] == "price")
                        requestUrl = requestUrl + "&sortBy=type.price&descending=" + sortDesc[0];
                    }
            }

            this.axios({url : requestUrl, 
                method: 'GET'
            }).then(response => {
                this.loading = false;
                if (this.title == "Illnesses")
                    this.updateIllnesses(response);
                else if (this.title == "Appointments")
                    this.updateAppointments(response);
                else 
                    this.updateOperations(response);
            }).catch(() => {
                this.loading = false;
            });  
        },
        updateIllnesses(response) {
            var data = []
            response.data.content.forEach(app => {
                var el = {
                    date: this.getDate(app.startDate).date,
                    diagnosis: app.diagnosis.join(', '),
                    description: app.description,
                    prescription: app.recipes.join(', ')
                    }
                data.push(el);
                });
            this.items = data;
            this.totalItems = response.data.totalElements;
        },
        updateAppointments(response) {
            var data = []
            response.data.content.forEach(app => {
                var price = app.price;
                if (app.discount != 0) {
                    var priceDiscounted = app.price * (100-app.discount)/100;
                    price = priceDiscounted + "(" + app.discount + "% off)"
                }
                var el = {
                    date: this.getDate(app.startDate).time,
                    type: app.type,
                    doctor: app.doctorName + " " + app.doctorSurname,
                    room: app.roomName,
                    clinic: app.clinic,
                    price: price,
                    }
                data.push(el);
                });
            this.items = data;
            this.totalItems = response.data.totalElements;
        },
        updateOperations(response) {
            var data = []
            response.data.content.forEach(op => {
                var el = {
                    date: this.getDate(op.startDate).time,
                    operationType: op.operationType,
                    roomName: op.roomName,
                    clinicName: op.clinicName,
                    price: op.price,
                    doctors: op.doctors
                    }
                data.push(el);
                });
            this.items = data;
            this.totalItems = response.data.totalElements;
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