    <template>
  <div class="index-page-container">
    <section class="nav-section">
      <nav-bar></nav-bar>
    </section>
    <section class="hero-section">
      <hero></hero>
    </section>
    <section class="content-section">
      <v-container>
        <v-row>
          <v-col cols="12" md="2"></v-col>
          <v-col cols="12" md="8">
            <article-list></article-list>
          </v-col>
          <v-col cols="12" md="2"></v-col>
        </v-row>
      </v-container>
    </section>
    <section>
      <chat-components></chat-components>
    </section>
    <section class="footer">
      <footer-components></footer-components>
    </section>
  </div>
</template>
    <script>
import NavbarComponents from "@/components/widget/navbar";
import HeroComponents from "@/components/widget/hero";
import ListArticleComponents from "@/components/widget/list-article";
import FooterComponents from "@/components/widget/footer";
import ChatComponents from "@/components/widget/chat-btn";

export default {
  name: "index-page-layout",
  components: {
    "nav-bar": NavbarComponents,
    hero: HeroComponents,
    "article-list": ListArticleComponents,
    "footer-components": FooterComponents,
    "chat-components": ChatComponents
  }
};
</script>

    <style scoped>
.main-content {
  flex-basis: 66.66666666666666%;
  -webkit-box-flex: 0;
  -ms-flex-positive: 0;
  flex-grow: 0;
  max-width: 66.66666666666666%;
}
.right-content {
  -ms-flex-preferred-size: 33.33333333333333%;
  flex-basis: 33.33333333333333%;
  -webkit-box-flex: 0;
  -ms-flex-positive: 0;
  flex-grow: 0;
  max-width: 33.33333333333333%;
  background: #f8f9fa !important;
}
.index-content {
  flex-direction: row;
}
@media only screen and (max-width: 600px) {
  .index-content {
    flex-direction: column;
  }
  .main-content {
    flex-basis: 100%;
    -webkit-box-flex: 0;
    -ms-flex-positive: 0;
    flex-grow: 0;
    max-width: 100%;
  }
  .right-content {
    flex-basis: 100%;
    -webkit-box-flex: 0;
    -ms-flex-positive: 0;
    flex-grow: 0;
    max-width: 100%;
  }
}
</style>
