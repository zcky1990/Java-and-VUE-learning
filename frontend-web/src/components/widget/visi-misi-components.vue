<template>
  <section class="section visi-misi">
    <v-container>
      <div class="data-kontak">
          <div class="vision-section">
            <div class="vision-title">Visi</div>
            <div class="vision-desc">“A World-class university 
 … In continuous pursuit of innovation and enterprise”</div>
          </div>
          <div class="mission-section">
            <div class="vision-title">Misi</div>
            <div class="vision-desc">The mission of BINUS University is to contribute to the global community through the provision of world-class education by :
              <ul>
              <li>Recognizing and rewarding the most creative and value-adding talents</li>
              <li>Providing a world-class teaching, learning and research experience that fosters excellence in scholarship, innovation and entrepreneurship.</li>
              <li>Conducting professional services with an emphasis on application of knowledge to the society</li>
              <li>Creating outstanding leaders for global community</li>
              <li>Improving the quality of life of Indonesians and the international community</li>
              </ul>  
            </div>
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
      
    };
  },
  created() {
  },
  methods: {
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
