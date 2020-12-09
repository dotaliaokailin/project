<template>
  <div class="notices-container">
    <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>公告管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <!-- 表单 -->
      <el-form :inline="true" :model="notices" class="users-form-inline" label-width="80px">
        <el-form-item label="标题" >
          <el-input v-model="notices.title" placeholder="请输入标题" clearable></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-select v-model="notices.userId" placeholder="请选择用户名" clearable>
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.username"
              :value="item.id">
              <span style="float: left">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item  style="padding-left: 40px">
          <el-button icon="el-icon-refresh">重置</el-button>
          <el-button type="primary" icon="el-icon-search">查询</el-button>
          <el-button type="success" icon="el-icon-plus">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "Notices",
    data () {
      return {
        notices: {
          title: '',
          userId: '',
        },
        userList : [
          {
            id: 1,
            username: "admin"
          },
          {
            id: 2,
            username: "admin2"
          }
        ]
      }
    },
    methods: {
    }
  }
</script>

<style scoped lang="less">
  .notices-container{
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;
  }
</style>
