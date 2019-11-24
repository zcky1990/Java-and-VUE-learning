<template>
  <section class="section kontak">
    <v-container>
      <div class="title">
        <div class="title-text">Kontak Kami </div>
      </div>
      <div class="data-kontak">
        <div class="name-text"><v-icon left color="#DC143C">home</v-icon>STIE DHARMA AGUNG</div>
        <div class="contact-phone"> <v-icon left color="#DC143C">phone</v-icon>(021) 8902808</div>
        <div class="location-text"><v-icon left color="#DC143C">map</v-icon>Jl industri no 33 Cikarang kota Cikarang Utara kab Bekasi 17530</div>
      </div>
    <v-container>
      <div id="top_div" style="height: 100%">
      <v-map :zoom="zoom" :center="center" style="height: 400px;">
        <v-tilelayer :url="url" :attribution="attribution"></v-tilelayer>
        <v-marker :lat-lng="marker" @click="openGoogleMap"></v-marker>       
      </v-map>
    </div>
  </v-container>
    </v-container>
    
  </section>
</template>
<script>
import { EventBus } from "./../../EventBus.js";
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';


export default {
  name: "contacts-components",
  components: {
    'v-map': LMap,
    'v-tilelayer' :LTileLayer,
    'v-marker': LMarker
  },
  data() {
    return {
      zoom: 17,
        center: [-6.9106821,107.6017346,17],
        url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        marker: L.latLng(-6.9106821,107.6017346,17),
    };
  },
  mounted() {
      setTimeout(function() { window.dispatchEvent(new Event('resize')) }, 250);
  },
  created() {
  },
  methods: {
    openGoogleMap: function(){
         window.open("https://www.google.com/maps/place/Sekolah+Tinggi+Ilmu+Ekonomi+Dharma+Agung/@-6.9106821,107.6017346,17z/data=!3m1!4b1!4m5!3m4!1s0x2e68e87e307767c3:0x2e9b4d9fbeab69e4!8m2!3d-6.9106821!4d107.6039233");
    },
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  },
  computed: {
    
  }
};
</script>
<style scoped>
@import "~leaflet/dist/leaflet.css";

.title-text {
  font-size: 1.8rem;
  font-weight: 400;
  line-height: 1.5;
  border-bottom: 1px solid #e1e4e8;
}
.name-text {
  font-weight: 800;
}
.data-kontak {
  padding-top: 20px;
  padding-bottom: 20px;
}
</style>
