<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" :title="title" :visible.sync="showDialog" v-if="showDialog">
      <el-form ref="elForm" :model="role" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="角色名" prop="roleName">
          <el-input v-model="role.roleName" placeholder="请输入角色名" :maxlength="20" show-word-limit clearable
            :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="role.remark" type="textarea" placeholder="请输入备注"
            :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}" :maxlength="255" show-word-limit></el-input>
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
  import {findRoleById, saveOrUpdate} from "../../api/roleApi";
  export default {
    name: 'AddRole',
    inheritAttrs: false,
    components: {},
    props: {
      addOrUpdateVisible:{
        type: Boolean,
        default: false
      },
      id:{
        type: Number,
        default: -1
      }
    },
    data() {
      return {
        // 控制弹出框显示隐藏
        showDialog:false,
        title: '',
        role: {
          id: -1,
          roleName: "",
          remark: "",
        },
        rules: {
          roleName: [{
            required: true,
            message: '请输入角色名',
            trigger: 'blur'
          }],
          remark: [{
            required: true,
            message: '请输入备注',
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
        if(this.addOrUpdateVisible && this.id >= 0){
          this.getRoleById(this.id);
          this.title = "修改角色信息";
        }else{
          this.title = "新增角色信息";
        }
      },
    },
    created() {
    },
    mounted() {
    },
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
          if (!valid) return;
          this.saveOrUpdate();
        })
      },
      //新增修改
      async saveOrUpdate(){
        const {data} = await saveOrUpdate(this.role);
        if(data.status){
            this.$message.success(data.message);
        }else{
          this.$message.error(data.message);
        }
        if(data.status){
          this.role = {
            id: -1,
            roleName: "",
            remark: "",
          };
          this.$emit('update:visible', false);
          // 子组件调用父组件方法，并传递参数
          this.$emit('changeShow','false');
          this.$parent.getRolePage(this.$parent.currentPage,this.$parent.pageSize,this.$parent.roleName);
        }
      },
      //根据ID查询用户信息
      async getRoleById(id){
        const {data} = await findRoleById(id);
        if(data.status){
          this.role.id = data.data.role.id;
          this.role.remark = data.data.role.remark;
          this.role.roleName = data.data.role.roleName;
        }
      },
    }
  }

</script>
<style>
</style>
