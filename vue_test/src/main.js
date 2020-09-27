// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import qs from 'qs';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import global_css from './assets/css/global.css';
import axios from "axios";  //axios
import VueAxios from 'vue-axios'; // vue-axios

Vue.prototype.$qs = qs;
// 直接使用 $axios 即可
Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(global_css);
Vue.use(VueAxios, axios);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App),
})
