import Vue from 'vue';
import Router from 'vue-router';
import Login from '../views/Login';
import Main from '../views/Main';
import Users from '../views/user/Users';
import Roles from '../views/role/roles';
import Welcome from '../views/user/Welcome';
Vue.use(Router);

const router =  new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      //component: () => import('../views/Login')
      component: Login
    },
    {
      path: '/main',
      name: '首页',
      component: Main,
      children: [
        {
          path: '/welcome',
          name: '欢迎页面',
          component: Welcome
        },
        {
          path: '/users',
          name: '用户管理',
          component: Users
        },
        {
          path: '/roles',
          name: '角色管理',
          component: Roles
        }
      ]
    }
  ],
})

//修改动态网页标题 beforeEach 导航钩子，路由改变前触发
router.beforeEach((to,from,next) =>{
  window.sessionStorage.setItem("activePath", to.path);
  next();
})

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router;




