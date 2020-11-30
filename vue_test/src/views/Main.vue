<template>
  <el-container class="main_container">
    <el-header :style="{'background-color': backgroundColor}">
      <div class="main_top_left_box">
        <img src="../assets/image/logo.gif">
        <span class="main_top_left_title">管理后台</span>
      </div>
      <div class="main_top_center_box">
        <el-color-picker
          @change='handleChange'
          v-model="color"
          show-alpha
          size="mini"
          :predefine="predefineColors">
        </el-color-picker>
        <div class="main_top_right_box">
          <el-dropdown @command="handleCommand">
            <img src="../assets/image/logo.gif">
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-house" command="1">系统首页</el-dropdown-item>
              <el-dropdown-item icon="el-icon-ship" command="2">交流讨论</el-dropdown-item>
              <el-dropdown-item icon="el-icon-switch-button" command="3">注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle_collapse" @click="sidebar_Collapse">
          <el-tooltip class="item" effect="dark" :content="isCollapse ? '展开侧边栏' : '收缩侧边栏'" placement="right">
            <el-button class="arrow_btn" :icon="isCollapse ? 'el-icon-d-arrow-right' : 'el-icon-d-arrow-left'" circle></el-button>
          </el-tooltip>
        </div>
        <el-menu :default-active="activePath" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse" :collapse-transition="false" :router="true">
          <!-- 菜单树-->
          <MenuTree :menuList="menuList"></MenuTree>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
    import {menuTree} from '../api/menuApi';
    import MenuTree from '../components/MenuTree';//菜单树
    import {logout} from '../api/userApi';
    export default {
      name: "Main",
      data(){
        return {
          activePath : '/welcome',
          isCollapse : false,
          sidebar_i : 'el-icon-d-arrow-left',
          menuList: [],
          color: window.localStorage.getItem("backgroundColor") == "" ? 'rgba(255, 69, 0, 0.68)' : window.localStorage.getItem("backgroundColor"),
          backgroundColor: '#409EFF',
          predefineColors: [
            '#ff4500',
            '#ff8c00',
            '#ffd700',
            '#90ee90',
            '#00ced1',
            '#1e90ff',
            '#c71585',
            'rgba(255, 69, 0, 0.68)',
            'rgb(255, 120, 0)',
            'hsv(51, 100, 98)',
            'hsva(120, 40, 94, 0.5)',
            'hsl(181, 100%, 37%)',
            'hsla(209, 100%, 56%, 0.73)',
            '#c7158577'
          ]
        }
      },
      components:{
        //变量名和页面名一样可以简写
        //MenuTree : MenuTree
        MenuTree
      },
      methods: {
        //监听颜色主题
        handleChange(value) {
          if(undefined != value){
            const reg = /[0-9]\d+/g;
            const getArr = value.match(reg);
            let hexStr = '#'+((getArr[0] << 16) | (getArr[1]  << 8) | getArr[2] ).toString(16);
            window.localStorage.setItem("backgroundColor", hexStr);
            this.backgroundColor = hexStr;
          }
        },
        handleCommand(command) {
          if(command == 3){
            logout();
            this.$message('成功登出');
          }else if(command == 1){
            this.$router.push('/welcome');
          }
        },
        handleOpen(key, keyPath) {
          //console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
          //console.log(key, keyPath);
        },
        sidebar_Collapse() {
          this.isCollapse = !this.isCollapse;
        },
        async getMenuDate() {
          const {data}= await menuTree();
          if(data.status){
            this.menuList = data.data.menuTree;//侧边栏
            this.$store.commit("buttons", data.data.buttonTree);//按钮权限
          }
        }
      },
      created() {
        //左侧栏的点中颜色
        let path = window.sessionStorage.getItem("activePath");
        if(path == '/main'){//如果跳main页面则跳到首页welcome
          path = '/welcome';
        }
        this.activePath = path;
        this.getMenuDate();
        //主题颜色
        this.backgroundColor = window.localStorage.getItem("backgroundColor");
      },
      watch: {//监听url变化,来选中侧边栏
        '$store.state.activePath': function () {
          this.activePath = window.sessionStorage.getItem("activePath");
        }
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
    //顶部中部盒子
    .main_top_center_box {
      display: flex;
      .el-color-picker {
        margin-top: 32px;
        margin-right: 10px;
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

  //主题颜色样式
  .el-color-picker{
    float: right;
    position: fixed;
    bottom: 0px;
    right: -10px;
  }
</style>
