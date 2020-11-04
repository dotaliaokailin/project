<template>
    <div class="welcome-constainer">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>欢迎页面</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>

      </el-card>
    </div>
</template>

<script>
    export default {
        name: "welcome"
    }
</script>

<style scoped lang="less">
  .welcome-constainer{
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;
  }
</style>
