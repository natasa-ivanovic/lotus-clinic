<template>
    <h1>{{msg}}</h1>
</template>

<script>

const apiURL = "http://localhost:9001/api/requests/registrations";

export default {
    props: ['logkey'],
    mounted() {
        fetch(apiURL + "/" + this.logkey)
        .then(response => {
            if (response.status != 200){
                alert(response.status)
                this.msg = "Doslo do greske na serveru"
            }
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