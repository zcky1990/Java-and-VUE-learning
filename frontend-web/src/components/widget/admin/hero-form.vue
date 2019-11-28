<template>
  <v-container>
    <div class="table-content">
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
        :headers="tableHeaderList"
        :items="dataTableList"
        :items-per-page="10"
        hide-default-footer
        class="elevation-1"
        :search="search"
      >
        <template v-slot:items="props">
          <tr>
            <td class="text-xs-left">{{ props.item.title }}</td>
            <td class="text-xs-left">{{ props.item.subtitle }}</td>
            <td class="text-xs-left">{{ props.item.isPublished }}</td>
            <td>
              <v-layout align-center justify-space-around>
                <v-icon @click="editListener(props.item.id)">fas fa-edit</v-icon>
                <v-icon @click="deleteListener(props.item.id)">fas fa-trash</v-icon>
              </v-layout>
            </td>
          </tr>
        </template>
      </v-data-table>
    </div>
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
              <v-card-text>
                <v-img v-if="isHasImage == true" alt="Avatar" :src="data.url_image" cover></v-img>
                <v-icon v-else size="150" center color="#00d1b2">account_circle</v-icon>
              </v-card-text>
              <div class="upload-file">
                <div>
                  <input type="file" name="displayname" accept="image/*" @change="onFilePicked" />
                </div>
              </div>

              <v-text-field
                v-model="data.title"
                :rules="roleTitleRules"
                label="Type"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
              <v-text-field
                v-model="data.subtitle"
                :rules="roleSubtitleRules"
                label="Name"
                required
                outline
                flat
                color="rgb(0, 209, 178)"
              ></v-text-field>
              <v-select v-model="data.isPublished" :items="status" label="isPublished"></v-select>
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
  name: "category-form",
  data() {
    return {
      valid: false,
      page: 0,
      dialog: false,
      mode: "new",
      title: "Table Hero",
      search: "",
      urlData: {
        createUrl: "/hero/create",
        editUrl: "/hero/edit",
        getUrl: "/hero/",
        deleteUrl: "/hero/delete/",
        getListUrl: "/hero/get-all-hero"
      },
      isFormShow: true,
      data: {
        id: "",
        type: "",
        name: "",
        status: false
      },
      tableHeaderList: [
        { text: "Title", value: "title" },
        { text: "Subtitle", value: "subtitle" },
        { text: "IsPublish", value: "isPublish" },
        { text: "Action", value: "action" }
      ],
      totalPage: 0,
      dataTableList: [],
      status: [true, false],
      roleTitleRules: [v => !!v || "Hero Title is required"],
      roleSubtitleRules: [v => !!v || "Hero Subtitle is required"]
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
        this.urlData.getListUrl + "?status=true&page=" + this.page,
        headers,
        function(response) {
          if (response.status == 200) {
            self.dataTableList = response.data.response;
            self.page++;
            self.totalPage = response.data.totalPage;
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
    onFilePicked(e) {
      let self = this;
      const files = e.target.files;
      if (files[0] !== undefined) {
        let imageName = files[0].name;
        if (imageName.lastIndexOf(".") <= 0) {
          return;
        }
        const fr = new FileReader();
        fr.readAsDataURL(files[0]);
        fr.addEventListener("load", () => {
          let imageFile = files[0];
          self.uploadImage(imageFile);
        });
      }
    },
    uploadImage(imageFile) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      headers["content-type"] = "multipart/form-data";
      let data = new FormData();
      data.append("file", imageFile);
      data.append("content", "hero");
      this.post(
        "upload_image",
        data,
        headers,
        function(response) {
          if (response.data.status == "success") {
            self.data.image_url = response.data.url;
          } else {
            self.setMessage("Upload image failed", 1);
          }
        },
        function() {
          self.setMessage("Upload image failed", 1);
        }
      );
    },
    isImageExists: function() {
      if (this.data.image_url == "" || this.data.image_url == undefined) {
        return false;
      } else {
        return true;
      }
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
    }
  },
  computed: {
    titleForm: function() {
      if (this.mode == "new") {
        return "Add new Category";
      } else {
        return "Edit Category";
      }
    },
    isHasImage() {
      return this.isImageExists();
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
