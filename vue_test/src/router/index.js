import Vue from 'vue';
import Router from 'vue-router';
import Login from '../views/Login';
import Main from '../views/Main';
import Users from '../views/user/Users';
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
      name: 'Main',
      component: Main,
      children: [
        {
          path: '/users',
          name: 'Users',
          component: Users
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




