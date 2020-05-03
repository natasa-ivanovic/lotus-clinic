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

                </v-card-text>
            </v-card>
          </v-col>
        </v-row>
    </v-container>

</template>


<script>
import PatientPage from "../components/Patients/PatientHomePage"
import AdminPage from "../components/Admins/AdminHomePage"
export default {
    components: {
        PatientPage,
        AdminPage
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
                    src: 'https://img.huffingtonpost.com/asset/5bb8159a1f000000022533d1.jpeg?ops=scalefit_720_noupscale',
                },
                {
                    src: 'https://images.pond5.com/group-smiling-doctors-footage-024000198_prevstill.jpeg',
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