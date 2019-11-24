<template>
  <v-container d-flex fluid grid-list-xs @change="validBtn">
    <v-layout align-baseline wrap>
      <div class="section-title">
        <div class="title">
          <div class="title-text">
          Visi & Misi
          </div>
        </div>
      </div>
      <v-flex xs12 d-felx>
        <v-text-field v-model="data.id" style="display:none;"></v-text-field>
      </v-flex>
      <v-flex xs12 d-flex>
        <alert-component ref="alert"></alert-component>
      </v-flex>
      <v-flex>
        <div class="asdsa">Visi</div>
        <ckeditor
          :editor="configEditor.editor"
          v-model="data.visi"
          :config="configEditor.editorConfig"
          @input="validBtn"
        ></ckeditor>
      </v-flex>
      <v-flex>
        <div class="asdsa">Misi</div>
        <ckeditor
          :editor="configEditor.editor"
          v-model="data.misi"
          :config="configEditor.editorConfig"
          @input="validBtn"
        ></ckeditor>
      </v-flex>
      <v-flex xs12 d-flex>
        <v-btn
          v-bind="btnOptions"
          class="white--text desc submit-btn"
          @click="submitAsDraft"
        >Simpan Sebagai Draft</v-btn>
        <v-btn v-bind="btnOptions" class="white--text desc submit-btn" @click="submit">Simpan</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import ClassicEditor from "@ckeditor/ckeditor5-editor-classic/src/classiceditor";
import Essentials from "@ckeditor/ckeditor5-essentials/src/essentials";
import Autoformat from "@ckeditor/ckeditor5-autoformat/src/autoformat";
import Bold from "@ckeditor/ckeditor5-basic-styles/src/bold";
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
import Heading from "@ckeditor/ckeditor5-heading/src/heading";
import Image from "@ckeditor/ckeditor5-image/src/image";
import ImageCaption from "@ckeditor/ckeditor5-image/src/imagecaption";
import ImageStyle from "@ckeditor/ckeditor5-image/src/imagestyle";
import ImageToolbar from "@ckeditor/ckeditor5-image/src/imagetoolbar";
import ImageUpload from "@ckeditor/ckeditor5-image/src/imageupload";
import Link from "@ckeditor/ckeditor5-link/src/link";
import List from "@ckeditor/ckeditor5-list/src/list";
import Paragraph from "@ckeditor/ckeditor5-paragraph/src/paragraph";
import Alignment from "@ckeditor/ckeditor5-alignment/src/alignment";

import Alert from "@/components/widget/alert";
import { Util } from "@/components/util";
import { AXIOS } from "@/components/http-common";

class UploadAdapter {
  constructor(loader) {
    this.loader = loader;
  }
  upload() {
    let self = this;
    return new Promise((resolve, reject) => {
      let headers = Util.getDefaultHeaders(Util.getMeta("token"));
      let stringImage = self.loader._reader._reader.result;
      let postBody = {
        image: stringImage,
        content: "visi-misi"
      };
      AXIOS.post("upload_image_string", postBody, { headers })
        .then(response => {
          if (response.data.status == "success") {
            resolve({
              default: response.data.url
            });
          } else {
            reject(response.data.message);
          }
        })
        .catch(e => {
          reject(e);
        });
    });
  }
  abort() {}
}

function MyCustomUploadAdapterPlugin(editor) {
  editor.plugins.get("FileRepository").createUploadAdapter = loader => {
    return new UploadAdapter(loader);
  };
}

