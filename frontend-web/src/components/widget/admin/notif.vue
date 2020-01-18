<template>
  <v-snackbar
      v-model="snackbar"
      :multi-line="multiLineMode"
      :right="right"
      :timeout="3000"
      :top="top"
  >
    {{textMessage}}
    <v-btn
        color="red"
        text
        @click="snackbar = false"
      >
        Close
      </v-btn>
  </v-snackbar>
</template>
<script>
export default {
  name: "notif",
  data() {
    return {
      snackbar: false,
      textMessage: "",
      multiLineMode: true,
      timeOut: 6000,
      verticalMode: true,
      right: true,
      top: true
    };
  },
  methods: {
    showSnackbar: function(message) {
      this.textMessage = message;
      this.snackbar = true;
    },
    hideSnackbar: function() {
      this.snackbar = false;
    }
  }
};
</script>
<style scoped>
</style>
