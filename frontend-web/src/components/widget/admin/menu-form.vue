<template>
<v-container>
    <v-card>
        <div class="btn-add">
            <v-btn absolute dark fab top right color="rgb(0, 209, 178)" @click="addData" style="position: relative; float:right;">
                <v-icon>add</v-icon>
            </v-btn>
        </div>
        <v-card-title class="table-title">{{title}}</v-card-title>
        <v-card-title>
            <v-spacer></v-spacer>
            <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
        </v-card-title>
        <v-data-table class="table-container" :headers="tableHeaderList" :items="dataTableList" :search="search">
            <template v-slot:items="props">
                <tr>
                    <td class="text-xs-left">{{ props.item.name }}</td>
                    <td class="text-xs-left">{{ props.item.menuType }}</td>
                    <td class="text-xs-left">{{ props.item.created_date }}</td>
                </tr>
            </template>
            <template v-slot:item.action="{ item }">
        <v-icon
          small
          class="mr-2"
          @click="editListener(item)"
        >
          edit
        </v-icon>
        <v-icon
          small
          @click="deleteListener(item)"
        >
          delete
        </v-icon>
      </template>
        </v-data-table>
    </v-card>
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <v-card>
            <v-toolbar dark color="#00d1b2">
                <v-btn icon dark @click="closeDialog">
                    <v-icon>close</v-icon>
                </v-btn>
                <v-toolbar-title>{{titleForm}}</v-toolbar-title>
                <div class="flex-grow-1"></div>
            </v-toolbar>

            <v-container>
                <div class="menu-itle-section"> Menu </div>
                <v-form ref="form" v-model="valid" width="300">
                    <v-text-field v-model="data.id" label="Id" required flat color="rgb(0, 209, 178)" class="hidden"></v-text-field>

                    <v-text-field v-model="data.name" label="Menu Name" required flat :rules=roleMenuNameRules color="rgb(0, 209, 178)"></v-text-field>

                    <v-select v-model="data.menuType" :items="type" label="Type Menu" v-on:change="setSubmenu"></v-select>

                    <v-select v-model="data.page" :items="pagesPublisList" label="Publis Page Url" return-object item-text="permalink" v-if="!isSubmenu"></v-select>

                    <div v-if="isSubmenu" class="sub-menu">
                        <div class="submenu-title-container">
                            <div class="submenu-title-section"> SubMenu</div>
                            <v-btn v-on:click="addNewSubMenu">
                                <v-icon>add</v-icon>
                                Add Submenu
                            </v-btn>
                        </div>
                        <v-container>
                            <div class="sub-menu field-container">
                                <v-card flat>
                                    <v-container>
                                        <div class="sub-menu-conbtainer" v-for="(item, index) in data.submenu" :key="item.name">
                                            <div class="title-container">
                                                <div class="submenu-title">Submenu {{index+1}}</div>
                                                <div class="remove-container">
                                                    <v-icon color="white" @click="removeSubMenu(index)">fas fa-trash</v-icon>
                                                </div>
                                            </div>
                                            <v-text-field v-model="item.id" label="Id" required flat color="rgb(0, 209, 178)" class="hidden"></v-text-field>

                                            <v-text-field v-model="item.name" label="Menu Name" required flat :rules=roleSubMenuNameRules color="rgb(0, 209, 178)"></v-text-field>

                                            <v-select v-model="item.page" :items="pagesPublisList" label="Publis Page Url" return-object item-text="permalink"></v-select>
                                        </div>
                                    </v-container>
                                </v-card>
                            </div>
                        </v-container>
                    </div>

                </v-form>
                <div class="btn-submit">
                    <div class="form-bttm-container">
                        <div class="btn-container">
                            <v-btn class="white--text desc" color="#00d1b2" @click="submitForm">Submit</v-btn>
                        </div>
                    </div>
                </div>
            </v-container>
        </v-card>
    </v-dialog>
</v-container>
</template>

<script>
import {
    EventBus
} from "./../../../EventBus.js";

