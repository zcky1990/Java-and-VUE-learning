import Vue from "vue";
import Vuetify from 'vuetify'
import VueSession from "vue-session";
import App from "./App.vue";
import router from "./router";
import  Util from "./util";
import "vuetify/dist/vuetify.min.css"; 
import CKEditor from "@ckeditor/ckeditor5-vue";

Vue.use(Vuetify);
Vue.config.productionTip = false;
Vue.use(VueSession);
Vue.use(CKEditor);
Vue.use(Util);

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
