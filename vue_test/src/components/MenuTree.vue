<template>
    <div>
      <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id+''" v-if="item.children.length > 0" :disabled="item.disabled">
      <template slot="title">
        <i :class="item.icon"></i>
        <span slot="title">{{item.menuName}}</span>
      </template>
      <MenuTree :menuList="item.children"></MenuTree>
    </el-submenu>
      <el-menu-item v-else :index="item.url+''" :key="item.url+''" :disabled="item.disabled" :route="item.url">
        <i :class="item.icon"></i>
        <span slot="title">{{item.menuName}}</span>
      </el-menu-item>
    </div>
</template>

<script>
    export default {
      name: "MenuTree",
      props: ['menuList']
    }
</script>

<style scoped>
  .el-menu--collapse span{
    height: 0;
    width: 0;
    overflow: hidden;
    visibility: hidden;
    display: inline-block;
  }
</style>
