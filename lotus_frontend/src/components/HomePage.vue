<template>
    <div>
        <div>Neko je ulogovan!</div>

    </div>
</template>


<script>
export default {
    components: {
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

    }
}
</script>

<style scoped>

</style>