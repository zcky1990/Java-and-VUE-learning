<template>
  <div class="nav-menu">
    <v-navigation-drawer fixed clipped v-model="drawer" app>
      <!--user detail drawer-->
      <div class="user-detail-controller">
        <div class="btn-drawer-login" v-if="!isLogged">
          <div class="user-title">
            <div class="name">Selamat Datang</div>
            <div class="email">Universitas STIE Dharma Agung Bandung</div>
          </div>
          <div class="btn-container">
            <div >
              <v-btn class="ma-2 white--text" outlined color="white" @click="openWa"> <v-img class="icons-btn" :src="whatsAppLogo" width="20" height="20"></v-img>Whatsapp</v-btn>
              <v-btn class="ma-2" outlined color="white" @click="goToPendaftaran">Pendaftaran</v-btn>
            </div>
          </div>
        </div>
      </div>
      <v-divider></v-divider>
      <div class="menu-list">
        <div class="menu-btn-link" v-for="(item ,index ) in navBarMenu" :key="item.name">
          <div
            v-if="item.isMenu"
            class="nav-draw-links drawer-links"
            @click="openPages(item.slug, index)"
          >
            <div class="menu-seide-bar-content-links">
              <div class="side-nav-title">{{ item.name }}</div>
            </div>
          </div>
          <div v-else class="nav-draw-links drawer-links">
            <div class="menu-seide-bar-content-links">
              <div class="side-nav-title" @click="showHideNav">{{ item.name }}</div>
              <div class="side-nav-title-child">
                <div
                  class="child-container"
                  v-for="(list, indexData) in item.submenu"
                  :key="indexData"
                >
                  <div
                    class="side-nav-title-child-list"
                    @click="openPages(list.slug, index)"
                  >{{ list.name }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </v-navigation-drawer>

    <div class="toolbar">
      <div class="container toolbar-title">
        <div class="title-container">
          <div class="title-navbar" @click="goToHome">
            <v-img :src="imageLogo" class="lighten-2 icon-header" width="65" height="70" cover></v-img>
            <div class="title">
              <h1 class="title-name">UNIVERSITAS</h1>
              <h1 class="title-name bold">STIE DHARMA AGUNG</h1>
            </div>
          </div>
        </div>
        <div v-if="isMobile">
          <v-toolbar-side-icon @click.stop="drawer = !drawer" class="burger-menu">
            <v-icon color="#DC143C">menu</v-icon>
          </v-toolbar-side-icon>
        </div>
        <div v-if="!isMobile">
          <div class="icon-container">
            <v-btn class="ma-2" outlined color="green" @click="openWa"> <v-img :src="whatsAppLogo" width="20" height="20"></v-img> Whatsapp</v-btn>
            <v-btn class="ma-2 " outlined color="#dc143c" @click="goToPendaftaran">Pendaftaran</v-btn>
          </div>
        </div>
      </div>

      <div v-if="!isMobile" ref="navLink" class="link-container">
        <v-toolbar class="toolbar-link" color="#dc143c" :fixed="fixed">
          <div class="links">
            <div class="menu-btn-link" v-for="(item, index) in navBarMenu" :key="item.text">
              <div v-if="item.isMenu">
                <div class="nav-draw-links" @click="openPages(item.slug, index)">
                  <div class="link">{{ item.name }}</div>
                </div>
              </div>
              <div v-else>
                <v-menu offset-y>
                  <template v-slot:activator="{ on }">
                    <div class="nav-draw-links" v-on="on">{{item.name}}</div>
                  </template>
                  <v-list>
                    <div v-for="(list, indexData) in item.submenu" :key="indexData">
                      <div class="sub-links">
                        <div class="sub-link" @click="openPages(list.slug, index)">{{list.name}}</div>
                      </div>
                    </div>
                  </v-list>
                </v-menu>
              </div>
            </div>
          </div>
        </v-toolbar>
      </div>
    </div>
  </div>
</template>

<script>
import { EventBus } from "./../../EventBus.js";
import imageLogo from "../../assets/image/stiami.jpeg";
import whatsappLogo from "./../../assets/image/whatsapp.png";

export default {
  name: "navbar",
  data() {
    return {
      drawer: false,
      isMobile: false,
      fixed: false,
      navBarMenu: [],
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
      imageLogo: imageLogo,
      whatsAppLogo: whatsappLogo,
      currentIndex: 0
    };
  },
  mounted: function() {
    let navbar = this.$cookies.get("navMenu");
    let JsonNavbar = [];
    if (navbar == null) {
      JsonNavbar = [
        {
          name: "Beranda",
          isMenu: true,
          slug: "Index",
          submenu: []
        },
        {
          name: "Kontak",
          isMenu: true,
          slug: "Kontak",
          submenu: []
        }
      ];
    } else {
      JsonNavbar = JSON.parse(navbar);
    }
    for (let i = 0; i < JsonNavbar.length; i++) {
      this.navBarMenu.push(JsonNavbar[i]);
    }
    window.addEventListener("scroll", this.handleFixedNavBar);
  },
  created() {
    this.isLogged = this.isLoggin(this.$session);
    this.setDefaultHeaderImage();
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.usersData = this.getUsers(this.$session);
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
    window.removeEventListener("scroll", this.handleFixedNavBar);
  },
  methods: {
    openWa: function(){
    var win = window.open('https://api.whatsapp.com/send?phone=6281997111818&text=Hallo%20saya%20ingin%20bertanya%20informasi%20seputar%20kampus&source=&data=', '_blank');
      win.focus();
    },
    goToPendaftaran: function(){
      this.$router.push("/pendaftaran");
    },
    showHideNav: function(e) {
      let nextSibling = e.srcElement.nextElementSibling;
      if (nextSibling.classList.contains("hidden")) {
        nextSibling.classList.remove("hidden");
      } else {
        nextSibling.classList.add("hidden");
      }
    },
    setDefaultHeaderImage: function() {
      let base_url = window.location.origin;
      this.defaultLogo = base_url + "/images/logo.png";
    },
    getKontak(index) {
      this.navBarMenu[index];
    },
    openPages(url, index) {
      let obj = this.navBarMenu[index];
      let isMenu = obj.isMenu;
      if (isMenu) {
        if (index == 0) {
          this.goToHome();
        } else if (obj.name == "Kontak") {
          this.goToKontak();
        } else {
          this.goToPage(url);
        }
      } else {
        this.goToPage(url);
      }
    },
    goToPage(url) {
      let routeUrl = "/pages/" + url;
      if (this.$router.currentRoute.name != routeUrl) {
        this.$router.push(routeUrl);
      }
    },
    goToHome() {
      if (this.$router.currentRoute.name != "Index") {
        this.$router.push("/");
      }
    },
    goToKontak() {
      if (this.$router.currentRoute.name != "Kontak") {
        this.$router.push("/Kontak");
      }
    },
    handleFixedNavBar() {
      if (this.isMobile == false) {
        if (this.$refs.navLink) {
          const top = this.$refs.navLink.getBoundingClientRect().top;
          if (top < 0) {
            this.fixed = true;
          } else {
            this.fixed = false;
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
<style scoped>
@media only screen and (max-width: 640px) {
  .toolbar-title {
    border-bottom: 1px solid #efefef;
  }
}
.hidden {
  display: none;
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
  display: flex;
}
.icon-header {
  margin-right: 12px;
}
.menu-list {
  padding: 20px;
}
.title-name {
  font-size: 20px;
  font-weight: 300;
  line-height: 1.2;
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
  padding: 10px;
  white-space: nowrap;
}
.sub-link {
  padding-left: 20px;
  padding-right: 20px;
  font-size: 1rem;
  font-weight: 700;
  line-height: 2;
  text-align: center;
}
.nav-draw-links {
  /*color: #00d1b2 !important; */
  font-size: 1rem;
  letter-spacing: 0.2rem;
  line-height: 1;
  font-weight: 800;
  width: 100%;
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
  padding: 5px;
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
  color: #000;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.bold {
  font-weight: 600;
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
.side-nav-title-child {
  margin-left: 5px;
}
.side-nav-title-child-list {
  line-height: 1.2;
  padding-left: 10px;
  padding-top: 15px;
  font-size: 12px;
  font-weight: 300;
}
.icon-container {
  display: flex;
}
.icons-btn {
  margin-right:4px;
}
</style>
