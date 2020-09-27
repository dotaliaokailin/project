<template>
  <el-container class="main_container">
    <el-header>
      <div class="main_top_left_box">
        <img src="../assets/image/logo.gif">
        <span class="main_top_left_title">我的博客</span>
      </div>
      <div class="main_top_right_box">
        <el-dropdown>
          <img src="../assets/image/logo.gif">
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-house">系统首页</el-dropdown-item>
            <el-dropdown-item icon="el-icon-ship">交流讨论</el-dropdown-item>
            <el-dropdown-item icon="el-icon-switch-button">注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle_collapse" @click="sidebar_Collapse">
          <el-tooltip class="item" effect="dark" :content="isCollapse ? '展开侧边栏' : '收缩侧边栏'" placement="right">
            <el-button class="arrow_btn" :icon="isCollapse ? 'el-icon-d-arrow-right' : 'el-icon-d-arrow-left'" circle></el-button>
          </el-tooltip>
        </div>
        <el-menu default-active="1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse" :collapse-transition="false">
          <!-- 菜单树-->
          <MenuTree :menuList="menuList"></MenuTree>
        </el-menu>
      </el-aside>
      <el-main>Main</el-main>
    </el-container>
  </el-container>
</template>

<script>
    import MenuTree from '../components/MenuTree';//菜单树
    export default {
      name: "Main",
      data(){
        return {
          isCollapse : false,
          sidebar_i : 'el-icon-d-arrow-left',
          menuList: [
            {
              id: 1,
              parentId: 0,
              menuName: '系统管理',
              url: '',
              icon: 'el-icon-setting',
              orderNum: 1,
              open: 1,
              disabled: false,
              perms: null,
              type: 0,
              children: [
                {
                  id: 253,
                  parentId: 1,
                  menuName: '欢迎页面',
                  url: '/welcome',
                  icon: 'el-icon-star-off',
                  orderNum: 1,
                  open: 0,
                  disabled: false,
                  perms: 'welcome:view',
                  type: 0,
                  children: []
                },
                {
                  id: 226,
                  parentId: 1,
                  menuName: '用户管理',
                  url: '/users',
                  icon: 'el-icon-user',
                  orderNum: 2,
                  open: 0,
                  disabled: false,
                  perms: 'users',
                  type: 0,
                  children: []
                },
                {
                  id: 321,
                  parentId: 1,
                  menuName: '附件管理',
                  url: '/attachments',
                  icon: 'el-icon-picture-outline',
                  orderNum: 2,
                  open: 1,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                },
                {
                  id: 4,
                  parentId: 1,
                  menuName: '菜单权限',
                  url: '/menus',
                  icon: 'el-icon-help',
                  orderNum: 3,
                  open: 0,
                  disabled: false,
                  perms: null,
                  type: 0,
                  children: []
                },
                {
                  id: 235,
                  parentId: 1,
                  menuName: '角色管理',
                  url: '/roles',
                  icon: 'el-icon-postcard',
                  orderNum: 3,
                  open: 0,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                },
                {
                  id: 261,
                  parentId: 1,
                  menuName: '部门管理',
                  url: '/departments',
                  icon: 'el-icon-s-home',
                  orderNum: 3,
                  open: 0,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                },
                {
                  id: 319,
                  parentId: 1,
                  menuName: '公告管理',
                  url: '/notices',
                  icon: 'el-icon-s-flag',
                  orderNum: 4,
                  open: 0,
                  disabled: true,
                  perms: '',
                  type: 0,
                  children: []
                }
              ]
            },
            {
              id: 312,
              parentId: 0,
              menuName: '业务管理',
              url: '',
              icon: 'el-icon-s-goods',
              orderNum: 2,
              open: 0,
              disabled: false,
              perms: null,
              type: 0,
              children: [
                {
                  id: 229,
                  parentId: 312,
                  menuName: '物资管理',
                  url: '',
                  icon: 'el-icon-date',
                  orderNum: 1,
                  open: 1,
                  disabled: false,
                  perms: 'el-icon-date',
                  type: 0,
                  children: [
                    {
                      id: 230,
                      parentId: 229,
                      menuName: '物资入库',
                      url: '/inStocks',
                      icon: 'el-icon-date',
                      orderNum: 1,
                      open: 1,
                      disabled: false,
                      perms: 'el-icon-date',
                      type: 0,
                      children: []
                    },
                    {
                      id: 267,
                      parentId: 229,
                      menuName: '物资资料',
                      url: '/products',
                      icon: 'el-icon-goods',
                      orderNum: 2,
                      open: 0,
                      disabled: false,
                      perms: '',
                      type: 0,
                      children: []
                    },
                    {
                      id: 268,
                      parentId: 229,
                      menuName: '物资类别',
                      url: '/productCategorys',
                      icon: 'el-icon-star-off',
                      orderNum: 2,
                      open: 0,
                      disabled: false,
                      perms: '',
                      type: 0,
                      children: []
                    },
                    {
                      id: 270,
                      parentId: 229,
                      menuName: '物资发放',
                      url: '/outStocks',
                      icon: 'el-icon-goods',
                      orderNum: 5,
                      open: 1,
                      disabled: false,
                      perms: '',
                      type: 0,
                      children: []
                    },
                    {
                      id: 316,
                      parentId: 229,
                      menuName: '物资库存',
                      url: '/stocks',
                      icon: 'el-icon-tickets',
                      orderNum: 5,
                      open: 0,
                      disabled: false,
                      perms: '',
                      type: 0,
                      children: []
                    }
                  ]
                },
                {
                  id: 311,
                  parentId: 312,
                  menuName: '物资流向',
                  url: '',
                  icon: 'el-icon-edit',
                  orderNum: 3,
                  open: 0,
                  disabled: false,
                  perms: null,
                  type: 0,
                  children: [
                    {
                      id: 310,
                      parentId: 311,
                      menuName: '物资去处',
                      url: '/consumers',
                      icon: 'el-icon-edit',
                      orderNum: 1,
                      open: 0,
                      disabled: false,
                      perms: '',
                      type: 0,
                      children: []
                    },
                    {
                      id: 269,
                      parentId: 311,
                      menuName: '物资来源',
                      url: '/suppliers',
                      icon: 'el-icon-coordinate',
                      orderNum: 5,
                      open: 0,
                      disabled: false,
                      perms: '',
                      type: 0,
                      children: []
                    }
                  ]
                }
              ]
            },
            {
              id: 303,
              parentId: 0,
              menuName: '健康报备',
              url: '',
              icon: 'el-icon-platform-eleme',
              orderNum: 3,
              open: 0,
              disabled: false,
              perms: '',
              type: 0,
              children: [
                {
                  id: 273,
                  parentId: 303,
                  menuName: '全国疫情',
                  url: '/map',
                  icon: 'el-icon-s-opportunity',
                  orderNum: 1,
                  open: 1,
                  disabled: false,
                  perms: 'map:view',
                  type: 0,
                  children: []
                },
                {
                  id: 304,
                  parentId: 303,
                  menuName: '健康打卡',
                  url: '/health',
                  icon: 'el-icon-s-cooperation',
                  orderNum: 1,
                  open: 0,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                },
                {
                  id: 305,
                  parentId: 303,
                  menuName: '查看情况',
                  url: '/chakan',
                  icon: 'el-icon-c-scale-to-original',
                  orderNum: 2,
                  open: 1,
                  disabled: false,
                  perms: null,
                  type: 0,
                  children: []
                },
                {
                  id: 272,
                  parentId: 303,
                  menuName: '疫情辟谣',
                  url: '/rumors',
                  icon: 'el-icon-help',
                  orderNum: 5,
                  open: 0,
                  disabled: false,
                  perms: null,
                  type: 0,
                  children: []
                }
              ]
            },
            {
              id: 295,
              parentId: 0,
              menuName: '其他管理',
              url: '',
              icon: 'el-icon-s-marketing',
              orderNum: 5,
              open: 0,
              disabled: false,
              perms: '',
              type: 0,
              children: [
                {
                  id: 297,
                  parentId: 295,
                  menuName: '监控管理',
                  url: '',
                  icon: 'el-icon-warning',
                  orderNum: 1,
                  open: 0,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: [
                    {
                      id: 298,
                      parentId: 297,
                      menuName: 'SQL监控',
                      url: '/druid',
                      icon: 'el-icon-view',
                      orderNum: 1,
                      open: 0,
                      disabled: false,
                      perms: null,
                      type: 0,
                      children: []
                    }
                  ]
                },
                {
                  id: 341,
                  parentId: 295,
                  menuName: '个人博客',
                  url: '/blog',
                  icon: 'el-icon-view',
                  orderNum: 1,
                  open: 0,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                },
                {
                  id: 296,
                  parentId: 295,
                  menuName: 'swagger文档',
                  url: '/swagger',
                  icon: 'el-icon-document',
                  orderNum: 2,
                  open: 0,
                  disabled: false,
                  perms: null,
                  type: 0,
                  children: []
                },
                {
                  id: 318,
                  parentId: 295,
                  menuName: '图标管理',
                  url: '/icons',
                  icon: 'el-icon-star-off',
                  orderNum: 2,
                  open: 1,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                }
              ]
            },
            {
              id: 5,
              parentId: 0,
              menuName: '日志管理',
              url: '',
              icon: 'el-icon-camera',
              orderNum: 6,
              open: 0,
              disabled: false,
              perms: null,
              type: 0,
              children: [
                {
                  id: 271,
                  parentId: 5,
                  menuName: '登入日志',
                  url: '/loginLog',
                  icon: 'el-icon-date',
                  orderNum: 1,
                  open: 0,
                  disabled: false,
                  perms: 'login:log',
                  type: 0,
                  children: []
                },
                {
                  id: 307,
                  parentId: 5,
                  menuName: '操作日志',
                  url: '/logs',
                  icon: 'el-icon-edit',
                  orderNum: 1,
                  open: 1,
                  disabled: false,
                  perms: '',
                  type: 0,
                  children: []
                }
              ]
            }
          ]
        }
      },
      components:{
        //变量名和页面名一样可以简写
        //MenuTree : MenuTree
        MenuTree
      },
      methods: {
        handleOpen(key, keyPath) {
          console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
          console.log(key, keyPath);
        },
        sidebar_Collapse() {
          this.isCollapse = !this.isCollapse;
        },
        getMenuDate() {
          let this_ = this;
          this_.axios({//异步加载
            method: 'get',
            url: '/static/mock/menuJson.txt',
          }).then(function(response){
            this_.menuList = response;
          });
        }
      },
      created() {
        //this.getMenuDate();
      }
    }
