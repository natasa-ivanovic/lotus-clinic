<template>
    <div>
        <h1>Edit room</h1>
        <form id="editRoom" v-on:submit.prevent="editRoom()">
            <table>
                <tr>
                    <td>Id:</td>
                    <td><input type="number" name="id"/></td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name"/></td>
                </tr>
                <tr>
                <tr>
                    <td></td>
                    <td><input type="submit"/></td>
                </tr>
            </table>
        </form>
    </div>
</template>

<script>

const apiURL = "https://localhost:9001/api/rooms";

export default {
    name: "editRoom",
    props: ['id'],
    data() {
        return {

        }
    },
    mounted() {
        fetch(apiURL + "/" + this.id)
        .then(response => {
            return response.json();
        })
        .then(room => {
            var form = document.forms['editRoom'];
            form.id.value = room.id;
            form.name.value = room.name;
        })
    },
    methods: {
        editRoom: function() {
            var form = document.forms['editRoom'];

            var room = {
                id : form.id.value,
                name : form.name.value
            };
            fetch(apiURL + "/" + this.id, {method: 'PUT',
                  headers: {'Content-Type': 'application/json'},
                  body: JSON.stringify(room)})
            .then(response => {
                if (response.status != 200)
                    alert("Couldn't update room!");
                else
                    this.$router.push({name :"rooms"});
            })
        }
    }
    
}
</script>

<style scoped>

</style>