<template>
    <v-dialog width="800" v-model="overlay" @click:outside="turnOff()">
    <l-map
      style="height: 400px; width: 800px; z-index: 0; "
      :zoom="zoom"
      :center="center"
      @update:zoom="zoomUpdated"
      @update:center="centerUpdated"
      @update:bounds="boundsUpdated"
    >
      <l-tile-layer :url="url"></l-tile-layer>
      <l-marker :lat-lng="latLng" />
    </l-map>
    </v-dialog>
</template>

<script>

import {LMap, LTileLayer, LMarker} from 'vue2-leaflet';
import "leaflet/dist/leaflet.css"
export default {
components: {
      LMap,
      LTileLayer,
      LMarker
    
  },
  data () {
    return {
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        zoom: 15,
        center: [45.25, 19.85],
        bounds: null,
        latLng: [45.25, 19.85]
        
    };
  },
  props: {
      overlay: {
          type: Boolean,
          default: false
      },
      address: {
          type: String,
          default: ""
      }
  },
  watch: {
      overlay : function() {
              if (this.address != "") {
                  setTimeout(function() {
          window.dispatchEvent(new Event("resize"))
      }, 250)
                  fetch("https://nominatim.openstreetmap.org/search?q=" + this.address + "&format=json")
                    .then(res => { return res.json()})
                    .then(res => {
                        if (res.length == 0) {
                            this.$store.commit('showSnackbar', {text: "No location found for specified address!", color: "error", })
                            return;
                        }
                        var result = res[0];
                        this.latLng = [result.lat, result.lon];
                        this.center = [result.lat, result.lon];
                        })
              }
      }
  },
  methods: {
    turnOff() {
        this.$emit('update:overlay', false);
        this.$emit('update:address', "");
        
    },
    zoomUpdated (zoom) {
      this.zoom = zoom;
    },
    centerUpdated (center) {
      this.center = center;
    },
    boundsUpdated (bounds) {
      this.bounds = bounds;
    },
  },
  mounted() {
      
  }
  
}
</script>