import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    buttons:JSON.parse(window.localStorage.getItem("buttons")) || {},
    activePath: window.sessionStorage.getItem("activePath") || '/welcome',
    token: window.localStorage.getItem("token") || '',
  },
  mutations: {
    buttons(state,buttons){
      window.localStorage.setItem('buttons', JSON.stringify(buttons));//将传递的数据先保存到localStorage中
      state.buttons = buttons;// 之后才是修改state中的状态
    },
    activePath(state, activePath){
      window.sessionStorage.setItem('activePath', activePath);
      state.activePath = activePath;// 之后才是修改state中的状态
    },
    token(state, token){
      window.localStorage.setItem("token", token);
      state.token = token;
    }
  },
  actions: {
  },
  modules: {
  }
})