export default {
    name: "pages-form",
    data() {
        return {
            valid: false,
            page: 0,
            dialog: false,
            mode: "new",
            title: "Menu List",
            search: "",

            isSubmenu: false,
            urlData: {
                createUrl: "/menu/create",
                editUrl: "/menu/edit",
                getUrl: "/menu/",
                deleteUrl: "/menu/delete/",
                getListUrl: "/menu/get_menu_list",
                getListPage: "/pages/get_published_pages_list"
            },
            isFormShow: true,
            data: {
                id: "",
                menuType: "Menu",
                permalink: "",
                submenu: [{
                    name: "",
                }],
            },
            tableHeaderList: [{
                    text: "Menu Name",
                    value: "name"
                },
                {
                    text: "Menu Tipe",
                    value: "menuType"
                },
                {
                    text: "Created Date",
                    value: "created_date"
                },
                {
                    text: 'Actions',
                    value: 'action',
                    sortable: false
                },
            ],
            dataTableList: [],
            type: ["Menu", "Submenu"],
            pagesPublisList: [],
            roleMenuNameRules: [v => !!v || "Menu Name is required"],
            roleSubMenuNameRules: [v => !!v || "Submenu Name is required"],
        };
    },
    created() {
        this.getMenuDataList();
    },
    methods: {
        addNewSubMenu: function () {
            let submenu = {};
            submenu.name = "";
            submenu.page = {};
            if (this.data.submenu) {
                this.data.submenu.push(submenu);
            } else {
                this.data.submenu = [];
                this.data.submenu.push(submenu);
            }
        },
        removeSubMenu: function (index) {
            this.data.submenu.splice(index);
        },
        setSubmenu: function () {
            if (this.data.menuType == "Menu") {
                this.data.menuType == "Menu"
                this.isSubmenu = false;
            } else {
                this.data.menuType == "Submenu"
                this.isSubmenu = true;
            }
        },
        submitForm: function () {
            if (this.$refs.form.validate()) {
                if (this.mode == "new") {
                    this.createData(this.data);
                } else {
                    this.updateData(this.data);
                }
            }
        },
        resetData: function () {
            this.data = {};
        },
        createData: function (model) {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            if (this.isSubmenu) {
                this.data.page = null;
            }
            this.post(
                this.urlData.createUrl,
                model,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.dataTableList.push(response.data.response);
                        self.resetData();
                        self.dialog = false;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        getMenuDataList: function () {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                this.urlData.getListUrl,
                headers,
                function (response) {
                    if (response.status == 200) {
                        console.log(response.data.response)
                        self.dataTableList = response.data.response;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        deleteData: function (id) {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.delete(
                this.urlData.deleteUrl + id,
                headers,
                function (response) {
                    if (response.status == 200) {
                        for (let i = 0; i < self.dataTableList.length; i++) {
                            if (self.dataTableList[i].id == id) {
                                self.dataTableList.splice(i, 1);
                                break;
                            }
                        }
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        getPagesList: function () {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                this.urlData.getListPage,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.pagesPublisList = response.data.response;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        getData: function (id) {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                this.urlData.getUrl + id,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.dialog = true;
                        self.data = response.data.response;
                        self.setSubmenu();
                        self.mode = "edit";
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        updateData: function (model) {
            let self = this;
            if (this.isSubmenu) {
                this.data.page = null;
            }
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.put(
                this.urlData.editUrl,
                model,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.resetData();
                        self.dialog = false;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        addData: function () {
            this.dialog = true;
            this.mode = "new";
            this.getPagesList();
            this.resetData();
        },
        editListener: function (items) {
            this.getData(items.id);
            this.getPagesList();
        },
        deleteListener: function (items) {
            this.deleteData(items.id);
        },
        setMessage: function (message, type) {
            let data = {};
            data.message = message;
            data.type = type;
            EventBus.$emit("SNACKBAR_TRIGGERED", data);
        },
        closeDialog: function () {
            this.dialog = false;
        }
    },
    computed: {
        titleForm: function () {
            if (this.mode == "new") {
                return "Add new Pages";
            } else {
                return "Edit Pages";
            }
        },
        isShow: function () {
            console.log(this.isSubmenu)
            if (this.isSubmenu == true) {
                return false;
            }
            return true
        }
    }
};
</script>

<style scoped>
.table-title {
    font-size: 2em;
}

.title {
    padding-bottom: 0.5em;
}

.roles-form-container {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
}

.hidden {
    display: none;
}

.btn-container {
    position: relative;
    margin: 0 auto;
    text-align: end;
}

.input-container {
    width: 350px;
}

.domain-container {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}

.btn-submit {
    display: flex;
}

.title-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    background-color: rgb(0, 209, 178);
    color: white;
    padding: 5px;
}

.submenu-title {
    font-size: 14px;
    font-weight: 700;
}

.menu-itle-section {
    font-size: 20px;
    font-weight: 700;
}

.submenu-title-section {
    font-size: 20px;
    font-weight: 700;
}

.submenu-title-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.sub-menu-conbtainer {
    margin-top: 10px;
}

@media only screen and (max-width: 600px) {
    .domain-container {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
    }

    .input-container {
        width: auto;
    }
}
</style>
