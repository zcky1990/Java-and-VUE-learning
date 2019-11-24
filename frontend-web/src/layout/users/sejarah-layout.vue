<template>
  <div class="article-page-container">
    <section class="nav-section">
      <user-nav-menu></user-nav-menu>
    </section>
    <section class="content-section container">
      <v-content>
        <v-container fill-height>
          <v-layout class="content-layout">
            <v-flex class="main-content-container">
              <article-content v-bind:content="content"></article-content>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import SejarahComponents from "@/components/widget/sejarah";

import { EventBus } from "../../EventBus.js";

export default {
  name: "sejarah-page-layout",
  data() {
    return {
      url:"/sejarah/get_sejarah",
      content: {},
    };
  },
  components: {
    "user-nav-menu": Navbar,
    "article-content": SejarahComponents,
  },
  methods: {
    getArticleService: function() {
      let self = this;
      let headers = {};
      headers = this.getDefaultHeaders(this.getMeta("token"));
      this.get(
        this.url,
        headers,
        function(response) {
          let responseData = response.data.response.sejarah_content;
          self.content = responseData;
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    setMessage(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  },
  created() {
    this.getArticleService();
  }
};
</script>
<style scoped>
.sign-up-page-container {
  background-color: white;
}
.content-layout {
  flex-direction: row;
}
.left-content,
.right-content {
  min-width: 15%;
}
@media only screen and (max-width: 959px) {
  .content-layout {
    flex-direction: column;
  }
}
</style>
