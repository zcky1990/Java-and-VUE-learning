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
            <td class="text-xs-left">{{ props.item.type }}</td>
            <td class="text-xs-left">{{ props.item.name }}</td>
            <td class="text-xs-left">{{ props.item.status }}</td>
            <td class="text-xs-left">{{ props.item.created_date }}</td>
          </tr>
        </template>
        <template v-slot:item.action="{ item }">
          <v-icon small class="mr-2" @click="editListener(item)">edit</v-icon>
          <v-icon small @click="deleteListener(item)">delete</v-icon>
        </template>
      </v-data-table>
    </v-card>
    <v-dialog v-model="dialog" persistent width="600">
      <v-card>
        <v-container>
          <div ref="formContainer" class="form-container">
            <div class="title">{{titleForm}}</div>
            <v-form ref="form" v-model="valid" width="300">
              <v-text-field
                v-model="data.id"
                label="Id"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
                class="hidden"
              ></v-text-field>
              <v-text-field
                v-model="data.type"
                :rules="roleTypeRules"
                label="Type"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
              <v-text-field
                v-model="data.name"
                :rules="roleNameRules"
                label="Name"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
              <v-select
                :items="dataDropdownList"
                v-model="data.access_level"
                label="Access Level"
                outlined
                item-text="level"
                return-object
                color="rgb(0, 209, 178)"
              ></v-select>
              <v-select v-model="data.status" :items="status" label="Status"></v-select>
              <v-flex align-center justify-center>
                <div class="form-bttm-container">
                  <div class="btn-container">
                    <v-btn class="white--text desc" color="#00d1b2" @click="dialog=!dialog">Cancel</v-btn>
                    <v-btn class="white--text desc" color="#00d1b2" @click="submitForm">Submit</v-btn>
                  </div>
                </div>
              </v-flex>
            </v-form>
          </div>
        </v-container>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { EventBus } from "./../../../EventBus.js";

export default {
  name: "roles-form",
  data() {
    return {
      valid: false,
      page: 0,
      dialog: false,
      mode: "new",
      title: "Table Role List",
      search: "",
      urlData: {
        createUrl: "/roles/create",
        editUrl: "/roles/edit",
        getUrl: "/roles/",
        deleteUrl: "/roles/delete/",
        getListUrl: "/roles/get_roles_list",
        getListDropdown: "/access_level/get_access_level_list"
      },
      isFormShow: true,
      data: {
        id: "",
        type: "",
        name: "",
        status: false,
        access_level: {
          id: "",
          level: "",
          description: ""
        }
      },
      tableHeaderList: [
        {
          text: "Type",
          value: "type"
        },
        {
          text: "Name",
          value: "name"
        },
        {
          text: "Status",
          value: "status"
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
      dataDropdownList: [],
      status: [true, false],
      roleTypeRules: [v => !!v || "Role type is required"],
      roleNameRules: [v => !!v || "Role Name is required"]
    };
  },
  created() {
    this.getDataList();
    this.getDataAccessList();
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
        this.urlData.getListUrl + "?status=true&page=" + this.page,
        headers,
        function(response) {
          if (response.status == 200) {
            self.dataTableList = response.data.response;
            self.page++;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    getDataAccessList: function() {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.urlData.getListDropdown,
        headers,
        function(response) {
          if (response.status == 200) {
            self.dataDropdownList = response.data.response;
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
    getRequestHeader: function() {
      this.requestHeader = this.getHeaders(this.$session);
      return this.requestHeader;
    }
  },
  computed: {
    titleForm: function() {
      if (this.mode == "new") {
        return "Add new Role";
      } else {
        return "Edit Role";
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
</style>
