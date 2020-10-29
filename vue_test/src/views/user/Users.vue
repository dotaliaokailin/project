<template>
    <div class="users-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card class="users-box-card">
        <!-- 表单 -->
        <el-form :inline="true" :model="userVo" class="users-form-inline" label-width="80px">
          <el-form-item label="部门">
            <el-select v-model="userVo.departmentId" placeholder="请选择部门">
              <el-option
                v-for="item in departments"
                :key="item.id"
                :label="item.name"
                :value="item.id">
                <span style="float: left">{{ item.name }}</span>
                <span style="float: right; color: #b5b5b5; font-size: 13px">
                  <el-tag type="success" size="mini" effect="plain">
                    {{ item.deptCount }}
                  </el-tag>
                </span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户名" >
            <el-input v-model="userVo.username" placeholder="请输入用户名" clearable></el-input>
          </el-form-item>
          <el-form-item label="昵称" >
            <el-input v-model="userVo.nickname" placeholder="请输入昵称" clearable></el-input>
          </el-form-item>
          <el-form-item label="邮箱" >
            <el-input v-model="userVo.email" placeholder="请输入邮箱" clearable></el-input>
          </el-form-item>
          <el-form-item label="性别" >
            <el-radio-group v-model="userVo.sex">
              <el-radio  label="0">男</el-radio>
              <el-radio  label="1">女</el-radio>
              <el-radio  label="">全部</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item  style="padding-left: 40px">
            <el-button icon="el-icon-refresh" @click="resetUserVo">重置</el-button>
            <el-button type="primary" icon="el-icon-search" @click="selectUserVo">查询</el-button>
            <el-button type="success" icon="el-icon-plus" @click="show('')">添加</el-button>
            <el-button type="info" icon="el-icon-download" @click="exportExcel">导出</el-button>
          </el-form-item>
        </el-form>
        <!-- table -->
        <el-table
          :data="userList"
          border
          height="630"
          style="width: 100%">
          <el-table-column
            prop="id"
            label="id"
            width="60">
          </el-table-column>
          <el-table-column
            sortable
            prop="username"
            label="姓名"
            width="180">
          </el-table-column>
          <el-table-column
            sortable
            prop="nickname"
            label="昵称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="sex"
            label="性别"
            width="50">
            <template slot-scope="scope">
              <el-tag type="success" size="mini">{{scope.row.sex == 0 ? '男' : scope.row.sex == 1 ? '女' : '保密'}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="phoneNumber"
            label="手机号"
            width="180">
          </el-table-column>
          <el-table-column
            sortable
            prop="deptName"
            label="部门"
            width="190">
          </el-table-column>
          <el-table-column
            sortable
            prop="birth"
            label="生日"
            width="180">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.birth }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            width="215">
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
            label="操作"
            width="300">
            <template slot-scope="scope"><!-- 通过作用域插槽获取scope row信息-->
            <el-button type="primary" size="small" icon="el-icon-edit" @click="show(scope.row.id)">修改</el-button>
            <el-button type="danger" size="small" icon="el-icon-delete" @click="del(scope.row, scope.$index)">删除</el-button>
            <el-button type="warning" size="small" icon="el-icon-setting" @click="showRole(scope.row.id,scope.row.username)">授权</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination style="padding-top: 20px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-card>
      <!-- 新增编辑弹框子组件 -->
      <add-or-update :addOrUpdateVisible="addOrUpdateVisible" @changeShow="showAddOrUpdate" :departments="departments" :title="title" :tbUser="tbUser"></add-or-update>
      <!-- 编辑用户角色弹框子组件 -->
      <user-role :userRoleVisible="userRoleVisible" @changeShow="showUserRole" :userRoleId="userRoleId" :userRoleName="userRoleName"></user-role>
    </div>
</template>

