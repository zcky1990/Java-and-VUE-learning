<template>
  <div class="chat-container">
    <div class="btn-container">
      <v-btn color="pink" dark large absolute bottom right fab @click="showChat">
        <v-icon>chat</v-icon>
      </v-btn>
    </div>
    <chat ref="chatContainer" class="hidden" @interface="hideChat"></chat>
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
      isDeleteHidden: true
    };
  },
  methods: {
    showChat() {
      this.$refs.chatContainer.$el.classList.remove("hidden");
    },
    hideChat() {
      this.$refs.chatContainer.$el.classList.add("hidden");
    }
  }
};
</script>
<style scoped>
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
</style>
