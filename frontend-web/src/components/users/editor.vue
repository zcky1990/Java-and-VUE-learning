<template>
  <v-container d-flex fluid grid-list-xs >
        <ckeditor
          :editor="configEditor.editor"
          v-model="data.article_content"
          :config="configEditor.editorConfig"
        ></ckeditor>
  </v-container>
</template>
<script>

import ClassicEditorBase from '@ckeditor/ckeditor5-editor-classic/src/classiceditor';
import EssentialsPlugin from '@ckeditor/ckeditor5-essentials/src/essentials';
import AutoformatPlugin from '@ckeditor/ckeditor5-autoformat/src/autoformat';
import BoldPlugin from '@ckeditor/ckeditor5-basic-styles/src/bold';
import ItalicPlugin from '@ckeditor/ckeditor5-basic-styles/src/italic';
import HeadingPlugin from '@ckeditor/ckeditor5-heading/src/heading';
import LinkPlugin from '@ckeditor/ckeditor5-link/src/link';
import ListPlugin from '@ckeditor/ckeditor5-list/src/list';
import ParagraphPlugin from '@ckeditor/ckeditor5-paragraph/src/paragraph';

// import BlockQuote from "@ckeditor/ckeditor5-block-quote/src/blockquote";
// import Image from "@ckeditor/ckeditor5-image/src/image";
// import ImageCaption from "@ckeditor/ckeditor5-image/src/imagecaption";
// import ImageStyle from "@ckeditor/ckeditor5-image/src/imagestyle";
// import ImageUpload from "@ckeditor/ckeditor5-image/src/imageupload";
// import ImageToolbar from "@ckeditor/ckeditor5-image/src/imagetoolbar";
// import CodeBlock from "@/lib/ckeditor5-code/src/codeblock";

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
  props: {
    addUrl: {
      type: String,
      default: ""
    },
    updateUrl: {
      type: String,
      default: ""
    },
    article: {
      type: Object
    }
  },
  data() {
    return {
      data:{},
      configEditor: {
        editor: ClassicEditor,
        editorConfig: {
          extraPlugins: [MyCustomUploadAdapterPlugin],
          plugins: [
            Essentials,
            Bold,
            Italic,
            BlockQuote,
            Heading,
            Image,
            ImageCaption,
            ImageStyle,
            ImageUpload,
            ImageToolbar,
            Link,
            List,
            Paragraph,
            Alignment,
            Image,
            CodeBlock
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
              "bulletedList",
              "numberedList",
              "|",
              "blockQuote",
              "pre",
              "|",
              "imageUpload",
              "imageStyle:full",
              "imageStyle:side"
            ]
          }
        }
      }
    };
  },
  components: {
  },
  methods: {
  },
  created() {
  },
  watch: {
  },
  computed: {
  }
};
</script>
<style>
.reference-title {
  padding-top: 1em;
  font-size: 1.5em;
  font-weight: 600;
}
</style>
