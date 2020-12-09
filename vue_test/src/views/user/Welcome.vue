<template>
    <div class="welcome-constainer">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>欢迎页面</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-row :span="24" style="justify-content: space-between; display: flex;">
          <el-col :span="10" style="justify-content: flex-start;display: block;">
            <el-carousel :interval="4000" type="card" height="200px">
              <el-carousel-item v-for="item in 6" :key="item">
                <h3 class="medium">{{ item }}</h3>
              </el-carousel-item>
            </el-carousel>
          </el-col>
          <el-col :span="10" style="justify-content: flex-end;display: flex;">
            <el-table
              :data="userData"
              border
              style="width: 100%">
              <el-table-column
                prop="avatar"
                label="头像"
                align="center"
                width="170">
                <template slot-scope="scope">
                  <el-avatar :src="scope.row.avatar" :size="150"></el-avatar>
                </template>
              </el-table-column>
              <el-table-column
                prop="username"
                label="姓名"
                align="center"
                width="80">
              </el-table-column>
              <el-table-column
                prop="nickname"
                align="center"
                label="昵称">
              </el-table-column>
              <el-table-column
                prop="department"
                align="center"
                label="部门">
              </el-table-column>
              <el-table-column
                prop="role"
                align="center"
                label="角色">
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-card>
    </div>
</template>

<script>
    export default {
      name: "welcome",
      data() {
        return {
          userData: [{
            avatar: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
            username: "管理员",
            nickname: "炒鸡管理员",
            department: "部门",
            role: "角色",
          }]
        }
      }
    }
</script>

<style scoped lang="less">
  .welcome-constainer{
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
