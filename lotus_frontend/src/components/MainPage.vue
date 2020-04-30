<template>
    <div>
        <Login v-if="checkRole('')" />
        <div v-else>Neko je ulogovan!</div>

    </div>
</template>


<script>
import Login from "../components/Login"
export default {
    components: {
        Login
    },
    methods: {
        checkRole: function(role) {
            if (this.$role == role)
                return true;
    }
    }
}
</script>

<style scoped>

</style>