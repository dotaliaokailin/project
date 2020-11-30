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
          <el-input placeholder="请输入角色名查询" v-model="roleName" class="input-with-select" clearable>
            <el-button slot="append" icon="el-icon-search" @click="getRolePage(1, pageSize, roleName)"></el-button>
          </el-input>
          <el-button style="margin-left: 10px" icon="el-icon-refresh" @click="reset">重置</el-button>
          <el-button type="primary" style="margin-left: 10px" icon="el-icon-plus" @click="show(-1)" v-has-permission="'role:add'">添加</el-button>
          <el-button type="info" style="margin-left: 10px" icon="el-icon-download" @click="exportExcel" v-has-permission="'role:export'">导出</el-button>
        </el-row>
        <!-- table -->
        <el-table
          :data="roleList"
          border
          height="626"
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
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="启用/禁用"
            width="85">
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
            width="695"
            prop="remark"
            label="备注">
          </el-table-column>
          <el-table-column
            label="操作"
            width="317">
            <template slot-scope="scope"><!-- 通过作用域插槽获取scope row信息-->
              <el-button type="warning" size="small" icon="el-icon-setting" @click="authorizationRole(scope.row.id)" v-has-permission="'role:authority'">授权</el-button>
              <el-button type="primary" size="small" icon="el-icon-edit" @click="show(scope.row.id)" v-has-permission="'role:update'">编辑</el-button>
              <el-button type="danger" size="small" icon="el-icon-delete" @click="del(scope.row, scope.$index)" v-has-permission="'role:delete'">删除</el-button>
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
      <!-- 新增编辑弹框子组件 -->
      <add-role :addOrUpdateVisible="addOrUpdateVisible" @changeShow="showAddOrUpdate"  :id="id"></add-role>
      <!-- 授权弹框子组件 -->
      <auth-role :authVisible="authVisible" @changeShow="authRoleShow"  :id="id"></auth-role>
    </div>
</template>

<script>
    import {findRolePage, saveOrUpdate, deleteRole, exportRoles} from "../../api/roleApi";
    //引入子组件
    import AddRole from  "../role/AddRole";
    import AuthRole from "../role/AuthRole";

    export default {
      name: "Role",
      components: {
        AddRole,
        AuthRole
      },
      methods: {
        handleSizeChange(val) {
          this.pageSize = val;
          this.getRolePage(this.currentPage, this.pageSize, this.roleName);
        },
        handleCurrentChange(val) {
          this.currentPage = val;
          this.getRolePage(this.currentPage, this.pageSize, this.roleName);
        },
        //授权弹出框
        authorizationRole(id){
          this.id = id;
          this.authVisible = true;
        },
        // 监听 子组件弹窗关闭后触发，有子组件调用
        authRoleShow(data){
          if(data === 'false'){
            this.authVisible = false;
          }else{
            this.authVisible = true;
          }
        },
        // 按钮点击事件 显示新增编辑弹窗组件
        show(id){
          this.id = id;
          this.addOrUpdateVisible = true;
        },
        // 监听 子组件弹窗关闭后触发，有子组件调用
        showAddOrUpdate(data){
          if(data === 'false'){
            this.addOrUpdateVisible = false;
          }else{
            this.addOrUpdateVisible = true;
          }
        },
        //启用禁用
        async changeStatus(row){
          let tbRole = {
            id: row.id,
            status: row.status
          };
          const {data} = await saveOrUpdate(tbRole);
          if(data.status){
            this.$message.success(data.message);
          }else{
            this.$message.error(data.message);
          }
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
        },
        //删除角色
        del(row, index) {
          this.$confirm('此操作将永久删除角色：' + row.roleName + ', 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteRole(row.id, index);
          }).catch(() => {
            this.$message.info('已取消删除');
          });
        },
        //删除用户方法
        async deleteRole(id, index){
          const {data} = await deleteRole(id);
          if(data.status){
            this.$message.success(data.message);
            // this.$notify.success({
            //   title: "操作成功",
            //   message: data.message
            // });
            this.roleList.splice(index, 1);//删除v-model roleList 的某列
          }else{
            this.$message.error(data.message);
          }
        },
        //导出excel
        async exportExcel(){
          this.$message.warning('导出中...');
          await exportRoles();
        },
      },
      data(){
        return {
          roleName: '',
          currentPage: 1,
          pageSize: 10,
          total: 0,
          roleList: [],
          addOrUpdateVisible: false,
          authVisible: false,
          id: -1,
        }
      },
      created() {
        this.getRolePage(this.currentPage, this.pageSize, this.roleName);
      },
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
