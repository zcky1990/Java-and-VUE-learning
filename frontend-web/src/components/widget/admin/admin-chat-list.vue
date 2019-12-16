<template>
  <div class="chat-container">
    <v-container>
      <v-row>
        <v-col cols="12" sm="4" v-for="item in messageList" :key="item.id">
          <v-card class="mx-auto" max-width="344" outlined>
            <v-list-item three-line>
              <v-list-item-content>
                <div class="overline mb-4">Message</div>
                <v-list-item-title class="headline mb-1">{{item.username}}</v-list-item-title>
                <v-list-item-subtitle>{{item.created}}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-card-actions>
              <v-btn text @click="openChat(item.message_id)">Open Chat</v-btn>
              <v-btn text @click="removeMessageList(item.id, item.message_id)">Delete Chat</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <div class="chat-content">
      <chat-content
        ref="chatContainer"
        class="hidden"
        :messageId="messageId"
        :username="username"
        :close-chat="closeChat"
      ></chat-content>
    </div>
  </div>
</template>
<script>
import { EventBus } from "./../../../EventBus.js";
// @ is an alias to /src
import fire from "../../../fire";
import ChatMessageContent from "./chat-admin";
import Util from "../../util";

export default {
  name: "admin-chat-list",
  components: {
    "chat-content": ChatMessageContent
  },
  created() {
    this.username = this.getUserName(this.$session);
  },
  data() {
    return {
      messageList: [],
      messageId: "",
      username: "",
      isShow: false
    };
  },
  methods: {
    setMessage(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    },
    openChat(message_Id) {
      this.$refs.chatContainer.$el.classList.remove("hidden");
      this.messageId = message_Id;
      this.isShow = true;
    },
    closeChat() {
      this.$refs.chatContainer.$el.classList.add("hidden");
      this.messageId = "";
      this.isShow = false;
    },
    getChatMessage() {
      let vm = this;
      const itemsRef = fire.database().ref("messages_list/messages");
      itemsRef.on("value", snapshot => {
        let data = snapshot.val();
        let messageList = [];
        if (data != null) {
          Object.keys(data).forEach(key => {
            messageList.push({
              id: key,
              message_id: data[key].message_id,
              username: data[key].user,
              created: data[key].created
            });
          });
          vm.messageList = messageList;
        }
      });
    },
    removeMessageList(key, message_id) {
      const messageRef = fire.database().ref("messages/"+message_id);
      messageRef.remove();

      const itemsRef = fire.database().ref("messages_list/messages");
      itemsRef.child(key).remove();
      if(this.messageList.length == 1){
        this.messageList = [];
      }
    }
  },
  mounted() {
    this.getChatMessage();
  }
};
</script>
<style scoped>
.no-container {
  padding-left: 0px !important;
  padding-right: 0px !important;
  padding-top: 16px !important;
  padding-bottom: 16px !important;
}
.article-component {
  width: 100%;
}
.article {
  margin-bottom: 0.7em;
}
.img-article_container {
  position: relative;
}
.article-content-card {
  flex-basis: 33.33333333333333%;
  -webkit-box-flex: 0;
  -ms-flex-positive: 0;
  flex-grow: 0;
  max-width: 100%;
  padding: 5px !important;
}
.article-list-container {
  margin-top: 0.7em;
}
.article-list {
  display: flex;
  flex-direction: row;
  margin-bottom: 0.7em;
  justify-content: space-between;
  border-bottom: 1px solid #efefef;
}
.no-elevation {
  -webkit-box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.2), 0 0 0 0 rgba(0, 0, 0, 0.14),
    0 0 0 0 rgba(0, 0, 0, 0.12) !important;
  box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.2), 0 0 0 0 rgba(0, 0, 0, 0.14),
    0 0 0 0 rgba(0, 0, 0, 0.12) !important;
}
.desc-article-list {
  padding-left: 16px;
  padding-right: 16px;
}
.read-more-links {
  text-decoration: none;
  font-size: 1em;
  color: #1eafed !important;
}
.article-headline {
  font-size: 1.2em;
  font-weight: bold;
  font-style: normal;
  margin-bottom: 0.5em;
  margin-top: 0.5em;
}
.article-list-headline {
  font-size: 1.2em;
  font-weight: bold;
  font-weight: 400;
}

.article-container {
  display: flex;
  flex-direction: row;
}
.center {
  margin: 0 auto;
  position: relative;
  text-align: center;
}
.title-btn-container {
  display: flex;
}
.btn {
  background-color: white !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  color: #00d1b2;
  cursor: pointer;
  text-align: center;
  padding-left: 20px;
  padding-right: 20px;
  height: 25px;
}
.btn:hover {
  background-color: #00d1b2 !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  color: white;
  cursor: pointer;
  text-align: center;
  padding-left: 20px;
  padding-right: 20px;
}
.icon-add-btn {
  color: #00d1b2;
}
.icon-add-btn:hover {
  color: white;
}
.add-btn {
  width: 30px;
  height: 30px;
  padding: 2px;
  border: 1px solid #00d1b2;
  border-radius: 50%;
  margin: 2px;
}
.add-btn:hover {
  width: 30px;
  height: 30px;
  padding: 2px;
  background: #00d1b2;
  border: 1px solid #00d1b2;
  border-radius: 50%;
}

.load-more-btn {
  background-color: white !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  font-weight: 700;
  color: #00d1b2;
  cursor: pointer;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
}
.load-more-btn:hover {
  background-color: #00d1b2 !important;
  border: 1px solid #00d1b2;
  border-radius: 25px;
  font-weight: 700;
  color: white;
  cursor: pointer;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
}
.not-found {
  width: 100%;
  text-align: center;
  font-size: 1.8rem;
  font-weight: 400;
  line-height: 1.5;
}
.title-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: nowrap;
  border-bottom: 1px solid #efefef;
}
.btn-container {
  position: relative;
  display: flex;
  flex-direction: column;
}
.add-article-btn {
  position: absolute;
  right: 0;
  top: -25px;
}
.action {
  display: flex;
  align-items: center;
}
.chat-content {
  position: fixed;
  right: 5px;
  bottom: 30px;
  width: 100%;
  z-index: 5;
}

@media only screen and (max-width: 600px) {
  .article-list {
    display: flex;
    flex-direction: column;
    margin-bottom: 0.7em;
  }
  .edit-btn {
    float: left;
    margin-right: 5px;
  }
  .delete-btn {
    margin-left: 5px;
    float: right;
  }
  .btn-container {
    margin-top: 5px;
    flex-direction: row;
    margin-bottom: 5px;
  }
  .action {
    display: flex;
    text-align: center;
    justify-content: center;
  }
}
.btn-no-shadow {
  -webkit-box-shadow: none !important;
  box-shadow: none !important;
  background: none !important;
}
.link-btn {
  color: #3670d2 !important;
}
.no-padding {
  padding: 0px;
}
.article-image-container {
  width: 100%;
}
.no-padding-btm {
  padding-bottom: 0 !important;
}
.no-padding-top {
  padding-top: 0 !important;
}
.article-component {
  display: flex;
  flex-direction: column;
}
.image-article {
  width: 240px;
  height: 128px;
}
.edit-btn {
  margin-bottom: 5px;
  width: 100px;
}
.delete-btn {
  width: 100px;
}
</style>
