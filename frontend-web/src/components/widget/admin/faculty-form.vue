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
            <td class="text-xs-left">{{ props.item.facultyName }}</td>
            <td class="text-xs-left">{{ props.item.isPublished }}</td>
            <td class="text-xs-left">{{ props.item.created_date }}</td>
            <td>
              <v-layout align-center justify-space-around>
                <v-icon @click="editListener(props.item.id)">fas fa-edit</v-icon>
                <v-icon @click="deleteListener(props.item.id)">fas fa-trash</v-icon>
              </v-layout>
            </td>
          </tr>
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
                v-model="data.facultyName"
                :rules="roleDomainRules"
                label="Add your Faculty Name, ex: sejarah"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
            </div>
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
      title: "List Fakultas",
      search: "",
      urlData: {
        createUrl: "/faculty/create",
        editUrl: "/faculty/edit",
        getUrl: "/faculty/",
        deleteUrl: "/faculty/delete/",
        getListUrl: "/faculty/get_all_faculty_list"
      },
      isFormShow: true,
      data: {
        id: "",
        facultyName: "",
        isPublished: false
      },
      tableHeaderList: [
        { text: "Fakultas", value: "Domain" },
        { text: "Is Published", value: "isPublish" },
        { text: "Created Date", value: "created_date" },
        { text: "Action", value: "action" }
      ],
      dataTableList: [],
      pagesPublisStatus: [true, false],
      roleDomainRules: [v => !!v || "Nama Fakultas harus diisi"]
    };
  },
  created() {
    this.getDataList();
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
      let headers = this.getDefaultHeaders(this.getMeta("token"));
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
      let headers = this.getDefaultHeaders(this.getMeta("token"));
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
    deleteData: function(id) {
      let self = this;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
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
      let headers = this.getDefaultHeaders(this.getMeta("token"));
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
      let headers = this.getDefaultHeaders(this.getMeta("token"));
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
    editListener: function(id) {
      this.getData(id);
    },
    deleteListener: function(id) {
      this.deleteData(id);
    },
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    },
    closeDialog: function() {
      this.dialog = false;
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
