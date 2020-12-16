import Vue from 'vue';
import Router from 'vue-router';
Vue.use(Router);

import ShowBlog from '../components/ShowBlog';
import AddBlog from '../components/AddBlog';
import SingleBlog from '../components/SingleBlog';


const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: ShowBlog
    },
    {
      path: '/AddBlog',
      component: AddBlog
    },
    {
      path: '/blog/:id',
      component: SingleBlog
    }
  ]
});

export default router;
