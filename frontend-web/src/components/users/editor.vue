<template>
    <div class="editor">
      <ckeditor
        :editor="configEditor.editor"
        v-model="data.article_content"
        :config="configEditor.editorConfig"
      ></ckeditor>
    </div>
</template>
<script>
import ClassicEditor from "@ckeditor/ckeditor5-editor-classic/src/classiceditor";
import Essentials from "@ckeditor/ckeditor5-essentials/src/essentials";
import Autoformat from "@ckeditor/ckeditor5-autoformat/src/autoformat";
import Bold from "@ckeditor/ckeditor5-basic-styles/src/bold";
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import Heading from "@ckeditor/ckeditor5-heading/src/heading";
import Link from "@ckeditor/ckeditor5-link/src/link";
import List from "@ckeditor/ckeditor5-list/src/list";
import Paragraph from "@ckeditor/ckeditor5-paragraph/src/paragraph";
import Alignment from "@ckeditor/ckeditor5-alignment/src/alignment";
// import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
import CodeBlock from "@/lib/ckeditor5-code/src/codeblock";
import Image from "@ckeditor/ckeditor5-image/src/image";
import ImageCaption from "@ckeditor/ckeditor5-image/src/imagecaption";
import ImageStyle from "@ckeditor/ckeditor5-image/src/imagestyle";
import ImageUpload from "@ckeditor/ckeditor5-image/src/imageupload";
import ImageToolbar from "@ckeditor/ckeditor5-image/src/imagetoolbar";

import { Util } from "../util";
import { AXIOS } from "../http-common";

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
        content: "article"
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
      data: {},
      configEditor: {
        editor: ClassicEditor,
        editorConfig: {
          extraPlugins: [MyCustomUploadAdapterPlugin],
          plugins: [
            Essentials,
            Autoformat,
            Bold,
            Italic,
            Heading,
            Link,
            List,
            Paragraph,
            Alignment,
            // BlockQuote,
            CodeBlock,
            Image,
            ImageCaption,
            ImageStyle,
            ImageUpload,
            ImageToolbar
          ],
          toolbar: {
            items: [
              "heading",
              "alignment",
              "bold",
              "italic",
              "link",
              "bulletedList",
              "numberedList",
              // "blockQuote",
              "pre",
              "imageUpload",
              "imageStyle:full",
              "imageStyle:side"
            ]
          }
        }
      }
    };
  },
  components: {},
  methods: {},
  created() {},
  watch: {},
  computed: {}
};
</script>
<style>
.editor {
    background: gainsboro;
}
</style>
