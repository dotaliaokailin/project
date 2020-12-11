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
          <el-select v-model="notices.userId" placeholder="请选择用户名" clearable filterable>
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
          <el-button icon="el-icon-refresh" @click="reset">重置</el-button>
          <el-button type="primary" icon="el-icon-search" @click="find">查询</el-button>
          <el-button type="success" icon="el-icon-plus" @click="dialogFormVisible = true">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog title="发布公告" :visible.sync="dialogFormVisible">
      <el-form :model="notices">
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="notices.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" :label-width="formLabelWidth">
          <div class="app-content">
            <div class="Tinymce_box">
              <tinymce v-model="notices.content" :height="500" />
              <div v-if="notices.content" class="editor-content">
                <h3>预览效果：</h3>
                <div v-html="notices.content" />
              </div>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="图片" :label-width="formLabelWidth">
          <el-input v-model="notices.title" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {findUsers} from "../../api/userApi";
  import Tinymce from "../../components/Tinymce";
  export default {
    name: "Notices",
    components: {
      Tinymce
    },
    data () {
      return {
        notices: {
          title: '',
          userId: '',
          content: ''
        },
        userList: [],
        dialogFormVisible: false,
        formLabelWidth: '120px',
      }
    },
    methods: {
      //查询所有用户
      async findUsers(){
        const {data} = await findUsers();
        this.userList = data.data.users;
      },
      //搜索按钮
      find(){
        console.log(this.notices);
      },
      //添加公告信息
      add(){

      },
      //重置按钮
      reset(){
        this.notices.title = '';
        this.notices.userId = '';
      },
      submit(){
        console.log(this.notices);
      }
    },
    created() {
      this.findUsers();
    }
  }
</script>

<style scoped lang="scss">
  .notices-container{
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;
  }
  .Tinymce_box {
    display: flex;
  }
  .editor-content {
    margin-left: 30px;
    flex-grow: 1;
    border: 2px dashed #f1f1f1;
    padding:0 20px;
  }
  h3 {
    color: #808080;
  }
</style>