export default {
  name: "editor-component",
  data() {
    return {
      updateUrl: "/visi-misi/update",
      getUrl: "/visi-misi/get",
      addUrl: "/visi-misi/add",
      data: {
        id: "",
        visi: "",
        misi: "",
        visiMisiPublishStatus: false,
      },
      categoryDropdown: [],
      configEditor: {
        editor: ClassicEditor,
        editorConfig: {
          extraPlugins: [MyCustomUploadAdapterPlugin],
          plugins: [
            Essentials,
            Autoformat,
            Bold,
            Italic,
            BlockQuote,
            Heading,
            Image,
            ImageCaption,
            ImageStyle,
            ImageToolbar,
            ImageUpload,
            Link,
            List,
            Paragraph,
            Alignment,
            Image,
            ImageToolbar,
            ImageCaption,
            ImageStyle
          ],
          toolbar: {
            items: [
              "heading",
              "alignment",
              "|",
              "bold",
              "italic",
              "|",
              "link",
              "|",
              "undo",
              "redo",
              "|",
              "bulletedList",
              "numberedList",
              "|",
              "blockQuote",
              "|",
              "imageUpload",
              "imageTextAlternative",
              "|",
              "imageStyle:full",
              "imageStyle:side"
            ]
          }
        }
      }
    };
  },
  components: {
    "alert-component": Alert
  },
  methods: {
    validBtn: function() {
      if (
        this.data.visi.length > 0 ||  this.data.misi.length > 0 
      ) {
        this.btnDisabled = false;
      } else {
        this.btnDisabled = true;
      }
    },
    submit: function() {
      this.data.visiMisiPublishStatus = true;
      if ("id" in this.data && this.data.id != "") {
        this.callUpdateRestService(this.data, this.updateUrl);
      } else {
        delete this.data["id"];
        this.callAddRestService(this.data, this.addUrl);
      }
    },
    submitAsDraft: function() {
      this.data.visiMisiPublishStatus = false;
      if ("id" in this.data && this.data.id != "") {
        this.callUpdateRestService(this.data, this.updateUrl);
      } else {
        delete this.data["id"];
        this.callAddRestService(this.data, this.addUrl);
      }
    },
    getRequestHeader: function() {
      this.requestHeader = this.getHeaders(this.$session);
      return this.requestHeader;
    },
    hideAlert: function() {
      this.$refs.alert.hide();
    },
    showSuccessAlert: function(message) {
      this.$refs.alert.setConfig({ type: "success" });
      this.$refs.alert.setMessage(message);
      this.$refs.alert.show();
    },
    showErrorAlert: function(message) {
      this.$refs.alert.setConfig({ type: "error" });
      this.$refs.alert.setMessage(message);
      this.$refs.alert.show();
    },
    callAddRestService: function(model, url) {
      let self = this;
      let headers = this.getRequestHeader();
      this.post(
        url,
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data.response;
            self.data.id = responseData.id;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success add document");
            }
          }
        },
        function(e) {
          self.showErrorAlert(e);
        }
      );
    },
    callUpdateRestService: function(model, url) {
      let self = this;
      let headers = this.getRequestHeader();
      this.put(
        url,
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data.response;
            if (responseData["error_message"] != undefined) {
              self.showErrorAlert(responseData["error_message"]);
            } else {
              self.showSuccessAlert("success edit document");
            }
          }
        },
        function(e) {
          self.showErrorAlert(e);
        }
      );
    },
    getDataList: function() {
      let self = this;
      let headers = this.getRequestHeader();
      this.get(
        this.getUrl,
        headers,
        function(response) {
          if (response.status == 200) {
            let list = response.data.response;
            if(list.length > 0){
              self.data=response.data.response[0];
            }
          }
        },
        function(e) {
          self.showErrorAlert(e);
        }
      );
    },
    resetData: function() {
      this.data = {};
    }
  },
  created() {
    this.getDataList();
  },
  watch: {
    article: function() {
      if (this.article.id != undefined) {
        this.data = this.article;
      }
    }
  },
  computed: {
    btnOptions() {
      const options = {
        disabled: this.btnDisabled,
        color: "#00d1b2"
      };
      return options;
    }
  }
};
</script>
<style>
.title-text {
    font-size: 2rem;
    line-height: 2;
}

</style>
