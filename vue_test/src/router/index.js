import Vue from 'vue';
import Router from 'vue-router';
import Login from '../views/Login';
import Main from '../views/Main';
import NotFound from '../views/NotFound';
import Users from '../views/user/Users';
import Roles from '../views/role/Roles';
import Departments from '../views/depaetment/Departments';
import Menus from  '../views/menu/Menus';
import Icons from '../views/other/Icons';
import Swagger from '../views/other/Swagger';
import Druid from '../views/other/Druid';
import BaiduMap from '../views/other/BaiduMap';
import Welcome from '../views/user/Welcome';
import Shop from '../views/shop/Shop';
import store from '../store/index';
Vue.use(Router);
Vue.use(store);

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
      path: '*',
      meta:{title:'页面未找到'},
      component: NotFound
    },
    {
      path: '/main',
      meta:{title: '首页'},
      component: Main,
      redirect: '/welcome',
      children: [
        {
          path: '/welcome',
          component: Welcome
        },
        {
          path: '/users',
          meta:{title: '用户管理'},
          component: Users
        },
        {
          path: '/roles',
          meta:{title: '角色管理'},
          component: Roles
        },
        {
          path: '/departments',
          meta:{title: '部门管理'},
          component: Departments
        },
        {
          path: '/menus',
          meta:{title: '角色管理'},
          component: Menus
        },
        {
          path: '/icons',
          meta:{title: '图标管理'},
          component: Icons
        },
        {
          path: '/swagger',
          meta: {title: 'Swagger文档'},
          component: Swagger
        },
        {
          path: '/druid',
          meta: {title: 'SQL监控'},
          component: Druid
        },
        {
          path: '/baiduMap',
          meta: {title: '百度地图'},
          component: BaiduMap
        },
        {
          path: '/shop',
          meta: {title: '商品搜索'},
          component: Shop
        }
      ]
    }
  ],
})

//修改动态网页标题 beforeEach 导航钩子，路由改变前触发
router.beforeEach((to,from,next) =>{
  store.commit("activePath", to.path);//activePath提交到store
  next();
})

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router;




