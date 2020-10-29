<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" :title="title" :visible.sync="showDialog" v-if="showDialog">
      <el-form ref="elForm" :model="dept" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="dept.name" placeholder="请输入部门名称" :maxlength="20" clearable show-word-limit
            :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="部门电话" prop="phone">
          <el-input v-model="dept.phone" placeholder="请输入部门电话" :maxlength="11" clearable show-word-limit
            :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="部门地址" prop="address">
          <el-input v-model="dept.address" type="textarea" placeholder="请输入部门地址" :maxlength="255"
            :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}" show-word-limit></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="onClose">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getDeptById, saveOrUpdate} from "../../api/departmentApi";
export default {
  name: 'AddDepartment',
  inheritAttrs: false,
  components: {},
  props: {
    addOrUpdateVisible:{
      type: Boolean,
      default: false
    },
    id:{
      type: Number,
      default: 0
    }
  },
  data() {
    //手机校验
    let checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[34578]\d{9}$$/;
      if (!value) {
        return callback(new Error("请输入部门电话"));
      }
      setTimeout(() => {
        // Number.isInteger是es6验证数字是否为整数的方法,实际输入的数字总是识别成字符串
        // 所以在前面加了一个+实现隐式转换
        if (!Number.isInteger(+value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (phoneReg.test(value)) {
            callback();
          } else {
            callback(new Error("电话号码格式不正确"));
          }
        }
      }, 100);
    };
    return {
      title: '',
      showDialog: false,
      dept: {
        id: 0,
        name: '',
        phone: '',
        address: '',
      },
      rules: {
        name: [],
        phone: [{
          required: true,
          validator: checkPhone,
          trigger: 'blur'
        }],
        address: [{
          required: true,
          message: '请输入部门地址',
          trigger: 'blur'
        }],
      },
    }
  },
  computed: {},
  watch: {
    // 监听 addOrUpdateVisible 改变
    addOrUpdateVisible(oldVal,newVal){
      this.showDialog = this.addOrUpdateVisible;
      //监听到 addOrUpdateVisible === True 和 id > 0 时查询用户
      if(this.addOrUpdateVisible && this.id > 0){
        this.findDeptById(this.id);
        this.title = "修改部门信息";
      }else{
        this.title = "新增部门信息";
      }
    },
  },
  created() {},
  mounted() {},
  methods: {
    onOpen() {
    },
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
    async saveOrUpdate(){
      const {data} = await saveOrUpdate(this.dept);
      if(data.status){
        this.$message.success(data.message);
      }else{
        this.$message.error(data.message);
      }
      if(data.status){
        this.dept = {
          id: 0,
          name: '',
          phone: '',
          address: '',
        };
        this.$emit('update:visible', false);
        // 子组件调用父组件方法，并传递参数
        this.$emit('changeShow','false');
        this.$parent.getDeptPage(this.$parent.currentPage,this.$parent.pageSize,this.$parent.roleName);
      }
    },
    //根据ID查询用户信息
    async findDeptById(id){
      const {data} = await getDeptById(id);
      if(data.status){
        this.dept.id = data.data.dept.id;
        this.dept.name = data.data.dept.name;
        this.dept.phone = data.data.dept.phone;
        this.dept.address = data.data.dept.address;
      }else{
        this.$message.error(data.message);
      }
    }
  }
}

</script>
<style>
</style>
