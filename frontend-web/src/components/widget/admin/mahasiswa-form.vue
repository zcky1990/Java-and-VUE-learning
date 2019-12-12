<template>
<v-container>
    <div class="filter">
        <div class="input-container">
            <div class="date-container">
                <v-menu ref="startDate" v-model="startDate" :close-on-content-click="false" :nudge-right="40" :return-value.sync="filter.startDate" transition="scale-transition" min-width="290px">
                    <template v-slot:activator="{ on }">
                        <v-text-field v-model="filter.startDate" label="Start Date" prepend-icon="event" readonly v-on="on"></v-text-field>
                    </template>
                    <v-date-picker v-model="filter.startDate" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="startDate = false">
                            Cancel
                        </v-btn>
                        <v-btn text color="primary" @click="$refs.startDate.save(filter.startDate)">
                            OK
                        </v-btn>
                    </v-date-picker>
                </v-menu>
                <v-menu ref="endDate" v-model="endDate" :close-on-content-click="false" :nudge-right="40" :return-value.sync="filter.endDate" transition="scale-transition" min-width="290px">
                    <template v-slot:activator="{ on }">
                        <v-text-field v-model="filter.endDate" label="End Date" prepend-icon="event" readonly v-on="on"></v-text-field>
                    </template>
                    <v-date-picker v-model="filter.endDate" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="endDate = false">
                            Cancel
                        </v-btn>
                        <v-btn text color="primary" @click="$refs.endDate.save(filter.endDate)">
                            OK
                        </v-btn>
                    </v-date-picker>
                </v-menu>
            </div>
            <div class="search-by-name">
                <v-text-field v-model="filter.name" solo-inverted prepend-inner-icon="mdi-magnify" label="Search" class="hidden-sm-and-down" />
            </div>
        </div>
        <div class="my-2 paddedd">
            <v-btn depressed large color="primary" right @click="download">Download</v-btn>
            <v-btn depressed large color="primary" right @click="filterTable">Filter Search</v-btn>
        </div>
    </div>
    <div class="title">
        Table Calon Mahasiswa
    </div>
    <v-simple-table>
        <template v-slot:default>
            <thead>
                <tr>
                    <th class="text-left">Nama Lengkap</th>
                    <th class="text-left">Email</th>
                    <th class="text-left">Jenis Kelamin</th>
                    <th class="text-left">Nomor Telpon</th>
                    <th class="text-left">Kota</th>
                    <th class="text-left">Tanggal Mendaftar</th>
                    <th class="text-left">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in dataTableList" :key="item.name">
                    <td>{{ item.fullName }}</td>
                    <td>{{ item.email }}</td>
                    <td>{{ item.gender }}</td>
                    <td>{{ item.phoneNumber }}</td>
                    <td>{{ item.city }}</td>
                    <td>{{item.created_date}}</td>
                    <td>
                        <v-layout align-center justify-space-around>
                            <v-icon small class="mr-2" @click="editListener(item.id)">edit</v-icon>
                            <v-icon small class="mr-2" @click="deleteListener(item.id)">delete</v-icon>
                        </v-layout>
                    </td>
                </tr>
            </tbody>
        </template>
    </v-simple-table>
    <template>
        <div class="text-center">
            <v-pagination v-model="page" :length="totalPage" prev-icon="mdi-menu-left" next-icon="mdi-menu-right" @input="getDataMahasiswa"></v-pagination>
        </div>
    </template>

    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <v-card>
            <v-toolbar dark color="#00d1b2" class="no-print">
                <v-btn icon dark @click="closeDialog">
                    <v-icon>close</v-icon>
                </v-btn>
                <v-toolbar-title>{{titleForm}}</v-toolbar-title>
                <div class="flex-grow-1"></div>
            </v-toolbar>
            <v-container>
                <mahasiswa-form ref="formEditor" :list="itemsProdiList" :data="data"></mahasiswa-form>
            </v-container>
        </v-card>
    </v-dialog>
</v-container>
</template>

<script>
import {
    EventBus
} from "./../../../EventBus.js";

import MahasiswaForm from './mahasiswa-data-form';
import axios from 'axios'

