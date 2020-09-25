<template>
    <div id="main">
      <el-container style="height: 100%;">
        <el-header>Header</el-header>
        <el-container>
          <el-aside :width="main_sidebar_width">
            <div id="sidebar">
              <el-menu  class="el-menu-vertical-demo" :collapse="sidebar_isCollapse" :default-active="'0-1'">
                <el-submenu :index="index+''"  v-for="(item,index) in this.$router.options.routes[3].children" :key="index">
                  <template slot="title"><i class="el-icon-menu"></i>{{item.name}}{{index}}</template>
                  <el-menu-item :index="index+'-'+index_child" v-for="(item_child, index_child) in item.children" :key="index_child">
                    {{item_child.name}}
                  </el-menu-item>
                </el-submenu>
                <el-tooltip id="icon-arrow-sidebar" class="item" effect="dark" :content="sidebar_content" placement="right" :style="{'left':tooltip_left}">
                  <el-button :icon="sidebar_icon" circle @click="tooltip_sidebar"></el-button>
                </el-tooltip>
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
          this_.tooltip_left = this_.sidebar_isCollapse ? '8%' : '0.5%';
          this_.sidebar_content = this_.sidebar_content == '展开侧边栏' ? '收缩侧边栏' : '展开侧边栏';
          this_.sidebar_icon = this_.sidebar_icon == 'el-icon-d-arrow-right' ? 'el-icon-d-arrow-left' : 'el-icon-d-arrow-right';
          this_.sidebar_isCollapse = this_.sidebar_isCollapse ? false : true;
          this_.main_sidebar_width = this_.sidebar_isCollapse ? '65px' : '201px';
        },
        getDate: function(){
          let this_ = this;
          this_.axios({//异步加载
            method: 'get',
            url: 'http://localhost:9999/static/mock/data.json',
          }).then(function(response){
            console.log(response);
            console.log(this_.$router.options);
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


