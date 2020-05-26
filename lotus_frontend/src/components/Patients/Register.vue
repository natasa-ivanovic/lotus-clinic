<template>
    <div >
        <AddUser v-if="this.$role != 'CENTRE_ADMIN'" userType="patients" header="Register" > </AddUser>    
        <AddUser v-if="this.$role == 'CENTRE_ADMIN'" userType="admins" header="Add Clinic Admin" > </AddUser>   
    </div>
</template>

<script>
import AddUser from "../Users/AddUser"
export default {
    components: {
        AddUser
    },
    
}
</script>