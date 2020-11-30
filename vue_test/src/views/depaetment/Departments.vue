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
          <el-button type="primary" style="margin-left: 10px" icon="el-icon-plus" @click="show(0)" v-has-permission="'department:add'">添加</el-button>
          <el-button type="info" style="margin-left: 10px" icon="el-icon-download" @click="exportExcel" v-has-permission="'department:export'">导出</el-button>
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
            width="140">
          </el-table-column>
          <el-table-column
            prop="name"
            label="部门名"
            width="200">
          </el-table-column>
          <el-table-column
            sortable
            prop="deptCount"
            label="部门人数"
            width="110">
            <template slot-scope="scope">
              <el-tag type="success" size="mini" effect="plain">
                {{scope.row.deptCount}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            sortable
            prop="createTime"
            label="创建时间"
            width="180">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">
                {{ scope.row.createTime }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            sortable
            prop="modifiedTime"
            label="修改时间"
            width="180">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">
                {{ scope.row.modifiedTime }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址"
            width="547">
          </el-table-column>
          <el-table-column
            label="操作"
            width="200">
            <template slot-scope="scope"><!-- 通过作用域插槽获取scope row信息-->
              <el-button size="small" type="primary" icon="el-icon-edit" @click="show(scope.row.id)" v-has-permission="'department:update'">编辑</el-button>
              <el-button size="small" type="danger" icon="el-icon-delete" @click="del(scope.row, scope.$index)" v-has-permission="'department:delete'">删除</el-button>
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
      <add-department :addOrUpdateVisible="addOrUpdateVisible" @changeShow="showAddOrUpdate"  :id="id"></add-department>
    </div>
</template>

<script>
    import AddDepartment from "../../views/depaetment/AddDepartment";
    import {getDeptPage, deleteById, exportDept} from "../../api/departmentApi";

    export default {
      name: "Departments",
      data(){
        return{
          addOrUpdateVisible: false,
          id: 0,
          name: "",
          currentPage: 1,
          pageSize: 10,
          total: 0,
          deptList: []
        }
      },
      components:{
        AddDepartment
      },
      created() {
        this.getDeptPage(this.currentPage, this.pageSize, this.name);
      },
      methods:{
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
        del(row, index){
          this.$confirm('此操作将永久删除该部门：' + row.name + ', 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteDept(row.id, index);
          }).catch(() => {
            this.$message.info('已取消删除');
          });
        },
        //删除部门信息
        async deleteDept(id, index){
          const {data} = await deleteById(id);
          if(data.status){
            this.$message.success(data.message);
            // this.$notify.success({
            //   title: "操作成功",
            //   message: data.message
            // });
            this.deptList.splice(index, 1);//删除v-model userList 的某列
          }else{
            this.$message.error(data.message);
          }
        },
        //导出excel
        async exportExcel(){
          this.$message.warning('导出中...');
          await exportDept();
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
