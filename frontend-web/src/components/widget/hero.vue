<template>
  <div class="hero-component">
    <v-carousel height="500">
      <v-carousel-item
        v-for="(item,i) in items"
        :key="i"
        :src="item.url_image"
        reverse-transition="fade-transition"
        transition="fade-transition"
      >
        <div class="title-cour-container">
          <v-container>
            <div class="title-carousel white--text align-end">{{item.title}}</div>
            <div class="sub-title-carousel white--text align-end">{{item.subtitle}}</div>
          </v-container>
        </div>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>
<script>
import { EventBus } from "./../../EventBus.js";

export default {
  name: "hero",
  data() {
    return {
      items: [
        {
          url_image: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
          title: "asdasdsadasdasd",
          subtitle: "asdasdasdasdasdasdasdsadasdasdasdasdasdasdasd"
        }
      ]
    };
  },
  created() {
    this.getHero();
  },
  methods: {
    getHero: function() {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.get(
        "/hero/show-hero",
        headers,
        function(response) {
          if (response.status == 200) {
            self.items = response.data.response;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    }
  },
  computed: {}
};
</script>
<style scoped>
.title-carousel {
  font-size: 25px;
  font-weight: 600;
}
.sub-title-carousel {
  font-size: 25px;
  font-weight: 300;
}
.title-cour-container {
  position: absolute;
  bottom: 10%;
  width: 100%;
  text-align: end;
}
</style>
