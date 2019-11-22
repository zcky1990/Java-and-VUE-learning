<template>
  <v-container>
    <div v-if="!username">
      <editor></editor>You can't chat without a name. What's your name?
      <br />
      <input type="text" placeholder="Name" v-on:keyup.enter="updateUsername" />
    </div>
    <div v-else>
      <div>
        From : {{username}}
        <br />Message:
        <br />
        <textarea
          name
          id
          cols="30"
          rows="10"
          placeholder="New Message"
          v-on:keyup.enter="sendMessage"
        ></textarea>
      </div>
      <hr />
      <div class="messages">
        <h3>Messages</h3>
        <div class="message" v-for="message in messages" v-bind:key="message.text">
          <strong>{{message.username}}</strong>
          <p>{{message.text}}</p>
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
// @ is an alias to /src
import fire from "../../fire";
import Editor from "../../components/users/editor";

export default {
  name: "home",
  components: {
    editor: Editor
  },
  data() {
    return {
      username: "",
      messages: [],
      user_id: ""
    };
  },
  methods: {
    updateUsername(e) {
      let self = this;
      e.preventDefault();
      if (e.target.value) {
        var user = fire.auth().signInAnonymously();
        fire.auth().onAuthStateChanged(function() {
          if (user) {
            self.user_id = user.uid;
          } else {
            // User is signed out.
          }
        });

        this.username = e.target.value;
      }
    },
    sendMessage(e) {
      e.preventDefault();
      var self = this;
      if (e.target.value) {
        alert(self.username + " " + self.user_id);
        const message = {
          username: self.username,
          text: e.target.value
        };
        fire
          .database()
          .ref("messages/test")
          .push(message);
        e.target.value = "";
      }
    }
  },
  mounted() {
    let vm = this;
    const itemsRef = fire.database().ref("messages/test");
    itemsRef.on("value", snapshot => {
      let data = snapshot.val();
      let messages = [];
      Object.keys(data).forEach(key => {
        messages.push({
          id: key,
          username: data[key].username,
          text: data[key].text
        });
      });
      vm.messages = messages;
    });
  }
};
</script>
