<template>
    <v-row justify="center">
        <v-dialog
            v-model="dialog"
            max-width="350"
            @click:outside="cancel();"
        >
            <v-card>
                <v-card-title class="headline">Schedule appointment</v-card-title>
                <v-card-text>
                    <v-autocomplete
                    label="Select a term"
                    items="[1,2,3]"
                    v-model="startDate"
                />
                </v-card-text>
                <v-card-actions>
                    <v-btn block>Schedule</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
export default {
    data() {
        return {
            appointmentType: '',
            startDate: ''

        }

    },
    props: {
        dialog: {
            type: Boolean,
            default: false
        },
        patient: {
            type: Number,
            default: 0
        },
        doctor: {
            type: Number,
            default: 0
        }
    }
    
}
</script>
