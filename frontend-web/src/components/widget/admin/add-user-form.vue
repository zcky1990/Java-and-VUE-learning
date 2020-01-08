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
        <v-select
          :items="filterDropDownList"
          v-model="dataFilter"
          label="Role"
          outlined
          item-text="name"
          item-value="id"
          color="rgb(0, 209, 178)"
          @change="filterData"
        ></v-select>

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
            <td class="text-xs-left">{{ props.item.username }}</td>
            <td class="text-xs-left">{{ props.item.validated }}</td>
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
                v-model="data.username"
                :rules="useranameRules"
                label="Username"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
              <v-text-field
                v-model="data.email"
                :rules="emailRules"
                label="Email"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
              <v-text-field
                v-model="data.password"
                :rules="passwordRules"
                :type="show1 ? 'text' : 'password'"
                @click:append="show1 = !show1"
                :append-icon="show1 ? 'visibility' : 'visibility_off'"
                label="Password"
                hint="At least 8 characters"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>

              <v-select
                :items="dataDropdownList"
                v-model="data.roles"
                label="Role"
                outlined
                item-text="name"
                return-object
                color="rgb(0, 209, 178)"
              ></v-select>
              <v-select v-model="data.status" :items="status" label="Status"></v-select>
              <v-select v-model="data.validated" :items="status" label="Validated"></v-select>
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
  name: "add-user-form",
  data() {
    return {
      show1: false,
      valid: false,
      page: 0,
      dialog: false,
      mode: "new",
      title: "Table User List",
      search: "",
      urlData: {
        createUrl: "/admin/user/create",
        editUrl: "/admin/user/edit",
        getUrl: "/admin/user/detail/",
        deleteUrl: "/admin/user/delete/",
        getListUrl: "/admin/user/find_all",
        getListDropdown: "/roles/get_roles_list"
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
          text: "Username",
          value: "username"
        },
        {
          text: "IsValidated",
          value: "validated"
        },
        {
          text: "Status",
          value: "status"
        },
        {
          text: "Join Date",
          value: "created_date"
        },
        {
          text: "Actions",
          value: "action",
          sortable: false
        }
      ],
      dataTableList: [],
      filterDropDownList: [
        {
          id: "All",
          name: "All Member",
          status: true,
          type: "member"
        }
      ],
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
    this.getDataRoleList();
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
        this.urlData.getListUrl + "?page=" + this.page,
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
    filterData: function() {
      this.page = 0;
      if (this.dataFilter !== "All") {
        this.getFilteredDataList();
      } else {
        this.getDataList();
      }
    },
    getFilteredDataList: function() {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.urlData.getListUrl +
          "?roleId=" +
          this.dataFilter +
          "&page=" +
          this.page,
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
    getDataRoleList: function() {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.urlData.getListDropdown + "?status=true",
        headers,
        function(response) {
          if (response.status == 200) {
            self.dataDropdownList = response.data.response;
            for (let i = 0; i < response.data.response.length; i++) {
              self.filterDropDownList.push(response.data.response[i]);
            }
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
        return "Add new User";
      } else {
        return "Edit User";
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
