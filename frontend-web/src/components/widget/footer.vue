<template>
  <div class="footer">
    <v-footer color="#DC143C">
      <v-card class="lighten-1 text-center flex" flat style="background-color:#DC143C;">
        <v-card-text class="py-2 white--text text-center">
          <div class="data-kontak">
            <div class="name-text">
              <v-icon left color="white">home</v-icon>STIE DHARMA AGUNG
            </div>
            <div class="contact-phone">
              <v-icon left color="white">phone</v-icon>081997111818
            </div>
            <div class="location-text">
              <v-icon left color="white">map</v-icon>Jl industri no 33 Cikarang kota Cikarang Utara kab Bekasi 17530
            </div>
          </div>
          <div class="copy-rigt">
            <strong>Hak Cipta © 2018.</strong>
            <strong hidden>Created By : soeltan zaki rizaldy</strong>
          </div>
        </v-card-text>
      </v-card>
    </v-footer>
  </div>
</template>

<script>
export default {
  name: "footer-components",
  data: () => ({})
};
</script>
<style scoped>
.copy-rigt {
  text-align: center;
  padding: 10px;
}
.footer {
  margin-top: 5%;
}
@media only screen and (max-width: 411px) {
  .footer {
    margin-top: 15%;
  }
}
</style>
