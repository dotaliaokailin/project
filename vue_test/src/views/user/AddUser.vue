<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" :title="title" :visible.sync="showDialog" v-if="showDialog">
      <el-row :gutter="15">
        <el-form ref="elForm" :model="tbUser" :rules="rules" size="medium" label-width="100px">
          <el-col :span="24">
            <!-- 用户头像 -->
            <el-form-item label="用户头像">
              <!-- 头衔缩略图 -->
              <pan-thumb :image="image"/>
              <!-- 文件上传按钮 -->
              <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
              </el-button>
              <!--
                v-show：是否显示上传组件
                :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                :url：后台上传的url地址
                @close：关闭上传组件
                @crop-upload-success：上传成功后的回调 -->
              <image-cropper
                v-show="imagecropperShow"
                :width="300"
                :height="300"
                :key="imagecropperKey"
                :url="'/ossservice/upload/uploadImgFile'"
                field="file"
                @close="closeImage"
                @crop-upload-success="cropSuccess"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="tbUser.username" placeholder="请输入用户名" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门" prop="departmentId">
              <el-select v-model="tbUser.departmentId" placeholder="请选择部门" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in departments" :key="index" :label="item.name"
                           :value="item.id" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="tbUser.nickname" placeholder="请输入昵称" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="tbUser.password" placeholder="请输入密码" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="tbUser.sex" size="medium">
                <el-radio v-for="(item, index) in sexOptions" :key="index" :label="item.value"
                  :disabled="item.disabled">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="tbUser.email" placeholder="请输入邮箱" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phoneNumber">
              <el-input v-model="tbUser.phoneNumber" placeholder="请输入手机号" clearable
                :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生日" prop="birth">
              <el-date-picker v-model="tbUser.birth" format="yyyy年MM月dd日" value-format="yyyy年MM月dd日"
                :style="{width: '100%'}" placeholder="请选择生日" clearable></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="onClose">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import ImageCropper from '../../components/ImageCropper';
  import PanThumb from '../../components/PanThumb';
  import {saveOrUpdate} from '../../api/userApi'
export default {
  name: 'AddUser',
  inheritAttrs: false,
  components: {
    ImageCropper,
    PanThumb
  },
  // 接受父组件传递的值
  props:{
    addOrUpdateVisible:{
      type: Boolean,
      default: false
    },
    departments:{
      type: Array,
      default: []
    },
    title:{
      type: String,
      default: ''
    },
    tbUser: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      // 控制弹出框显示隐藏
      showDialog:false,
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
      image: 'https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191',
      rules: {
        avatar:[{
          required: true,
          message: '请选择头像',
          trigger: 'blur'
        }],
        username: [{
          required: true,
          message: '请输入用户名',
          trigger: 'blur'
        }],
        departmentId: [{
          required: true,
          message: '请输入部门',
          trigger: 'blur'
        }],
        nickname: [{
          required: true,
          message: '请输入昵称',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }],
        sex: [{
          required: true,
          message: '性别不能为空',
          trigger: 'change'
        }],
        email: [{
          required: true,
          message: '请输入邮箱',
          trigger: 'blur'
        }],
        phoneNumber: [{
          required: true,
          message: '请输入手机号',
          trigger: 'blur'
        }],
        birth: [{
          required: true,
          message: '请选择生日',
          trigger: 'change'
        }],
      },
      avatarAction: 'https://jsonplaceholder.typicode.com/posts/',
      avatarList: [],
      sexOptions: [{
        "label": "男",
        "value": 0
      }, {
        "label": "女",
        "value": 1
      }],
    }
  },
  computed: {},
  watch:{
    // 监听 addOrUpdateVisible 改变
    addOrUpdateVisible(oldVal,newVal){
      this.showDialog = this.addOrUpdateVisible
    },
  },
  created() {},
  mounted() {},
  methods: {
    onOpen() {},
    onClose() {
      this.$refs['elForm'].resetFields();
      // 子组件调用父组件方法，并传递参数
      this.$emit('changeShow','false');
    },
    handelConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.saveOrUpdate();
      })
    },
    //新增修改
    async saveOrUpdate() {
      const {data} = await saveOrUpdate(this.tbUser);
      this.common.message(data.message, data.status ? this.common.messageType.success : this.common.messageType.error, this);
      if(data.status){
        this.$emit('update:visible', false);
        // 子组件调用父组件方法，并传递参数
        this.$emit('changeShow','false');
        this.$parent.getUserPageCondition(this.$parent.currentPage,this.$parent.pageSize);
      }
    },
    // 上传成功后的回调函数
    cropSuccess(data) {
      this.imagecropperShow = false
      this.image = data.url;
      // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 关闭上传组件
    closeImage() {
      this.imagecropperShow = false
      // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    }
  }
}

</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
