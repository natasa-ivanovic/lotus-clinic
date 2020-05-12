<template>
    <h1>{{msg}}</h1>
</template>

<script>

const apiURL = "http://localhost:9001/api/requests/registrations";

export default {
    props: ['key'],
    mounted() {
        fetch(apiURL + "/" + this.key,{headers: { 'Authorization': this.$authKey}})
        .then(response => {
            if (response.status != 200)
                this.msg = "Doslo do greske na serveru"
            else
                this.msg = "Uspesno ste se registrovali"
        })
    },
    data() {
        return {
            msg: ""
        }
    },
}
</script>