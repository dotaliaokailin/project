<template>
    <div class="druid-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>其他管理</el-breadcrumb-item>
        <el-breadcrumb-item>监控管理</el-breadcrumb-item>
        <el-breadcrumb-item>SQL监控</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <iframe src="http://localhost:9091/druid/sql.html" frameborder="no" border="0" marginwidth="0" marginheight="0"  allowtransparency="yes" style="overflow:hidden;" height="850px" width="100%"></iframe>
      </el-card>
    </div>
</template>

<script>
    export default {
        name: "Druid"
    }
</script>

<style scoped lang="less">
  .druid-container{
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;
  }
</style>
