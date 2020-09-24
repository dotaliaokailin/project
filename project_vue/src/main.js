import Vue from 'vue';
import App from './App';
import router from './router';  //路由
import ElementUI from 'element-ui';  //element ui
import 'element-ui/lib/theme-chalk/index.css'; //element ui css
import axios from "axios";  //axios
import VueAxios from 'vue-axios'; // vue-axios
import qs from 'qs'; //json
import md5 from 'md5';// md5 utils

Vue.prototype.$qs = qs;
Vue.prototype.$md5 = md5;

// 直接使用 $axios 即可
Vue.prototype.$axios = axios;

// 代理后端 使每次请求都会带一个 /api 前缀,修改路径在config下的index.js中的proxyTable属性
axios.defaults.baseURL = '/api';

Vue.use(VueAxios, axios);
Vue.use(ElementUI);

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})
