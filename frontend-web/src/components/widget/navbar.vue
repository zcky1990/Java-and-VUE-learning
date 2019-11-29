<template>
  <div class="nav-menu">
    <v-navigation-drawer fixed clipped v-model="drawer" app>
      <!--user detail drawer-->
      <div class="user-detail-controller">
        <div class="btn-drawer-login" v-if="!isLogged">
          <div class="user-title">
            <div class="name">Welcome</div>
            <div class="email">please login before you continue</div>
          </div>
          <div class="btn-container">
            <div class="button-login rounded">
              <router-link
                class="link-login nav-draw-links button-login"
                to="/pendaftaran"
              >Pendaftaran</router-link>
            </div>
          </div>
        </div>
      </div>

      <v-divider></v-divider>

      <v-list-tile class="menu-btn-link" v-for="item in items" :key="item.text">
        <router-link class="nav-draw-links drawer-links" v-bind:to="item.url">
          <v-list-tile-content>
            <div class="container">
              <v-list-tile-title>{{ item.title }}</v-list-tile-title>
            </div>
          </v-list-tile-content>
        </router-link>
      </v-list-tile>
    </v-navigation-drawer>

    <div class="toolbar">
      <div class="container toolbar-title">
        <div class="title-container">
          <div class="title-navbar" @click="goToHome">
            <v-img
              :src="defaultLogo"
              class="lighten-2"
              size="56"
            ></v-img>
          </div>
        </div>
        <div v-if="isMobile">
          <v-toolbar-side-icon @click.stop="drawer = !drawer" class="burger-menu">
            <v-icon color="#DC143C">menu</v-icon>
          </v-toolbar-side-icon>
        </div>
        <div v-if="!isMobile">
          <div class="icon-container">
            <div class="login-container">
              <div class="user-avatar-container">
                <v-chip class="button-login">
                  <router-link class="link-login button-login" to="/pendaftaran">Pendaftaran</router-link>
                </v-chip>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="!isMobile" ref="navLink" class="link-container">
        <div class="container toolbar-link">
          <div class="links">
            <div class="menu-btn-link" v-for="item in items" :key="item.text">
              <div v-if="isLinksMenu(item.type)">
                <router-link class="nav-draw-links" v-bind:to="item.url">
                  <div class="link">{{ item.title }}</div>
                </router-link>
              </div>
              <div v-else>
                <v-menu offset-y>
                  <template v-slot:activator="{ on }">
                    <div class="nav-draw-links" v-on="on">{{item.title}}</div>
                  </template>
                  <v-list>
                    <v-list-item v-for="(list, index) in item.listUrl" :key="index">
                      <v-list-item-title>
                        <div class="sub-link" @click="goToPage(list.url)">{{list.name}}</div>
                      </v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--fixed navbar -->
      <div v-if="!isMobile && isHeaderFixedShow" class="link-container fixed-menu">
        <div class="container toolbar-link">
          <div class="links">
            <div class="menu-btn-link">
              <div v-if="!isLinksMenu(item.type)">
                <router-link class="nav-draw-links" v-bind:to="item.url">
                  <div class="link">{{ item.title }}</div>
                </router-link>
              </div>
              <div v-else>
                <v-menu offset-y>
                  <template v-slot:activator="{ on }">
                    <div class="nav-draw-links" v-on="on">{{item.title}}</div>
                  </template>
                  <v-list>
                    <v-list-item v-for="(list, index) in item.listUrl" :key="index">
                      <v-list-item-title>
                        <div class="sub-link" @click="goToPage(list.url)">{{list.name}}</div>
                      </v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { EventBus } from "./../../EventBus.js";

