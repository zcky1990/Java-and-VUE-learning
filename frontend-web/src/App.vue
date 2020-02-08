<template>
  <div id="app">
    <!--need to add v-app so vuetify working correctly.
    This component is used for dynamically managing your content area 
    and is the mounting point for many components.-->
    <v-app class="app-container" id="content-container">
      <div class="snack-bar-container">
        <snack-bar ref="snackbar" @showSnackBar="setMessage()"></snack-bar>
      </div>
      <router-view :key="$route.fullPath"></router-view>
    </v-app>
    <div id="sound" hidden>
      <source :src="mp3Url" type="audio/ogg" />
      <source :src="oggUrl" type="audio/mpeg" />
    </div>
  </div>
</template>

<script>
import SnackBar from "@/components/widget/snack-bar";
import { EventBus } from "./EventBus.js";
import soundMp3 from "@/assets/sound/when.mp3";
import soundOgg from "@/assets/sound/when.ogg";

export default {
  name: "app",
  data() {
    return {
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
      urlNavbar: "/menu/get_menu",
      navMenu: [],
      mp3Url: "",
      oggUrl: "",
    };
  },
  created() {
    EventBus.$on("SNACKBAR_TRIGGERED", val => {
      this.setMessage(val.message, val.type);
    });
    this.mp3Url = soundMp3;
    this.oggUrl = soundOgg;
  },
  components: {
    "snack-bar": SnackBar
  },
  methods: {
    setMessage: function(message, type) {
      if (type == 0) {
        this.snackBarConfig.color = "success";
      } else {
        this.snackBarConfig.color = "error";
      }
      this.$refs.snackbar.setConfig(this.snackBarConfig);
      this.$refs.snackbar.showSnackbar(message);
    },
  }
};
</script>
<style lang="scss">
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.app-container {
  background-color: #fff !important;
}
.post-date {
  font-size: 1.7rem;
  font-style: normal;
  font-weight: 400;
  height: auto;
  letter-spacing: -0.74px;
  color: #00d1b2;
}
.post-title {
  color: rgb(0, 0, 0);
  font-size: 1.4rem;
  font-style: normal;
  font-weight: 400;
}
.post-desc {
  font-family: Lato;
  font-size: 1em;
  font-style: normal;
  font-weight: 400;
  line-height: 21.6px;
  text-decoration-color: rgb(176, 176, 176);
  text-decoration-line: none;
  text-decoration-style: solid;
  -moz-text-size-adjust: 100%;
  text-size-adjust: 100%;
  /*letter-spacing: 1.44px;
  text-transform: uppercase;*/
}
.marker {
  width: 100px;
  border: 1px solid #dc143c;
  margin-bottom: 5px;
  margin-top: 5px;
}
.post-category {
  font-family: minion-pro;
  font-size: 1rem;
  font-style: italic;
  font-weight: 400;
  height: auto;
  letter-spacing: normal;
  line-height: 25.5px;
}
pre > p {
  margin-bottom: 0px !important;
}
</style>
