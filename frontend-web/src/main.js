import Vue from 'vue'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import 'vuetify/dist/vuetify.min.css' 
import App from './App.vue'
import router from './router'
import CKEditor from '@ckeditor/ckeditor5-vue';
import '@fortawesome/fontawesome-free/css/all.css' 
import  Util from "./util";
import VueCookies from 'vue-cookies'
import VueSession from 'vue-session'

import L from 'leaflet';
delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});
require('./assets/sass/main.scss');
// set default config
Vue.config.productionTip = false
Vue.use(VueSession)
Vue.use(VueCookies)
Vue.use(CKEditor)
Vue.use(Util)

new Vue({
  icons: {
    iconfont: 'fa',
},
  vuetify: vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
