<template>
  <div class="chat-container">
    <div class="btn-container">
      <v-btn class="btn-chat higher" color="pink" dark large fixed bottom right fab @click="showChat">
        <v-icon>chat</v-icon>
      </v-btn>
    </div>
    <div class="chat-content" v-if="isShow">
      <chat
        ref="chatContainer"
        class="hidden"
        :message-id="messageId"
        :hide-chat="hideChat"
        :delete-chat="deleteChat"
      ></chat>
    </div>
  </div>
</template>

<script>
import Chat from "./chat";
// @ is an alias to /src
export default {
  name: "chat-btn",
  components: {
    chat: Chat
  },
  data() {
    return {
      isNewChat: true,
      isDestroyed: false,
      messageId: ""
    };
  },
  methods: {
    setMessageId() {
      let date = new Date();
      let date_created =
        date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
      this.date = date_created;
      let chatId =
        date.getDate() +
        "-" +
        (date.getMonth() + 1) +
        "-" +
        (date.getFullYear() + "_" + date.getUTCMilliseconds());
      this.messageId = "user_chat_" + chatId;
    },
    isShow() {
      return this.messageId != "" ? true : false;
    },
    showChat() {
      if (this.isNewChat) {
        this.setMessageId();
      }
      this.isNewChat = false;
      this.$refs.chatContainer.$el.classList.remove("hidden");
    },
    hideChat() {
      this.$refs.chatContainer.$el.classList.add("hidden");
    },
    deleteChat() {
      this.hideChat();
      this.isNewChat = true;
    }
  }
};
</script>
<style scoped>
@media only screen and (max-width: 640px) {
  .higher{
  bottom:80px !important;
}
}

.chat-message-container {
  position: absolute;
  right: 10px;
  bottom: -32px;
  z-index: 20;
  background: white;
  min-height: 300px;
  width: 300px;
}
.hidden {
  display: none;
}
.username-container {
  padding: 20px;
}
.chat-message-content {
  padding: 5px;
  position: relative;
  background: rgb(220, 20, 60);
  height: 65px;
}
.text-message {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
}
.username {
  background: rgb(220, 20, 60);
  display: flex;
  padding: 10px;
  font-size: 1.4em;
  color: white;
  justify-content: space-between;
}
.messages {
  height: 300px;
  overflow-y: auto;
  margin-bottom: 5px;
  padding: 10px;
}
.user-message {
  border-radius: 10px;
  width: fit-content;
  float: right;
  margin-bottom: 10px;
}
.other-user-message {
  border-radius: 10px;
  width: fit-content;
  float: left;
  margin-bottom: 10px;
}
.message-bubble {
  padding: 5px;
}
.chat-content {
  position: fixed;
  right: 5px;
  bottom: 30px;
  width: 100%;
  z-index: 5;
}
</style>
