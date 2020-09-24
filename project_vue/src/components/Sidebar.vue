<template>

</template>

<script>
    export default {
      name: "Sidebar",
      data() {
        return {
          sidebar_isCollapse: false,
          sidebar_content: '收缩侧边栏',
          sidebar_icon: 'el-icon-d-arrow-left',
          tooltip_left: '8%',
        };
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
        menu_item_click: function () {
          console.log(1111)
          this.$router.push('/tools/calendar');
        }
      },
      created: function () {
        this.getDate();
      }
    }
</script>

<style scoped>
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
