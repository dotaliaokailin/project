<template>
    <div class="swagger-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>其他管理</el-breadcrumb-item>
        <el-breadcrumb-item>Swagger文档</el-breadcrumb-item>
      </el-breadcrumb>
      <el-tabs type="border-card">
        <el-tab-pane>
          <span slot="label"><i class="el-icon-document"></i>  knife4j</span>
          <iframe src="http://localhost:9091/doc.html" frameborder="no" border="0" marginwidth="0" marginheight="0"  allowtransparency="yes" style="overflow:hidden;" height="850px" width="100%"></iframe>
        </el-tab-pane>
        <el-tab-pane>
          <span slot="label"><i class="el-icon-document"></i>  swagger-ui</span>
          <iframe src="http://localhost:9091/swagger-ui.html" frameborder="no" border="0" marginwidth="0" marginheight="0"  allowtransparency="yes" style="overflow:hidden;" height="850px" width="100%"></iframe>
        </el-tab-pane>
      </el-tabs>
    </div>
</template>

<script>
    export default {
        name: "Swagger"
    }
</script>

<style scoped lang="less">
  .swagger-container{
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
  }
</style>
