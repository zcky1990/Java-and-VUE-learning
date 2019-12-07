<template>
<div class="nav-menu">
    <v-app-bar color="#00d1b2" class="nav-toolbar" app clipped-right>
        <v-app-bar-nav-icon color="white" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
        <v-toolbar-title ccolor="white">Admin</v-toolbar-title>
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
import {
    EventBus
} from "./../../../EventBus.js";

export default {
    name: "admin-navbar",
    data() {
        return {
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
                // { title: "Sejarah", url: "/admin/sejarah" },
                // { title: "Visi Misi", url: "/admin/visi-misi" },
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
        }
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
