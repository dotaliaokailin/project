<template>
    <div class="dept-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>部门管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!-- body -->
      <el-card class="roles-box-card">
        <el-row>
          <el-input placeholder="请输入部门名称查询" v-model="name" class="input-with-select" clearable>
            <el-button slot="append" icon="el-icon-search" @click="getDeptPage(1, pageSize, name)"></el-button>
          </el-input>
          <el-button size="large" style="margin-left: 10px" icon="el-icon-refresh" @click="reset">重置</el-button>
          <el-button type="primary" style="margin-left: 10px" icon="el-icon-plus" >添加</el-button>
          <el-button type="info" style="margin-left: 10px" icon="el-icon-download">导出</el-button>
        </el-row>
        <el-table
          :data="deptList"
          height="620"
          border
          style="width: 100%; margin-top: 20px; margin-bottom: 20px;">
          <el-table-column
            prop="id"
            label="ID"
            width="80">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="办公电话"
            width="180">
          </el-table-column>
          <el-table-column
            prop="name"
            label="部门名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="deptCount"
            label="部门人数"
            width="80">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="modifiedTime"
            label="修改时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址"
            width="507">
          </el-table-column>
          <el-table-column
            label="操作"
            width="250">
            <template slot-scope="scope"><!-- 通过作用域插槽获取scope row信息-->
              <el-button size="small" type="primary" icon="el-icon-edit">编辑</el-button>
              <el-button size="small" type="danger" icon="el-icon-delete">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </el-card>
    </div>
</template>

<script>
    import {getDeptPage} from "../../api/departmentApi";

    export default {
      name: "Departments",
      data(){
        return{
          name: "",
          currentPage: 1,
          pageSize: 10,
          total: 0,
          deptList: []
        }
      },
      created() {
        this.getDeptPage(this.currentPage, this.pageSize, this.name);
      },
      methods:{
        handleSizeChange(val) {
          this.pageSize = val;
          this.getDeptPage(this.currentPage, this.pageSize, this.roleName);
        },
        handleCurrentChange(val) {
          this.currentPage = val;
          this.getDeptPage(this.currentPage, this.pageSize, this.roleName);
        },
        //分页查询部门
        async getDeptPage(currentPage, pageSize, name){
          const {data} = await getDeptPage(currentPage, pageSize, name);
          if(data.status){
            this.total = data.data.deptList.total;
            this.deptList = data.data.deptList.records;
          }else{
            this.$message.error(data.message);
          }
        },
        //重置
        reset(){
          this.name = '';
          this.getDeptPage(1, 10, this.name);
        }
      }
    }
</script>

<style scoped lang="less">
  .dept-container {
    height: 100%;
    width: 100%;
    padding: 0;
    margin: 0;
  }
  .el-input {
    width: 400px;
  }
</style>
