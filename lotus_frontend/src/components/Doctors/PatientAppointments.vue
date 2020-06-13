<template>
  <v-row align="center" justify="center">
    <v-col cols="6">
      <v-expansion-panels>
        <v-expansion-panel>
          <v-expansion-panel-header style="background:#424242;color:white" class="spacerHeader">Medical record</v-expansion-panel-header>
          <v-expansion-panel-content eager >
            <MedicalRecord :id="this.id.toString()" :edit="true" class="mt-4" />
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-col>
  </v-row>
</template>

<script>
import MedicalRecord from "../MedicalRecord/MedicalRecord"
export default {
  components: {
    MedicalRecord
  },
  props: ['id']
}
</script>