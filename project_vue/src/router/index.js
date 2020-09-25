import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Main from '../views/Main'
import Error from '../views/Error'
import Calendar from '../views/tools/Calendar'

Vue.use(Router)

export const constantRoutes = [
  {//登录页
    path: '/',
    meta: {
      role: 'public'
    },
    redirect:'/login',
  },
  {//登录页
    path: '/login',
    name: '登录页面',
    meta: {
      title:'login',
      role: 'public'
    },
    component: Login,
  },
  {
    path: '/error',
    name: '错误页面',
    component: Error,
    meta: {
      role: 'public'
    },
  },
  {//主页
    path: '/main/:name',
    name: '首页',
    component: Main,
    props: true,
    meta: {
      role: 'user'
    },
    children:[
      {//日历
        path: '/tools/calendar',
        name: 'Calendar',
        component: Calendar,
        meta: {
          role: 'user'
        },
        children:[
          {//日历
            path: '/tools/test',
            name: '子菜单1',
            component: Calendar,
            meta: {
              role: 'user'
            },
          },
          {//日历
            path: '/tools/test',
            name: '子菜单2',
            component: Calendar,
            meta: {
              role: 'user'
            },
          },
          {//日历
            path: '/tools/test',
            name: '子菜单3',
            component: Calendar,
            meta: {
              role: 'user'
            },
          },
          {//日历
            path: '/tools/test',
            name: '子菜单4',
            component: Calendar,
            meta: {
              role: 'user'
            },
          }
        ]
      }
    ]
  },
];


const router =  new Router({
  mode: 'history',
  routes: constantRoutes,
})

//修改动态网页标题 beforeEach 导航钩子，路由改变前触发
router.beforeEach((to,from,next) =>{
  window.document.title = to.name;
  if(to.name == 'Login' || to.name == 'Error') {
    next()
  }else{
    let username  = localStorage.getItem("username");
    if(username !== null && username !==undefined && username.length > 0  && username.includes(to.meta.role)){
      //this.$router.addRoutes(this.asyncRoutes);
      next();
    }else{
      next({path:"/error"})
    }
  }
})


const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router;