<script>
    import {findDeptAndCount} from "../../api/departmentApi";
    import {findUserPage, findUserById, deleteUser, exportUsers, saveOrUpdate, userPage} from '../../api/userApi';
    // 引入子组件
    import AddOrUpdate from '../user/AddUser';
    import UserRole from '../user/UserRole';
    export default {
      name: "Users",
      data() {
        return {
          userVo: {
            departmentId: '',
            username: '',
            nickname:'',
            email: '',
            sex: '',
          },
          currentPage: 1,
          total: 0,
          pageSize: 10,
          userList: [],
          departments: [],
          // 控制新增编辑弹窗的显示与隐藏
          addOrUpdateVisible: false,
          // 控制用户角色操作的显示与隐藏
          userRoleVisible: false,
          userRoleId: 0,
          userRoleName: '',
          title: '',
          tbUser:{
            id: '',
            username: '',
            nickname: '',
            email: '',
            phoneNumber: '',
            sex: '',
            avatar: '',
            birth: '',
            password: '',
            departmentId: '',
            status: ''
          }
        }
      },
      // 使用子组件
      components:{
        AddOrUpdate,
        UserRole,
      },
      methods: {
        //启用禁用
        async changeStatus(row){
          this.tbUser = row;
          const {data} = await saveOrUpdate(this.tbUser);
          if(data.status){
            this.$message.success(data.message);
            this.getUserPageCondition(this.currentPage,this.pageSize);
          }else{
            this.$message.error(data.message);
          }
        },
        handleSizeChange(val) {
          this.pageSize = val;
          //this.getUserPage();
          this.getUserPageCondition(this.currentPage,this.pageSize);
        },
        handleCurrentChange(val) {
          this.currentPage = val;
          //this.getUserPage();
          this.getUserPageCondition(this.currentPage,this.pageSize);
        },
        /*async getUserPage(){//异步ajax
          const {data}= await userPage(this.currentPage, this.pageSize);
          this.userList = data.data.userList;
          this.total = data.data.total;
        },*/
        async getDeptAndCount(){
          const {data} = await findDeptAndCount();
          this.departments = data.data.departments;
        },
        async getUserPageCondition(currentPage, pageSize){
          const {data} = await findUserPage(currentPage, pageSize, this.userVo);
          this.userList = data.data.userList;
          this.total = data.data.total;
        },
        //查询
        selectUserVo(){
          this.currentPage = 1;
          this.pageSize = 10;
          this.getUserPageCondition(this.currentPage,this.pageSize);
        },
        //重置表单
        resetUserVo() {
          this.userVo.departmentId = '';
          this.userVo.username = '';
          this.userVo.nickname = '';
          this.userVo.sex = '';
          this.userVo.email = '';
          this.currentPage = 1;
          this.pageSize = 10;
          this.getUserPageCondition(this.currentPage,this.pageSize);
        },
        //根据ID查询用户
        async findUserById(id){
          const {data} = await findUserById(id);
          if(data.status){
            this.tbUser = data.data.tbUser
          }
        },
        // 按钮点击事件 显示新增编辑弹窗组件
        show(id){
          this.title = id == '' ? '新增用户' : '修改用户';
          if(id != ''){
            this.findUserById(id);
          }else {
            this.tbUser.id = '';
            this.tbUser.username = '';
            this.tbUser.nickname = '';
            this.tbUser.email = '';
            this.tbUser.phoneNumber = '';
            this.tbUser.sex = '';
            this.tbUser.avatar = '';
            this.tbUser.birth = '';
            this.tbUser.password = '';
            this.tbUser.departmentId = '';
            this.tbUser.status = '';
          }
          this.addOrUpdateVisible = true;
        },
        //操作用户的角色
        showRole(id, username){
          this.userRoleVisible = true;
          this.userRoleId = id;
          this.userRoleName = '('+ username+')角色管理';
        },
        //删除用户方法
        async deleteUser(id, index){
          const {data} = await deleteUser(id);
          if(data.status){
            this.$message.success(data.message);
            // this.$notify.success({
            //   title: "操作成功",
            //   message: data.message
            // });
            this.userList.splice(index, 1);//删除v-model userList 的某列
            this.getDeptAndCount();
          }else{
            this.$message.error(data.message);
          }
        },
        //删除用户
        del(row, index) {
          this.$confirm('此操作将永久删除该用户：' + row.username + ', 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteUser(row.id, index);
          }).catch(() => {
            this.$message.info('已取消删除');
          });
        },
        //导出excel
        async exportExcel(){
          this.$message.warning('导出中...');
          await exportUsers();
        },
        // 监听 修改用户 子组件弹窗关闭后触发，有子组件调用
        showAddOrUpdate(data){
          if(data === 'false'){
            this.addOrUpdateVisible = false;
          }else{
            this.addOrUpdateVisible = true;
          }
        },
        // 监听 用户角色子组件弹窗关闭后触发，有子组件调用
        showUserRole(data){
          if(data === 'false'){
            this.userRoleVisible = false;
          }else{
            this.userRoleVisible = true;
          }
        }
      },
      created() {
        //this.getUserPage();
        this.getDeptAndCount();
        this.getUserPageCondition(this.currentPage,this.pageSize);
      }
    }
</script>

<style lang="less" scoped>
  //用户管理容器
  .users-container{
    height: 100%;
    width: 100%;
    padding: 0;
    margin: 0;
  }
</style>
