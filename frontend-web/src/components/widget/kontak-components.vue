<template>
  <section class="section kontak">
    <v-container>
      <div class="title">
        <div class="title-text">Kontak Kami </div>
      </div>
      <div class="data-kontak">
        <div class="name-text"><v-icon left color="#DC143C">home</v-icon>STIE DHARMA AGUNG</div>
        <div class="contact-phone"> <v-icon left color="#DC143C">phone</v-icon>081997111818</div>
        <div class="location-text"><v-icon left color="#DC143C">map</v-icon>Jl industri no 33 Cikarang kota Cikarang Utara kab Bekasi 17530</div>
      </div>
    <v-container>
      <div id="top_div" style="height: 100%; ">
        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3966.012252747181!2d107.14556471517442!3d-6.262115595467388!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x2e69851df0221813%3A0x721c77d11f33c02b!2sJl.%20Industri%20No.33%2C%20Cikarang%20Kota%2C%20Kec.%20Cikarang%20Utara%2C%20Bekasi%2C%20Jawa%20Barat%2017530!5e0!3m2!1sid!2sid!4v1579375892848!5m2!1sid!2sid" width="100%" height="450" frameborder="0" style="border:0;" allowfullscreen=""></iframe>
    </div>
  </v-container>
    </v-container>
    
  </section>
</template>
<script>
import { EventBus } from "./../../EventBus.js";


export default {
  name: "contacts-components",
  data() {
    return {
    };
  },
  mounted() {
      setTimeout(function() { window.dispatchEvent(new Event('resize')) }, 250);
  },
  created() {
  },
  methods: {
    openGoogleMap: function(){
      window.open("https://www.google.com/maps/place/Jl.+Industri+No.33,+Cikarang+Kota,+Kec.+Cikarang+Utara,+Bekasi,+Jawa+Barat+17530/@-6.2621103,107.1455647,17z/data=!3m1!4b1!4m5!3m4!1s0x2e69851df0221813:0x721c77d11f33c02b!8m2!3d-6.2621156!4d107.1477534");
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
.google-maps {
        position: relative;
        padding-bottom: 75%;
        height: 0;
        overflow: hidden;
    }
    .google-maps iframe {
        position: absolute;
        top: 0;
        left: 0;
        width: 100% !important;
        height: 100% !important;
    }

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
