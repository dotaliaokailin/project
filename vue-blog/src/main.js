// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import VueResource from 'vue-resource';//npm install vue-resource --save  //下载vue.$http
import router from './router';
import axios from './util/axios';

Vue.prototype.$axios = axios;
Vue.use(VueResource);
Vue.config.productionTip = false;
//钩子函数,自定义函数
Vue.directive("rainbow",{
  bind(el, binding, vnode){
    el.style.color = "#" + Math.random().toString(16).slice(2, 8);
  }
});
//自定义函数
Vue.directive("theme", {
  bind(el, binding, vnode){
    if(binding.value == 'wide'){
      el.style.maxWidth = '1280px';
    }else if(binding.value == 'narrow'){
      el.style.maxWidth = '520px';
    }
    if(binding.arg == 'color'){
      el.style.background = '#eeeeee';
      el.style.padding = '20px';
    }
  }
});
//自定义过滤器
// Vue.filter("toUpperCase", function (value) {
//   return value.toUpperCase();//变大写
// });
// Vue.filter("snippet", function (value) {
//   return value.slice(0, 100) + '...';
// });

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: { App },
  router,
  template: '<App/>'
})