export default {
  name: "navbar",
  data() {
    return {
      drawer: false,
      isMobile: false,
      items: [
        { title: "Beranda", type: "links", url: { name: "Index" } },
        {
          title: "Tentang",
          type: "sub-links",
          listUrl: [
            { name: "Visi & Misi", url: "/visi-misi" },
            { name: "Sejarah", url: "/sejarah" }
          ]
        },
        { title: "Kontak", type: "links", url: { name: "Kontak" } }
      ],
      icon: {
        type: String,
        default: "$vuetify.icons.cancel"
      },
      window: {
        width: 0,
        height: 0
      },
      users: false,
      menu: false,
      message: false,
      usersData: {},
      isHeaderFixedShow: false,
      defaultLogo: ""
    };
  },
  created() {
    this.isLogged = this.isLoggin(this.$session);
    this.setDefaultHeaderImage();
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.usersData = this.getUsers(this.$session);
    window.addEventListener("scroll", this.handleFixedNavBar);
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
    window.removeEventListener("scroll", this.handleFixedNavBar);
  },
  computed: {
    isLinks(itemtype) {
      return this.isLinksMenu(itemtype);
    }
  },
  methods: {
    setDefaultHeaderImage: function() {
      let base_url = window.location.origin;
      this.defaultLogo = base_url + "/images/logo.png";
    },
    isLinksMenu(itemtype) {
      if (itemtype != "links") {
        return false;
      } else {
        return true;
      }
    },
    goToPage(url) {
      this.$router.push(url);
    },
    goToHome() {
      if (this.$router.currentRoute.name != "Index") {
        this.$router.push("/");
      }
    },
    handleFixedNavBar() {
      if (this.isMobile == false) {
        if (this.$refs.navLink) {
          const top = this.$refs.navLink.getBoundingClientRect().top;
          console.log(top);
          if (top < 0) {
            this.isHeaderFixedShow = true;
          } else {
            this.isHeaderFixedShow = false;
          }
        }
      }
    },
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      this.checkRes();
    },
    checkRes() {
      if (this.window.width <= 640) {
        this.isMobile = true;
      } else {
        this.drawer = false;
        this.isMobile = false;
      }
    },
    setMessage(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  }
};
</script>
    <!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@media only screen and (max-width: 640px) {
  .toolbar-title {
    border-bottom: 1px solid #efefef;
  }
}
.drawer-links {
  color: #000 !important;
  font-weight: 500 !important;
  font-size: 1rem !important;
}
.toolbar-title {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: space-between;
}
.title-navbar {
  line-height: 1;
  font-size: 2rem;
  font-weight: 500;
  margin-bottom: 4px;
  width: 56px;
  height: 56px;
}
.sub-title {
  font-size: 0.9rem;
  line-height: 1;
}
.link-container {
  border-bottom: 1px solid #dc143c;
  background: #dc143c;
}
.links {
  display: flex;
  flex-direction: row;
  min-width: 200px;
  max-width: 200px;
  justify-content: space-between;
}
.menu-btn-link {
  margin-right: 10px;
}
.sub-link {
  padding-left: 20px;
  padding-right: 20px;
  font-size: 1rem;
  font-weight: 500;
  line-height: 1.5;
}
.nav-draw-links {
  /*color: #00d1b2 !important; */
  font-size: 1rem;
  letter-spacing: 0.2rem;
  line-height: 1;
  font-weight: 800;
  display: inline-block;
  text-decoration: none !important;
  color: #fff;
}
.nav-toolbar {
  color: #dc143c !important;
}
.green-color {
  color: #dc143c !important;
}
.login-container {
  border: 1px solid #dc143c;
  border-radius: 5px;
}
.button-login {
  background: white;
  color: #dc143c;
}
.pop-up-menu-title {
  font-size: 1.2rem;
  font-weight: 600;
  line-height: 1;
}
.pop-up-link {
  text-decoration: none;
  display: flex;
  font-size: 1rem;
  color: #000;
}
.rounded {
  border-radius: 25px;
}
.title {
  color: white;
}
.link-login {
  text-decoration: none;
}
.nav-link,
.user-avatar-container {
  display: flex;
  padding: 5px;
}
.login-form-container {
  padding: 20px;
}
/** drawer css **/
.user-detail-controller {
  padding: 10px;
  background: #dc143c;
  min-height: 200px;
}
.btn-drawer-login {
  border: 1px solid #dc143c;
  width: fit-content;
  align-items: center;
  text-align: center;
  margin: 0 auto;
}
.image-container {
  border: 1px solid #dc143c;
}
.image-user {
  margin: 0 auto;
  align-items: center;
  text-align: center;
}
.image-border {
  border: 2px solid white;
  border-radius: 50%;
}
.name {
  text-transform: capitalize;
  text-align: center;
  font-size: 1.5rem;
  font-weight: 400;
  letter-spacing: normal;
  line-height: 2rem;
  padding: 16px 16px 8px;
  word-break: break-all;
  color: #fff;
}
.email {
  text-align: center;
  color: #fff;
  letter-spacing: normal;
}
.btn-container {
  margin-top: 20px;
}
.user-controller {
  position: absolute;
  bottom: 0;
  width: 100%;
}
.logout-drawer {
  width: 200px;
  justify-content: center;
  text-align: center;
  margin: 0 auto;
}
.burger-menu {
  color: white;
}
.fixed-menu {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 15;
}
</style>
