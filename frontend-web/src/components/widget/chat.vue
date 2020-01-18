<template>
  <v-card ref="chatContainer" class="chat-message-container">
    <div class="username-container" v-if="!username">
      You can't chat without a name. What's your name?
      <br />
      <input type="text" placeholder="Name" v-on:keyup.enter="updateUsername" />
    </div>
    <div class="text-message" v-else>
      <div class="username">
        <div class="title">{{username}}</div>
        <div class="icons-container">
          <div class="icons">
            <v-icon color="white" class="minus" small @click="hideMessageChat">minus</v-icon>
          </div>
          <div class="icons">
            <v-icon color="white" @click="deleteMessageChat">close</v-icon>
          </div>
        </div>
      </div>
      <div class="messages">
        <div class="message" v-for="message in messages" v-bind:key="message.text">
          <template>
            <div v-if="message.username == username" class="user-message">
              <v-card class="message-bubble">
                <strong>You:</strong>
                <p>{{message.text}}</p>
              </v-card>
            </div>
            <div v-else class="other-user-message">
              <v-card class="message-bubble">
                <strong>{{message.username}}</strong>
                <p>{{message.text}}</p>
              </v-card>
            </div>
          </template>
        </div>
      </div>

      <div class="chat-message-content">
        <!-- From : {{username}} -->
        <v-text-field
          label="Type your message"
          single-line
          outlined
          solo
          v-on:keyup.enter="sendMessage"
        ></v-text-field>
      </div>
      <hr />
    </div>
  </v-card>
</template>

<script>
// @ is an alias to /src
import fire from "../../fire";

export default {
  name: "chat",
  props: {
    messageId: {
      type: String
    },
    hideChat: {
      type: Function,
      required: true
    },
    deleteChat: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      username: "",
      messages: [],
      user_id: "",
      message_id: "",
      isIdExists: false,
      date: ""
    };
  },
  methods: {
    playSoundNotif() {
      this.playNotifSound();
    },
    hideMessageChat() {
      this.hideChat();
    },
    deleteMessageChat() {
      this.username = "";
      this.messages = [];
      this.isIdExists = false;
      this.deleteChat();
    },
    updateUsername(e) {
      let self = this;
      e.preventDefault();
      if (e.target.value) {
        var user = fire
          .auth()
          .signInAnonymously()
          .catch(function(error) {
            var errorCode = error.code;
            var errorMessage = error.message;
          });
        fire.auth().onAuthStateChanged(function() {
          if (user) {
            self.user_id = user.uid;
          }
        });
        this.username = e.target.value;
      }
    },
    sendMessage(e) {
      e.preventDefault();
      var self = this;
      if (this.isIdExists == false) {
        let date = new Date();
        let date_created =
          date.getDate() +
          "-" +
          (date.getMonth() + 1) +
          "-" +
          date.getFullYear();
        const message = {
          message_id: self.message_id,
          user: self.username,
          created: date_created
        };
        fire
          .database()
          .ref("messages_list/messages")
          .push(message);
        this.isIdExists = true;

        fire
          .database()
          .ref("notif_list/messages")
          .push(message);
      }
      if (e.target.value) {
        const message = {
          username: self.username,
          text: e.target.value
        };
        fire
          .database()
          .ref("messages/" + this.message_id)
          .push(message);
        e.target.value = "";
      }
    },
    getMessage() {
      this.message_id = this.messageId;
      let vm = this;
      const itemsRef = fire.database().ref("messages/" + vm.message_id);
      itemsRef.on("value", snapshot => {
        let data = snapshot.val();
        let messages = [];
        if (data != null) {
          Object.keys(data).forEach(key => {
            messages.push({
              id: key,
              username: data[key].username,
              text: data[key].text
            });
          });
          vm.messages = messages;
        }
      });
    }
  },
  watch: {
    messageId: function() {
      this.getMessage();
    },
    messages: function(newValue, oldValue) {
      if (newValue[newValue.length-1].username != this.username){
        this.playSoundNotif();
      }
    }
  },
  mounted() {
    // this.getMessage();
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
.icons-container {
  display: flex;
  flex-direction: row;
}
.icons {
  margin-left: 5px;
}
.messages {
  height: 300px;
  overflow-y: auto;
  margin-bottom: 5px;
  padding: 10px;
}
.user-message {
  border-radius: 10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: flex-end;
  align-content: flex-end;
  width: 100%;
}
.other-user-message {
  border-radius: 10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: flex-start;
  align-content: flex-start;
  width: 100%;
}
.message-bubble {
  padding: 5px;
}
.minus {
  width: 16px;
  height: 24px;
  border-bottom: 2px solid #fff !important;
}
</style>