export default {
    name: "add-user-form",
    components: {
        "mahasiswa-form": MahasiswaForm
    },
    data() {
        return {
            show1: false,
            valid: false,
            page: 0,
            dialog: false,
            mode: "new",
            title: "Table User List",
            search: "",
            totalPage: 0,
            startDate: false,
            endDate: false,
            filter: {
                startDate: "",
                endDate: "",
                name: "",
            },
            urlData: {
                createUrl: "/mahasiswa/create",
                editUrl: "/mahasiswa/edit",
                getUrl: "/mahasiswa/",
                deleteUrl: "/mahasiswa/delete/",
                getListUrl: "/mahasiswa/get_all_mahasiswa_list",
                getFilteredListUrl: "/mahasiswa/get_all_mahasiswa_list_filtered",
                getFilteredDataListByNameOrEmailUrl: "/mahasiswa/get_all_mahasiswa_list_filtered_by_name_email",
                download: "/api/mahasiswa/exportCSV"
            },
            isFormShow: true,
            data: {
                id: "",
            },
            itemsProdiList: {},
            dataTableList: [],
            filterDropDownList: [{
                id: "All",
                name: "All Member",
                status: true,
                type: "member"
            }],
            dataDropdownList: [],
            dataFilter: "",
            status: [true, false],
            useranameRules: [
                v => !!v || "Username is required",
                v =>
                (v && v.length >= 8) || "Username must be or more than 8 characters"
            ],
            passwordRules: [
                v => !!v || "Password is required",
                v =>
                (v && v.length >= 8) || "Password must be or more than 8 characters"
            ],
            emailRules: [
                v => !!v || "E-mail is required",
                v => /.+@.+/.test(v) || "E-mail must be valid"
            ]
        };
    },
    created() {
        this.getDataList();
        this.getProdi();
    },
    methods: {
        getProdi: function () {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                "/prodi/get_all_published_prodi_list",
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.itemsProdiList = response.data.response;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        closeDialog: function () {
            this.dialog = false;
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
        getDataList: function () {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                this.urlData.getListUrl + "?page=" + this.page,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.dataTableList = response.data.response;
                        self.totalPage = response.data.total_page;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        filterTable: function () {
            if (this.filter.startDate != "" && this.filter.endDate != null) {
                this.page = 0;
                this.getFilteredDataList();
            }
            if (this.filter.name != null && this.filter.name != undefined) {
                this.page = 0;
                this.getFilteredDataListByNameOrEmail();
            }
        },
        download: function () {
            if (this.filter.startDate != "" && this.filter.endDate != null) {
                let self = this;
                let headersData = this.getDefaultHeaders(this.getMeta("token"));
                axios({
                    url: this.urlData.download+ "?startDate=" + this.filter.startDate + "&endDate=" + this.filter.endDate,
                    method: 'GET',
                    responseType: 'blob',
                    headers: headersData
                }).then((response) => {
                     var fileURL = window.URL.createObjectURL(new Blob([response.data]));
                     var fileLink = document.createElement('a');
                     fileLink.href = fileURL;
                     fileLink.setAttribute('download', response.headers["content-disposition"].split("filename=")[1]);
                     document.body.appendChild(fileLink);
                     fileLink.click();
                });
            }
        },
        getFilteredDataList: function () {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                this.urlData.getFilteredListUrl + "?page=" + this.page + "&startDate=" + this.filter.startDate + "&endDate=" + this.filter.endDate,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.dataTableList = response.data.response;
                        self.totalPage = response.data.total_page;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        getFilteredDataListByNameOrEmail: function () {
            let self = this;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.get(
                this.urlData.getFilteredDataListByNameOrEmailUrl + "?page=" + this.page + "&name=" + this.filter.name,
                headers,
                function (response) {
                    if (response.status == 200) {
                        self.dataTableList = response.data.response;
                        self.totalPage = response.data.total_page;
                    }
                },
                function (e) {
                    self.setMessage(e, 1);
                }
            );
        },
        getDataMahasiswa: function (page) {
            if (this.filter.startDate != "" && this.filter.endDate != null) {
                this.page = page - 1;
                this.getFilteredDataList();
            } else if (this.filter.name != null && this.filter.name != undefined) {
                this.page = page - 1;
                this.getFilteredDataListByNameOrEmail();
            } else {
                this.page = page - 1;
                this.getDataList();
            }
        },
        filterData: function () {
            this.page = 0;
            if (this.dataFilter !== "All") {
                this.getFilteredDataList();
            } else {
                this.getDataList();
            }
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
            this.resetData();
        },
        editListener: function (id) {
            this.getData(id);
        },
        deleteListener: function (id) {
            this.deleteData(id);
        },
        setMessage: function (message, type) {
            let data = {};
            data.message = message;
            data.type = type;
            EventBus.$emit("SNACKBAR_TRIGGERED", data);
        }
    },
    computed: {
        titleForm: function () {
            if (this.mode == "new") {
                return "Add new Calon Mahasiswa";
            } else {
                return "Edit Calon Mahasiswa";
            }
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

.filter {
    display: flex;
    justify-content: flex-end;
}

.date-container {
    display: flex;
    margin-right: 20px;
}

.input-container {
    display: flex;
    padding: 20px;
}

.paddedd {
    padding: 20px;
}

.btn-container {
    position: relative;
    margin: 0 auto;
    text-align: end;
}

@media print {
   body {
    margin: 0;
    color: #000;
    background-color: #fff;
  }
  .no-print,
  .no-print * {
    display: none !important;
  }
}
</style>
