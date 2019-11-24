<template>
  <v-container>
    <div class="item-form">
      <div class="login-form-container">
        <div class="title-container">
          <div class="title-sub-title-container">
            <div class="title">Login</div>
            <div class="sub-title">Masuk ke Akun Anda</div>
          </div>
        </div>
        <v-layout class="sign-up-container" align-center justify-center>
            <div class="form-container">
              <v-form ref="form" v-model="valid" width="300">
                <v-text-field
                  v-model="username"
                  :rules="useranameRules"
                  label="Username"
                  hint="At least 6 characters"
                  required
                ></v-text-field>

                <v-text-field
                  v-model="password"
                  :rules="passwordRules"
                  :type="show1 ? 'text' : 'password'"
                  @click:append="show1 = !show1"
                  :append-icon="show1 ? 'visibility' : 'visibility_off'"
                  label="Password"
                  hint="At least 8 characters"
                  required
                  @keyup.enter.native="submitLogin" 
                ></v-text-field>

                <v-flex align-center justify-center>
            <div class="submit-btn-container">
              <div class="sign-in-btn-container">
                <div class="sign-in-btn" @click="submitLogin">Sign In</div>
              </div>
            </div>
          </v-flex>
              </v-form>
            </div>
          </v-layout>
      </div>
    </div>
  </v-container>
</template>

<script>
import { EventBus } from "./../../../EventBus.js";

export default {
  name: "admin-login-form",
  data() {
    return {
      valid: false,
      username: "",
      password: "",
      show1: false,
      messageError: "",
      snackBarConfig: {
        color: "error",
        timeout: 6000,
        top: true
      },
      useranameRules: [
        v => !!v || "Username is required",
        v =>
          (v && v.length >= 5) || "Username must be or more than 5 characters"
      ],
      passwordRules: [
        v => !!v || "Password is required",
        v =>
          (v && v.length >= 5) || "Password must be or more than 5 characters"
      ]
    };
  },
  created() {
    let isLogged = this.isLoggin(this.$session);
    if (isLogged) {
      this.$router.push("/admin");
    }
  },
  methods: {
    submitLogin: function() {
      if (this.$refs.form.validate()) {
        var username = this.username;
        var password = this.password;
        var model = {};
        model.username = username;
        model.password = password;
        this.callRestService(model);
      }
    },
    callRestService(model) {
      let self = this;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.post(
        "/admin/login",
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setMessage(responseData.error_message, 1);
            } else {
              self.$session.start();
              self.$session.set("users", responseData.response);
              self.$session.set("jwt", responseData.token);
              self.$session.set("uid", responseData.response.id);
              self.$session.set("username", responseData.response.username);
              self.$session.set("exp_date", responseData.exp_date);
              router.push("/admin");
            }
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    setMessage(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  }
};
</script>
<style scoped>
@media only screen and (max-width: 600px) {
  .sign-up-container {
    flex-direction: column !important;
  }
  .title {
    font-size: 1.4rem !important;
    font-weight: 600;
    color: #DC143C;	
    text-align: center;
  }
}

.item-form {
    text-align: center;
    align-items: center;
    justify-content: center;
    display: flex;
}

.title-container {
  flex-grow: 1;
  text-align: center;
  margin-bottom: 16px;
}
.form-container {
  flex-grow: 0;
  width: 320px;
  margin-left: 2%;
  margin-right: 2%;
}
.sign-up-container {
  flex-direction: row;
}
.submit-btn-container {
  display: flex;
  flex-direction: row;
}
.link-not-sign-up {
  flex-grow: 1;
  z-index: 1;
}
.sign-up-link {
  text-decoration: none;
}
.title {
  text-align: center;
  line-height: 1.5 !important;
  font-size: 1.5rem !important;
  color: #DC143C;
  font-weight: 900;
}
.sub-title {
  text-align: center;
  color: #6c757d;
  font-size: 1rem;
  font-weight: 300;
}
.submit-btn-container {
  display: flex;
  flex-direction: row;
}
.link-not-sign-up {
  flex-grow: 1;
}
.sign-up-link {
  text-decoration: none;
}
.link-btn {
  color: #DC143C;;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  cursor: pointer;
}
.facebook {
  margin-bottom: 20px;
  width: 300px;
  margin-left: 2%;
  margin-right: 2%;
}
.title-sub-title-container {
  width: fit-content;
  margin: 0 auto;
}
.desc {
  font-size: 1.25rem;
  font-weight: 600;
}
.facebook-btn,
.google-btn {
  margin-bottom: 10px;
}
.term-condition-container {
  text-align: center;
  max-width: 600px;
  margin: 0 auto;
  margin-top: 5%;
  line-height: 24px;
  color: #858f96;
}
.sign-in-btn {
  padding: 8px;
  width: 110px;
  border: 1px solid;
  text-align: center;
  border-radius: 15px;
  border: 1px solid #DC143C;
  color: #DC143C;
  cursor: pointer;
}
.sign-in-btn:hover {
  background: #DC143C;
  border: 1px solid #DC143C;
  color: white;
}
</style>
