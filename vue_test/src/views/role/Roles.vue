<template>
    <div class="role-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!-- body -->
      <el-card class="roles-box-card">
        <el-row>
          <el-input placeholder="请输入角色名查询" v-model="roleName" class="input-with-select">
            <el-button slot="append" icon="el-icon-search" @click="getRolePage(1, pageSize, roleName)"></el-button>
          </el-input>
          <el-button style="margin-left: 10px" icon="el-icon-refresh" @click="reset">重置</el-button>
          <el-button type="primary" style="margin-left: 10px" icon="el-icon-plus">添加</el-button>
          <el-button type="info" style="margin-left: 10px" icon="el-icon-download">导出</el-button>
        </el-row>
        <!-- table -->
        <el-table
          :data="roleList"
          border
          height="620"
          style="width: 100%; margin-top: 20px; margin-bottom: 20px;">
          <el-table-column
            prop="id"
            label="ID"
            width="180">
          </el-table-column>
          <el-table-column
            prop="roleName"
            label="角色名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="status"
            label="启用/禁用"
            width="180">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="0"
                @change="changeStatus(scope.row)">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            prop="remark"
            label="备注">
          </el-table-column>
          <el-table-column
            label="操作"
            width="280">
            <template slot-scope="scope"><!-- 通过作用域插槽获取scope row信息-->
              <el-button type="primary" icon="el-icon-edit"></el-button>
              <el-button type="danger" icon="el-icon-delete"></el-button>
              <el-button type="warning" icon="el-icon-setting"></el-button>
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
    import {findRolePage} from "../../api/roleApi";

    export default {
      name: "Role",
      methods: {
        handleSizeChange(val) {
          this.pageSize = val;
          this.getRolePage(this.currentPage, this.pageSize, this.roleName);
        },
        handleCurrentChange(val) {
          this.currentPage = val;
          this.getRolePage(this.currentPage, this.pageSize, this.roleName);
        },
        //启用禁用
        async changeStatus(row){

        },
        //重置
        reset(){
          this.roleName = '';
          this.getRolePage(1, 10, this.roleName);
        },
        //分页查询
        async getRolePage(currentPage, pageSize, roleName){
          const {data} = await findRolePage(currentPage, pageSize, roleName);
          if(data.status){
            this.roleList = data.data.roleList.records;
            this.total = data.data.roleList.total;
          }else{
            this.$message.error(data.message);
          }
        }
      },
      data(){
        return {
          roleName: '',
          currentPage: 1,
          pageSize: 10,
          total: 0,
          roleList: []
        }
      },
      created() {
        this.getRolePage(this.currentPage, this.pageSize, this.roleName);
      }
    }
</script>

<style scoped lang="less">
  .role-container {
    height: 100%;
    width: 100%;
    padding: 0;
    margin: 0;
  }
  .el-input {
    width: 400px;
  }
</style>
