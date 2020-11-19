export const hasPermission = {
  install (Vue) {
    Vue.directive('hasPermission', {
      bind (element, binding, vnode) {
        let flag = false;//默认不显示
        let buttons = vnode.context.$store.state.buttons;//用户的按钮权限
        let value = binding.value; //绑定的值
        if(buttons.indexOf(value) != -1){
          flag=true;//如果有该权限按钮显示
        }
        if (!flag) {
          element.setAttribute("disabled",true);//鼠标不可点击
          element.classList.add("is-disabled");//按钮隐藏样式
        }
      }
    })
  }
}