</script>

<style lang="less" scoped>
  //主容器
  .main_container{
    height: 100%;
  }

  //顶部
  .el-header{
    background-color: #0747a6;
    color: #FFFFFF;
    display: flex;
    justify-content: space-between;
    padding: 0;
    text-align: center;
    font-size: 20px;
    //顶部左边盒子
    .main_top_left_box{
      display: flex;
      justify-content: center;
      //图片
      img{
        width: 60px;
        height: 60px;
        margin-left: 10px;
      }
      //标题
      .main_top_left_title{
        margin: 15px 10px;
      }
    }
    //顶部右边盒子
    .main_top_right_box{
      display: flex;
      justify-content: flex-end;
      border-radius: 50%;
      margin-right: 10px;
      box-shadow: 0 2px 12px 0 rgba(84, 147, 226, 0.95);
      //图片
      img{
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background-color: #ffe6e9;
        cursor: pointer;
      }
      //下拉菜单
      .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
      }
    }
  }

  //侧边栏
  .el-aside {
    background-color: #FFFFFF;
    .el-menu-vertical-demo{
      border-right: none;
    }
    //展开收缩
    .toggle_collapse{
      display: flex;
      justify-content: flex-end;
      background-color: #FFFFFF;
      padding-right: 12px;
      height: 56px;
      .arrow_btn{
        height: 40px;
        width: 40px;
        margin-top: 8px;
        background-color: #FFFFFF;
      }
    }
  }

  //内容主体
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    line-height: 160px;
  }

</style>
