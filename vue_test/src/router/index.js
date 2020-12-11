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
import Notices from '../views/other/Notices';
Vue.use(Router);
Vue.use(store);

//进度条
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
//进度条configure
NProgress.configure({
  easing: 'ease',  // 动画方式
  speed: 500,  // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3 // 初始化时的最小百分比
})

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
        },
        {
          path: '/notices',
          meta: {title: '公告管理'},
          component: Notices
        }
      ]
    }
  ],
})

//修改动态网页标题 beforeEach 导航钩子，路由改变前触发
router.beforeEach((to,from,next) =>{
  // 每次切换页面时，调用进度条
  NProgress.start();
  // 若加载时间长且不定，担心进度条走完都没有加载完，可以调用
  NProgress.inc();//这会以随机数量递增，且永远达不到100%，也可以设指定增量
  store.commit("activePath", to.path);//activePath提交到store
  next();
})
//当路由进入后：关闭进度条
router.afterEach(() => {
  // 在即将进入新的页面组件前，关闭掉进度条
  NProgress.done();
})
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router;




