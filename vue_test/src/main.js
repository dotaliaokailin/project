// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import qs from 'qs';
import ElementUI from 'element-ui';//element ui
import 'element-ui/lib/theme-chalk/index.css';//element ui css
import global_css from './assets/css/global.css';//全局CSS
import axios from "axios";  //axios
import VueAxios from 'vue-axios'; // vue-axios
import store from './store';
import moment from 'moment';//时间
import 'default-passive-events';
import $ from 'jquery';

Vue.prototype.Message=ElementUI.Message;
Vue.prototype.$moment = moment;
// 直接使用 $axios 即可
Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(global_css);
Vue.use(VueAxios, axios);



Vue.filter('moment', function (value, formatString) {
  formatString = formatString || 'YYYY年MM月DD日';
  // return moment(value).format(formatString); // value可以是普通日期 20170723
  return moment.unix(value).format(formatString); // 这是时间戳转时间
});


/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
