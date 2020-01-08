<template>
  <v-container>
    <v-card>
      <div class="btn-add">
        <v-btn
          absolute
          dark
          fab
          top
          right
          color="rgb(0, 209, 178)"
          @click="addData"
          style="position: relative; float:right;"
        >
          <v-icon>add</v-icon>
        </v-btn>
      </div>
      <v-card-title class="table-title">{{title}}</v-card-title>
      <v-card-title>
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
      </v-card-title>
      <v-data-table
        class="table-container"
        :headers="tableHeaderList"
        :items="dataTableList"
        :search="search"
      >
        <template v-slot:items="props">
          <tr>
            <td class="text-xs-left">{{ props.item.fakultas.facultyName }}</td>
            <td class="text-xs-left">{{ props.item.degree }}</td>
            <td class="text-xs-left">{{ props.item.prodiName }}</td>
            <td class="text-xs-left">{{ props.item.isPublished }}</td>
            <td class="text-xs-left">{{ props.item.created_date }}</td>
          </tr>
        </template>
        <template v-slot:item.action="{ item }">
          <v-icon small class="mr-2" @click="editListener(item)">edit</v-icon>
          <v-icon small @click="deleteListener(item)">delete</v-icon>
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
          <v-form ref="form" v-model="valid" width="300">
            <v-text-field
              v-model="data.id"
              label="Id"
              required
              outline
              flat
              width="300"
              color="rgb(0, 209, 178)"
              class="hidden"
            ></v-text-field>

            <div class="domain-container">
              <v-text-field
                v-model="data.prodiName"
                :rules="roleDomainRules"
                label="Add your Prodi Name"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
            </div>
            <v-select
              :items="facultyList"
              v-model="data.fakultas"
              label="Fakultas"
              required
              item-text="facultyName"
              return-object
              color="rgb(0, 209, 178)"
            ></v-select>
            <v-select v-model="data.degree" :items="degreeList" label="Jenjang Pendidikan"></v-select>
            <v-select v-model="data.isPublished" :items="pagesPublisStatus" label="Publis Fakultas"></v-select>
            <v-flex align-center justify-center>
              <div class="form-bttm-container">
                <div class="btn-container">
                  <v-btn class="white--text desc" color="#00d1b2" @click="submitForm">Submit</v-btn>
                </div>
              </div>
            </v-flex>
          </v-form>
        </v-container>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { EventBus } from "./../../../EventBus.js";
import Alert from "@/components/widget/alert";
import { Util } from "@/components/util";
import { AXIOS } from "@/components/http-common";

export default {
  name: "faculty-form",
  data() {
    return {
      valid: false,
      page: 0,
      dialog: false,
      mode: "new",
      title: "List Prodi",
      search: "",
      urlData: {
        createUrl: "/prodi/create",
        editUrl: "/prodi/edit",
        getUrl: "/prodi/",
        deleteUrl: "/prodi/delete/",
        getListUrl: "/prodi/get_all_prodi_list",
        getFacultyListUrl: "/faculty/get_all_published_faculty_list"
      },
      isFormShow: true,
      data: {
        id: "",
        prodiName: "",
        fakultas: {},
        isPublished: false
      },
      tableHeaderList: [
        {
          text: "Fakultas",
          value: "fakultas.facultyName"
        },
        {
          text: "Jenjang",
          value: "degree"
        },
        {
          text: "Prodi",
          value: "prodiName"
        },
        {
          text: "Is Published",
          value: "isPublished"
        },
        {
          text: "Created Date",
          value: "created_date"
        },
        {
          text: "Actions",
          value: "action",
          sortable: false
        }
      ],
      dataTableList: [],
      pagesPublisStatus: [true, false],
      degreeList: ["D3", "S1", "S2"],
      facultyList: [],
      roleDomainRules: [v => !!v || "Nama Prodi harus diisi"]
    };
  },
  created() {
    this.getDataList();
    this.getFacultyDataList();
  },
  methods: {
    submitForm: function() {
      if (this.$refs.form.validate()) {
        if (this.mode == "new") {
          this.createData(this.data);
        } else {
          this.updateData(this.data);
        }
      }
    },
    resetData: function() {
      this.data = {};
    },
    createData: function(model) {
      let self = this;
      let headers = this.getRequestHeader();
      this.post(
        this.urlData.createUrl,
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            self.dataTableList.push(response.data.response);
            self.resetData();
            self.dialog = false;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    getDataList: function() {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.urlData.getListUrl,
        headers,
        function(response) {
          if (response.status == 200) {
            self.dataTableList = response.data.response;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    getFacultyDataList: function() {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.urlData.getFacultyListUrl,
        headers,
        function(response) {
          if (response.status == 200) {
            self.facultyList = response.data.response;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    deleteData: function(id) {
      let self = this;
      let headers = this.getRequestHeader();
      this.delete(
        this.urlData.deleteUrl + id,
        headers,
        function(response) {
          if (response.status == 200) {
            for (let i = 0; i < self.dataTableList.length; i++) {
              if (self.dataTableList[i].id == id) {
                self.dataTableList.splice(i, 1);
                break;
              }
            }
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    getData: function(id) {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.urlData.getUrl + id,
        headers,
        function(response) {
          if (response.status == 200) {
            self.dialog = true;
            self.data = response.data.response;
            self.mode = "edit";
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    updateData: function(model) {
      let self = this;
      let headers = this.getRequestHeader();
      this.put(
        this.urlData.editUrl,
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            self.resetData();
            self.dialog = false;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    addData: function() {
      this.dialog = true;
      this.mode = "new";
      this.resetData();
    },
    editListener: function(item) {
      this.getData(item.id);
    },
    deleteListener: function(item) {
      this.deleteData(item.id);
    },
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    },
    closeDialog: function() {
      this.dialog = false;
    },
    getRequestHeader: function() {
      this.requestHeader = this.getHeaders(this.$session);
      return this.requestHeader;
    }
  },
  computed: {
    titleForm: function() {
      if (this.mode == "new") {
        return "Add Fakultas Baru";
      } else {
        return "Edit Fakultas";
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
