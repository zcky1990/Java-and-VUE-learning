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
              <pages-content></pages-content>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </section>
    <section class="footer">
      <footer-components></footer-components>
    </section>
  </div>
</template>

<script>
import Navbar from "@/components/widget/navbar";
import PagesComponents from "@/components/widget/pages";
import { EventBus } from "../../EventBus.js";
import FooterComponents from "@/components/widget/footer";

export default {
  name: "pages-page-layout",
  data() {
    return {
      content: {},
      slug: "",
      isUserLoggin: false
    };
  },
  components: {
    "user-nav-menu": Navbar,
    "pages-content": PagesComponents,
    "footer-components": FooterComponents
  },
  methods: {},
  created() {}
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
