<template>
    <v-container fluid>
        <v-row align="center" justify="center">
          <v-col cols="6">
            <v-card class="elevation-3">
                <v-toolbar flat color="secondary" dark>
                    <v-toolbar-title>Welcome to Lotus Clinic!</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <v-carousel height=250>
                        <v-carousel-item
                        v-for="(item,i) in items"
                        :key="i"
                        :src="item.src"
                        reverse-transition="fade-transition"
                        transition="fade-transition"
                        ></v-carousel-item>
                    </v-carousel>
                    <PatientPage v-if="this.$role == 'PATIENT'" />
                    <AdminPage v-if="this.$role == 'ADMIN'" />
                    <DoctorPage v-if="this.$role == 'DOCTOR'" />
                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>

</template>


<script>
import PatientPage from "../components/Patients/PatientHomePage"
import AdminPage from "../components/Admins/AdminHomePage"
import DoctorPage from "../components/Doctors/DoctorHomePage"

export default {
    components: {
        PatientPage,
        AdminPage,
        DoctorPage
    },
    methods: {
        checkRole: function(role) {
            if (this.$role == role)
                return true;
    }
    },
    created() {
        console.log("User is: " + this.$role)
        if (this.$role == null)
            this.$router.push({ name: "login" })

    },
    data() {
        return {
            items: [
                {
                    src: 'https://www.omstuff.com/content/2018/07/beautiful-bright-pink-lotus-flower-wallpaper.jpg',
                },
                {
                    src: 'https://www.omstuff.com/content/2018/07/dark-pink-lotus-flower-and-lillypads-wallpaper.jpg',
                },
                {
                    src: 'http://sussexlaserlipo.co.uk/wp-content/uploads/2013/11/Smiling-doctors-Sussex-Laser-Lipo.jpeg',
                },
                {
                    src: 'https://cdn.vuetifyjs.com/images/carousel/planet.jpg',
                },
            ],
        }
    }
}
</script>

<style scoped>

</style>