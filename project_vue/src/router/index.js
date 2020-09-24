import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Main from '../views/Main'
import Error from '../views/Error'

Vue.use(Router)

const router =  new Router({
  mode: 'history',
  routes: [
    {//登录页
      path: '/',
      meta: {
        role: 'public'
      },
      redirect:'/login'
    },
    {//登录页
      path: '/login',
      name: 'Login',
      meta: {
        title:'login',
        role: 'public'
      },
      component: Login
    },
    {
      path: '/error',
      name: 'Error',
      component: Error,
      meta: {
        role: 'public'
      }
    },
    {//主页
      path: '/main/:name',
      name: 'Main',
      component: Main,
      props: true,
      meta: {
        role: 'user'
      }
    }
  ]
})

//修改动态网页标题 beforeEach 导航钩子，路由改变前触发
router.beforeEach((to,from,next) =>{
  window.document.title = to.name;
  if(to.name == 'Login' || to.name == 'Error') {
    next()
  }else{
    let username  = sessionStorage.getItem("username");
    if(username !== null && username !==undefined && username.length > 0  && username.includes(to.meta.role)){
      next();
    }else{
      next({path:"/error"})
    }
  }
})

export default router;
