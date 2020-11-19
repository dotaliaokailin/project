import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    buttons:JSON.parse(window.localStorage.getItem("buttons")) || {},
  },
  mutations: {
    buttons(state,buttons){
      window.localStorage.setItem('buttons', JSON.stringify(buttons));//将传递的数据先保存到localStorage中
      state.buttons = buttons;// 之后才是修改state中的状态
    }
  },
  actions: {
  },
  modules: {
  }
})
