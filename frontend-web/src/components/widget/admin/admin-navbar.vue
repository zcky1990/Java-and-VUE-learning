<template>
<div class="nav-menu">
    <notif ref="notif" @showSnackBar="setMessage()"></notif>
    <v-app-bar color="#00d1b2" class="nav-toolbar" app clipped-right>
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
        <v-toolbar-title class="white--text desc">Admin</v-toolbar-title>
        <v-menu left bottom>
            <template v-slot:activator="{ on }">
                <v-btn icon v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
            </template>

            <v-list>
                <v-list-item v-for="item in items" :key="item.text">
                    <router-link class="nav-draw-links" v-bind:to="item.url">
                        <v-list-item-content>
                            <v-list-item-title class="menu-link">{{ item.title }}</v-list-item-title>
                        </v-list-item-content>
                    </router-link>
                </v-list-item>
            </v-list>
        </v-menu>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app>
        <v-list dense nav>
            <v-list-item v-for="item in items" :key="item.text">
                <router-link class="nav-draw-links" v-bind:to="item.url">
                    <v-list-item-content>
                        <v-list-item-title class="menu-link">{{ item.title }}</v-list-item-title>
                    </v-list-item-content>
                </router-link>
            </v-list-item>

        </v-list>
        <v-spacer></v-spacer>

        <div class="user-controller" v-if="isLogged">
            <div class="user-container">
                <v-container>
                    <div class="user-avatar-container" @click="logout">
                        <div class="logout-drawer">
                            <div class="logout-btn logout">Logout</div>
                        </div>
                    </div>
                </v-container>
            </div>
        </div>
    </v-navigation-drawer>
</div>
</template>

<script>
import { EventBus } from "./../../../EventBus.js";
import fire from "../../../fire";
import Util from "../../util";
import Notif from "./notif";

export default {
    name: "admin-navbar",
    data() {
        return {
            messageList: [],
            drawer: false,
            isMobile: false,
            items: [{
                    title: "Home",
                    url: "/admin"
                },
                {
                    title: "Hero Banner",
                    url: "/admin/hero"
                },
                {
                    title: "News Category",
                    url: "/admin/category"
                },
                {
                    title: "News",
                    url: "/admin/news"
                },
                {
                    title: "Pages",
                    url: "/admin/pages"
                },
                {
                    title: "Menu",
                    url: "/admin/menu"
                },
                {
                    title: "Fakultas",
                    url: "/admin/faculty"
                },
                {
                    title: "Prodi",
                    url: "/admin/prodi"
                },
                {
                    title: "Access Level",
                    url: "/admin/access_level"
                },
                {
                    title: "Roles",
                    url: "/admin/roles"
                },
                {
                    title: "Calon Mahasiswa",
                    url: "/admin/mahasiswa"
                },
                {
                    title: "Chat",
                    url: "/admin/chat"
                },
                {
                    title: "Users",
                    url: "/admin/users"
                }
            ],
            title: "Admin",
            window: {
                width: 0,
                height: 0
            },
            isLogged: false,
            isSuperAdmin: false
        };
    },
    components: {
        "notif": Notif
    },
    created() {
        this.isLogged = this.isLoggin(this.$session);
        this.isSuperAdmin = this.isHasSuperAdminAccessLevel(
            this.getAccessLevel(this.$session)
        );
        window.addEventListener("resize", this.handleResize);
        this.handleResize();
    },
    destroyed() {
        window.removeEventListener("resize", this.handleResize);
    },
    methods: {
        setNotifMessage: function(message) {
            this.$refs.notif.showSnackbar(message);
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
                this.isMobile = false;
                this.drawer = true;
            }
        },
        setMessage(message, type) {
            let data = {};
            data.message = message;
            data.type = type;
            EventBus.$emit("SNACKBAR_TRIGGERED", data);
            this.closePopUpMenu();
            this.$refs.form.reset();
        },
        logout() {
            this.$session.destroy();
            this.isLogged = false;
            this.$router.push("/");
        },
        getChatMessage() {
            let vm = this;
            const itemsRef = fire.database().ref("notif_list/messages");
            itemsRef.on("value", snapshot => {
                let data = snapshot.val();
                let messageList = [];
                if (data != null) {
                Object.keys(data).forEach(key => {
                    messageList.push({
                    id: key,
                    message_id: data[key].message_id,
                    username: data[key].user,
                    created: data[key].created
                    });
                });
                vm.messageList = messageList;
                }
            });
        },
        removeMessageList(key) {
            const itemsRef = fire.database().ref("notif_list/messages");
            itemsRef.child(key).remove();
        }
    },
    watch: {
        messageList: function(newValue, oldValue) {
            if (newValue.length > oldValue.length) {
                if(newValue[newValue.length-1].username != undefined && newValue[newValue.length-1].username != null){
                    let message = "You have new message from " + (newValue[newValue.length-1].username);
                    this.setNotifMessage(message)
                    let key = newValue[newValue.length-1].id
                    this.removeMessageList(key);
                }
            }
        }
  },
  mounted() {
    this.getChatMessage();
  }
};
</script>

<style scoped>
.nav-draw-links {
    display: flex;
    text-decoration: none;
}

a.v-list__tile.v-list__tile--link.theme--light {
    border: 1px solid red;
    margin: 5px;
    border-radius: 40px;
}

.nav-link,
.user-avatar-container {
    display: flex;
    padding: 5px;
}

.login-form-container {
    padding: 20px;
}

.user-controller {
    position: absolute;
    bottom: 0;
    width: 100%;
}

.logout-drawer {
    width: 100%;
}

.logout-btn {
    padding: 5px;
    color: #00d1b2;
    border: 1px solid #00d1b2;
    border-radius: 15px;
    text-align: center;
}

.menu-link {
    color: #00d1b2;
}
</style>
