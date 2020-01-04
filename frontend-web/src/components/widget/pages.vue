<template>
  <section class="section article">
    <div id="grid" class="content-container" tag="section">
      <div class="content">
        <div class="article-content-container">
          <div class="article-content">
            <div v-html="data.content"></div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import { EventBus } from "./../../EventBus.js";

export default {
  name: "pages-components",
  data() {
    return {
      getArticleUrl: "/pages/get_page/",
      data: {
        id: "",
        userId: "",
        articleId: ""
      },
    };
  },
  created() {
    this.slug = this.$route.params.slug;
    this.isUserLoggin = this.isLoggin(this.$session);
    if (this.isUserLoggin) {
      this.data.userId = this.getUserId(this.$session);
    }
    this.getArticleService();
  },
  methods: {
    getArticleService: function() {
      let self = this;
      let headers = {};
      headers = this.getDefaultHeaders(this.getMeta("token"));
      this.get(
        this.getArticleUrl + this.slug,
        headers,
        function(response) {
          let responseData = response.data.response;
          self.data = responseData;
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
    },
    seeAuthorsDetails: function(id) {
      let url = "/detail/" + id;
      this.$router.push(url);
    },
    setCssSideImage: function() {
      let elm = document.querySelectorAll(".image");
      if (elm.length > 0) {
        for (let i = 0; i < elm.length; i++) {
          let el = elm[i];
          if (el.className.includes("image-style-side")) {
            el.style.float = "right";
            el.style.maxWidth = "50%";
          }
          el.style.padding = "15px";
          el.style.textAlign = "center";
          el.styleposition = "relative";
          el.styleoverflow = "hidden";
          let child = el.children[0];
          child.style.maxWidth = "100%";
          child.style.display = "block";
          child.style.margin = "0 auto";
        }
      }
    },
    setCssQuote: function() {
      let elm = document.querySelectorAll("blockquote");
      if (elm.length > 0) {
        for (let i = 0; i < elm.length; i++) {
          let el = elm[i];
          el.style.background = "aliceblue";
          el.style.padding = "5px";
          el.style.borderLeft = "5px solid #00d1b2";
          el.style.marginBottom = "16px";
        }
      }
    },
    setBulletNumberingCss: function() {
      let bulletElList = document.querySelectorAll(
        "#grid > div.content > div.article-content-container > div.article-content > div > ul"
      );
      let numberingElList = document.querySelectorAll(
        "#grid > div.content > div.article-content-container > div.article-content > div > ol"
      );
      if (bulletElList.length > 0) {
        for (let i = 0; i < bulletElList.length; i++) {
          let el = bulletElList[i];
          el.style.marginBottom = "16px";
        }
      }
      if (numberingElList.length > 0) {
        for (let i = 0; i < numberingElList.length; i++) {
          let el = numberingElList[i];
          el.style.marginBottom = "16px";
        }
      }
    },
  },
  updated() {
    this.setCssSideImage();
    this.setCssQuote();
    this.setBulletNumberingCss();
    document.querySelectorAll("#grid > div.content > div > div > div > figure.table > table").forEach(block => {
      block.style.borderSpacing="0";
      block.style.marginBottom="16px";
    });
    document.querySelectorAll("#grid > div.content > div > div > div > figure.table > table > thead > tr > th").forEach(block => {
      block.style.border="1px solid lightgrey";
    });
    document.querySelectorAll("#grid > div.content > div > div > div > figure.table > table > tbody > tr > td")
    .forEach(block => {
      block.style.border="1px solid lightgrey";
      block.style.padding="5px";
    });
  },
  computed: {
  }
};
</script>
<style scoped>
.article-authors {
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
}
.authors-container {
  display: flex;
}
.author {
  padding-top: 7px;
}
.rounded {
  border-radius: 66px;
  width: 64px;
  height: 64px;
}
.authors-name {
  font-size: 1.5em;
  font-weight: 600;
  line-height: 1.5;
}
.article-create-date {
  color: grey;
  margin-bottom: 5px;
}
.headline {
  font-size: 2.3rem !important;
  font-weight: 600;
  line-height: 1.5 !important;
  text-transform: capitalize;
}
.content {
  font-size: 1em;
}
.article-authors {
  padding-bottom: 16px;
}
.article-content {
  display: block;
}
.image > img {
  display: block;
  margin: 0 auto;
  max-width: 100%;
}
.categories {
  padding: 5px 15px 5px 15px;
  border: 1px solid #dc143c;
  background: #dc143c;
  color: white;
  font-weight: 400;
}
.content-container {
  padding-top: 10px;
  padding-bottom: 16px;
}
.article-content-container {
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
}
.follow-btn {
  color: #00d1b2;
  text-align: center;
  font-size: 12px;
  font-weight: 100;
  margin-left: 10px;
}
.unfollow-btn {
  color: chartreuse !important;
  border: 1px solid chartreuse !important;
}
.btn-follow {
  border: 1px solid #00d1b2;
  border-radius: 5px;
  padding-left: 5px;
  padding-right: 5px;
  margin-top: 5px;
  cursor: pointer;
}
@media only screen and (max-width: 600px) {
  .article-content-container {
    display: flex;
    flex-wrap: nowrap;
    flex-direction: column;
  }
}
.bookmark-container {
  width: 100px;
  padding-right: 20px;
}
.reference-list {
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 2;
}
blockquote {
  background: aliceblue;
  padding: 5px;
  border-left: 5px solid #00d1b2;
}
</style>
