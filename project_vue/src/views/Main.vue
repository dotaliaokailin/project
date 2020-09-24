<template>
    <div id="main">
      <el-container style="height: 100%;">
        <el-header>Header</el-header>
        <el-container>
          <el-aside :width="main_sidebar_width">
            <div id="sidebar">
              <el-menu default-active="1-4-1" class="el-menu-vertical-demo" :collapse="sidebar_isCollapse">
                <el-submenu index="1">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span slot="title">导航一</span>
                  </template>
                  <el-menu-item-group>
                    <span slot="title">分组一</span>
                    <el-menu-item index="1-1"><router-link to="/tools/calendar">选项1</router-link></el-menu-item>
                    <el-menu-item index="1-2">选项2</el-menu-item>
                  </el-menu-item-group>
                  <el-menu-item-group title="分组2">
                    <el-menu-item index="1-3">选项3</el-menu-item>
                  </el-menu-item-group>
                  <el-submenu index="1-4">
                    <span slot="title">选项4</span>
                    <el-menu-item index="1-4-1">选项1</el-menu-item>
                  </el-submenu>
                </el-submenu>
                <el-menu-item index="2">
                  <i class="el-icon-menu"></i>
                  <span slot="title">导航二</span>
                </el-menu-item>
                <el-menu-item index="3" disabled>
                  <i class="el-icon-document"></i>
                  <span slot="title">导航三</span>
                </el-menu-item>
                <el-menu-item index="4">
                  <i class="el-icon-setting"></i>
                  <span slot="title">导航四</span>
                </el-menu-item>
                <!-- 伸缩展开按钮
                <el-tooltip id="icon-arrow-sidebar" class="item" effect="dark" :content="sidebar_content" placement="right" :style="{'left':tooltip_left}">
                  <el-button :icon="sidebar_icon" circle @click="tooltip_sidebar"></el-button>
                </el-tooltip>
                -->
              </el-menu>
            </div>
          </el-aside>
          <el-container>
            <el-main><router-view></router-view></el-main>
            <el-footer><Foot></Foot></el-footer>
          </el-container>
        </el-container>
      </el-container>
    </div>
</template>

<script>
    //底部栏
    import Foot from '../components/Foot'
    export default {
      name: "Main",
      props: ['name'],
      components: {
        Foot: Foot,
      },
      data(){
        return {
          main_sidebar_width: '201px',
          sidebar_isCollapse: false,
          sidebar_content: '收缩侧边栏',
          sidebar_icon: 'el-icon-d-arrow-left',
          tooltip_left: '8%',
        }
      },
      methods: {
        tooltip_sidebar(){//伸展收缩侧边栏
          let this_ = this;
          this_.tooltip_left = this_.sidebar_isCollapse == 0 ? '0.5%' : '8%';
          this_.sidebar_content = this_.sidebar_content == '展开侧边栏' ? '收缩侧边栏' : '展开侧边栏';
          this_.sidebar_icon = this_.sidebar_icon == 'el-icon-d-arrow-right' ? 'el-icon-d-arrow-left' : 'el-icon-d-arrow-right';
          this_.sidebar_isCollapse = this_.sidebar_isCollapse ? false : true;
          this_.main_sidebar_width = this_.sidebar_isCollapse ? '40px' : '201px';
        },
        getDate: function(){
          this.axios({//异步加载
            method: 'get',
            url: 'http://localhost:9999/static/mock/data.json',
          }).then(function(response){
            console.log(response);
          });
        },
        menu_item_click: function (item) {
          console.log(item)
        }
      },
      created: function () {
        this.getDate();
      }
    }
</script>

<style scoped>
  #main {
    height: 100%;
    width: 100%;
  }
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  #sidebar,ul{
    height: 100%;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    height: 100%;
  }
  #icon-arrow-sidebar {
    position: fixed;
    bottom: 0%;
  }
</style>


