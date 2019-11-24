<template>
  <section class="section visi-misi">
    <v-container>
      <div class="data-kontak">
          <div class="vision-section">
            <div class="vision-title">Visi</div>
            <div v-html="visi"></div>
          </div>
          <div class="mission-section">
            <div class="vision-title">Misi</div>
            <div v-html="misi"></div>
            </div>
          </div>
    </v-container>
  </section>
</template>
<script>
import { EventBus } from "./../../EventBus.js";

export default {
  name: "visi-misi-components",
 
  data() {
    return {
      url:"/visi-misi/get_visi_misi",
      visi: "",
      misi: ""
    };
  },
  created() {
    console.log("sadasdasd")
    this.getVisiMisi();
  },
  methods: {
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    },
    getVisiMisi: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.get(
        this.url,
        headers,
        function(response) {
          if (response.status == 200) {
            console.log(response.data.response)
            self.visi = response.data.response.visi;
            self.misi = response.data.response.misi;
          }
        },
        function(e) {
          self.showErrorAlert(e);
        }
      );
    },
  },
  computed: {
    
  }
};
</script>
<style scoped>
.data-kontak {
  margin-top: 40px;
}
.vision-title {
  font-size: 1.5em;
  font-weight: 400;
  line-height: 2.5;
}
.vision-desc {
  line-height: 2.5;
}
@media only screen and (max-width: 640px) {
  .data-kontak {
    margin-top: 10px;
  }
}
</style>
